<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    
	<div class="page-header">
	  <h1>James Bond Programme Guide</h1>
	</div>
	
	
  <div class="container">
    <div class="row">
      <div class="col-xs-2">Channel</div>
      
      <div class="col-xs-10">
        <div class="row">
          <div class="col-xs-2">9 AM</div>
          <div class="col-xs-2">10 AM</div>
          <div class="col-xs-2">11 AM</div>
          <div class="col-xs-2">12 PM</div>
          <div class="col-xs-2">1 PM</div>
          <div class="col-xs-2">2 PM</div>
        </div>
      </div>
	  </div>
	  
	  
	  <c:forEach items="${channels}" var="channel">
	  
		  <div class="row">
	      <div class="col-xs-2">${channel.name}</div>
	      
	      <div class="col-xs-10">
	        <div class="row">
	        
            <c:choose>
              <c:when test="${empty channel.movies}">
                <div class="col-xs-12">No programme information</div>
              </c:when>
              <c:otherwise>
                <c:forEach items="${channel.movies}" var="movie">
                  <c:set var="width" value="${(100 / (6*60)) * movie.minuteDuration}" />
                  <c:set var="offset" value="${(100 / (6*60)) * movie.minuteDuration}" />
                  <div style="background-color:red; margin-left: 0%; width:${width}%">${movie.name}</div>
                </c:forEach>
              </c:otherwise>
            </c:choose>

	        </div>
	      </div>
	    </div>
    </c:forEach>
	  
  </div>
	
	<!--
	<table class="table">
	  <thead>
	    <tr>
	      <th>Channel</th>
	      <th>9 AM</th>
	      <th>10 AM</th>
	      <th>11 AM</th>
	      <th>12 PM</th>
	      <th>1 PM</th>
	      <th>2 PM</th>
	    </tr> 
	  </thead>
	  
	  <tbody>
	    <c:forEach items="${channels}" var="channel">
	      <tr>
	        <td>${channel.name}</td>
	        <td class="active" colspan="6">No programme information</td>
	        <c:forEach items="${channel.movies}" var="movie">
	          <p>${movie.name}</p>
	        </c:forEach>
	      </tr>
	    </c:forEach>
	    <tr>
	        <td>TEST</td>
	        <td colspan="6" class="active">
	          <div>hi</div> 
	        </td>
	      </tr>
	  </tbody>
	</table>
	
	-->

</t:template>
