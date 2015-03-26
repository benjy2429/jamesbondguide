package com.sky.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.JDOMException;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sky.datastore.DataStoreManager;
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
	private List<String> times = new ArrayList<String>();
	
	// Define the first and last hours of the guide as constants
	private static final int FIRST_HOUR = 9;
	private static final int LAST_HOUR = 20;
	
	/**
	 * Overrides the init method of the servlet
	 * This method calculates and stores the hours to display on the guide
	 */
	public void init(ServletConfig config) throws ServletException {
		// Call the super init method
		super.init(config);
		
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
			DataStoreManager dataStoreManager = new DataStoreManager(getServletContext());
			ArrayList<Channel> channels = dataStoreManager.getChannelsWithMovies(FIRST_HOUR, LAST_HOUR);
			request.setAttribute("channels", channels);
			
		} catch (JDOMException | IllegalArgumentException e) {
			// If there was an error in the file, log it and display an error to the user
			LOGGER.log(Level.SEVERE, e.getMessage());
			request.setAttribute("error", "Error: The data file is malformed. Please upload a valid file.");
			
		} catch (IOException ioe) {
			// If there was an error reading the file, log it
			// This is usually becuase the file has not been uploaded yet
			LOGGER.log(Level.INFO, "No XML data file has been uploaded yet.");
		}
		
		// Set parameters to send to the view
		request.setAttribute("times", times);
		request.setAttribute("startHour", FIRST_HOUR);
		
		// Render the guide JSP view
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/guide.jsp");
		requestDispatcher.forward(request, response);
	}
}
