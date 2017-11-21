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
            set_up_msg_modal("Reset Password Error", xhr.responseText);
        }
    })
    
}

function set_up_msg_modal(title, message){

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}