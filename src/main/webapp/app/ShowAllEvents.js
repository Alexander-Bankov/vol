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

document.getElementById('showEvents').addEventListener('click', function () {
    const actionName = document.getElementById('actionNameInput').value;

    if (!actionName) {
        alert("Пожалуйста, выберите название события.");
        return;
    }

    fetch(`/create-event/events?actionName=${encodeURIComponent(actionName)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Сеть не доступна');
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.querySelector('#eventInfoTable tbody');
            tableBody.innerHTML = ''; // Очистить предыдущие данные перед заполнением

            data.forEach((event, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${event.eventName}</td>
                    <td>${event.actionName}</td>
                    <td>${event.place}</td>
                    <td>${event.startTime}</td>
                    <td>${event.endTime}</td>
                    <td>${event.volunteerCount}</td>
                    <td>${event.maxVolunteerCount}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert('Ошибка при получении данных о мероприятиях.');
        });
});
