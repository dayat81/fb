<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Dashboard</title>
</head>
<body>
<tag:notloggedin>
  <p>Login flow for Web Applications:</p>
  <a href="signin"><img src="./assets/login-with-facebook.png" alt="Sign in with Facebook"></a>
  <hr />
  <p>Login from Devices:</p>
  <a href="gencode"><img src="./assets/login-with-facebook.png" alt="Sign in with Facebook"></a>
</tag:notloggedin>
<tag:loggedin>
  <h1>Welcome ${facebook.name} (${facebook.id})</h1>
  ${data }<br>
<a href="./logout">logout</a>
</tag:loggedin>
</body>
</html>