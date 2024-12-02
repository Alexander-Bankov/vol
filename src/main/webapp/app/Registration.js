
function validateName(input) {
    // Регулярное выражение для проверки на наличие только букв, пробелов и дефисов
    const regex = /^[А-Яа-яЁёA-Za-z\s-]+$/;
    return regex.test(input.value);
}

// Находим все поля для ввода фамилии, имени и отчества
const lastNameInput = document.getElementById('lastNameInput');
const firstNameInput = document.getElementById('firstNameInput');
const secondNameInput = document.getElementById('secondNameInput');

// Функция для вывода сообщения об ошибке
function showError(input, message) {
    const errorDiv = document.getElementById(input.id.replace('Input', 'Error'));
    errorDiv.textContent = message;
    errorDiv.style.display = 'block'; // Показываем элемент
}

// Функция для снятия сообщения об ошибке
function clearError(input) {
    const errorDiv = document.getElementById(input.id.replace('Input', 'Error'));
    errorDiv.textContent = ''; // Очищаем текст ошибки
    errorDiv.style.display = 'none'; // Скрываем элемент
}
// Добавляем обработчики событий для каждого поля
lastNameInput.addEventListener('input', function() {
    if (!validateName(lastNameInput)) {
        showError(lastNameInput, 'Фамилия может содержать только буквы, пробелы и дефисы.');
        alert('Пожалуйста, используйте только буквы в фамилии!');
        lastNameInput.value = ''; // Очищаем поле ввода
    } else {
        clearError(lastNameInput);
    }
});

firstNameInput.addEventListener('input', function() {
    if (!validateName(firstNameInput)) {
        showError(firstNameInput, 'Имя может содержать только буквы, пробелы и дефисы.');
        alert('Пожалуйста, используйте только буквы в имени!');
        firstNameInput.value = ''; // Очищаем поле ввода
    } else {
        clearError(firstNameInput);
    }
});

secondNameInput.addEventListener('input', function() {
    if (!validateName(secondNameInput)) {
        showError(secondNameInput, 'Отчество может содержать только буквы, пробелы и дефисы.');
        alert('Пожалуйста, используйте только буквы в отчестве!');
        secondNameInput.value = ''; // Очищаем поле ввода
    } else {
        clearError(secondNameInput);
    }
});

// Обработчик отправки формы
document.getElementById('form').addEventListener('submit', function(event) {
    event.preventDefault(); // Остановка стандартного поведения формы

    // Получаем выбранные языки
    const selectedLanguages = Array.from(document.getElementById("languageInput").selectedOptions).map(option => option.value);
    console.log("Выбранные языки:", selectedLanguages); // Логируем выбранные языки

    const languagesString = selectedLanguages.join(", "); // Объединяем в строку
    console.log("Строка языков для отправки:", languagesString); // Логируем строку языков

    const volunteerData = {
        firstName: document.getElementById("firstNameInput").value,
        lastName: document.getElementById("lastNameInput").value,
        secondName: document.getElementById("secondNameInput").value,
        mail: document.getElementById("mailInput").value,
        passwords: document.getElementById("passwordInput").value,
        birthdate: document.getElementById("birthDateInput").value,
        address: document.getElementById("addressInput").value,
        phone: document.getElementById("phoneInput").value,
        language: languagesString, // Передаем строку языков
        volunteerInfo: document.getElementById("informationAboutPersonInput").value
    };

    console.log("Данные для отправки:", volunteerData); // Логируем все данные перед отправкой

    // Отправка данных на сервер
    fetch('/vol/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(volunteerData)
    })
        .then(response => {
            return response.text().then(text => {
                if (!response.ok) {
                    throw new Error(text); // Бросаем ошибку, если ответ не ок
                }
                return text; // Возвращаем текст, если все хорошо
            });
        })
        .then(data => {
            // Обработка успешного ответа
            document.getElementById("result").textContent = data;
            // Перенаправление на страницу авторизации
            window.location.href = 'Autorization.html';
        })
        .catch((error) => {
            // Обработка ошибок
            document.getElementById("result").textContent = error.message; // Выводим ошибку
            console.error('There was a problem with the fetch operation:', error);
        });
});

