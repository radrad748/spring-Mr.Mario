document.addEventListener('DOMContentLoaded', function () {

    var form = document.getElementById('form-register');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        var firstName = form.querySelector('input[name="firstName"]').value;
        var lastName = form.querySelector('input[name="lastName"]').value;
        var email = form.querySelector('input[name="email"]').value;
        var password = form.querySelector('input[name="password"]').value;

        var validMessage = validateFormData(firstName, lastName, email, password);

        if (validMessage === "success") {
            sendData(firstName, lastName, email, password);
        } else {
            showErrorMessage(validMessage);
        }
    });
});


    function validateFormData(firstName, lastName, email, password) {
        if (firstName.length < 3 || firstName.length > 20) {
            return "Имя должно иметь длину от 3 символов до 20";
        }

        if (lastName.length < 3 || lastName.length > 20) {
            return "Фамилия должна иметь длину от 3 символов до 20";
        }

        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            return "не корректный email";
        }

        if (password.length < 6 || password.length > 50) {
            return "Пароль должен иметь длину от 6 до 50 символов ";
        }

        if (!/[0-9]/.test(password)) {
            return "Пароль должен содержать цифры";
        }

        if (!/[a-z]/.test(password) || !/[A-Z]/.test(password)) {
            return "Пароль должен содержать символы верхнего и нижнего регистра";
        }
        return "success";
    }

    function sendData(firstName, lastName, email, password) {
        var csrfToken = document.getElementById('csrf-id').value;

        var data = {
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: password
        };

        fetch('/register',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = "/success-registration";
                } else {
                    var errorMessage = "Введены некорректные данные";
                    showErrorMessage(errorMessage);
                }
            })
            .catch(error => {
            console.error('Error:', error);
        });
    }

    function showErrorMessage(validMessage) {
        var existingErrorMessage = document.getElementById("error-message");
        if (existingErrorMessage) {
            existingErrorMessage.parentNode.removeChild(existingErrorMessage);
        }

        var errorMessage = document.createElement("div");
        errorMessage.id = "error-message";
        errorMessage.textContent = validMessage;
        errorMessage.style.color = "red";
        document.querySelector(".container").appendChild(errorMessage);
    }


