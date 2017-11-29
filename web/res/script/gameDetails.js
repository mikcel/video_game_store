$(document).ready(function(){
    $("#frm-edit-game").submit(function (event) {

        event.preventDefault();
        update_game();

    });
});

function update_game(){

    var game_form = $("#frm-edit-game");
    $.ajax({
        method: game_form.attr("method"),
        url: game_form.attr("action"),
        data: game_form.serialize(),
        success: function(){
            $("#edit-game-modal").modal("hide");
            $("#msg-modal").on('hidden.bs.modal', function(){
                location.reload();
            });
            set_up_msg_modal("Game Updated successfully", "All details entered were saved!");
        },
        error: function (xhr) {
            $("#edit-game-modal").modal("hide");
            $("#msg-modal").on('hidden.bs.modal', function(){
                $("#edit-game-modal").modal("show");
            });
            set_up_msg_modal("Updating Game Error", xhr.responseText)
        }
    });

}

function add_game_cart(game_id){

    $.ajax({
        url: '/cart_game/',
        method: 'POST',
        data:{
            game_id: game_id,
            action: 'add_game'
        },
        success: function(returned_data){
            $("#cart-items-amt").html(returned_data["cart_size"]);
            set_up_msg_modal("Item Added", returned_data["message"]);
        },
        error: function(xhr){
            set_up_msg_modal("Error while adding to cart", xhr.responseText);
        }
    })

}

function edit_game(){

    $("#edit-game-modal").modal("show");

}

function set_up_msg_modal(title, message){

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}