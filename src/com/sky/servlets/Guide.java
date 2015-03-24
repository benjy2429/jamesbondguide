package com.sky.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		Date start = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 12);
		List<Movie> movies = new ArrayList<Movie>();
		movies.add(new Movie(1, "Golden Eye", cal.getTime(), cal.getTime()));
		
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
