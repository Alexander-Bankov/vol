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

async function loadActions() {
    const select = document.getElementById('actionNameInput');

    // Очистка текущего содержимого выпадающего меню
    select.innerHTML = '<option value="" disabled selected>Выберите название события</option>';

    try {
        const response = await fetch('/get-guide/actions');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const actions = await response.json();

        // Добавляем полученные значения в выпадающее меню
        actions.forEach(action => {
            const option = document.createElement('option');
            option.value = action.actionName; // предполагается, что поле называется actionName
            option.textContent = action.actionName;
            select.appendChild(option);
        });

    } catch (error) {
        console.error('Ошибка при загрузке данных:', error);
        alert('Не удалось загрузить данные, пожалуйста, попробуйте позже.');
    }
}

// Вызываем функцию при загрузке страницы
document.addEventListener("DOMContentLoaded", loadActions);
