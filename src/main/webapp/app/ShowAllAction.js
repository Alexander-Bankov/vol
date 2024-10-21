async function fetchData() {
    try {
        const response = await fetch('/vol/getAllUsers'); // URL для запроса
        if (!response.ok) {
            throw new Error('Сеть ответила с ошибкой');
        }
        const data = await response.json();
        populateTable(data);
    } catch (error) {
        console.error('Ошибка при получении данных:', error);
    }
}

function populateTable(data) {
    const tbody = document.querySelector('#actionTable tbody');
    tbody.innerHTML = ''; // Очищаем таблицу перед добавлением новых данных

    data.forEach((item, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <th scope="row">${index + 1}</th>
            <td>${item.firstName}</td> <!-- Используем поле 'firstName' -->
            <td>${item.email}</td> <!-- Используем поле 'email' -->
        `;
        tbody.appendChild(row);
    });
}

// Вызываем функцию fetchData при загрузке страницы
window.onload = fetchData;
