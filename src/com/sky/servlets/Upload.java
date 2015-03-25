package com.sky.servlets;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * The Upload servlet displays the XML upload form and processes the uploaded file
 * @author Ben Carr
 *
 */
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Guide.class.getName());
	public static final String uploadFileName = "movie-data.xml"; 

	
	/**
	 * Handles GET requests, sets the parameters needed for the page and renders a JSP file to display
	 * Overrides doGet from HTTPServlet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Render the upload JSP view
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/upload.jsp");
		requestDispatcher.forward(request, response);
	}

	
	/**
	 * Handles POST requests, processes the submitted file and writes it to the server if correct
	 * Overrides doPost from HTTPServlet
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uploadPath = getServletContext().getRealPath("") + File.separator + uploadFileName;
		
		try {
			// Parse the file that was selected
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
		} catch (FileUploadException fue) {
			// If there was a problem uploading, log it and display an error to the user
			LOGGER.log(Level.WARNING, fue.getMessage());
			request.setAttribute("error", "There was a problem uploading this file to the server.");
	    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/upload.jsp");
			requestDispatcher.forward(request, response);
			return;
	    } catch (Exception e) {
	    	// If there was any other exception, log it and display the error to the user
	    	LOGGER.log(Level.WARNING, e.getMessage());
	    	request.setAttribute("error", e.getMessage());
	    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/upload.jsp");
			requestDispatcher.forward(request, response);
			return;
		}			
				
		// If the file was uploaded successfully, redirect back to the Guide servlet
		response.sendRedirect("Guide");
	}
}
