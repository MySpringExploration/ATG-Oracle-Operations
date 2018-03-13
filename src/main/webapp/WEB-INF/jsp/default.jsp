<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<html>

<head>
<title>DataBase Access Application</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">

</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="/">Home</a>
			</div>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="/register"><span
						class="glyphicon glyphicon-user"></span>Register</a></li>
				<li><a href="/login"><span
						class="glyphicon glyphicon-log-in"></span> Login</a></li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<p align="center">Welcome to GMO Operations Manager!! 
	</div>
	

</body>
</html>