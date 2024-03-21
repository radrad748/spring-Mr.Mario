var topUp = document.getElementById('top-up');
var modalTopUp = document.getElementById('myModal-profile');
var divTopUp = document.getElementById('div-top-up');

topUp.addEventListener('click', function (event) {
   event.preventDefault();

   modalTopUp.style.display = 'block';
});

document.addEventListener('click', function (event) {
   if (!modalTopUp.contains(event.target) && !divTopUp.contains(event.target)) {
      modalTopUp.style.display = 'none';
   }
});
/* модальное окно для пополнее счета */
/* -------------------------------------------------------------------------------------- */

var changeData = document.getElementById('change-data');
var modalChangeData = document.getElementById('myModal-profile-1');
var divChangeData =  document.getElementById('div-change-data');

changeData.addEventListener('click', function (event) {
   event.preventDefault();

   modalChangeData.style.display = 'block';
});

document.addEventListener('click', function (event) {
   if (!modalChangeData.contains(event.target) && !divChangeData.contains(event.target)) {
      modalChangeData.style.display = 'none';
   }
});

/* модальное окно для изменение данных */
/* -------------------------------------------------------------------------------------- */

var deleteProfile = document.getElementById('delete-profile');
var modalDeleteProfile = document.getElementById('myModal-profile-2');
var divDeleteProfile = document.getElementById('delete-profile');

deleteProfile.addEventListener('click', function (event) {
   event.preventDefault();

   modalDeleteProfile.style.display = 'block';
});

document.addEventListener('click', function (event) {
   if (!modalDeleteProfile.contains(event.target) && !divDeleteProfile.contains(event.target)) {
      modalDeleteProfile.style.display = 'none';
   }
});