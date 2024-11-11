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

// Функция для загрузки всех действий из нового эндпоинта
async function loadAllActions(sortOption = null) {
    const tbody = document.querySelector('#actionInfoTable tbody');
    tbody.innerHTML = ''; // Очистка текущего содержимого таблицы

    try {
        let url = '/create-action/all-actions';
        if (sortOption) {
            url = `sorted/actions?nameSorted=${sortOption}`; // Запрос с сортировкой
        }

        const response = await fetch(url);
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
}

// Функция для получения выбранного параметра сортировки
function getSelectedSortOption() {
    const sortOptions = document.querySelectorAll('input[name="sortOptions"]');
    for (let option of sortOptions) {
        if (option.checked) {
            return option.value; // Вернуть значение проверенного радиобаттона
        }
    }
    return null; // Если ни один не выбран
}

// Вызываем функции при загрузке страницы
document.addEventListener("DOMContentLoaded", async () => {
    await loadActions(); // Загружаем названия событий в выпадающее меню
    await loadAllActions(); // Загружаем все действия в таблицу
});

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

// Добавляем обработчик для сортировки
document.querySelectorAll('input[name="sortOptions"]').forEach(input => {
    input.addEventListener('change', async function() {
        const sortOption = getSelectedSortOption(); // Получаем выбранное значение
        await loadAllActions(sortOption); // Загружаем отсортированные действия
    });
});
const resetButton = document.querySelector('#Reset');
resetButton.addEventListener('click', function () {
    location.reload(); // перезагрузка страницы
});

document.getElementById('applyFilters').addEventListener('click', async function() {
    const startDate = document.getElementById('startDateInput').value;
    const endDate = document.getElementById('endDateInput').value;
    const status = document.getElementById('statusSelect').value;

    const tbody = document.querySelector('#actionInfoTable tbody');
    tbody.innerHTML = ''; // Очистка текущего содержимого таблицы

    try {
        let url = '';
        let params = '';

        // Проверяем, какой фильтр выбран и формируем запрос
        if (status) {
            url = '/filter/status-actions';
            params = `?status=${encodeURIComponent(status)}`;
        } else if (startDate && endDate) {
            url = '/filter/date-actions';
            params = `?dateStart=${encodeURIComponent(startDate)}&dateEnd=${encodeURIComponent(endDate)}`;
        } else {
            alert('Пожалуйста, выберите один фильтр для применения.');
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