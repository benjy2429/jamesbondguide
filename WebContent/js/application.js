$(document).ready(function(){	 
	 $("#recordModal").on("show.bs.modal", function(e) {
		    var movieName = $(e.relatedTarget).data("movie-name");
		    $(".modal-movie-name").text(movieName);
	}); 
	 
	var attachment = false, lastPosition, position, difference;
	$($(".times").selector).on("mousedown mouseup mousemove", function(e) {
	    if (e.type == "mousedown") attachment = true, lastPosition = e.clientX;
	    if (e.type == "mouseup") attachment = false;
	    if (e.type == "mousemove" && attachment) {
	        position = e.clientX;
	        difference = position - lastPosition;
	        $(".guide-wrapper").scrollLeft($(".guide-wrapper").scrollLeft() - difference);
	        lastPosition = e.clientX;
	    }
	});
	
	$(window).on("mouseup", function(e) {
	    attachment = false;
	});
	
});


