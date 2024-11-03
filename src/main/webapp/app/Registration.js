const form = document.querySelector('#form');
const sendButton = document.querySelector('#Send');
let out = document.getElementById("result");
const resetButton = document.querySelector('#Reset');

resetButton.addEventListener('click', function () {
    out.textContent = "";
});




sendButton.addEventListener('click', () => {
    const data = {
        mail: document.getElementById("mailInput").value,
        password: document.getElementById("passwordInput").value,
    };
    fetch('/create/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/admin.html'; // Перенаправление на home.html в случае успеха
            } else {
                throw new Error('Ошибка HTTP: ' + response.status);
            }
        })
        .catch(error => {
            out.textContent = error.message;
        });
});

form.addEventListener('submit', (event) => {
    event.preventDefault();
    if(document.getElementById("mailInput").value===""||document.getElementById("passwordInput").value===""){
        alert("Заполните все поля!");
    }else{
        sendButton.click();}
});

function getLanguages(input) {
    const trimmedInput = input.trim();
    const languagesArray = trimmedInput.split(' ').filter(Boolean);

    if (languagesArray.length === 0 || languagesArray.some(lang => lang.length === 0)) {
        alert('Ошибка: введите список языков корректно.'); // Выдаёт предупреждение, если ввод неверный
        return [];
    }

    return languagesArray;
}
