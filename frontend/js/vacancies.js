let currentVacancyId = null;

async function isUserWorker(orgId) {
    const currentUser = JSON.parse(localStorage.getItem("CurrentUser"));
    if (!currentUser) return false;

    try {
        const response = await fetch(`http://localhost:8080/organisations/${orgId}/employee/${currentUser.id}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("Token")}`
            }
        });
        localStorage.setItem("CurrentEmployee", JSON.stringify(await response.json()));
        return response.ok;
    } catch (error) {
        console.error('Ошибка при проверке роли:', error);
        return false;
    }
}

async function displayVacancies(vacancies) {
    const applyModal = new bootstrap.Modal(document.getElementById("applyModal"));
    const vacanciesContainer = document.getElementById('vacancies');
    vacanciesContainer.innerHTML = '';

    for (const vacancy of vacancies) {
        const isWorker = await isUserWorker(vacancy.organisation);
        const card = document.createElement('div');
        card.className = 'vacancy-card';
        const org = await getOrganisation(vacancy.organisation);
        let vacancyTxt = `
            <h3>${vacancy.title}</h3>
            <p>${vacancy.description}</p>
            <p>Тип: ${vacancy.type}</p>
            <p>Организация: ${org.title}</p>
            <div class="btn-group-vacancy">
        `;

        if (isWorker) {
            vacancyTxt += `<a href="organisation.html?id=${org.id}" class="btn btn-info">Перейти к организации</a>`;
        }

        const currentUser = JSON.parse(localStorage.getItem("CurrentUser"));
        let hasResponse = false;
        let responseId = null;

        if (currentUser && vacancy.responses.length !== 0) {
            for (const response of vacancy.responses) {
                if (response.author.id === currentUser.id) {
                    hasResponse = true;
                    responseId = response.id;
                    break;
                }
            }
        }

        if (hasResponse) {
            vacancyTxt += `<button class="btn btn-danger btn-delete" data-vacancy-id="${vacancy.id}" data-response-id="${responseId}">Удалить отклик</button>`;
        } else if (currentUser) {
            vacancyTxt += `<button class="btn btn-success btn-apply" data-vacancy-id="${vacancy.id}">Откликнуться</button>`;
        }

        if (vacancy.skills && Object.keys(vacancy.skills).length > 0) {
            vacancyTxt += `<div class="mt-2"><strong>Навыки:</strong> ${Object.values(vacancy.skills).join(', ')}</div>`;
        }

        if (vacancy.testTask) {
            const fileExist = await fetch(
                `http://localhost:8080/files/exists?filename=${vacancy.testTask.fileName}`,
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
            ).then(response => response.json())
                .then(data => data);

            if (fileExist) {
                vacancyTxt += `
                    <a href="http://localhost:8080/files/download?filename=${vacancy.testTask.fileName}" 
                       class="test-task-btn">
                        Получить тестовое задание
                    </a>
                `;
            } else {
                vacancyTxt += `
                    <button class="test-task-btn" disabled>
                        Тестовое задание недоступно
                    </button>
                `;
            }
        } else {
            vacancyTxt += `
                <button class="test-task-btn" disabled>
                    Тестовое задание недоступно
                </button>
            `;
        }

        vacancyTxt += `</div>`;
        card.innerHTML = vacancyTxt;
        vacanciesContainer.appendChild(card);
    }

    const applyButtons = document.querySelectorAll(".btn-apply");
    applyButtons.forEach(button => {
        button.addEventListener("click", function() {
            currentVacancyId = this.getAttribute("data-vacancy-id");
            applyModal.show();
        });
    });

    const deleteButtons = document.querySelectorAll(".btn-danger");
    deleteButtons.forEach(button => {
        button.addEventListener("click", async function() {
            const vacancyId = this.getAttribute("data-vacancy-id");
            const responseId = this.getAttribute("data-response-id");

            try {
                const response = await fetch(`http://localhost:8080/vacancies/${vacancyId}/responses/${responseId}/delete`, {
                    method: "PATCH",
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem("Token")}`
                    }
                });

                if (!response.ok) throw new Error("Ошибка при удалении отклика");

                const filteredVacancies = await filterVacancies(
                    titleFilter.value,
                    typeFilter.value,
                    orgName.value,
                    pageFilter.value,
                    perPageFilter.value
                );
                await displayVacancies(filteredVacancies);
                alert("Отклик успешно удален!");
            } catch (error) {
                console.error("Ошибка:", error);
                alert("Произошла ошибка при удалении отклика.");
            }
        });
    });
}

function getOrganisation(id) {
    return fetch(`http://localhost:8080/organisations/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("Token")}`
        }
    }).then(response => response.json())
        .then(data => data)
        .catch(error => {
            console.error('Ошибка при получении организации:', error);
            return null;
        });
}

function filterVacancies(title, type, orgName, page, perPage) {
    let url = `http://localhost:8080/vacancies/all?`;

    if (title) url += `title=${title}&`;
    if (type) url += `type=${type}&`;
    if (orgName) url += `orgName=${orgName}&`;
    if (page) url += `page=${page}&`;
    if (perPage) url += `perPage=${perPage}`;

    return fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("Token")}`
        }
    }).then(response => {
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        return response.json();
    }).catch(error => {
        console.error('Ошибка:', error);
        return [];
    });
}

function init() {
    const applyModalElement = document.getElementById("applyModal");
    const submitApplyButton = document.getElementById("submitApply");
    const titleFilter = document.querySelector('#titleFilter');
    const typeFilter = document.querySelector('#typeFilter');
    const orgName = document.querySelector('#orgName');
    const pageFilter = document.querySelector('#pageFilter');
    const perPageFilter = document.querySelector('#perPageFilter');
    const submitBtn = document.querySelector('#filter-btn');

    const loadInitialVacancies = async () => {
        try {
            const vacancies = await filterVacancies(
                titleFilter.value,
                typeFilter.value,
                orgName.value,
                pageFilter.value,
                perPageFilter.value
            );
            await displayVacancies(vacancies);
        } catch (error) {
            console.error("Ошибка при загрузке вакансий:", error);
            alert("Произошла ошибка при загрузке вакансий.");
        }
    };

    loadInitialVacancies();

    submitApplyButton.addEventListener("click", async function() {
        const messageText = document.getElementById("messageText").value;
        const fileUpload = document.getElementById("fileUpload").files[0];

        if (!messageText || !fileUpload) {
            alert("Пожалуйста, заполните все поля.");
            return;
        }

        if (!currentVacancyId) {
            alert("Не выбрана вакансия для отклика.");
            return;
        }

        const formData = new FormData();
        formData.append("answer", messageText);
        formData.append("file", fileUpload);

        try {
            const applyModal = bootstrap.Modal.getInstance(applyModalElement);
            applyModal.hide();

            const response = await fetch(`http://localhost:8080/vacancies/${currentVacancyId}/responses/add`, {
                method: "PATCH",
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("Token")}`
                },
                body: formData
            });

            if (!response.ok) throw new Error("Ошибка при отправке данных");

            const data = await response.json();
            console.log("Ответ сервера:", data);
            alert("Ваш отклик успешно отправлен!");

            const filteredVacancies = await filterVacancies(
                titleFilter.value,
                typeFilter.value,
                orgName.value,
                pageFilter.value,
                perPageFilter.value
            );
            await displayVacancies(filteredVacancies);
        } catch (error) {
            console.error("Ошибка:", error);
            alert("Произошла ошибка при отправке отклика.");
        }
    });

    applyModalElement.addEventListener("hidden.bs.modal", function() {
        document.body.classList.remove("modal-open");
        document.body.style.paddingRight = "";
        document.getElementById("applyForm").reset();
    });

    submitBtn.addEventListener('click', async () => {
        try {
            const filteredVacancies = await filterVacancies(
                titleFilter.value,
                typeFilter.value,
                orgName.value,
                pageFilter.value,
                perPageFilter.value
            );
            await displayVacancies(filteredVacancies);
        } catch (error) {
            console.error("Ошибка при фильтрации вакансий:", error);
            alert("Произошла ошибка при загрузке вакансий.");
        }
    });
}

document.addEventListener('DOMContentLoaded', function() {
    if (typeof bootstrap === 'undefined' || !bootstrap.Modal) {
        console.error("Bootstrap не загружен");
        return;
    }
    init().catch(error => console.error("Ошибка инициализации:", error));
});