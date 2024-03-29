var order = {

}
function addProduct(productId) {
    var btOrder = document.getElementById('scroll-order');
    btOrder.style.display = 'block';

    if (order[productId]) {
        order[productId]++;
    } else {
        order[productId] = 1;
    }
}

/* подсчет товаров */
/* ----------------------------------------------------------------------------------------------- */

document.getElementById('scroll-order').addEventListener('click', function (event) {
    event.preventDefault();

    var csrfToken = document.getElementById('csrf-id-modal').value;
    order.restaurantTitle = 'The lot';


    fetch('/order/preparation', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(order)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/order/preparation';
            } else {
                throw new Error('Ошибка: ' + response.status);
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
});



/* отправка товаров на сервер */
/* ----------------------------------------------------------------------------------------------- */