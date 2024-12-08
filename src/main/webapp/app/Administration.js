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
async function loadActionByName(sortOption = null) {
    const tbody = document.querySelector('#actionInfoTable tbody');
    tbody.innerHTML = ''; // Очистка текущего содержимого таблицы

    // Получение значения из поля ввода
    const actionInput = document.getElementById('actionInput');
    const actionNames = actionInput.value.trim(); // Убираем лишние пробелы

    if (!actionNames) {
        alert('Пожалуйста, введите название события.');
        return; // Выход, если имя события не указано
    }

    try {
        let url = '/admin/get-action-by-action-name?actionName=' + encodeURIComponent(actionNames);
        if (sortOption) {
            url += `&nameSorted=${sortOption}`; // Запрос с сортировкой (если требуется)
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

//мероприятия
async function loadAllEvents(sortOption = null) {
    const tbody = document.querySelector('#eventInfoTable tbody'); // Изменено на eventInfoTable
    tbody.innerHTML = ''; // Очистка текущего содержимого таблицы

    try {
        let url = '/admin/events';

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Ошибка сети: ' + response.statusText);
        }

        const eventInfo = await response.json();

        console.log('Полученные данные:', eventInfo); // Логирование полученных данных

        // Проверка, что данные возвращаются и имеют правильную структуру
        if (Array.isArray(eventInfo) && eventInfo.length > 0) {
            eventInfo.forEach((event, index) => {
                const row = tbody.insertRow();
                row.insertCell(0).textContent = index + 1; // Номер
                row.insertCell(1).textContent = event.eventName; // Название мероприятия
                row.insertCell(2).textContent = event.actionName; // Название события, к которому относится
                row.insertCell(3).textContent = event.place; // Место проведения
                row.insertCell(4).textContent = event.startTime; // Дата и время начала
                row.insertCell(5).textContent = event.endTime; // Дата и время конца
                row.insertCell(6).textContent = event.volunteerCount; // Количество участников
                row.insertCell(7).textContent = event.maxVolunteerCount; // Максимальное количество участников
            });
        } else {
            tbody.insertRow().insertCell(0).textContent = 'Нет данных для отображения.';
        }

    } catch (error) {
        console.error('Ошибка при загрузке данных:', error);
        alert('Не удалось загрузить данные, пожалуйста, попробуйте позже.');
    }
}
async function loadEventsnByName(sortOption = null) {
    const tbody = document.querySelector('#eventInfoTable tbody');
    tbody.innerHTML = ''; // Очистка текущего содержимого таблицы

    // Получение значения из поля ввода
    const eventInput = document.getElementById('eventNameInput');
    const eventName = eventInput.value.trim(); // Убираем лишние пробелы

    if (!eventName) {
        alert('Пожалуйста, введите название события.');
        return; // Выход, если имя события не указано
    }

    try {
        let url = '/admin/events-by-name?eventName=' + encodeURIComponent(eventName);
        if (sortOption) {
            url += `&nameSorted=${sortOption}`; // Запрос с сортировкой (если требуется)
        }

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Ошибка сети: ' + response.statusText);
        }

        const eventInfo = await response.json();

        console.log('Полученные данные:', eventInfo); // Логирование полученных данных

        if (Array.isArray(eventInfo) && eventInfo.length > 0) {
            eventInfo.forEach((event, index) => {
                const row = tbody.insertRow();
                row.insertCell(0).textContent = index + 1; // Номер
                row.insertCell(1).textContent = event.eventName; // Название мероприятия
                row.insertCell(2).textContent = event.actionName; // Название события, к которому относится
                row.insertCell(3).textContent = event.place; // Место проведения
                row.insertCell(4).textContent = event.startTime; // Дата и время начала
                row.insertCell(5).textContent = event.endTime; // Дата и время конца
                row.insertCell(6).textContent = event.volunteerCount; // Количество участников
                row.insertCell(7).textContent = event.maxVolunteerCount; // Максимальное количество участников
            });
        } else {
            tbody.insertRow().insertCell(0).textContent = 'Нет данных для отображения.';
        }

    } catch (error) {
        console.error('Ошибка при загрузке данных:', error);
        alert('Не удалось загрузить данные, пожалуйста, попробуйте позже.');
    }
}

async function loadAllVolunteers(sortOption = null) {
    const tbody = document.querySelector('#volunteerTable tbody'); // Изменено на eventInfoTable
    tbody.innerHTML = ''; // Очистка текущего содержимого таблицы

    try {
        let url = '/admin/get-all-volunteer';

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Ошибка сети: ' + response.statusText);
        }

        const volunteerInfo = await response.json();

        console.log('Полученные данные:', volunteerInfo); // Логирование полученных данных

        // Проверка, что данные возвращаются и имеют правильную структуру
        if (Array.isArray(volunteerInfo) && volunteerInfo.length > 0) {
            volunteerInfo.forEach((volunteer, index) => {
                const row = tbody.insertRow();
                row.insertCell(0).textContent = index + 1;
                row.insertCell(1).textContent = volunteer.mail;
                row.insertCell(2).textContent = volunteer.lastName;
                row.insertCell(3).textContent = volunteer.firstName;
                row.insertCell(4).textContent = volunteer.secondName;
                row.insertCell(5).textContent = volunteer.birthdate;
                row.insertCell(6).textContent = volunteer.address;
                row.insertCell(7).textContent = volunteer.phone;
                row.insertCell(8).textContent = volunteer.volunteerInfo;
                row.insertCell(9).textContent = volunteer.language;
            });
        } else {
            tbody.insertRow().insertCell(0).textContent = 'Нет данных для отображения.';
        }

    } catch (error) {
        console.error('Ошибка при загрузке данных:', error);
        alert('Не удалось загрузить данные, пожалуйста, попробуйте позже.');
    }
}


async function loadVolunteerByMail(sortOption = null) {
    const tbody = document.querySelector('#volunteerTable tbody');
    tbody.innerHTML = ''; // Очистка текущего содержимого таблицы

    // Получение значения из поля ввода
    const mailInput = document.getElementById('mailInput');
    const mail = mailInput.value.trim(); // Убираем лишние пробелы

    if (!mail) {
        alert('Пожалуйста, введите название события.');
        return; // Выход, если имя события не указано
    }

    try {
        let url = '/admin/get-personal-info?mail=' + encodeURIComponent(mail);

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Ошибка сети: ' + response.statusText);
        }

        const volunteerInfo = await response.json();

        console.log('Полученные данные:', volunteerInfo); // Логирование полученных данных

        if (volunteerInfo) {
            const row = tbody.insertRow();
            row.insertCell(0).textContent = 1; // Показать как первый элемент
            row.insertCell(1).textContent = volunteerInfo.mail;
            row.insertCell(2).textContent = volunteerInfo.lastName;
            row.insertCell(3).textContent = volunteerInfo.firstName;
            row.insertCell(4).textContent = volunteerInfo.secondName;
            row.insertCell(5).textContent = volunteerInfo.birthdate;
            row.insertCell(6).textContent = volunteerInfo.address;
            row.insertCell(7).textContent = volunteerInfo.phone;
            row.insertCell(8).textContent = volunteerInfo.volunteerInfo;
            row.insertCell(9).textContent = volunteerInfo.language;
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
    const showActionByNameButton = document.getElementById('SendNameAction');
    showActionByNameButton.addEventListener('click', () => {
        loadActionByName(); // Вызываем функцию при нажатии
    });
    const showAllEventsButton = document.getElementById('ShowAllEventAction');
    showAllEventsButton.addEventListener('click', () => {
        loadAllEvents(); // Вызываем функцию при нажатии
    });
    const showEventByEventName = document.getElementById('SendEventName');
    showEventByEventName.addEventListener('click', () => {
        loadEventsnByName(); // Вызываем функцию при нажатии
    });
    const showAllVolunteerButton = document.getElementById('ShowAllVolunteer');
    showAllVolunteerButton.addEventListener('click', () => {
        loadAllVolunteers(); // Вызываем функцию при нажатии
    });
    const showVolunteerByMailButton = document.getElementById('SendMail');
    showVolunteerByMailButton.addEventListener('click', () => {
        const mailInput = document.getElementById('mailInput'); // Получаем элемент ввода email
        const email = mailInput.value; // Получаем значение из поля ввода
        loadVolunteerByMail(email); // Вызываем функцию и передаем email
    });
});

// function loadApplications() {
//     fetch('/admin/get-application-info')
//         .then(response => {
//             if (!response.ok) throw new Error('Сетевая ошибка: ' + response.status);
//             return response.json();
//         })
//         .then(applications => {
//             const applicationsBody = document.querySelector('#applicationInfoTable tbody');
//             applicationsBody.innerHTML = ''; // очищаем тело таблицы
//
//             applications.forEach((application, index) => {
//                 const row = document.createElement('tr');
//                 row.innerHTML = `
//                     <td>${index + 1}</td>
//                     <td>${application.eventName}</td>
//                     <td>${application.actionName}</td>
//                     <td>${application.mail}</td>
//                     <td>${application.status}</td>
//                     <td>
//                         <button class="btn btn-success confirm-application" data-action="${application.actionName}" data-event="${application.eventName}" data-mail="${application.mail}">Подтвердить</button>
//                         <button class="btn btn-danger reject-application" data-action="${application.actionName}" data-event="${application.eventName}" data-mail="${application.mail}">Отклонить</button>
//                     </td>
//                 `;
//                 applicationsBody.appendChild(row);
//             });
//
//             // Добавляем обработчики событий для кнопок
//             document.querySelectorAll('.confirm-application').forEach(button => {
//                 button.addEventListener('click', () => updateApplicationStatus(button, 'ПОДТВЕРДИТЬ'));
//             });
//
//             document.querySelectorAll('.reject-application').forEach(button => {
//                 button.addEventListener('click', () => updateApplicationStatus(button, 'ОТКЛОНИТЬ'));
//             });
//         })
//         .catch(error => alert('Не удалось загрузить заявки.'));
// }
function loadApplications() {
    fetch('/admin/get-application-info')
        .then(response => {
            if (!response.ok) throw new Error('Сетевая ошибка: ' + response.status);
            return response.json();
        })
        .then(applications => {
            const applicationsBody = document.querySelector('#applicationInfoTable tbody');
            applicationsBody.innerHTML = ''; // очищаем тело таблицы

            applications.forEach((application, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${application.eventName}</td>
                    <td>${application.actionName}</td>
                    <td>${application.mail}</td>
                    <td>${application.status}</td>
                    <td>
                        ${application.status === 'INPROCESS' ? `
                            <button class="btn btn-success confirm-application" data-action="${application.actionName}" data-event="${application.eventName}" data-mail="${application.mail}">Подтвердить</button>
                            <button class="btn btn-danger reject-application" data-action="${application.actionName}" data-event="${application.eventName}" data-mail="${application.mail}">Отклонить</button>
                        ` : ''}
                    </td>
                `;
                applicationsBody.appendChild(row);
            });

            // Добавляем обработчики событий для кнопок
            document.querySelectorAll('.confirm-application').forEach(button => {
                button.addEventListener('click', () => updateApplicationStatus(button, 'ПОДТВЕРДИТЬ'));
            });

            document.querySelectorAll('.reject-application').forEach(button => {
                button.addEventListener('click', () => updateApplicationStatus(button, 'ОТКЛОНИТЬ'));
            });
        })
        .catch(error => alert('Не удалось загрузить заявки.'));
}

function updateApplicationStatus(button, status) {
    const actionName = button.getAttribute('data-action');
    const eventName = button.getAttribute('data-event');
    const mail = button.getAttribute('data-mail');

    fetch('/admin/update-status', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            actionName: actionName,
            eventName: eventName,
            mail: mail,
            status: status
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при обновлении статуса: ' + response.statusText);
            }
            return response.text();
        })
        .then(message => {
            alert(message);
            loadApplications(); // Обновляем список заявок после изменения статуса
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert('Не удалось обновить статус заявки.');
        });
}

// Привязываем функцию загрузки к кнопке "Показать все"
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('ShowAllApplications').addEventListener('click', loadApplications);
});


document.getElementById('logoutButton').onclick = function() {
    fetch('/vol/logout', {
        method: 'GET',
        credentials: 'same-origin' // чтобы отправить cookie с запросом (если используется)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/Autorization.html'; // Перенаправление на страницу Authorization.html
            } else {
                alert('Ошибка при выводе из системы!');
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert('Сетевой запрос завершился неудачно.');
        });
};


