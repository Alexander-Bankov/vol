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

    // Определяем выбранный тип сортировки
    const sortOrder = document.querySelector('input[name="sortOptions"]:checked'); // Изменение здесь
    const sortType = sortOrder ? sortOrder.value : 'default';  // Используем 'default', если сортировка не выбрана

    // Запрос к контроллеру с учетом выбранной сортировки
    fetch(`/sorted/events?nameAction=${encodeURIComponent(actionName)}&nameSorted=${encodeURIComponent(sortType)}`)
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

// Обработчик событий для радиокнопок сортировки
const sortButtons = document.querySelectorAll('input[name="sortOptions"]'); // Изменение здесь
sortButtons.forEach(button => {
    button.addEventListener('change', function () {
        // При изменении порядка сортировки вызываем функцию показа мероприятий
        document.getElementById('showEvents').click();
    });
});

const resetButton = document.querySelector('#Reset');
resetButton.addEventListener('click', function () {
    location.reload(); // перезагрузка страницы
});



document.getElementById('applyFilters').addEventListener('click', async function() {
    const startDate = document.getElementById('startDateInput').value;
    const endDate = document.getElementById('endDateInput').value;
    const maxVolunteerCount = document.getElementById('maxVolunteersInput').value;
    const actionName = document.getElementById('actionNameInput').value;

    const tbody = document.querySelector('#eventInfoTable tbody');
    tbody.innerHTML = ''; // Очистка текущего содержимого таблицы перед заполнением

    try {
        let url = '';
        let params = '';

        // Проверяем, какой фильтр выбран и формируем запрос
        if (maxVolunteerCount) {
            url = '/filter/max-vol-count-events';
            params = `?actionName=${encodeURIComponent(actionName)}&maxVolCount=${encodeURIComponent(maxVolunteerCount)}`;
        } else if (startDate && endDate) {
            url = '/filter/date-events';
            params = `?actionName=${encodeURIComponent(actionName)}&dateStart=${encodeURIComponent(startDate)}&dateEnd=${encodeURIComponent(endDate)}`;
        } else {
            alert('введите дату начала и конца вместе');
            return;
        }

        const response = await fetch(url + params);
        if (!response.ok) {
            throw new Error('Ошибка сети: ' + response.statusText);
        }

        const actionInfo = await response.json();
        console.log('Полученные данные после фильтрации:', actionInfo); // Логирование полученных данных

        // Проверка, что данные возвращаются и имеют правильную структуру
        if (Array.isArray(actionInfo) && actionInfo.length > 0) {
            actionInfo.forEach((event, index) => { // Обратите внимание на "actionInfo", это исправление
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
                tbody.appendChild(row);
            });
        } else {
            tbody.insertRow().insertCell(0).textContent = 'Нет данных для отображения.';
        }

    } catch (error) {
        console.error('Ошибка при загрузке данных:', error);
        alert('Не удалось загрузить данные, пожалуйста, попробуйте позже.');
    }
});




