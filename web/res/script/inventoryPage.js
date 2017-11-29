$(document).ready(function(){
    $('#tbl_inventory').DataTable();
});

function update_stock(game_id){

    var new_qty = parseInt($("#tr_game_" + game_id).find("input.ipt-game-qty").val());

    if(isNaN(new_qty) || new_qty < 0){
        set_up_msg_modal("Invalid Qty", "Invalid Qty entered for game. Please correct and try again!");
        return;
    }

    $.ajax({
        method: "POST",
        url: '',
        data: {
            game_id: game_id,
            new_qty: new_qty
        },
        success: function(){
            $("#msg-modal").on('hidden.bs.modal', function(){
                location.reload();
            });
            set_up_msg_modal("Qty updated successfully", "Qty in stock has changed!");
        },
        error: function (xhr) {
            set_up_msg_modal("Error while updating qty for game", xhr.responseText)
        }
    });

}

function set_up_msg_modal(title, message){

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}