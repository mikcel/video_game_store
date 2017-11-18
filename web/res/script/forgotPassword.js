$(function () {

    $("#form-forgot-pass").submit(function (event) {

        event.preventDefault();
        send_email();

    });

});

function send_email() {

    if ($("#user_email").val() === "" || $("#user_login").val() === "") {
        set_up_msg_modal("Empty Fields", "Please enter both your email and password to login.");
        return;
    }

    var form_login = $("#form-forgot-pass");
    $.ajax({
        method: form_login.attr("method"),
        url: form_login.attr("action"),
        data: form_login.serialize(),
        success: function(){
            set_up_msg_modal("Email sent successfully", "We have sent you an email with the temporary password");
        },
        error: function (xhr) {
            set_up_msg_modal("Send Email Error", xhr.responseText)
        }
    })

}


function set_up_msg_modal(title, message) {

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}