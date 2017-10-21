$(function () {

    $("#form-login").submit(function (event) {

        event.preventDefault();
        login_user();

    });

});

function login_user() {

    if ($("#user_email").val() === "" || $("#user_password").val() === "") {
        set_up_msg_modal("Empty Fields", "Please enter both your email and password to login.");
        return;
    }

    var form_login = $("#form-login");
    $.ajax({
        method: form_login.attr("method"),
        url: form_login.attr("action"),
        data: form_login.serialize(),
        success: function(){
            document.location.href = "/";
        },
        error: function (xhr) {
            set_up_msg_modal("Login Error", xhr.responseText)
        }
    })

}


function set_up_msg_modal(title, message) {

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}