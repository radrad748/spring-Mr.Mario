var order = {};

var orderListDataElement = document.getElementById('orderListData');
var orderList = JSON.parse(orderListDataElement.getAttribute('data-orderList'));
var totalPrice = orderListDataElement.getAttribute('data-totalPrice');
var countOrder = orderList.length;


orderList.forEach(function (item) {
    var menu = item.menu;
    order[menu.id] = item.count;
});

/* запоминаем список заказов */
/* --------------------------------------------------------------------------------------- */

function deleteOrder(idOrder, productPrice) {
    if (countOrder === 1) {

    } else {
        sumNewPrice(totalPrice, productPrice, order[idOrder]);

        countOrder--;
        delete order[idOrder];

        var divItem = document.getElementById('item-' + idOrder);
        divItem.remove();

        var hrItem = document.getElementById('hr-' + idOrder);
        hrItem.remove();
    }
}

function sumNewPrice(totalPrice, productPrice, count) {
    var price = productPrice * count;
    var newTotalPrice = totalPrice - price;

    var itemTotalPrice = document.getElementById('totalPrice');
    itemTotalPrice.innerText = 'Стоимость: ' + newTotalPrice.toFixed(2) + '$';

    this.totalPrice = newTotalPrice.toFixed(2);
}

/* удаление продукта */
/* --------------------------------------------------------------------------------------- */

function decrement(idProduct, price) {
    if (order[idProduct] === 1) {

    } else {
        order[idProduct]--;

        decrementTotalPrice(price);
        changeQuantity(idProduct, order[idProduct]);
        changeTextQuantity(idProduct, order[idProduct], price);
    }
}

function decrementTotalPrice(price) {
    var newTotalPrice = totalPrice - price;

    var itemTotalPrice = document.getElementById('totalPrice');
    itemTotalPrice.innerText = 'Стоимость: ' + newTotalPrice.toFixed(2) + '$';

    this.totalPrice = newTotalPrice.toFixed(2);
}

function changeQuantity(idProduct, count) {
    var quantity = document.getElementById('quantity-' + idProduct);
    quantity.innerText = count;
}

function changeTextQuantity(idProduct, count, price) {
    var textQuantity = document.getElementById('p-quantity-' + idProduct);
    textQuantity.innerText = price + "$ - " + count + "шт.";
}


/* decrement */
/* --------------------------------------------------------------------------------------- */

function increment(idProduct, price) {
    order[idProduct]++;

    incrementTotalPrice(price);
    changeQuantity(idProduct, order[idProduct]); //берем метод из раздела "decrement"
    changeTextQuantity(idProduct, order[idProduct], price); //берем метод из раздела "decrement"
}

function incrementTotalPrice(price) {
    var newTotalPrice = parseFloat(totalPrice) + parseFloat(price);

    var itemTotalPrice = document.getElementById('totalPrice');
    itemTotalPrice.innerText = 'Стоимость: ' + newTotalPrice.toFixed(2) + '$';

    this.totalPrice = newTotalPrice.toFixed(2);
}

/* increment */
/* --------------------------------------------------------------------------------------- */

function makeOrder(userCount) {
    var address = document.getElementById('client-address').value;
    var phone = document.getElementById('client-phone').value;
    var checkData = checkAddressAndPhone(address, phone);

    if (parseFloat(userCount) < parseFloat(totalPrice)) {
        var message = "Недостаточно средств";
        showCountMessage(message);
    } else if (checkData !== "ok") {
        showCountMessage(checkData);
    } else {
        sendOrder(address, phone);
    }
}

function showCountMessage(message) {
    var form = document.getElementById('order-button');
    var checkFault = document.getElementById('p-fault');
    if (checkFault) checkFault.remove();

    var textMessage = document.createElement("p");
    textMessage.id = "p-fault"
    textMessage.textContent = message;
    textMessage.style.color = "red";

    form.appendChild(textMessage);
}

function sendOrder(address, phone) {
    order['totalPrice'] = totalPrice;
    order['address'] = address;
    order['phone'] = phone;
    var csrfToken = document.getElementById('csrf-id-modal').value;

    fetch('/order/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(order)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/order/success-order";
            } else if (response.status === 402) {
                return response.text()
                    .then(errorMessage => {
                        showCountMessage(errorMessage);
                });
            } else if (response.status === 400) {
                console.log('Не корректный запрос: ' + response.status);
            }
        })
        .catch(error => {
            console.error('Произошла ошибка:', error);
        });
}

function checkAddressAndPhone(address, phone) {
    var addressLength = address.trim().length;
    var phoneLength = phone.trim().length;

    if (addressLength < 6 || addressLength > 100) {
        return "Введен не корректный адрес";
    }
    if (phoneLength < 6 || phoneLength > 25) {
        return "Введен не корректный номер";
    }
    return "ok";
}

/* делать заказ */
/* --------------------------------------------------------------------------------------- */