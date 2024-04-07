var userId;
var buttonDelete = document.getElementById('modal-delete');
var overlay = document.getElementById('overlay');

function showButtonDelete(id) {
    userId = id;
    buttonDelete.style.display = 'block';
    overlay.style.display = 'block';
}
function deleteModalDelete() {
    buttonDelete.style.display = 'none';
    overlay.style.display = 'none';
}
function deleteUser(currentPage, pageSize) {
    var urlDelete = '/admin/delete/user/' + userId;
    var url = '/admin/?page=' + currentPage + '&size=' + pageSize;
    var csrfToken = document.getElementById('csrf-id-modal').value;

    fetch(urlDelete, {
        method: 'DELETE',
        headers: {
            'X-CSRF-TOKEN': csrfToken
        }
    })
        .then(response => {
            if (response.ok) {
                window.location.href = url;
            } else  {
                console.log('Ошибка: ' + response.status);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

/* удалить user */
/* ------------------------------------------------------------------------------------------------ */

function blockAndUnblockUser(id, currentPage, pageSize, command) {
    var urlBlock = '/admin/' + command + '/user/' + id;
    var url = '/admin/?page=' + currentPage + '&size=' + pageSize;
    var csrfToken = document.getElementById('csrf-id-modal').value;

    fetch(urlBlock, {
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': csrfToken
        }
    })
        .then(response => {
            if (response.ok) {
                window.location.href = url;
            } else  {
                console.log('Ошибка: ' + response.status);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}


/* Block user */
/* ------------------------------------------------------------------------------------------------ */

function showSelectAddRoles(id) {
    var buttonAdd = document.getElementById('button-add-roles' + id);
    buttonAdd.style.display = 'none';

    var selectAdd = document.getElementById('select-add-roles' + id);
    selectAdd.style.display = 'block';
    var buttonAccept = document.getElementById('button-send-roles' + id);
    buttonAccept.style.display = 'block';
}



/* add roles user */
/* ------------------------------------------------------------------------------------------------ */