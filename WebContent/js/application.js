/*
 * This file contains the system wide Javascript functionality
 * @author Ben Carr
 */

$(document).ready(function(){
	// When showing the modal window, set the movie title to the value stored in the callers data attribute
	 $("#recordModal").on("show.bs.modal", function(e) {
		    var movieName = $(e.relatedTarget).data("movie-name");
		    $(".modal-movie-name").text(movieName);
	}); 
	 
	// The following enables clicking and dragging to scroll along the guide 
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
	
	// Also remove the attachment when the mouseup event is fired anywhere in the window
	$(window).on("mouseup", function(e) {
	    attachment = false;
	});
	
});


