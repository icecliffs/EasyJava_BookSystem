<%--
  Created by IntelliJ IDEA.
  User: rYu1nser
  Date: 2023/4/17
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="org.apache.tomcat.util.security.MD5Encoder" %>
<%@ page import="java.util.Base64" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="users" class="moe.iloli.dao.UserDAO"></jsp:useBean>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./assets/styles.css">
    <title>登录页面</title>
</head>
<%
    session.setAttribute("users", users);
    String username = request.getParameter("username");
    String pwd = request.getParameter("pwd");
    Boolean flag = users.getUser(username, pwd);
//    String encPassword = new String(Base64.getEncoder().encode(pwd.getBytes()));
//    System.out.println(encPassword);
    if (flag) {
        session.setAttribute("username", username);
        session.setAttribute("password", pwd);
        response.sendRedirect("main.jsp");
    } else {
        out.print("<script>alert(\"密码输入错误！\");</script>");
    }
%>
<body>
<div class="container">
    <div class="box">
        <form action="index.jsp" method="post">
            <label>用户名</label>
            <input type="text" value="" name="username">
            <label>密码</label>
            <input type="text" value="" name="pwd">
            <input type="submit" value="登录">
        </form>
    </div>
</div>
</body>
</html>