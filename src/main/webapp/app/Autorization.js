document.getElementById('form').addEventListener('submit', function(event) {
    event.preventDefault(); // Останавливаем стандартное поведение формы

    // Считываем данные из полей ввода
    const mail = document.getElementById('mailInput').value;
    const passwords = document.getElementById('passwordInput').value;

    // Создаем объект с данными для отправки
    const loginRequest = {
        mail: mail,
        passwords: passwords
    };
    console.log("Строка языков для отправки:", loginRequest); // Логируем строку языков

    // Отправляем данные на сервер
    fetch('/vol/login', { // Убедитесь, что путь соответствует вашему контроллеру
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginRequest)
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            }
            else {
                throw new Error('Неверные учетные данные');
            }
        })
        .then(data => {
            console.log(data); // Выводим сообщение об успешной авторизации
            window.location.href = 'PersonalInfoVolunteer.html'; // Перенаправляем на страницу
        })
        .catch(error => {
            alert(error.message); // Выводим ошибку пользователю
            console.error('Ошибка:', error);
        });
});
