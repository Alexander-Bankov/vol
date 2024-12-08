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
                const response = await fetch('/create-guide/action', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ actionName })
                });

                if (response.ok) {
                    actionInput.value = ''; // Очистить поле ввода
                    alert('Событие добавлено!'); // Уведомление пользователю
                } else {
                    const errorMessage = await response.text(); // Получаем текст ошибки из ответа
                    alert(`Ошибка при добавлении события: ${errorMessage}`);
                }
            } catch (error) {
                console.error('Ошибка при добавлении события:', error);
                alert('Произошла ошибка при добавлении события. Пожалуйста, попробуйте позже.');
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
                const response = await fetch('/create-guide/language', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ languageName })
                });

                if (response.ok) {
                    languageInput.value = ''; // Очистить поле ввода
                    alert('Язык добавлен!'); // Уведомление пользователю
                } else {
                    const errorMessage = await response.text(); // Получаем текст ошибки из ответа
                    alert(`Ошибка при добавлении языка: ${errorMessage}`);
                }
            } catch (error) {
                console.error('Ошибка при добавлении языка:', error);
                alert('Произошла ошибка при добавлении языка. Пожалуйста, попробуйте позже.');
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
                actionElement.innerText = action.actionName;
                actionElement.classList.add('action-item', 'centered'); // Применяем класс centered
                resultDiv.appendChild(actionElement);
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
                languageElement.innerText = language.languageName;
                languageElement.classList.add('language-item', 'centered'); // Применяем класс centered
                resultDiv.appendChild(languageElement);
            });
        } catch (error) {
            console.error('Ошибка при получении языков:', error);
        }
    });
});
