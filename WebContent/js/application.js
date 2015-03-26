/*
 * This file contains the system wide Javascript functionality
 * @author Ben Carr
 */

$(document).ready(function(){
	 
	// The following enables clicking and dragging to scroll along the guide 
	var aboutToDrag = false, isDragging = false, lastPosition, position, difference;
	$($(".guide-wrapper").selector).on("mousedown mouseup mousemove", function(e) {
	    if (e.type == "mousedown") {
	    	aboutToDrag = true, lastPosition = e.clientX;
	    }
	    if (e.type == "mouseup") {
	    	// Use a delay to prevent triggering a click and opening the modal window on mouseup
	    	setTimeout(function() {
	    		isDragging = false;
	    	}, 1);
	    }
	    if (e.type == "mousemove" && (aboutToDrag || isDragging)) {
	    	aboutToDrag = false;
	    	isDragging = true;
	        position = e.clientX;
	        difference = position - lastPosition;
	        $(this).scrollLeft($(this).scrollLeft() - difference);
	        lastPosition = e.clientX;
	    }
	});
	
	// Also remove the dragging when the mouseup event is fired anywhere in the window
	$(window).on("mouseup", function() {
		setTimeout(function() {
			isDragging = false;
		}, 1);
	});
	
	// Only show the modal window on click if not currently isDragging
	$(".movie").click(function(e) {
		if (!isDragging) {
			// Also ensure that dragging is not accidentally enabled on modal close
			aboutToDrag = false;
			$("#recordModal").modal();
			var movieName = $(this).data("movie-name");
		    $(".modal-movie-name").text(movieName);
		}
	});
	
});
