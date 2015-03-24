<!DOCTYPE html>

<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sky - James Bond TV Guide</title>
    <link rel = "stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel = "stylesheet" href="${pageContext.request.contextPath}/css/application.css">
  </head>

  <body>

    <nav class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div id="navbar" class="navbar-collapse collapse">
          <div class="navbar-header">
	          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="navbar-brand" href="Guide">TV Guide</a>
	        </div>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="Upload"><span class="glyphicon glyphicon-upload" aria-hidden="true"></span> Upload</a></li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container">
      <jsp:doBody/>
    </div>

  </body>
</html>