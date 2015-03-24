<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    
	<div class="page-header">
	  <h1>James Bond Programme Guide</h1>
	</div>
	
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
                <c:set var="width" value="${(100 / (6*60)) * movie.minuteDuration}" />
                <c:set var="hourOffset" value="${(movie.startHour + (movie.startMinutes / 60)) - startHour}" />
                <c:set var="percentOffset" value="${(100 / 6) * hourOffset}" />
                <div class="movie" style="left: ${percentOffset}%; width:${width}%">${movie.name} ${movie.startHour} - ${movie.endHour}</div>
              </c:forEach>
            </c:otherwise>
          </c:choose>

        </div>
      </div>
    </div>
  </c:forEach>

</t:template>
