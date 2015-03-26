package com.sky.datastore;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sky.models.Channel;
import com.sky.models.Movie;

/**
 * Handles reading and writing of the data store XML file
 * @author Ben Carr
 *
 */
public class DataStoreManager {
	// Store the file name and path as constants and instance variables
	private static final String DATASTORE_FILE_NAME = "movie-data.xml";
	private String uploadPath;
	
	
	/**
	 * Constructor for the data store manager
	 * @param context The servlet context used to generate the uploaded file path
	 */
	public DataStoreManager(ServletContext context) {
		uploadPath = context.getRealPath("") + File.separator + DATASTORE_FILE_NAME;
	}

	
	/**
	 * Responsible for uploading a new XML data file to the server
	 * @param request The request parameters containing the file to be uploaded
	 * @throws FileUploadException Thrown if there is an error uploading to the server
	 * @throws Exception Thrown if the file is the wrong type or for any other error
	 */
	public void uploadDataStore(HttpServletRequest request) throws FileUploadException, Exception {
		// Parse the request that was selected
        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        
        for (FileItem item : items) {
        	
        	// Ensure we are checking the file and not the form fields 
            if (!item.isFormField()) {
            	
            	// Get the file name and ensure it is the correct type, otherwise throw an exception
            	String fileName = item.getName();
            	if (fileName != null && !fileName.endsWith("xml")) {
            		throw new Exception("Error: Only XML files can be uploaded.");
            	}
            	
            	// Write the file to the server
                File storeFile = new File(uploadPath);
                item.write(storeFile);
            }
        }
	}
	
	
	/**
	 * Creates the channel objects
	 * @return A LinkedHashMap of Channel objects with its ID as the key
	 */
	public LinkedHashMap<String,Channel> createChannels() {
		LinkedHashMap<String, Channel> channels = new LinkedHashMap<String,Channel>();
		
		// Create and add the channels to the map
		// A map is used to quickly find a channel when adding new movies to it
		channels.put("sean_channel", new Channel("Sean Connery"));
		channels.put("george_channel", new Channel("George Lazenby"));
		channels.put("roger_channel", new Channel("Roger Moore"));
		channels.put("timothy_channel", new Channel("Timothy Dalton"));
		channels.put("pierce_channel", new Channel("Pierce Brosnan"));
		channels.put("daniel_channel", new Channel("Daniel Craig"));
		
		return channels;
	}
	
	
	/**
	 * Reads and parses the XML data file if present. The data is cast into Movie objects which are then added to the relevant Channel 
	 * @param channels The map of channels to add movies to. If null, new channels will be created
	 * @param firstHour	The first hour to be displayed on the guide
	 * @param lastHour The last hour to be displayed on the guide
	 * @throws JDOMException Thrown if the XML file is malformed and cannot be parsed
	 * @throws IOException Thrown if there is an error reading the file
	 * @throws IllegalArgumentException	Thrown if the start or end time cannot be parsed
	 */
	public LinkedHashMap<String,Channel> loadMovies(LinkedHashMap<String,Channel> channels, int firstHour, int lastHour) throws JDOMException, IOException, IllegalArgumentException {
		if (channels == null) {
			channels = createChannels();
		}
		
		// Parse the XML file
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(uploadPath);
		
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
			if (movie.getMinuteDuration() > 0 && (movie.getEndHour() > firstHour || (movie.getEndHour() == firstHour && movie.getEndMinutes() > 0)) && movie.getStartHour() < lastHour) { 
				channel.addMovie(movie);
			}
		}
		
		return channels;
	}
}
