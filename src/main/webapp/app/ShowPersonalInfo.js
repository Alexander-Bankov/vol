
document.addEventListener("DOMContentLoaded", function() {
    // Функция для загрузки данных
    function loadData() {
        fetch('/volunteer/get-personal-info') // Получаем данные
            .then(response => {
                if (!response.ok) {
                    throw new Error('Сетевая ошибка: ' + response.status);
                }
                return response.json();
            })
            .then(data => {
                // Заполнение таблицы данными
                document.getElementById('email').value = data.mail || ''; // Изменяем innerText на value
                document.getElementById('lastName').innerText = data.lastName || '';
                document.getElementById('firstName').innerText = data.firstName || '';
                document.getElementById('secondName').innerText = data.secondName || '';
                document.getElementById('birthdate').innerText = data.birthdate || '';
                document.getElementById('address').innerText = data.address || '';
                document.getElementById('phone').innerText = data.phone || '';
                document.getElementById('info').innerText = data.volunteerInfo || '';
                document.getElementById('language').innerText = data.language || '';
            })
            .catch(error => {
                console.error('Ошибка загрузки данных:', error);
                alert('Не удалось загрузить данные.');
            });
    }
    function loadApplications() {
        fetch('/volunteer/get-application-info')
            .then(response => {
                if (!response.ok) throw new Error('Сетевая ошибка: ' + response.status);
                return response.json();
            })
            .then(applications => {
                const applicationsBody = document.getElementById('applicationsBody');
                applicationsBody.innerHTML = ''; // очищаем тело таблицы

                applications.forEach((application, index) => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${application.actionName}</td>
                        <td>${application.eventName}</td>
                        <td>${application.mail}</td>
                        <td>${application.statusApplication}</td>
                        <td><button class="btn btn-danger cancel-application" data-action="${application.actionName}" data-event="${application.eventName}">Отменить</button></td>
                    `;
                    applicationsBody.appendChild(row);
                });
                // Добавляем обработчик событий для кнопок "Отменить"
                document.querySelectorAll('.cancel-application').forEach(button => {
                    button.onclick = function() {
                        const actionName = this.getAttribute('data-action');
                        const eventName = this.getAttribute('data-event');
                        cancelApplication(actionName, eventName); // вызов функции отмены заявки
                    };
                });
            })
            .catch(error => alert('Не удалось загрузить заявки.'));
    }

    // Функция для отмены заявки
    function cancelApplication(actionName, eventName) {
        const url = `/create-application/delete-application?actionName=${encodeURIComponent(actionName)}&eventName=${encodeURIComponent(eventName)}`;

        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    alert('Заявка успешно отменена!');
                    loadApplications(); // Обновляем список заявок
                } else {
                    return response.json().then(err => {
                        alert('Ошибка при отмене заявки: ' + err.message);
                    });
                }
            })
            .catch(error => {
                console.error('Ошибка:', error);
                alert('Сетевой запрос завершился неудачно.');
            });
    }

    // Вызов функции загрузки данных при загрузке страницы
    loadData();
    loadApplications();

    document.getElementById('Change').onclick = function() {
        const cells = document.querySelectorAll('tbody td');
        cells.forEach((cell, index) => {
            if (index > 0) { // Пропустить поле email
                const data = cell.innerText; // Получаем текстовое значение ячейки
                cell.innerHTML = `<input type="text" class="form-control" value="${data}" />`;
            }
        });
        this.style.display = 'none'; // Скрыть кнопку "Изменить"
        document.getElementById('Cancel').style.display = 'inline'; // Показать кнопку "Отмена"
    };

    document.getElementById('Save').onclick = function() {
        const inputs = document.querySelectorAll('tbody input');
        const dataToSend = {
            lastName: inputs[1].value,
            firstName: inputs[2].value,
            secondName: inputs[3].value,
            birthdate: inputs[4].value,  // предполагается, что это значение в формате LocalDate
            address: inputs[5].value,
            phone: inputs[6].value,
            volunteerInfo: inputs[7].value,
            language: inputs[8].value,
        };

        console.log('Данные для отправки:', dataToSend); // Вывод массива данных в консоль

        fetch('/volunteer/updateByMail', {
            method: 'PUT',  // Используем PUT метод
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dataToSend) // Отправляем данные в формате JSON
        })
            .then(response => {
                if (response.ok) {
                    location.reload();
                    alert('Данные успешно сохранены!');
                } else {
                    alert('Ошибка при сохранении данных!');
                }
            })
            .catch(error => {
                console.error('Ошибка:', error);
                alert('Сетевой запрос завершился неудачно.');
            });
    };

    document.getElementById('Cancel').onclick = function() {
        location.reload(); // Перезагрузить страницу при нажатии на Cancel
    };
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
document.getElementById('viewActions').onclick = function() {
    window.location.href = 'Actions.html';
};

document.getElementById('viewEvents').onclick = function() {
    window.location.href = 'EventsOnAction.html';
};

document.getElementById('createApplication').onclick = function() {
    window.location.href = 'CreateApplication.html';
}


