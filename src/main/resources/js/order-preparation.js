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
    if (parseFloat(userCount) < parseFloat(totalPrice)) {
        var message = "Недостаточно средств";
        showCountMessage(message);
    } else {
        sendOrder();
    }
}

function showCountMessage(message) {
    var divMain = document.getElementById('div-main');

    var textMessage = document.createElement("p");
    textMessage.textContent = message;
    textMessage.style.color = "red";

    divMain.appendChild(textMessage);
}

function sendOrder() {
    order['totalPrice'] = totalPrice;
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

            } else {
                return response.text()
                    .then(errorMessage => {
                        showCountMessage(errorMessage);
                });
            }
        })
        .catch(error => {
            console.error('Произошла ошибка:', error);
        });
}

/* делать заказ */
/* --------------------------------------------------------------------------------------- */