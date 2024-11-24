// Функция для загрузки всех действий
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

// Привязка функции к кнопке ShowAllAction
document.addEventListener('DOMContentLoaded', () => {
    const showAllActionButton = document.getElementById('ShowAllAction');
    showAllActionButton.addEventListener('click', () => {
        loadAllActions(); // Вызываем функцию при нажатии
    });
});

function loadApplications() {
    fetch('/volunteer/get-application-info')
        .then(response => {
            if (!response.ok) throw new Error('Сетевая ошибка: ' + response.status);
            return response.json();
        })
        .then(applications => {
            const applicationsBody = document.querySelector('#applicationInfoTable tbody'); // менять на tbody вашей таблицы
            applicationsBody.innerHTML = ''; // очищаем тело таблицы

            applications.forEach((application, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${application.eventName}</td>
                    <td>${application.actionName}</td>
                    <td>${application.mail}</td>
                    <td>${application.statusApplication}</td>
                    <td>
                        <button class="btn btn-success confirm-application" data-action="${application.actionName}" data-event="${application.eventName}">Подтвердить</button>
                        <button class="btn btn-danger reject-application" data-action="${application.actionName}" data-event="${application.eventName}">Отклонить</button>
                    </td>
                `;
                applicationsBody.appendChild(row);
            });
        })
        .catch(error => alert('Не удалось загрузить заявки.'));
}

// Привязываем функцию загрузки к кнопке "Показать все"
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('ShowAllApplications').addEventListener('click', loadApplications);
});


