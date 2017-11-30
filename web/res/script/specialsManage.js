function send_request(action){

    var games_selected = get_games_checked();

    if (games_selected.length === 0){return;}

    $.ajax({
        method: 'POST',
        url:'',
        data: {
            action: action,
            games: games_selected
        },
        success: function(msg){
            $("#msg-modal").on('hidden.bs.modal', function(){
                location.reload();
            });
            set_up_msg_modal("Specials Game Updated", msg);
        },
        error: function(xhr){
            set_up_msg_modal("Error while updating", xhr.responseText);
        }
    })

}

function get_games_checked(){
    var game_ids_selected = [];
    $("tr.tr-specials:has(input.ipt-game-checked:checked)").each(function(){
        game_ids_selected.push(parseInt($(this)[0].id.replace("tr_game_", "")));
    });

    if (game_ids_selected.length === 0){
        set_up_msg_modal("No games selected", "Please select at least one game to proceed");
    }

    return game_ids_selected;

}

function set_up_msg_modal(title, message){

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}