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
    fetch('/vol/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginRequest)
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // Измените на json() для получения объектов
            } else {
                throw new Error('Неверные учетные данные');
            }
        })
        .then(data => {
            console.log(data); // Логируем весь ответ
            console.log(data.message); // Выводим сообщение об успешной авторизации
            if (data.role === 'ADMIN') {
                window.location.href = 'AdministratorInfo.html'; // Перенаправляем на страницу администратора
            } else if (data.role === 'VOLUNTEER') {
                window.location.href = 'PersonalInfoVolunteer.html'; // Перенаправляем на страницу волонтера
            } else if (data.role === 'UNKNOWN') {
                alert('Роль пользователя не найдена. Пожалуйста, свяжитесь с администратором.'); // Уведомление для пользователя
            } else {
                console.error('Неизвестная роль:', data.role);
            }
        })
        .catch(error => {
            alert(error.message); // Выводим ошибку пользователю
            console.error('Ошибка:', error);
        });
});
