<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    
	<div class="page-header">
	  <h1>Upload Channel Data</h1>
	</div>
	
	<c:if test="${not empty error}">
		<div class="alert alert-danger" role="alert">${error}</div>
	</c:if>
	
	<form method="post" action="Upload" enctype="multipart/form-data">
		<div class="form-group">
	    <label for="file-upload">Upload XML File</label>
	    <input type="file" name="file-upload" id="file-upload">
	    <br>
	    <button type="submit" class="btn btn-primary">Submit</button>
	  </div>
	</form>

</t:template>
