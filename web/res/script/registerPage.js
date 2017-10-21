$(function () {

    $("#form-register-user").submit(function(event) {
        event.preventDefault();
        validate_send_form();
    });

});

function validate_send_form(){

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

    if ($("#ipt-password").val() !== $("#ipt-conf-pass").val()){
        set_up_msg_modal("Passwords don't match", "The passwords entered do not match. Please try again!");
        return;
    }

    var form = $("#form-register-user");
    $.ajax({
        method: form.attr("method"),
        url: form.attr("action"),
        data: form.serialize(),
        success: function(){
            set_up_msg_modal("User Registered Successfully", "You have been successfully registered in the system!");
            $("#msg-modal").on("hide.bs.modal", function(){
                document.location.href = "/";
            });
        },
        error: function (xhr, err) {
            set_up_msg_modal("Error while saving data", xhr.responseText);
        }
    });
}

function get_ipts(){
    return $("#div-register-user").find("input");
}

function set_up_msg_modal(title, message){

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}