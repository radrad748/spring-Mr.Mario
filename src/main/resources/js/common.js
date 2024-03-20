var modal = document.getElementById('myModal');

function openModal() {
    modal.style.display = "block";
}
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}