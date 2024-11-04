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

document.getElementById('SaveEventInfo').addEventListener('click', async (event) => {
    event.preventDefault(); // Предотвратите отправку формы

    const actionName = document.getElementById('actionNameInput').value; // Название события из выпадающего списка
    const eventName = document.getElementById('eventNameInput').value; // Название мероприятия
    const place = document.getElementById('placeInput').value;
    const startTime = document.getElementById('eventStartInput').value; // Дата и время начала
    const endTime = document.getElementById('eventEndInput').value; // Дата и время конца
    const maxVolunteerCount = document.getElementById('maxCountVolunteerInput').value; // Максимальное количество волонтеров

    // Проверяем, заполнены ли обязательные поля
    if (actionName && eventName && startTime && endTime && place && maxVolunteerCount) {
        try {
            // Отправляем данные на сервер
            await fetch('/create-event/full-event', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    actionName: actionName,
                    eventName: eventName,
                    startTime: startTime,
                    endTime: endTime,
                    place: place,
                    maxVolunteerCount: maxVolunteerCount
                })
            });

            // Очищаем поля ввода после успешного добавления
            document.getElementById('actionNameInput').value = '';
            document.getElementById('eventNameInput').value = '';
            document.getElementById('eventStartInput').value = '';
            document.getElementById('eventEndInput').value = '';
            document.getElementById('placeInput').value = '';
            document.getElementById('maxCountVolunteerInput').value = '';

            alert('Мероприятие успешно добавлено! 🎉'); // Уведомление для пользователя
        } catch (error) {
            console.error('Ошибка при добавлении мероприятия:', error);
        }
    } else {
        alert('Пожалуйста, заполните все поля! ⚠️');
    }
});
