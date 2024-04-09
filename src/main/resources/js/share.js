var buttonOrderShare ;
var modalSHare;

function orderShare(id) {
    buttonOrderShare = document.getElementById('button-order-share' + id);
    modalSHare = document.getElementById('modal-share' + id);

    modalSHare.style.display = 'block';
}

/*buttonOrderShare.addEventListener('click', function (event) {
    event.preventDefault();

    modalSHare.style.display = 'block';
});*/

document.addEventListener('click', function (event) {
    if (!modalSHare.contains(event.target) && !buttonOrderShare.contains(event.target)) {
        modalSHare.style.display = 'none';
    }
});

/* модальное окно заказа */
/* ------------------------------------------------------------------------------------------ */