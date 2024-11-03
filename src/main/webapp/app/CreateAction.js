document.getElementById('SaveActionInfo').addEventListener('click', async (event) => {
    event.preventDefault(); // Предотвратите отправку формы
    const actionName = actionNameInput.value; // Предполагается, что это поле для ввода названия события
    const actionStart = actionStartInput.value; // Поле ввода даты начала
    const actionEnd = actionEndInput.value; // Поле ввода даты конца
    const status = actionStatusInput.value; // Поле со статусом события

// Проверяем, заполнены ли обязательные поля
    if (actionName && actionStart && actionEnd && status) {
        try {
            // Отправляем данные на сервер
            await fetch('/create-action/full-action', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    actionName: actionName,
                    actionStart: actionStart,
                    actionEnd: actionEnd,
                    status: status
                })
            });

            // Очищаем поля ввода после успешного добавления
            actionNameInput.value = '';
            actionStartInput.value = '';
            actionEndInput.value = '';
            actionStatusInput.value = '';

            alert('Действие успешно добавлено!'); // Уведомление для пользователя
        } catch (error) {
            console.error('Ошибка при добавлении действия:', error);
        }
    } else {
        alert('Пожалуйста, заполните все поля!');
    }
});