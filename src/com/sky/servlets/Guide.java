package com.sky.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sky.models.*;

public class Guide extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Channel> channels = new ArrayList<Channel>();
		channels.add(new Channel("sean_channel", "Sean Connery"));
		channels.add(new Channel("george_channel", "George Lazneby"));
		channels.add(new Channel("roger_channel", "Roger Moore"));
		channels.add(new Channel("timothy_channel", "Timothy Dalton"));
		channels.add(new Channel("pierce_channel", "Pierce Brosnan"));
		channels.add(new Channel("daniel_channel", "Daniel Craig"));
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("h.m a");
		List<Movie> movies = new ArrayList<Movie>();
		movies.add(new Movie(1, "Golden Eye", formatter.parseLocalTime("9.00 AM"), formatter.parseLocalTime("11.00 AM")));
		
		channels.add(new Channel("test", "Dave", movies));
		
		request.setAttribute("channels", channels);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/guide.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
