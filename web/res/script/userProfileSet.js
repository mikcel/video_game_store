$(function () {

    $("#form-update-user").submit(function(event) {
        event.preventDefault();
        validate_send_form();
    });

    $("#ipt-cc-expiry").val(new Date().toDateInputValue());

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

    var form = $("#form-update-user");
    $.ajax({
        method: form.attr("method"),
        url: form.attr("action"),
        data: form.serialize(),
        success: function(){
            set_up_msg_modal("User Data Updates Successfully", "Your data has been successfully saved!");
            $("#msg-modal").on("hide.bs.modal", function(){
                location.reload();
            });
        },
        error: function (xhr) {
            set_up_msg_modal("Error while saving data", xhr.responseText);
        }
    });
}

function get_ipts(){
    return $("#form-update-user").find("input[required='true']");
}

function set_up_msg_modal(title, message){

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}