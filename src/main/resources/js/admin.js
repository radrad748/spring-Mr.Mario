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
function showSelectDeleteRoles(id) {
    var buttonDelete = document.getElementById('button-delete-roles' + id);
    buttonDelete.style.display = 'none';

    var selectDelete = document.getElementById('select-delete-roles' + id);
    selectDelete.style.display = 'block';
    var buttonAccept = document.getElementById('button-send1-roles' + id);
    buttonAccept.style.display = 'block';
}

function changeUserRoles(id, currentPage, pageSize, command) {
    var urlAddRole = '/admin/' + command + '/role/user/' + id;
    var url = '/admin/?page=' + currentPage + '&size=' + pageSize;
    var role = document.getElementById('select-add-roles' + id).value;
    var csrfToken = document.getElementById('csrf-id-modal').value;

    var data = {
        role: role
    };

    fetch(urlAddRole,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(data)
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

/* add/delete roles user */
/* ------------------------------------------------------------------------------------------------ */