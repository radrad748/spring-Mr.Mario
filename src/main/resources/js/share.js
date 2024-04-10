var buttonOrderShare ;
var modalSHare;

function orderShare(id) {
    buttonOrderShare = document.getElementById('button-order-share' + id);
    modalSHare = document.getElementById('modal-share' + id);

    modalSHare.style.display = 'block';
}

document.addEventListener('click', function (event) {
    if (!modalSHare.contains(event.target) && !buttonOrderShare.contains(event.target)) {
        modalSHare.style.display = 'none';
    }
});

function makeShareOrder(allPrice, userCount, shareId) {
    var address = document.getElementById('client-address' + shareId).value;
    var phone = document.getElementById('client-phone' + shareId).value;

    var allPriceN = parseFloat(allPrice);
    var userCountN = parseFloat(userCount);

    if (userCountN < allPriceN) {
        var message = 'Недостаточно средств';
        showUserMessage(message, shareId);
    } else if (!validAddress(address)) {
        var message = 'Некорректный адрес';
        showUserMessage(message, shareId);
    } else if (!validPhone(phone)) {
        var message = 'Некорректный телефон';
        showUserMessage(message, shareId);
    } else {
        sendShareOrder(allPrice, shareId, address, phone);
    }
}

function showUserMessage(message, shareId) {
    var oldMessage = document.getElementById('p-message' + shareId);
    if (oldMessage) oldMessage.remove();

    var pMessage = document.createElement('p');
    pMessage.id = 'p-message' + shareId;
    pMessage.style.color = 'red';
    pMessage.textContent = message;

    var form = document.getElementById('order-button' + shareId);
    form.appendChild(pMessage);
}

function validAddress(address) {
    if (address.trim().length < 6 || address.trim().length > 100) {
        return false;
    }
    return true;
}
function validPhone(phone) {
    if (phone.trim().length < 6 || phone.trim().length > 20) {
        return false;
    }
    return true;
}

function sendShareOrder(allPrice, shareId, address, phone) {
    var url = '/shares/order/' + shareId;

    var csrfToken = document.getElementById('csrf-id-modal').value;

    var data = {
        allPrice: allPrice,
        address: address,
        phone: phone
    };

    fetch(url,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/order/success-order";
            } else  {
                if (response.status === 402) {
                    var message = 'Недостаточно средств';
                    showUserMessage(message, shareId)
                } else {
                    console.log('Ошибка: ' + response.status);
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
/* модальное окно заказа */
/* ------------------------------------------------------------------------------------------ */