package com.sky.servlets;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sky.models.*;

/**
 * The Guide servlet handles the GET request for the guide and is responsible for generating and sending data to the JSP pages
 * It also is responsible for reading data from the uploaded XML data file
 * @author Ben Carr
 *
 */
public class Guide extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Guide.class.getName());
	
	// Instance variables to store the channel objects and the times
	private Map<String,Channel> channels = new LinkedHashMap<String,Channel>();
	private List<String> times = new ArrayList<String>();
	
	// Define the first and last hours of the guide as constants
	private static final int FIRST_HOUR = 9;
	private static final int LAST_HOUR = 20;
	
	/**
	 * Overrides the init method of the servlet
	 * This method creates the channel objects and stores them in "channels"
	 * It also calculates and stores the hours to display on the guide
	 * @param config	The servlet config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		// Call the super init method
		super.init(config);
		
		// Create and add the channels to the map
		// A map is used to quickly find a channel and add new movies to it
		channels.put("sean_channel", new Channel("Sean Connery"));
		channels.put("george_channel", new Channel("George Lazenby"));
		channels.put("roger_channel", new Channel("Roger Moore"));
		channels.put("timothy_channel", new Channel("Timothy Dalton"));
		channels.put("pierce_channel", new Channel("Pierce Brosnan"));
		channels.put("daniel_channel", new Channel("Daniel Craig"));
		
		// Create two date time formatters
		DateTimeFormatter parseFormatter = DateTimeFormat.forPattern("H");
		DateTimeFormatter printFormatter = DateTimeFormat.forPattern("h a");
		
		// For each hour in the specified range
		for (int i=FIRST_HOUR; i<=LAST_HOUR-1; i++) {
			// Parse the hour into a LocalTime object
			LocalTime hour = parseFormatter.parseLocalTime(Integer.toString(i));
			// Print and store the time in the list with the specified display formatting
			times.add(printFormatter.print(hour));
		}
	}

	
	/**
	 * Handles GET requests, sets the parameters needed for the page and renders a JSP file to display
	 * Overrides doGet from HTTPServlet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Attempt to load data in from the XML data file
		try {
			loadMoviesFromXML();
		} catch (JDOMException | IllegalArgumentException e) {
			// If there was an error in the file, log it and display an error to the user
			LOGGER.log(Level.SEVERE, e.getMessage());
			request.setAttribute("error", "Error: The data file is malformed. Please upload a correct version.");
		} catch (IOException ioe) {
			// If there was an error reading the file, log it
			// This is usually becuase the file has not been uploaded yet
			LOGGER.log(Level.INFO, "No XML data file has been uploaded yet.");
		}
		
		// Set parameters to send to the view
		request.setAttribute("channels", channels);
		request.setAttribute("times", times);
		request.setAttribute("startHour", FIRST_HOUR);
		
		// Render the guide JSP view
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/guide.jsp");
		requestDispatcher.forward(request, response);
	}
	
	
	/**
	 * Reads and parses the XML data file if present. The data is cast into Movie objects which are then added to the relevant Channel 
	 * @throws JDOMException			Thrown if the XML file is malformed and cannot be parsed
	 * @throws IOException				Thrown if there is an error reading the file
	 * @throws IllegalArgumentException	Thrown if the start or end time cannot be parsed
	 */
	private void loadMoviesFromXML() throws JDOMException, IOException, IllegalArgumentException {
		String filePath = getServletContext().getRealPath("") + File.separator + Upload.uploadFileName;
		
		// Clear the existing channel data
		for (Channel channel : channels.values()) {
			channel.removeAllMovies();
		}
		
		// Parse the XML file
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(filePath);
		
		// Get all the "movie" nodes
		Element rootNode = doc.getRootElement();
		List<Element> movieNodes = rootNode.getChildren("movie");
		
		// For each movie node
		for (Element movieNode : movieNodes) {
			// Get its ID
			int movieId = Integer.parseInt(movieNode.getAttributeValue("id"));
			
			// Get the channel name
			Element channelNode = movieNode.getChildren().get(0);
			Channel channel = channels.get(channelNode.getName());
			
			// Get the movie name
			String movieName = channelNode.getChildText("name");
			
			// Parse the start and end times
			DateTimeFormatter formatter = DateTimeFormat.forPattern("h.ma");
			LocalTime movieStartTime = formatter.parseLocalTime(channelNode.getChildText("start_time").toLowerCase());
			LocalTime movieEndTime = formatter.parseLocalTime(channelNode.getChildText("end_time").toLowerCase());
			
			// Create a new movie object with the parameters
			Movie movie = new Movie(movieId, movieName, movieStartTime, movieEndTime);
			
			// Don't add a movie if it is outside the guide range or has a duration of zero
			if (movie.getMinuteDuration() > 0 && (movie.getEndHour() > FIRST_HOUR || (movie.getEndHour() == FIRST_HOUR && movie.getEndMinutes() > 0)) && movie.getStartHour() < LAST_HOUR) { 
				channel.addMovie(movie);
			}
		}
	}
}
