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
            const response = await fetch('/create-action/full-action', {
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

            // Проверяем, был ли ответ успешным
            const responseData = await response.text(); // Опытный метод для чтения ответа как текста

            // Если ответ не успешный, выбрасываем ошибку
            if (!response.ok) {
                // Попытка разобрать ответ как JSON, если это возможно
                let errorMessage = 'Ошибка при добавлении действия';
                try {
                    const errorData = JSON.parse(responseData);
                    errorMessage = errorData.message || errorMessage;
                } catch (e) {
                    // Ответ не является JSON, используем текст
                    errorMessage = responseData || errorMessage;
                }
                throw new Error(errorMessage);
            }

            // Очищаем поля ввода после успешного добавления
            actionNameInput.value = '';
            actionStartInput.value = '';
            actionEndInput.value = '';
            actionStatusInput.value = '';

            alert('Действие успешно добавлено!'); // Уведомление для пользователя
        } catch (error) {
            console.error('Ошибка при добавлении действия:', error);
            alert(`Ошибка: ${error.message}`); // Показываем сообщение об ошибке
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
        const responseData = await response.text(); // Читаем ответ как текст

        // Если ответ не успешный, выбрасываем ошибку
        if (!response.ok) {
            let errorMessage = 'Сетевая ошибка';
            try {
                const errorData = JSON.parse(responseData);
                errorMessage = errorData.message || errorMessage;
            } catch (e) {
                // Ответ не является JSON, используем текст
                errorMessage = responseData || errorMessage;
            }
            throw new Error(errorMessage);
        }

        const actions = JSON.parse(responseData); // Разбираем валидный JSON

        // Добавляем полученные значения в выпадающее меню
        actions.forEach(action => {
            const option = document.createElement('option');
            option.value = action.actionName; // предполагается, что поле называется actionName
            option.textContent = action.actionName;
            select.appendChild(option);
        });

    } catch (error) {
        console.error('Ошибка при загрузке данных:', error);
        alert(`Ошибка: ${error.message}`); // Показываем сообщение об ошибке
    }
}

// Вызываем функцию при загрузке страницы
document.addEventListener("DOMContentLoaded", loadActions);


// document.getElementById('SaveActionInfo').addEventListener('click', async (event) => {
//     event.preventDefault(); // Предотвратите отправку формы
//     const actionName = actionNameInput.value; // Предполагается, что это поле для ввода названия события
//     const actionStart = actionStartInput.value; // Поле ввода даты начала
//     const actionEnd = actionEndInput.value; // Поле ввода даты конца
//     const status = actionStatusInput.value; // Поле со статусом события
//
// // Проверяем, заполнены ли обязательные поля
//     if (actionName && actionStart && actionEnd && status) {
//         try {
//             // Отправляем данные на сервер
//             await fetch('/create-action/full-action', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json'
//                 },
//                 body: JSON.stringify({
//                     actionName: actionName,
//                     actionStart: actionStart,
//                     actionEnd: actionEnd,
//                     status: status
//                 })
//             });
//
//             // Очищаем поля ввода после успешного добавления
//             actionNameInput.value = '';
//             actionStartInput.value = '';
//             actionEndInput.value = '';
//             actionStatusInput.value = '';
//
//             alert('Действие успешно добавлено!'); // Уведомление для пользователя
//         } catch (error) {
//             console.error('Ошибка при добавлении действия:', error);
//         }
//     } else {
//         alert('Пожалуйста, заполните все поля!');
//     }
// });
//
// async function loadActions() {
//     const select = document.getElementById('actionNameInput');
//
//     // Очистка текущего содержимого выпадающего меню
//     select.innerHTML = '<option value="" disabled selected>Выберите название события</option>';
//
//     try {
//         const response = await fetch('/get-guide/actions');
//         if (!response.ok) {
//             throw new Error('Network response was not ok');
//         }
//
//         const actions = await response.json();
//
//         // Добавляем полученные значения в выпадающее меню
//         actions.forEach(action => {
//             const option = document.createElement('option');
//             option.value = action.actionName; // предполагается, что поле называется actionName
//             option.textContent = action.actionName;
//             select.appendChild(option);
//         });
//
//     } catch (error) {
//         console.error('Ошибка при загрузке данных:', error);
//         alert('Не удалось загрузить данные, пожалуйста, попробуйте позже.');
//     }
// }
//
// // Вызываем функцию при загрузке страницы
// document.addEventListener("DOMContentLoaded", loadActions);
