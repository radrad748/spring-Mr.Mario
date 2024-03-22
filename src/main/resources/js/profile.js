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

/* модальное окно delete profile */
/* -------------------------------------------------------------------------------------- */

document.getElementById('modify-name').addEventListener('submit', function (event) {
   event.preventDefault();

   var firstName = document.getElementById('input-firstName').value;

   if (!validateName(firstName)) {
      var errorMessage = 'Имя должно иметь от 3 до 50 символов';
      showErrorMessage(errorMessage);
   } else {
      sendNameData(firstName);
   }
});

function sendNameData(firstName) {
   var csrfToken = document.getElementById('csrf-id-name').value;
   var id = document.getElementById('user-id').value;

   var data = {
      name: firstName
   };

   fetch(`/modify/name/${id}`, {
      method: 'POST',
      headers: {
         'Content-Type': 'application/json',
         'X-CSRF-TOKEN': csrfToken
      },
      body: JSON.stringify(data)
   })
       .then(response => {
          if (!response.ok) {
             if (response.status === 400) {
                var errorMessage = 'Имя должно иметь от 3 до 50 символов';
                showErrorMessage(errorMessage);
             } else {
                location.reload();
                console.error('Ошибка сервера: ' + response.status);
             }
          } else {
             location.reload();
          }
       })
       .catch(error => {
          console.error('Произошла ошибка:', error);
       });
}

/* изменить имя */
/* -------------------------------------------------------------------------------------- */

document.getElementById('modify-surname').addEventListener('submit', function (event) {
   event.preventDefault();

   var lastName = document.getElementById('input-lastName').value;

   if (!validateName(lastName)) {
      var errorMessage = 'Фамилия должно иметь от 3 до 50 символов';
      showErrorMessage(errorMessage);
   } else {
      sendSurenameData(lastName);
   }
});

function sendSurenameData(lastName) {
   var csrfToken = document.getElementById('csrf-id-surname').value;
   var id = document.getElementById('user-id').value;

   var data = {
      surname: lastName
   };

   fetch(`/modify/surname/${id}`, {
      method: 'POST',
      headers: {
         'Content-Type': 'application/json',
         'X-CSRF-TOKEN': csrfToken
      },
      body: JSON.stringify(data)
   })
       .then(response => {
          if (!response.ok) {
             if (response.status === 400) {
                var errorMessage = 'Фамилия должно иметь от 3 до 50 символов';
                showErrorMessage(errorMessage);
             } else {
                location.reload();
                console.error('Ошибка сервера: ' + response.status);
             }
          } else {
             location.reload();
          }
       })
       .catch(error => {
          console.error('Произошла ошибка:', error);
       });
}

/* изменить фамилию */
/* -------------------------------------------------------------------------------------- */

document.getElementById('modify-email').addEventListener('submit', function (event) {
   event.preventDefault();

   var email = document.getElementById('input-email').value;

   if (!validateEmail(email)) {
      var errorMessage = 'Вы ввели некорректный емайл';
      showErrorMessage(errorMessage);
   } else {
      sendEmailData(email);
   }

});

function validateEmail(email) {
   var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

   if (!emailRegex.test(email)) {
      return false;
   }
   return true;
}

function sendEmailData(email) {
   var csrfToken = document.getElementById('csrf-id-surname').value;
   var id = document.getElementById('user-id').value;

   var data = {
      email: email
   };

   fetch(`/modify/email/${id}`, {
      method: 'POST',
      headers: {
         'Content-Type': 'application/json',
         'X-CSRF-TOKEN': csrfToken
      },
      body: JSON.stringify(data)
   })
       .then(response => {
          if (response.ok) {
             location.reload();
          } else  {
             if (response.status === 409) {
                return response.text().then(errorMessage => {
                   showErrorMessage(errorMessage);
                });
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
/* изменить email */
/* -------------------------------------------------------------------------------------- */

function showErrorMessage(errorMessage) {
   var modalProfile = document.getElementById('myModal-profile-1');

   var existingErrorMessage = document.getElementById("p-error-message");
   if (existingErrorMessage) {
      existingErrorMessage.parentNode.removeChild(existingErrorMessage);
   }

   var validateMessage = document.createElement('p');
   validateMessage.textContent = errorMessage;
   validateMessage.id = 'p-error-message';
   validateMessage.style.color = 'red';

   modalProfile.appendChild(validateMessage);
}

function validateName(name) {
   if (name.trim() === '') {
      return false;
   }
   if (name.length < 3 || name.length > 50) {
      return false;
   }
   return true;
}
/* общие методы */
/* -------------------------------------------------------------------------------------- */