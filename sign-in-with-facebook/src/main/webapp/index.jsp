<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Dashboard</title>
<script>
var ref="<%= request.getParameter("ref") %>";
</script>
</head>
<body>
<tag:notloggedin>
  <p>Login flow for Web Applications:</p>
  <a href="#" onclick="window.location.href = 'signin?ref='+ref"><img src="./assets/login-with-facebook.png" alt="Sign in with Facebook"></a>
</tag:notloggedin>
<tag:loggedin>
  <h1>Welcome ${facebook.name} (${facebook.id})</h1>
  ${data }<br>
<a href="./logout">logout</a>
</tag:loggedin>
</body>
</html>