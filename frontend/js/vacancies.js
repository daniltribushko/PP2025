async function displayVacancies(vacancies) {
    const applyModal = new bootstrap.Modal(document.getElementById("applyModal"));
    const vacanciesContainer = document.getElementById('vacancies');
    vacanciesContainer.innerHTML = '';

    for (const vacancy of vacancies) {
        const card = document.createElement('div');
        card.className = 'vacancy-card';
        const org = await getOrganisation(vacancy.organisation);
        let vacancyTxt = `
            <h3>${vacancy.title}</h3>
            <p>${vacancy.description}</p>
            <p>Тип: ${vacancy.type}</p>
            <p>Организация: ${org.title}</p>
        `;

        let hasResponse = false;
        if (vacancy.responses.length !== 0) {
            for (const response of vacancy.responses) {
                if (response.author.id === JSON.parse(localStorage.getItem("CurrentUser")).id) {
                    hasResponse = true;
                    break;
                }
            }
        }

        if (hasResponse) {
            vacancyTxt += `<button class="btn btn-danger btn-apply" data-vacancy-id="${vacancy.id}">Удалить отклик</button>`;
        } else {
            vacancyTxt += `<button class="btn btn-success btn-apply" data-vacancy-id="${vacancy.id}">Откликнуться</button>`;
        }

        if (vacancy.skills && Object.keys(vacancy.skills).length > 0) {
            vacancyTxt += `<p>Навыки: ${Object.values(vacancy.skills).join(', ')}</p>`;
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
                    <button class="btn btn-secondary mt-2">
                        <a href="http://localhost:8080/files/download?filename=${vacancy.testTask.fileName}">Получить тестовое задание</a>
                    </button>
                `;
            } else {
                vacancyTxt += `
                    <button class="btn btn-secondary mt-2" disabled>
                        Тестовое задание недоступно
                    </button>
                `;
            }
        } else {
            vacancyTxt += `
                <button class="btn btn-secondary mt-2" disabled>
                    Тестовое задание недоступно
                </button>
            `;
        }

        card.innerHTML = vacancyTxt;
        vacanciesContainer.appendChild(card);
    }

    const applyButtons = document.querySelectorAll(".btn-apply");
    applyButtons.forEach(button => {
        button.addEventListener("click", function () {
            applyModal.show();
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
        }
    ).then(response => {
            return response.json()
        }
    ).then(data => {
        return data
    })
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
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .catch(error => {
            console.error('Ошибка:', error);
            return [];
        });
}

function init() {
    const applyModalElement = document.getElementById("applyModal");
    const submitApplyButton = document.getElementById("submitApply");
    let currentVacancyId = null;

    const titleFilter = document.querySelector('#titleFilter');
    const typeFilter = document.querySelector('#typeFilter');
    const orgName = document.querySelector('#orgName');
    const pageFilter = document.querySelector('#pageFilter');
    const perPageFilter = document.querySelector('#perPageFilter');
    const submitBtn = document.querySelector('#filter-btn');

    submitApplyButton.addEventListener("click", async function () {
        const messageText = document.getElementById("messageText").value;
        const fileUpload = document.getElementById("fileUpload").files[0];

        if (!messageText || !fileUpload) {
            alert("Пожалуйста, заполните все поля.");
            return;
        }

        const formData = new FormData();
        formData.append("vacancyId", currentVacancyId);
        formData.append("message", messageText);
        formData.append("file", fileUpload);

        try {
            const applyModal = bootstrap.Modal.getInstance(applyModalElement);
            applyModal.hide();

            alert("Ваш отклик успешно отправлен!");
        } catch (error) {
            console.error("Ошибка:", error);
            alert("Произошла ошибка при отправке отклика.");
        }
    });

    applyModalElement.addEventListener("hidden.bs.modal", function () {
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

init();