<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Sky - James Bond TV Guide</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <div class="container">
    
      <div class="page-header">
        <h1>James Bond Programme Guide</h1>
      </div>
      
      
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
              <td class="active"></td>
              <td class="active"></td>
              <td colspan="2" class="info">Hello</td>
              <td class="active"></td>
              <td class="active"></td>
            </tr>
        </tbody>
      </table>
      
    </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>