$(document).ready(function(){
    $('#tbl_users').DataTable();
});

function unlock_lock_account(user_id){

    $.ajax({
        method: 'POST',
        url: '',
        data:{
            user_id: user_id,
            action: 'unlock_lock'
        } ,
        success: function(msg){
            set_up_msg_modal("User Account Updated", msg);
            $("#msg-modal").on("hide.bs.modal", function(){
                location.reload();
            });
        },
        error: function(xhr){
            set_up_msg_modal("Error while saving data", xhr.responseText);
        }
    });

}

function set_admin(user_id) {

    $.ajax({
        method: 'POST',
        url: '',
        data:{
            user_id: user_id,
            action: 'set_admin'
        },
        success: function(msg){
            set_up_msg_modal("User Account Updated", msg);
            $("#msg-modal").on("hide.bs.modal", function(){
                location.reload();
            });
        },
        error: function(xhr){
            set_up_msg_modal("Error while saving data", xhr.responseText);
        }
    });

}

function set_up_msg_modal(title, message){

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}