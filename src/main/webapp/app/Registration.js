// document.getElementById('form').addEventListener('submit', function(event) {
//     event.preventDefault(); // Остановка стандартного поведения формы
//
//     // Получаем выбранные языки
//     const selectedLanguages = Array.from(document.getElementById("languageInput").selectedOptions).map(option => option.value);
//     const languagesString = selectedLanguages.join(", "); // Объединяем в строку
//     console.log(selectedLanguages)
//
//     const volunteerData = {
//         firstName: document.getElementById("firstNameInput").value,
//         lastName: document.getElementById("lastNameInput").value,
//         secondName: document.getElementById("secondNameInput").value,
//         mail: document.getElementById("mailInput").value,
//         passwords: document.getElementById("passwordInput").value,
//         birthdate: document.getElementById("birthDateInput").value,
//         address: document.getElementById("addressInput").value,
//         phone: document.getElementById("phoneInput").value,
//         language: languagesString, // Используйте строку языков
//         volunteerInfo: document.getElementById("informationAboutPersonInput").value
//     };
//
//     // Отправка данных на сервер
//     fetch('/vol/register', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify(volunteerData)
//     })
//         .then(response => {
//             return response.text().then(text => {
//                 if (!response.ok) {
//                     throw new Error(text); // Бросаем ошибку, если ответ не ок
//                 }
//                 return text; // Возвращаем текст, если все хорошо
//             });
//         })
//         .then(data => {
//             // Обработка успешного ответа
//             document.getElementById("result").textContent = data;
//         })
//         .catch((error) => {
//             // Обработка ошибок
//             document.getElementById("result").textContent = error.message; // Выводим ошибку
//             console.error('There was a problem with the fetch operation:', error);
//         });
// });
//
// //////////////
//
// document.addEventListener('DOMContentLoaded', () => {
//     const resultDiv = document.getElementById('languageInput'); // Изменено для использования select
//
//     // Функция для получения языков
//     const fetchLanguages = async () => {
//         resultDiv.innerHTML = ''; // Сбросить результаты
//
//         try {
//             const response = await fetch('/get-guide/languages');
//             const languages = await response.json();
//
//             languages.forEach(language => {
//                 const optionElement = document.createElement('option'); // Создаем элемент option
//                 optionElement.value = language.languageCode; // Можно использовать код языка в качестве значения
//                 optionElement.innerText = language.languageName;
//                 resultDiv.appendChild(optionElement); // Добавляем элемент в select
//             });
//
//             // Инициализация selectpicker после добавления опций
//             $('.selectpicker').selectpicker('refresh');
//         } catch (error) {
//             console.error('Ошибка при получении языков:', error);
//         }
//     };
//
//     // Вызов функции при загрузке страницы
//     fetchLanguages();
// });
// document.getElementById('form').addEventListener('submit', function(event) {
//     event.preventDefault(); // Остановка стандартного поведения формы
//
//     // Получаем выбранные языки
//     const selectedLanguages = Array.from(document.getElementById("languageInput").selectedOptions).map(option => option.value);
//     console.log("Выбранные языки:", selectedLanguages); // Логируем выбранные языки
//
//     const languagesString = selectedLanguages.join(", "); // Объединяем в строку
//     console.log("Строка языков для отправки:", languagesString); // Логируем строку языков
//
//     const volunteerData = {
//         firstName: document.getElementById("firstNameInput").value,
//         lastName: document.getElementById("lastNameInput").value,
//         secondName: document.getElementById("secondNameInput").value,
//         mail: document.getElementById("mailInput").value,
//         passwords: document.getElementById("passwordInput").value,
//         birthdate: document.getElementById("birthDateInput").value,
//         address: document.getElementById("addressInput").value,
//         phone: document.getElementById("phoneInput").value,
//         language: languagesString, // Передаем строку языков
//         volunteerInfo: document.getElementById("informationAboutPersonInput").value
//     };
//
//     console.log("Данные для отправки:", volunteerData); // Логируем все данные перед отправкой
//
//     // Отправка данных на сервер
//     fetch('/vol/register', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify(volunteerData)
//     })
//         .then(response => {
//             return response.text().then(text => {
//                 if (!response.ok) {
//                     throw new Error(text); // Бросаем ошибку, если ответ не ок
//                 }
//                 return text; // Возвращаем текст, если все хорошо
//             });
//         })
//         .then(data => {
//             // Обработка успешного ответа
//             document.getElementById("result").textContent = data;
//         })
//         .catch((error) => {
//             // Обработка ошибок
//             document.getElementById("result").textContent = error.message; // Выводим ошибку
//             console.error('There was a problem with the fetch operation:', error);
//         });
// });
//
// document.addEventListener('DOMContentLoaded', () => {
//     const resultDiv = document.getElementById('languageInput');
//
//     // Функция для получения языков
//     const fetchLanguages = async () => {
//         resultDiv.innerHTML = ''; // Сбросить результаты
//
//         try {
//             const response = await fetch('/get-guide/languages');
//             const languages = await response.json();
//
//             languages.forEach(language => {
//                 const optionElement = document.createElement('option'); // Создаем элемент option
//                 optionElement.value = language.languageCode; // Код языка
//                 optionElement.innerText = language.languageName; // Название языка
//                 resultDiv.appendChild(optionElement); // Добавляем элемент в select
//             });
//
//             // Инициализация selectpicker после добавления опций
//             $('.selectpicker').selectpicker('refresh');
//         } catch (error) {
//             console.error('Ошибка при получении языков:', error);
//         }
//     };
//
//     // Вызов функции при загрузке страницы
//     fetchLanguages();
// });
document.addEventListener('DOMContentLoaded', () => {
    const resultDiv = document.getElementById('languageInput');

    // Функция для получения языков
    const fetchLanguages = async () => {
        resultDiv.innerHTML = ''; // Сбросить результаты

        try {
            const response = await fetch('/get-guide/languages');
            const languages = await response.json();

            languages.forEach(language => {
                const optionElement = document.createElement('option'); // Создаем элемент option
                optionElement.value = language.languageName; // Используйте name языка в качестве значения
                optionElement.innerText = language.languageName; // Название языка также
                resultDiv.appendChild(optionElement); // Добавляем элемент в select
                console.log(`Добавлен язык: ${optionElement.value} - ${optionElement.innerText}`); // Логируем добавленный язык
            });

            // Инициализация selectpicker после добавления опций
            $('.selectpicker').selectpicker('refresh');
        } catch (error) {
            console.error('Ошибка при получении языков:', error);
        }
    };

    // Вызов функции при загрузке страницы
    fetchLanguages();
});

// Обработчик отправки формы
// document.getElementById('form').addEventListener('submit', function(event) {
//     event.preventDefault(); // Остановка стандартного поведения формы
//
//     // Получаем выбранные языки
//     const selectedLanguages = Array.from(document.getElementById("languageInput").selectedOptions).map(option => option.value);
//     console.log("Выбранные языки:", selectedLanguages); // Логируем выбранные языки
//
//     const languagesString = selectedLanguages.join(", "); // Объединяем в строку
//     console.log("Строка языков для отправки:", languagesString); // Логируем строку языков
//
//     const volunteerData = {
//         firstName: document.getElementById("firstNameInput").value,
//         lastName: document.getElementById("lastNameInput").value,
//         secondName: document.getElementById("secondNameInput").value,
//         mail: document.getElementById("mailInput").value,
//         passwords: document.getElementById("passwordInput").value,
//         birthdate: document.getElementById("birthDateInput").value,
//         address: document.getElementById("addressInput").value,
//         phone: document.getElementById("phoneInput").value,
//         language: languagesString, // Передаем строку языков
//         volunteerInfo: document.getElementById("informationAboutPersonInput").value
//     };
//
//     console.log("Данные для отправки:", volunteerData); // Логируем все данные перед отправкой
//
//     // Отправка данных на сервер
//     fetch('/vol/register', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify(volunteerData)
//     })
//         .then(response => {
//             return response.text().then(text => {
//                 if (!response.ok) {
//                     throw new Error(text); // Бросаем ошибку, если ответ не ок
//                 }
//                 return text; // Возвращаем текст, если все хорошо
//             });
//         })
//         .then(data => {
//             // Обработка успешного ответа
//             document.getElementById("result").textContent = data;
//         })
//         .catch((error) => {
//             // Обработка ошибок
//             document.getElementById("result").textContent = error.message; // Выводим ошибку
//             console.error('There was a problem with the fetch operation:', error);
//         });
// });

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
            window.location.href = 'Authorization.html';
        })
        .catch((error) => {
            // Обработка ошибок
            document.getElementById("result").textContent = error.message; // Выводим ошибку
            console.error('There was a problem with the fetch operation:', error);
        });
});

