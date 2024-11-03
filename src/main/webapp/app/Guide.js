document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('form');
    const actionInput = document.getElementById('actionInput');
    const languageInput = document.getElementById('languageInput');
    const resultDiv = document.getElementById('result');

    // Обработчик для добавления события
    document.getElementById('Save_action').addEventListener('click', async (event) => {
        event.preventDefault(); // Предотвратите отправку формы
        const actionName = actionInput.value;

        if (actionName) {
            try {
                await fetch('/create-guide/action', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ actionName })
                });

                actionInput.value = ''; // Очистить поле ввода
                alert('Событие добавлено!'); // Уведомление пользователю
            } catch (error) {
                console.error('Ошибка при добавлении события:', error);
            }
        } else {
            alert('Введите название события!');
        }
    });

    // Обработчик для добавления языка
    document.getElementById('SaveLanguage').addEventListener('click', async (event) => {
        event.preventDefault(); // Предотвратите отправку формы
        const languageName = languageInput.value;

        if (languageName) {
            try {
                await fetch('/create-guide/language', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ languageName })
                });

                languageInput.value = ''; // Очистить поле ввода
                alert('Язык добавлен!'); // Уведомление пользователю
            } catch (error) {
                console.error('Ошибка при добавлении языка:', error);
            }
        } else {
            alert('Введите название языка!');
        }
    });

    // Обработчик для показа событий
    document.getElementById('ShowAction').addEventListener('click', async () => {
        resultDiv.innerHTML = ''; // Сбросить результаты
        const languages = document.querySelectorAll('.language-item');

        // Удаляем языки, если они есть
        languages.forEach(language => language.remove());

        try {
            const response = await fetch('/get-guide/actions');
            const actions = await response.json();

            actions.forEach(action => {
                const actionElement = document.createElement('p');
                actionElement.innerText = action.actionName; // Предполагается, что объект имеет это свойство
                resultDiv.appendChild(actionElement);
                actionElement.classList.add('action-item');
            });
        } catch (error) {
            console.error('Ошибка при получении событий:', error);
        }
    });

    // Обработчик для показа языков
    document.getElementById('ShowLanguage').addEventListener('click', async () => {
        resultDiv.innerHTML = ''; // Сбросить результаты
        const actions = document.querySelectorAll('.action-item');

        // Удаляем события, если они есть
        actions.forEach(action => action.remove());

        try {
            const response = await fetch('/get-guide/languages');
            const languages = await response.json();

            languages.forEach(language => {
                const languageElement = document.createElement('p');
                languageElement.innerText = language.languageName; // Предполагается, что объект имеет это свойство
                resultDiv.appendChild(languageElement);
                languageElement.classList.add('language-item');
            });
        } catch (error) {
            console.error('Ошибка при получении языков:', error);
        }
    });
});
