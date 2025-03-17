document.querySelector("#login-form")
    .addEventListener(
        "submit",
        function (event) {
            event.preventDefault()

            const email = document.querySelector("#email").value

            if (email.length < 7 || email.length > 50) {
                document.querySelector("#error-text").textContent = "Электронный адрес пользователя, должен иметь длину от 7 до 50 символов"
            } else {

                const password = document.querySelector("#password").value

                const signInRequest = {
                    "email": email,
                    "password": password
                }

                const body = JSON.stringify(signInRequest)

                const req = new XMLHttpRequest()
                req.onload = () => {
                    if (req.status === 200) {
                        const jwtToken = JSON.parse(req.responseText)
                        localStorage.setItem("Token", jwtToken.token)
                        fetch("http://localhost:8080/auth?token=" + jwtToken.token, {
                            method: "GET",
                            headers: {
                                "Content-Type": "application/json"
                            }
                        })
                            .then(response => {
                                    if (response.status === 200) {
                                        return response.json()
                                    }
                                }
                            )
                            .then(data => {
                                console.log(JSON.stringify(data))
                                localStorage.setItem("CurrentUser", JSON.stringify(data))
                            })
                        window.location.href = "../html/vacancies.html"
                    } else {
                        if (req.status === 422) {
                            let text = ""
                            const response = JSON.parse(req.responseText).errors
                            Object.entries(response).forEach(
                                (key) => {
                                    text += `${key}\n`
                                }
                            )
                            document.querySelector("#error-text")
                                .textContent = text
                        } else {
                            document.querySelector("#error-text")
                                .textContent = JSON.parse(req.responseText).message
                        }
                    }
                }

                req.open("POST", "http://localhost:8080/auth/sign-in")
                req.setRequestHeader("Content-Type", "application/json")
                req.send(body)
            }
        }
    )

document.querySelector("#email")
    .addEventListener(
        "inout",
        (
            event => {
                document.querySelector("#error-text")
                    .textContent = ""
            }
        )
    )