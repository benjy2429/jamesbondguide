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


public class Guide extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Guide.class.getName());
	
	private Map<String,Channel> channels = new LinkedHashMap<String,Channel>();
	private List<String> times = new ArrayList<String>();
	
	private static final int FIRST_HOUR = 9;
	private static final int LAST_HOUR = 20;
	
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		channels.put("sean_channel", new Channel("Sean Connery"));
		channels.put("george_channel", new Channel("George Lazenby"));
		channels.put("roger_channel", new Channel("Roger Moore"));
		channels.put("timothy_channel", new Channel("Timothy Dalton"));
		channels.put("pierce_channel", new Channel("Pierce Brosnan"));
		channels.put("daniel_channel", new Channel("Daniel Craig"));
		
		DateTimeFormatter parseFormatter = DateTimeFormat.forPattern("H");
		DateTimeFormatter printFormatter = DateTimeFormat.forPattern("h a");
		for (int i=FIRST_HOUR; i<=LAST_HOUR-1; i++) {
			LocalTime hour = parseFormatter.parseLocalTime(Integer.toString(i));
			times.add(printFormatter.print(hour));
		}
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			loadMoviesFromXML();
		} catch (JDOMException | IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			request.setAttribute("error", "Error: The data file is malformed. Please upload a correct version.");
		} catch (IOException ioe) {
			LOGGER.log(Level.INFO, "No XML data file has been uploaded yet.");
		}
		
		request.setAttribute("channels", channels);
		request.setAttribute("times", times);
		request.setAttribute("startHour", FIRST_HOUR);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/guide.jsp");
		requestDispatcher.forward(request, response);
	}
	
	
	private void loadMoviesFromXML() throws JDOMException, IOException, IllegalArgumentException {
		String filePath = getServletContext().getRealPath("") + File.separator + Upload.uploadFileName;
		
		// Clear the existing channel data
		for (Channel channel : channels.values()) {
			channel.removeAllMovies();
		}
		
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(filePath);
		
		Element rootNode = doc.getRootElement();
		List<Element> movieNodes = rootNode.getChildren("movie");
		
		for (Element movieNode : movieNodes) {
			int movieId = Integer.parseInt(movieNode.getAttributeValue("id"));
			
			Element channelNode = movieNode.getChildren().get(0);
			Channel channel = channels.get(channelNode.getName());
			
			String movieName = channelNode.getChildText("name");
			
			DateTimeFormatter formatter = DateTimeFormat.forPattern("h.ma");
			LocalTime movieStartTime = formatter.parseLocalTime(channelNode.getChildText("start_time").toLowerCase());
			LocalTime movieEndTime = formatter.parseLocalTime(channelNode.getChildText("end_time").toLowerCase());
			
			Movie movie = new Movie(movieId, movieName, movieStartTime, movieEndTime);
			
			// Only add movies that are within the guide range
			if (movie.getMinuteDuration() > 0 && (movie.getEndHour() > FIRST_HOUR || (movie.getEndHour() == FIRST_HOUR && movie.getEndMinutes() > 0)) && movie.getStartHour() < LAST_HOUR) { 
				
				channel.addMovie(movie);
			}
		}

	}

}
