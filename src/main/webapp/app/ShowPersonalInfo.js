document.getElementById("Send").addEventListener("click", function() {
    const email = document.getElementById("mailInput").value;

    if (!email) {
        alert('Пожалуйста, введите email.'); // Please enter an email
        return;
    }

    fetch(`/vol/getUser?eMail=${encodeURIComponent(email)}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.querySelector("#personalInfoTable tbody");
            tbody.innerHTML = ""; // Clear previous entries

            if (data) {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>1</td>
                    <td>${data.firstName}</td>
                    <td>${data.lastName}</td>
                    <td>${data.secondName ? data.secondName : ''}</td>
                    <td>${data.eMail}</td>
                    <td>${data.birthdate ? new Date(data.birthdate).toLocaleDateString() : ''}</td>
                    <td>${data.sex ? data.sex : ''}</td>
                    <td>${data.experience ? data.experience : ''}</td>
                    <td>${data.language ? data.language : ''}</td>
                    <td>${data.briefInfo ? data.briefInfo : ''}</td>
                `;
                tbody.appendChild(tr);
            } else {
                alert('Пользователь не найден.'); // User not found
            }
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
});

document.getElementById("Reset").addEventListener("click", function() {
    document.getElementById("mailInput").value = ""; // Clear email input
    document.querySelector("#personalInfoTable tbody").innerHTML = ""; // Clear table
});
