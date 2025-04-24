let currentOrg = null;
let isCurrentUserEmployee = false;
const urlParams = new URLSearchParams(window.location.search);
const orgId = urlParams.get('id');

if (!orgId) {
    alert('ID организации не указан');
    window.location.href = 'index.html';
}

document.addEventListener('DOMContentLoaded', async function() {
    await loadOrganization();
    await loadOrganizationVacancies();
    setupEventListeners();
});

async function loadOrganization() {
    try {
        const response = await fetch(`http://localhost:8080/organisations/${orgId}`, {
    headers: {
        'Authorization': `Bearer ${localStorage.getItem('Token')}`
    }
});

if (!response.ok) throw new Error('Ошибка загрузки организации');
currentOrg = await response.json();
displayOrganization(currentOrg);

const currentUser = JSON.parse(localStorage.getItem('CurrentUser'));
if (currentUser) {
    const employeeCheck = await fetch(`http://localhost:8080/organisations/${orgId}/employee/${currentUser.id}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('Token')}`
        }
    });
    isCurrentUserEmployee = employeeCheck.ok;
}
} catch (error) {
    console.error('Ошибка:', error);
    alert('Не удалось загрузить данные организации');
}
}

function displayOrganization(org) {
    document.getElementById('orgTitle').textContent = org.title || 'Название не указано';
    document.getElementById('orgDescription').textContent = org.description || 'Описание отсутствует';
    document.getElementById('orgContacts').textContent = `Контакты: ${org.contacts || 'не указаны'}`;
    document.getElementById('orgTitleInput').value = org.title || '';
    document.getElementById('orgDescriptionInput').value = org.description || '';
    document.getElementById('orgContactsInput').value = org.contacts || '';
}

async function loadOrganizationVacancies() {
    try {
        const response = await fetch(`http://localhost:8080/vacancies/all?organisation=${orgId}&perPage=100`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('Token')}`
            }
        });
        if (!response.ok) throw new Error('Ошибка загрузки вакансий');
        displayVacancies(await response.json());
    } catch (error) {
        console.error('Ошибка:', error);
        alert('Не удалось загрузить вакансии организации');
    }
}

function displayVacancies(vacancies) {
    const container = document.getElementById('orgVacancies');
    container.innerHTML = '';
    if (!vacancies || vacancies.length === 0) {
        container.innerHTML = '<div class="alert alert-info">У этой организации пока нет вакансий</div>';
        return;
    }

    vacancies.forEach(vacancy => {
        const card = document.createElement('div');
        card.className = 'vacancy-card mb-3 p-3 border rounded';
        const title = vacancy.title || 'Название не указано';
        const description = vacancy.description || 'Описание отсутствует';
        const type = getVacancyType(vacancy.type) || 'Тип не указан';

        let skillsHtml = '';
        if (vacancy.skills && Object.keys(vacancy.skills).length > 0) {
            skillsHtml = `<p><strong>Навыки:</strong> ${Object.values(vacancy.skills).join(', ')}</p>`;
        }

        let testTaskBtn = '';
        if (vacancy.testTask?.fileName) {
            testTaskBtn = `<button class="btn btn-sm btn-outline-secondary download-test-task mt-2" data-filename="${vacancy.testTask.fileName}"><i class="bi bi-download"></i> Скачать тестовое задание</button>`;
        }

        card.innerHTML = `<h3>${title}</h3><p>${description}</p><p><strong>Тип:</strong> ${type}</p>${skillsHtml}${testTaskBtn}${isCurrentUserEmployee ? `<div class="mt-3"><button class="btn btn-info me-2 btn-view-responses" data-vacancy-id="${vacancy.id || ''}"><i class="bi bi-people-fill"></i> Просмотреть отклики</button><button class="btn btn-danger me-2 btn-delete-vacancy" data-vacancy-id="${vacancy.id || ''}"><i class="bi bi-trash"></i> Удалить</button></div>` : ''}`;
        container.appendChild(card);
    });

    document.querySelectorAll('.btn-delete-vacancy').forEach(btn => {
        btn.addEventListener('click', async function() {
            if (confirm('Вы уверены, что хотите удалить эту вакансию?')) {
                await deleteVacancy(this.getAttribute('data-vacancy-id'));
            }
        });
    });

    document.querySelectorAll('.btn-view-responses').forEach(btn => {
        btn.addEventListener('click', async function() {
            const vacancyId = this.getAttribute('data-vacancy-id');
            await loadResponses(vacancyId);
            new bootstrap.Modal(document.getElementById('responsesModal')).show();
        });
    });

    document.querySelectorAll('.download-test-task').forEach(btn => {
        btn.addEventListener('click', () => downloadFile(btn.getAttribute('data-filename')));
    });
}

async function deleteVacancy(vacancyId) {
    try {
        const response = await fetch(`http://localhost:8080/vacancies/${vacancyId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('Token')}`
            }
        });
        if (!response.ok) throw new Error('Ошибка удаления вакансии');
        alert('Вакансия успешно удалена');
        await loadOrganizationVacancies();
    } catch (error) {
        console.error('Ошибка:', error);
        alert('Не удалось удалить вакансию');
    }
}

async function loadResponses(vacancyId) {
    try {
        const response = await fetch(`http://localhost:8080/vacancies/${vacancyId}/responses`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('Token')}`
            }
        });
        if (!response.ok) throw new Error('Ошибка загрузки откликов');
        displayResponses(await response.json());
    } catch (error) {
        console.error('Ошибка:', error);
        alert('Не удалось загрузить отклики');
    }
}

function displayResponses(responses) {
    const container = document.getElementById('responsesContainer');
    container.innerHTML = '';
    if (!responses || responses.length === 0) {
        container.innerHTML = '<div class="alert alert-info">Нет откликов на эту вакансию</div>';
        return;
    }

    responses.forEach(response => {
        const card = document.createElement('div');
        card.className = `response-card mb-3 p-3 border rounded status-${response.responseState}`;
        card.setAttribute('data-response-id', response.id);
        card.setAttribute('data-status', response.responseState);

        const authorName = response.author?.firstName || 'Не указано';
        const authorEmail = response.author?.email || 'Не указано';
        const answer = response.answer || 'Текст отклика отсутствует';
        const createdAt = response.createdAt ? new Date(response.createdAt).toLocaleDateString() : 'Дата не указана';

        let resumeButton = '';
        if (response.resume) {
            resumeButton = `<div class="mt-2"><button class="btn btn-sm btn-primary download-resume" data-filename="${response.resume.fileName}"><i class="bi bi-download"></i> Скачать резюме</button></div>`;
        }

        const statusOptions = ['WAITING', 'APPROVED', 'REJECTED'];
        const statusSelect = `<div class="mt-3"><label class="form-label">Статус отклика:</label><div class="d-flex align-items-center gap-3"><span class="status-badge badge bg-${getStatusColor(response.responseState)}">${getStatusName(response.responseState)}</span><select class="form-select response-status" data-response-id="${response.id}">${statusOptions.map(option => `<option value="${option}" ${response.responseState === option ? 'selected' : ''}>${getStatusName(option)}</option>`).join('')}</select></div></div>`;

        card.innerHTML = `<div class="d-flex justify-content-between align-items-start"><h5>Отклик #${response.id || ''}</h5><small class="text-muted">${createdAt}</small></div><p><strong>От:</strong> ${authorName} (${authorEmail})</p><p><strong>Ответ:</strong> ${answer}</p>${resumeButton}${statusSelect}`;
        container.appendChild(card);
    });

    document.querySelectorAll('.download-resume').forEach(btn => {
        btn.addEventListener('click', () => downloadFile(btn.getAttribute('data-filename')));
    });

    document.querySelectorAll('.response-status').forEach(select => {
        select.addEventListener('change', async function() {
            const responseId = this.getAttribute('data-response-id');
            const newStatus = this.value;
            const success = await updateResponseStatus(responseId, newStatus);
            if (success) {
                const currentFilter = document.getElementById('responseStatusFilter').value;
                if (currentFilter !== 'ALL' && currentFilter !== newStatus) {
                    this.closest('.response-card').style.display = 'none';
                }
            } else {
                this.value = this._previousValue;
            }
        });
        select._previousValue = select.value;
    });

    document.getElementById('responseStatusFilter').addEventListener('change', function() {
        const status = this.value;
        document.querySelectorAll('.response-card').forEach(card => {
            card.style.display = (status === 'ALL' || card.getAttribute('data-status') === status) ? '' : 'none';
        });
    });
}

async function updateResponseStatus(responseId, newStatus) {
    try {
        const endpoint = newStatus === 'APPROVED' ? `approve` : `reject`;
        const response = await fetch(`http://localhost:8080/vacancies/${responseId}/${endpoint}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('Token')}`
            }
        });
        if (!response.ok) throw new Error('Ошибка обновления статуса');
        const updatedResponse = await response.json();

        const card = document.querySelector(`.response-card[data-response-id="${responseId}"]`);
        if (card) {
            card.classList.remove('status-WAITING', 'status-APPROVED', 'status-REJECTED');
            card.classList.add(`status-${updatedResponse.state}`);
            card.setAttribute('data-status', updatedResponse.state);

            const statusBadge = card.querySelector('.status-badge');
            if (statusBadge) {
                statusBadge.textContent = getStatusName(updatedResponse.state);
                statusBadge.className = `status-badge badge bg-${getStatusColor(updatedResponse.state)}`;
            }
        }
        return true;
    } catch (error) {
        console.error('Ошибка:', error);
        alert('Не удалось обновить статус отклика');
        return false;
    }
}

function getStatusName(status) {
    const statusNames = {
        'WAITING': 'Ожидает рассмотрения',
        'APPROVED': 'Одобрен',
        'REJECTED': 'Отклонен'
    };
    return statusNames[status] || status;
}

function getStatusColor(status) {
    const colors = {
        'WAITING': 'warning',
        'APPROVED': 'success',
        'REJECTED': 'danger'
    };
    return colors[status] || 'secondary';
}

function getVacancyType(type) {
    const types = {
        'FULL_TIME': 'Полная занятость',
        'PART_TIME': 'Частичная занятость',
        'INTERNSHIP': 'Стажировка'
    };
    return types[type] || type;
}

async function downloadFile(filename) {
    try {
        const response = await fetch(`http://localhost:8080/files/download?filename=${filename}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('Token')}`
            }
        });
        if (!response.ok) throw new Error('Файл не найден');
        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
    } catch (error) {
        console.error('Ошибка при скачивании файла:', error);
        alert('Не удалось скачать файл: ' + error.message);
    }
}

function setupEventListeners() {
    const editOrgModal = new bootstrap.Modal(document.getElementById('editOrgModal'));
    const addVacancyModal = new bootstrap.Modal(document.getElementById('addVacancyModal'));

    document.getElementById('editOrgBtn')?.addEventListener('click', () => editOrgModal.show());
    document.getElementById('addVacancyBtn')?.addEventListener('click', () => addVacancyModal.show());

    document.getElementById('saveOrgChanges')?.addEventListener('click', async () => {
        try {
            const orgData = {
                id: orgId,
                title: document.getElementById('orgTitleInput').value,
                description: document.getElementById('orgDescriptionInput').value,
                contacts: document.getElementById('orgContactsInput').value
            };
            const response = await fetch(`http://localhost:8080/organisations`, {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('Token')}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(orgData)
            });
            if (!response.ok) throw new Error('Ошибка сохранения изменений');
            currentOrg = await response.json();
            displayOrganization(currentOrg);
            editOrgModal.hide();
            alert('Изменения сохранены');
        } catch (error) {
            console.error('Ошибка:', error);
            alert(`Не удалось сохранить изменения: ${error.message}`);
        }
    });

    document.getElementById('saveVacancyBtn')?.addEventListener('click', async () => {
        try {
            const formData = new FormData();
            formData.append('title', document.getElementById('vacancyTitle').value);
            formData.append('description', document.getElementById('vacancyDescription').value);
            formData.append('type', document.getElementById('vacancyType').value);
            formData.append('organisation', orgId);
            const skills = document.getElementById('vacancySkills').value;
            if (skills) formData.append('skills', skills);
            const testTaskFile = document.getElementById('vacancyTestTask').files[0];
            if (testTaskFile) formData.append('file', testTaskFile);

            const response = await fetch(`http://localhost:8080/vacancies`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('Token')}`
                },
                body: formData
            });
            if (!response.ok) throw new Error('Ошибка создания вакансии');
            addVacancyModal.hide();
            document.getElementById('addVacancyForm').reset();
            alert('Вакансия успешно создана');
            await loadOrganizationVacancies();
        } catch (error) {
            console.error('Ошибка:', error);
            alert(`Не удалось создать вакансию: ${error.message}`);
        }
    });

    document.getElementById('responseStatusFilter').innerHTML = `
        <option value="ALL">Все отклики</option>
        <option value="WAITING">Ожидают рассмотрения</option>
        <option value="APPROVED">Одобренные</option>
        <option value="REJECTED">Отклоненные</option>
    `;
}
