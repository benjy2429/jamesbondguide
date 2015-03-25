<%--
This file contains the HTML for the guide
It is rendered into the template
@author Ben Carr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    
	<div class="page-header">
	  <h1>James Bond Programme Guide</h1>
	</div>
	
	
	<div class="channel-list">
      <div class="channel-title">Channel</div>
      <c:forEach var="channelMap" items="${channels}">
        <div class="channel-name">${channelMap.value.name}</div>
      </c:forEach>
    </div>
	
	<div class="guide-wrapper">
	  <c:set var="hourWidth" value="160" />
    <c:set var="totalWidth" value="${hourWidth * fn:length(times)}" />
    
	
    <ul class="times">
      <c:forEach items="${times}" var="time">
       <li>${time}</li>
      </c:forEach>
    </ul>	
		
		
		<c:forEach var="channelMap" items="${channels}">
		  <c:set var="channel" value="${channelMap.value}" />
		  <div class="channel" style="width:${totalWidth}px;">
	      <%-- <div class="col-xs-2 channel-name">${channel.name}</div> --%> 
	     
	       
         <c:choose>
           <c:when test="${empty channel.movies}">
             <div class="no-info">No programmes available</div>
           </c:when>
           <c:otherwise>
             <c:forEach items="${channel.movies}" var="movie">
               
               <%-- Calculate the width of this movie element --%>
               <c:set var="width" value="${hourWidth * (movie.minuteDuration / 60)}" />
               <c:if test="${width < 0}"><c:set var="width" value="0" /></c:if>
               
               <%-- Calculate the start position of this movie element --%>
               <c:set var="hourOffset" value="${(movie.startHour + (movie.startMinutes / 60)) - startHour}" />
               <c:set var="pixelOffset" value="${hourWidth * hourOffset}" />
               
               <%-- Check for movies that start before the start time and display them correctly --%>
               <c:if test="${pixelOffset < 0}">
                 <c:set var="width" value="${width - ((startHour - (movie.startHour + (movie.startMinutes / 60))) * hourWidth)}" />
                 <c:set var="pixelOffset" value="0" />
               </c:if>
               
               <%-- Check for movies that end before the final time and crop the end off --%>
               <c:if test="${(pixelOffset + width) > totalWidth}">
                 <c:set var="width" value="${totalWidth - pixelOffset}" />
               </c:if>
               
               <button class="movie" data-toggle="modal" data-target="#recordModal" data-movie-name="${movie.name}" style="left: ${pixelOffset}px; width:${width}px">${movie.name}</button>
             </c:forEach>
           </c:otherwise>
         </c:choose>
	
	      </div>
	  </c:forEach>
  
  </div>
  
  
  <!-- Modal -->
	<div class="modal fade" id="recordModal" tabindex="-1" role="dialog">
	  <div class="modal-dialog">
	    <div class="modal-content">
	    
	      <div class="modal-header">
		      <div class="media">
            <div class="media-left">
              <img class="skyLogo" src="${pageContext.request.contextPath}/img/sky-logo.png" alt="Sky Logo" width="82" height="52" />  
            </div>
            <div class="media-body">
              <h2 class="media-heading">Remote Record</h2>
            </div>
          </div>
	      </div>
	      
	      <div class="modal-body">  
	        <p>The James Bond movie <span class="modal-movie-name"></span> is set to be recorded</p>
	      </div>
	      
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>

</t:template>
