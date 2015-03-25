<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    
	<div class="page-header">
	  <h1>James Bond Programme Guide</h1>
	</div>
	
	<div class="guideWrapper">
	
	  <div class="row time-header">
	    <div class="col-xs-2">Channel</div>
	    
	    <div class="col-xs-10">
	      <div class="row times">
	        <div class="col-xs-2">9 AM</div>
	        <div class="col-xs-2">10 AM</div>
	        <div class="col-xs-2">11 AM</div>
	        <div class="col-xs-2">12 PM</div>
	        <div class="col-xs-2">1 PM</div>
	        <div class="col-xs-2">2 PM</div>
	      </div>
	    </div>
		</div>
		
		<c:forEach var="channelMap" items="${channels}">
		  <c:set var="channel" value="${channelMap.value}" />
		  <div class="row channel clearfix">
	      <div class="col-xs-2 channel-name">${channel.name}</div>
	     
	      <div class="col-xs-10 channel-contents">
	        <div class="row">
	       
	          <c:choose>
	            <c:when test="${empty channel.movies}">
	              <div class="col-xs-12 no-info">No programme information</div>
	            </c:when>
	            <c:otherwise>
	              <c:forEach items="${channel.movies}" var="movie">
	                <c:set var="hourOffset" value="${(movie.startHour + (movie.startMinutes / 60)) - startHour}" />
	                <c:set var="percentOffset" value="${(100 / 6) * hourOffset}" />
	                <c:if test="${percentOffset < 0}"><c:set var="percentOffset" value="0" /></c:if>
	                <c:if test="${percentOffset > 100}"><c:set var="percentOffset" value="100" /></c:if>
	                
	                <c:set var="width" value="${(100 / (6*60)) * movie.minuteDuration}" />
	                <c:if test="${width < 0}"><c:set var="width" value="0" /></c:if>
	                <c:if test="${width > 100}"><c:set var="width" value="100" /></c:if>
	                <c:if test="${(width + percentOffset) > 100}"><c:set var="width" value="${100 - percentOffset}" /></c:if>
	                
	                
	                <button class="movie" data-toggle="modal" data-target="#recordModal" data-movie-name="${movie.name}" style="left: ${percentOffset}%; width:${width}%">${movie.name}</button>
	              </c:forEach>
	            </c:otherwise>
	          </c:choose>
	
	        </div>
	      </div>
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
