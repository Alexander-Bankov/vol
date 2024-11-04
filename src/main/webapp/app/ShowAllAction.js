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


document.getElementById('showActions').addEventListener('click', async function() {
    const select = document.getElementById('actionNameInput');
    const actionName = select.value;

    if (!actionName) {
        alert('Пожалуйста, выберите название события.');
        return;
    }

    const tbody = document.querySelector('#actionInfoTable tbody');
    // Очистка текущего содержимого таблицы
    tbody.innerHTML = '';

    try {
        const response = await fetch(`/create-action/actions?actionName=${encodeURIComponent(actionName)}`);
        if (!response.ok) {
            throw new Error('Ошибка сети: ' + response.statusText);
        }

        const actionInfo = await response.json();

        console.log('Полученные данные:', actionInfo); // Логирование полученных данных

        // Проверка, что данные возвращаются и имеют правильную структуру
        if (Array.isArray(actionInfo) && actionInfo.length > 0) {
            actionInfo.forEach((action, index) => {
                const row = tbody.insertRow();
                row.insertCell(0).textContent = index + 1; // Номер
                row.insertCell(1).textContent = action.actionName; // Название события
                row.insertCell(2).textContent = action.actionStart; // Дата начала события
                row.insertCell(3).textContent = action.actionEnd; // Дата конца события
                row.insertCell(4).textContent = action.status; // Статус события

                // Проверяем, что eventNames существует и не пуст
                if (action.eventNames) {
                    const eventNamesArray = action.eventNames.split(',').map(name => name.trim());
                    row.insertCell(5).textContent = eventNamesArray.join(', '); // Список мероприятий
                } else {
                    row.insertCell(5).textContent = 'Нет мероприятий'; // Если нет мероприятий
                }
            });
        } else {
            tbody.insertRow().insertCell(0).textContent = 'Нет данных для отображения.';
        }

    } catch (error) {
        console.error('Ошибка при загрузке данных:', error);
        alert('Не удалось загрузить данные, пожалуйста, попробуйте позже.');
    }
});


