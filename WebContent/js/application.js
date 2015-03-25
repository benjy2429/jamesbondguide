$("#recordModal").on("show.bs.modal", function(e) {
    var movieName = $(e.relatedTarget).data("movie-name");
    $(".modal-movie-name").text(movieName);
});