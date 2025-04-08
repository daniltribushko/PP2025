document.querySelector("#registration-form").addEventListener("submit", event => {
    event.preventDefault();

    const email = document.querySelector("#email").value;
    const password = document.querySelector("#password").value;
    const confirmPassword = document.querySelector("#confirm-password").value;
    const lastName = document.querySelector("#last-name").value;
    const middleName = document.querySelector("#middle-name").value;
    const firstName = document.querySelector("#first-name").value;

    if (email.length < 7 || email.length > 50) {
        document.querySelector("#error-text").textContent = "Электронный адрес пользователя должен иметь длину от 7 до 50 символов";
        return;
    }

    if (password !== confirmPassword) {
        document.querySelector("#error-text").textContent = "Пароли не совпадают";
        return;
    }

    if (password.length < 6) {
        document.querySelector("#error-text").textContent = "Пароль должен содержать минимум 6 символов";
        return;
    }

    const signUpRequest = {
        "email": email,
        "password": password,
        "confirmPassword": confirmPassword,
        "lastName": lastName,
        "middleName": middleName,
        "firstName": firstName
    };

    const body = JSON.stringify(signUpRequest);

    const req = new XMLHttpRequest();
    req.onload = () => {
        if (req.status === 200) {
            const jwtToken = JSON.parse(req.responseText);
            localStorage.setItem("Token", jwtToken.token);
            window.location.href = "../html/vacancies.html";
        } else {
            if (req.status === 422) {
                let text = "";
                const response = JSON.parse(req.responseText).errors;
                Object.entries(response).forEach((key) => {
                    text += `${key}\n`;
                });
                document.querySelector("#error-text").textContent = text;
            } else {
                document.querySelector("#error-text").textContent = JSON.parse(req.responseText).message;
            }
        }
    };

    req.open("POST", "http://localhost:8080/auth/sign-up");
    req.setRequestHeader("Content-Type", "application/json");
    req.send(body);
});