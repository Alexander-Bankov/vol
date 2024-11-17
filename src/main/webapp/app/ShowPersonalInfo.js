

// document.addEventListener("DOMContentLoaded", function() {
//     // Функция для загрузки данных
//     function loadData() {
//         fetch('/volunteer/get-personal-info') // Получаем данные
//             .then(response => {
//                 if (!response.ok) {
//                     throw new Error('Сетевая ошибка: ' + response.status);
//                 }
//                 return response.json();
//             })
//             .then(data => {
//                 // Заполнение таблицы данными
//                 document.getElementById('email').value = data.mail || ''; // Изменяем innerText на value
//                 document.getElementById('lastName').innerText = data.lastName || '';
//                 document.getElementById('firstName').innerText = data.firstName || '';
//                 document.getElementById('secondName').innerText = data.secondName || '';
//                 document.getElementById('birthdate').innerText = data.birthdate || '';
//                 document.getElementById('address').innerText = data.address || '';
//                 document.getElementById('phone').innerText = data.phone || '';
//                 document.getElementById('info').innerText = data.volunteerInfo || '';
//                 document.getElementById('language').innerText = data.language || '';
//             })
//             .catch(error => {
//                 console.error('Ошибка загрузки данных:', error);
//                 alert('Не удалось загрузить данные.');
//             });
//     }
//
//     // Вызов функции загрузки данных при загрузке страницы
//     loadData();
//
//     document.getElementById('Change').onclick = function() {
//         const cells = document.querySelectorAll('tbody td');
//         cells.forEach((cell, index) => {
//             if (index > 0) { // Пропустить поле email
//                 const data = cell.querySelector('.data').innerText;
//                 cell.innerHTML = `<input type="text" class="form-control" value="${data}" />`;
//             }
//         });
//         this.style.display = 'none'; // Скрыть кнопку "Изменить"
//         document.getElementById('Cancel').style.display = 'inline'; // Показать кнопку "Отмена"
//     };
//
//     document.getElementById('Save').onclick = function() {
//         const inputs = document.querySelectorAll('tbody input');
//         const dataToSend = Array.from(inputs).map(input => input.value);
//
//         fetch('/volunteer/get-personal-info', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify({
//                 mail: document.getElementById('email').value, // Сохраняем значение почты из readonly поля
//                 lastName: dataToSend[0],
//                 firstName: dataToSend[1],
//                 secondName: dataToSend[2],
//                 birthdate: dataToSend[3],
//                 address: dataToSend[4],
//                 phone: dataToSend[5],
//                 volunteerInfo: dataToSend[6],
//                 language: dataToSend[7]
//             })
//         })
//             .then(response => {
//                 if (response.ok) {
//                     alert('Данные успешно сохранены!');
//                 } else {
//                     alert('Ошибка при сохранении данных!');
//                 }
//             })
//             .catch(error => {
//                 console.error('Ошибка:', error);
//                 alert('Сетевой запрос завершился неудачно.');
//             });
//     };
//
//     document.getElementById('Cancel').onclick = function() {
//         location.reload(); // Перезагрузить страницу при нажатии на Cancel
//     };
// });

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

    // Вызов функции загрузки данных при загрузке страницы
    loadData();

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



