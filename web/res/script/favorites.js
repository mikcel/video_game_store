function remove_favorite(game_id) {

    $.ajax({
        url: '',
        method: 'POST',
        data: {
            game_id: game_id,
            action: 'remove_fav'
        },
        success: function () {
            $("#msg-modal").on('hidden.bs.modal', function () {
                location.reload();
            });
            set_up_msg_modal("Game Removed from Favorites", "Game has been successfully removed from your favorites");
        },
        error: function (xhr) {
            set_up_msg_modal("Error while removing from favorites", xhr.responseText);
        }
    });

}

function set_up_msg_modal(title, message) {

    $("#msg-title").text(title);
    $("#msg-body").text(message);
    $("#msg-modal").modal("show");

}