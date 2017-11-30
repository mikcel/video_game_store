function update_password(){

    var new_password = $("#new-password").val();
    var confirm_pass = $("#confirm-password").val();

    if (new_password !== confirm_pass){
        $("#reset-password-modal").modal("hide");
        set_up_msg_modal("Passwords do not match", "Passwords do not match. Please try again!");
        $("#msg-modal").on('hidden.bs.modal', function(){
           $("#reset-password-modal").modal("show");
        });
        return;
    }

    $.ajax({
        url: '',
        method: 'POST',
        data:{
            old_pass: $("#current-pass").val(),
            new_pass: new_password
        },
        success: function() {
            $("#reset-password-modal").modal("hide");
            $("#msg-modal").on('hidden.bs.modal', function(){
                document.location.href = "/u_settings/";
            });
            set_up_msg_modal("Password changed successfully", "Your password has been successfully changed!");
        },
        error: function(xhr){
            $("#reset-password-modal").modal("hide");
            set_up_msg_modal("Error while saving data", xhr.responseText);
            $("#msg-modal").on('hidden.bs.modal', function(){
                $("#reset-password-modal").modal("show");
            });
        },
        complete: function(){
            $("#reset-password-modal input").val("");
        }
    });

}

function set_up_msg_modal(title, message){

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}