$(function () {

    $("#form-reset-pass").submit(function (event) {

        event.preventDefault();
        reset_password();

    });

});

function reset_password() {

    get_ipts().each(function(){
        $(this).parents("div.form-group").removeClass("has-error");
    });

    var valid_ipts = get_ipts().each(function(){

        var ipt_val = $(this).val();
        if (ipt_val !== "" || ipt_val.length > 0){
            var ipt_lbl = $(this).previousSibling().text();
            set_up_msg_modal("Missing Value", "Please enter a value for your " + ipt_lbl);
            $(this).parent("div.form-group").addClass("has-error");
            return false;
        }

    });

    if (!valid_ipts){
        return;
    }

    if ($("#user_new_pass").val() !== $("#user_conf_pass").val()){
        set_up_msg_modal("Passwords don't match", "The passwords entered do not match. Please try again!");
        return;
    }

    var form_login = $("#form-reset-pass");
    $.ajax({
        method: form_login.attr("method"),
        url: form_login.attr("action"),
        data: form_login.serialize(),
        success: function(){
            $("#msg-modal").on("hide.bs.modal", function(){
                document.location.href = "/";
            });
            set_up_msg_modal("Password Reset!", "Your password has been reset successfully.");
        },
        error: function (xhr) {
            set_up_msg_modal("Reset Password Error", xhr.responseText);
        }
    });

}

function get_ipts(){
    return $("#form-reset-pass").find("input[required='true']");
}

function set_up_msg_modal(title, message) {

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}