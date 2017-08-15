<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.codetinkerhack.persistence.Storage" %>
<html>
<head>
<title>Hello World!</title>
</head>
<body>
	<h1>Hello World!</h1>
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