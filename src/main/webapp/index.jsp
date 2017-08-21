<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.codetinkerhack.persistence.Storage" %>
<%@page import="com.codetinkerhack.Environment" %>
<html>
<head>
    <title>Hello World!</title>
    <link rel="stylesheet" href="<%= Environment.get("RESOURCES_URL") %>/static/css/main.css"/>
</head>
<body>
	<h1>Hello World!</h1>
	<img src="<%= Environment.get("RESOURCES_URL") %>/static/images/cat.jpg"/>
	<p>
		It is now
		<%= new java.util.Date() %></p>
	<p>
		You are coming from:
		<%= request.getRemoteAddr()  %>
	</p>
	<p>
	    Last 20 visitors from:
	    <%
            ServletContext sc = request.getSession().getServletContext();
            Storage storage =  (Storage) sc.getAttribute("storage");

            if (storage != null) {
                for(String ip : storage.getLast20Visitors()) {
        %>
                    <%= ip %> <br>
        <%
                }

                storage.store(request.getRemoteAddr());
            }
	    %>
	</p>

</body>
</html>