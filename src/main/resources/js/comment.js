function checkLength(event) {
    var maxLength = 500;
    var textLength = document.getElementById('client-message').value.length;

    if (textLength >= maxLength && event.key !== 'Backspace' && event.key !== 'Delete') {
        event.preventDefault();
    }
}

document.getElementById('submit-comment').addEventListener('click', function (event) {
    event.preventDefault();

    var csrfToken = document.getElementById('csrf-id-comment').value;
    var commentText = document.getElementById('client-message').value;
    var titleRestaurant = document.getElementById('title-restaurant').value;

    if (commentText.trim() !== '') {

        var commentData = {
            comment: commentText,
            title: titleRestaurant
        }

        fetch('/comment/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
            body: JSON.stringify(commentData)
        })
            .then(response => {
                if (response.ok) {
                    location.reload();
                } else {
                    if (response.status === 409) {
                        location.reload();
                        console.error('Некорректные данные' + response.status);
                    } else {
                        location.reload();
                        console.error('Ошибка сервера: ' + response.status);
                    }
                }
            })
            .catch(error => {
                console.error('Произошла ошибка:', error);
            });
    }
});