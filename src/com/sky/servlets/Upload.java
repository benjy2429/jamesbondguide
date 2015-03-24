package com.sky.servlets;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String uploadFileName = "movie-data.xml"; 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/upload.jsp");
		requestDispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uploadPath = getServletContext().getRealPath("") + File.separator + uploadFileName;
		
		try {
	        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	        for (FileItem item : items) {
	            if (!item.isFormField()) {
	            	String fileName = item.getName();
	            	if (fileName != null && !fileName.endsWith("xml")) {
	            		throw new Exception("Error: Only XML files can be uploaded.");
	            	}
	                File storeFile = new File(uploadPath);
	                
	                item.write(storeFile);
	            }
	        }
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    	request.setAttribute("error", e.getMessage());
	    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/upload.jsp");
			requestDispatcher.forward(request, response);
			return;
		}			
				
		response.sendRedirect("Guide");
	}

}
