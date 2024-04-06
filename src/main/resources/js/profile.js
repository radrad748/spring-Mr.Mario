var topUp = document.getElementById('top-up');
var modalTopUp = document.getElementById('myModal-profile');
var divTopUp = document.getElementById('div-top-up');
var buttonIdCounter = 1;

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

document.getElementById('modify-password').addEventListener('submit', function (event) {
   event.preventDefault();

   var password = document.getElementById('input-password').value;
   var newPassword = document.getElementById('input-new-password').value;

   var checkPassword = validatePassword(password, newPassword);

   if (checkPassword === "success") {
      sendPasswordData(password, newPassword);
   } else {
      showErrorMessage(checkPassword);
   }
});

function validatePassword(password, newPassword) {
   if (password.length < 6 || password.length > 50 || newPassword.length < 6 || newPassword.length > 50) {
      return "Пароль должен иметь длину от 6 до 50 символов";
   }

   if (!/[0-9]/.test(password) || !/[0-9]/.test(newPassword)) {
      return "Пароль должен содержать цифры";
   }

   if (!/[a-z]/.test(password) || !/[A-Z]/.test(password) || !/[a-z]/.test(newPassword) || !/[A-Z]/.test(newPassword)) {
      return "Пароль должен содержать символы верхнего и нижнего регистра";
   }
   return "success";
}

function sendPasswordData(password, newPassword) {
   var csrfToken = document.getElementById('csrf-id-password').value;
   var id = document.getElementById('user-id').value;

   var data = {
      password: password,
      newPassword: newPassword
   };

   fetch(`/modify/password/${id}`, {
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

/* изменить пароль */
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

var myOrders = document.getElementById('my-orders');
var modalMyOrders = document.getElementById('modal-my-orders');
var divMyOrders =  document.getElementById('div-my-orders');

myOrders.addEventListener('click', function (event) {
   event.preventDefault();

   modalMyOrders.style.display = 'block';
});

document.addEventListener('click', function (event) {
   if (!modalMyOrders.contains(event.target) && !divMyOrders.contains(event.target)) {
      modalMyOrders.style.display = 'none';
   }
});
/* модальное окно для заказов */
/* -------------------------------------------------------------------------------------- */

function getProducts(period) {
   var button = document.getElementById('orders-button');
   button.textContent = textButton(period);

   var url = '/order/user/orders?period=' + period;

   fetch(url)
       .then(response => {
          if (!response.ok) {
             throw new Error('Код ответа: ' + response.status);
          }
          return response.json();
       })
       .then(data => {
         showNewListOfProducts(data);
       })
       .catch(error => {
          console.error('Ошибка', error);
       });
}

function showNewListOfProducts(data) {
   var divOrders = document.getElementById('div-orders');
   if (divOrders) {
      divOrders.remove();
   }

   var mainDivOrders = document.getElementById('main-div-orders');
   mainDivOrders.innerHTML = '';

   var pTitle = document.createElement('p');
   pTitle.textContent = 'Заказы';
   pTitle.style.fontSize = '24px';
   pTitle.style.fontStyle = 'italic';
   mainDivOrders.appendChild(pTitle);

   var newDivOrders = document.createElement('div');
   newDivOrders.id = 'div-orders';
   mainDivOrders.appendChild(newDivOrders);


   data.forEach(function (order) {

      var ulElement = document.createElement('ul');
      ulElement.classList.add('list-group', 'list-group-flush');
      addLiElements(ulElement, order);
      newDivOrders.appendChild(ulElement);

      /* --------------------------------------------------------------------------- */
      /* добавляем лист */

      var generalListMenu = document.createElement("div");
      generalListMenu.classList.add('accordion');

      var subGeneralListMenu = document.createElement('div');
      subGeneralListMenu.classList.add('accordion-item');

      generalListMenu.appendChild(subGeneralListMenu);

      var productsDiv = document.createElement('div');
      productsDiv.classList.add('accordion-collapse', 'collapse');
      productsDiv.setAttribute('data-bs-parent', '#accordionExample');

      addButtonList(subGeneralListMenu, productsDiv);
      subGeneralListMenu.appendChild(productsDiv);

      order.listMenu.forEach(function (menu) {

         var divSpaceForMenu = document.createElement('div');
         divSpaceForMenu.classList.add('accordion-body');
         addUlElementForDivMenu(divSpaceForMenu, menu);

         productsDiv.appendChild(divSpaceForMenu);

      });

      newDivOrders.appendChild(generalListMenu);

      /* добавляем лист */
      /* --------------------------------------------------------------------------- */

      var brElement = document.createElement('br');
      newDivOrders.appendChild(brElement);
   });
}

function addLiElements(ulElement, order) {
   var liElementDateOrder = document.createElement('li');
   liElementDateOrder.classList.add('list-group-item');
   liElementDateOrder.textContent = 'Дата заказа: ' + order.date;
   ulElement.appendChild(liElementDateOrder);

   var liElementRestaurantName = document.createElement('li');
   liElementRestaurantName.classList.add('list-group-item');
   liElementRestaurantName.textContent = 'Ресторан: ' + order.nameRestaurant;
   ulElement.appendChild(liElementRestaurantName);

   var liElementPrice = document.createElement('li');
   liElementPrice.classList.add('list-group-item');
   liElementPrice.textContent = 'Сумма заказа: ' + order.sum + '$';
   ulElement.appendChild(liElementPrice);

   var liElementBlank = document.createElement('li');
   liElementBlank.classList.add('list-group-item');
   ulElement.appendChild(liElementBlank);
}

function textButton(period) {
   if (period === 'week') return 'За последнюю неделю';
   else if (period === 'month') return 'За последний месяц';
   else if (period === 'year') return 'За последний год';
   else if (period === 'all') return 'За все время';
}

function addButtonList(subGeneralListMenu, productsDiv) {
   var collapseId = 'collapseOne' + buttonIdCounter;
   buttonIdCounter++;

   var nameButton = document.createElement('h2');
   nameButton.classList.add('accordion-header');

   var button = document.createElement('button');
   button.classList.add('accordion-button');
   button.setAttribute('type', 'button');
   button.setAttribute('data-bs-toggle', 'collapse');
   button.setAttribute('data-bs-target', '#' + collapseId);
   button.setAttribute('aria-expanded', 'false');
   button.setAttribute('aria-controls', collapseId);
   button.textContent = 'Лист';

   nameButton.appendChild(button);

   subGeneralListMenu.appendChild(nameButton);

   var bsCollapse = new bootstrap.Collapse(button, {
      target: '#' + collapseId
   });
   button.addEventListener('click', function () {
      if (productsDiv.classList.contains('show')) {
         productsDiv.classList.remove('show'); // Если класс 'show' уже есть, удаляем его
      } else {
         productsDiv.classList.add('show'); // Если класс 'show' отсутствует, добавляем его
      }
   });
}

function addUlElementForDivMenu(divSpaceForMenu, menu) {
   var ulElementMenu = document.createElement('ul');
   ulElementMenu.classList.add('list-group', 'list-group-flush');
   addLiElementsMenu(ulElementMenu, menu);

   divSpaceForMenu.appendChild(ulElementMenu);
}
function addLiElementsMenu(ulElementMenu, menu) {
   var liElementName = document.createElement('li');
   liElementName.classList.add('list-group-item');
   liElementName.textContent = 'Имя продукта: ' + menu.menu.name;
   ulElementMenu.appendChild(liElementName);

   var liElementPrice = document.createElement('li');
   liElementPrice.classList.add('list-group-item');
   liElementPrice.textContent = 'Цена: ' + menu.menu.price + '$';
   ulElementMenu.appendChild(liElementPrice);

   var liElementCount = document.createElement('li');
   liElementCount.classList.add('list-group-item');
   liElementCount.textContent = 'Кол-во: ' + menu.count;
   ulElementMenu.appendChild(liElementCount);

   var liElementBlank = document.createElement('li');
   liElementBlank.classList.add('list-group-item');
   ulElementMenu.appendChild(liElementBlank);
}

/* получение заказов */
/* -------------------------------------------------------------------------------------- */