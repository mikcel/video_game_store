function update_price(cart_game_id){

    var tr_game = $("#tr_game_" + cart_game_id);

    var discounted_price = parseFloat(tr_game.find("td.td-discounted-price > span.spn-discounted-price").text());
    var qty = parseInt(tr_game.find("input.ipt-game-qty").val());

    if (isNaN(qty) || qty < 0){
        set_up_msg_modal("Incorrect quantity", "Game Quantity can only be integer and great than or equal to 0.");
        return;
    }

    var total_price = discounted_price * qty;

    tr_game.find("td.game-total-price > span.total-price").text(total_price.toFixed(2));

}

function save_game(cart_game_id){

    var qty = parseInt($("#tr_game_" + cart_game_id).find("input.ipt-game-qty").val());

    $.ajax({
        url: '/cart_game/',
        method: 'POST',
        data:{
            cart_game_id: cart_game_id,
            game_qty: qty,
            action: 'update_game'
        },
        success: function(returned_data){
            $("#cart-items-amt").html(returned_data["cart_size"]);
            set_up_msg_modal("Item Added", returned_data["message"]);
        },
        error: function(xhr){
            set_up_msg_modal("Save Game Error", xhr.responseText);
        }
    });

}

function remove_game(cart_game_id){

    $.ajax({
        url: '/cart_game/',
        method: 'POST',
        data:{
            cart_game_id: cart_game_id,
            action: 'remove_game'
        },
        success: function(returned_data){
            $("#cart-items-amt").html(returned_data["cart_size"]);
            set_up_msg_modal("Item Removed", returned_data["message"]);

            $('#msg-modal').on('hidden.bs.modal', function () {
                location.reload();
            });

        },
        error: function(xhr){
            set_up_msg_modal("Game Removal Error", xhr.responseText);
        }
    });

}

function empty_cart(){

    $.ajax({
        url: '/cart_game/',
        method: 'POST',
        data:{
            action: 'empty_cart'
        },
        success: function(returned_data){
            $("#cart-items-amt").html(returned_data["cart_size"]);
            set_up_msg_modal("Item Removed", returned_data["message"]);

            $('#msg-modal').on('hidden.bs.modal', function () {
                location.reload();
            });

        },
        error: function(xhr){
            set_up_msg_modal("Game Removal Error", xhr.responseText);
        }
    });

}

function set_up_msg_modal(title, message){

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}