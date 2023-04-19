<%@ page import="java.util.ArrayList" %>
<%@ page import="moe.iloli.model.Book" %>
<%@ page import="moe.iloli.dao.BookDAO" %>
<%@ page import="moe.iloli.model.Comment" %>
<%@ page import="moe.iloli.model.Press" %>
<%@ page import="org.apache.taglibs.standard.lang.jstl.parser.ELParserConstants" %>
<%@ page import="moe.iloli.dao.CommentDAO" %>
<%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="bookDAO" class="moe.iloli.dao.BookDAO"></jsp:useBean>
<jsp:useBean id="bookEdit" class="moe.iloli.model.Book"></jsp:useBean>
<jsp:useBean id="editBook" class="moe.iloli.model.Book"></jsp:useBean>
<jsp:useBean id="commentDAO" class="moe.iloli.dao.CommentDAO"></jsp:useBean>
<jsp:useBean id="book" class="moe.iloli.model.Book"></jsp:useBean>
<jsp:useBean id="pressDAO" class="moe.iloli.dao.PressDAO"></jsp:useBean>
<jsp:setProperty property="*" name="book"/>
<%
    if(session.getAttribute("username") == null || session.getAttribute("username").equals("")){
%>
    <jsp:forward page="/"></jsp:forward>
<%
    }
    request.setCharacterEncoding("UTF-8");
    Boolean flag = null;
    String keyword = request.getParameter("keyword");
    if (keyword == null) {
        ArrayList<Book> books = bookDAO.listBook();
        session.setAttribute("books", books);
    } else {
//        获取图书所有信息
        ArrayList<Book> books = bookDAO.getBooks(keyword);
        session.setAttribute("books", books);
    }
    if (request.getParameter("id") != null) {
        int id = 0;
        id = Integer.parseInt(request.getParameter("id"));
        String bookname = request.getParameter("bookname");
        String author = request.getParameter("author");
        String press = request.getParameter("press");
        String pubdate = request.getParameter("pubdate");
        String price = request.getParameter("price");
        String isbn = request.getParameter("isbn");
        editBook.setId(id);
        editBook.setBookname(bookname);
        editBook.setAuthor(author);
        editBook.setPress(press);
        editBook.setPubdate(pubdate);
        editBook.setPrice(Float.parseFloat(price));
        editBook.setIsbn(isbn);
        flag = bookDAO.updateBook(editBook);
        if (flag) {
            response.sendRedirect("./main.jsp");
        }
    }
    if (request.getParameter("delid") != null) {
        int id = 0;
        id = Integer.parseInt(request.getParameter("delid"));
        flag = bookDAO.deleteBook(id);
        if (flag) {
            response.sendRedirect("./main.jsp");
        }
    }
    if (request.getParameter("newadd") != null) {
        String bookname = request.getParameter("bookname1");
        String author = request.getParameter("author1");
        String press = request.getParameter("press1");
        String pubdate = request.getParameter("pubdate1");
        String price = request.getParameter("price1");
        String isbn = request.getParameter("isbn1");
        editBook.setBookname(bookname);
        editBook.setAuthor(author);
        editBook.setPress(press);
        editBook.setPubdate(pubdate);
        editBook.setPrice(Float.parseFloat(price));
        editBook.setIsbn(isbn);
        flag = bookDAO.addBook(editBook);
        System.out.println(flag);
        if (flag) {
            response.sendRedirect("./main.jsp");
        }
    }
//    出版商处理
    ArrayList<Press> presses = pressDAO.listPress();
    session.setAttribute("presses", presses);
//    评论区处理
    int lid = 0;
    if (request.getParameter("listid") != null) {
        lid = Integer.parseInt(request.getParameter("listid"));
        System.out.println(lid);
    }
//    评论区提交
    Comment comment = new Comment();
    if (request.getParameter("commentAdd") != null) {
        System.out.println("OK!");
        String username = request.getParameter("username");
        int id = Integer.parseInt(request.getParameter("bookid"));
        String ip = request.getParameter("ip");
        String comment1 = request.getParameter("comment");
        String date = request.getParameter("date");
        comment.setUsername(username);
        comment.setBookid(id);
        comment.setIp(ip);
        comment.setComment(comment1);
        flag = commentDAO.add(comment);
        if (flag) {
            System.out.println("OK");
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="./assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="./assets/styles.css">
    <script src="./assets/jquery-2.0.0.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div style="margin: 40px">
    <h2>书籍列表</h2>
    <form class="form-search" action="./main.jsp" method="post">
        <input class="input-medium search-query" type="text" name="keyword" value="${requestScope.keyword}"/>
        <button type="submit" value="" class="btn">搜索</button>
    </form>
    <a href="#myAddModal" role="button" class="btn" data-toggle="modal">添加书籍</a>
    <div id="myAddModal" class="modal hide fade" tabindex="-1" aria-labelledby="myModalLabel">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">添加书籍</h3>
        </div>
        <form action="./main.jsp" method="post">
            <input type="hidden" name="newadd" value="newadd">
            <div class="modal-body">
                <label>书名：</label>
                <input type="text" name="bookname1" value="${book.bookname}">
                <label>作者：</label>
                <input type="text" name="author1" value="${book.author}">
                <label>出版社：</label>
                <select class="span3" name="press1">
                    <c:forEach items="${presses}" var="press">
                        <option value="${press.id}">${press.pressname}</option>
                    </c:forEach>
                </select>
                <label>出版时间：</label>
                <input type="date" name="pubdate1" value="${book.pubdate}">
                <label>价格：</label>
                <input type="text" name="price1" value="${book.price}">
                <label>ISBN：</label>
                <input type="text" name="isbn1" value="${book.isbn}">
            </div>
            <div class="modal-footer">
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                <button class="btn btn-primary">添加</button>
            </div>
        </form>
    </div>
</div>
<br>
<table>
    <tr>
        <th>序号</th>
        <th>书名</th>
        <th>作者</th>
        <th>出版社</th>
        <th>出版时间</th>
        <th>价格（元）</th>
        <th>ISBN号</th>
        <th>编辑</th>
        <th>删除</th>
    </tr>
    <c:forEach items="${books}" var="book">
        <tr>
            <td>${book.id}</td>
            <td>
                <a href="#bookinfo${book.id}" onclick="autoSend${book.id}(${book.id})" role="button" class="btn btn-info" data-toggle="modal">${book.bookname}</a>
                <div id="bookinfo${book.id}" class="modal hide fade" tabindex="-1" aria-labelledby="myModalLabel">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 id="myModalLabel">${book.bookname}</h3>
                    </div>
                    <div class="modal-body">
                        <form action="./main.jsp" method="post" name="commentAdd">
                            <input type="hidden" name="commentAdd" value="commentAdd">
                            <input type="hidden" name="bookid" value="${book.id}">
                            <input type="hidden" name="username" value="<%=session.getAttribute("username")%>">
                            <input type="hidden" name="ip" value="<%=request.getRemoteAddr()%>">
                            <% long date = new Date().getTime(); request.setAttribute("date", date); %>
                            <input type="hidden" name="date" value="${date}">
                            <p>书名：${book.bookname}</p>
                            <p>作者：${book.author}</p>
                            <p>出版社：${book.press}</p>
                            <p>出版时间：${book.pubdate}</p>
                            <p>价格：${book.price}</p>
                            <p>ISBN：${book.isbn}</p>
                            <textarea rows="4" style="width: 97%" placeholder="还敢说啊，不要命🌶..." name="comment"></textarea>
                            <div class="media" id="comment-area${book.id}">
                                <script>
                                    function autoSend${book.id}(id){
                                        console.log(id);
                                        var th = $(this).next().children(".id");
                                        $.ajax({
                                            url: "http://${pageContext.request.remoteAddr}:${pageContext.request.serverPort}/comment/list",
                                            method: "GET",
                                            data:{
                                                "id": ${book.id},
                                            },
                                            success: function(data) {
                                                $("#comment-area${book.id}").html("");//清空info内容
                                                $.each(data, function(i, item) {
                                                    console.log(item.comment);
                                                    $("#comment-area${book.id}").append(
                                                        '<a class="pull-left" href="#"> <img style="width: auto\9; height: auto; max-width: 100%; vertical-align: middle; border: 0; -ms-interpolation-mode: bicubic; width: 75px; border-radius: 10px;" decoding="async" class="media-object" src="http://${pageContext.request.remoteAddr}:${pageContext.request.serverPort}/assets/img/dingzhen.png" alt="Media Object"> </a> <div class="media-body"> <h4 class="media-heading" id="comment-name">'+ item.username +"</div>"+
                                                        "<h6>"+ item.commenttime  +"<h6></h4>"+ item.comment +"</div><hr/>");
                                                });
                                            },
                                            error: function() {
                                                console.log("请求数据失败！");
                                            }
                                        });
                                    }
                                </script>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-primary">添加</button>
                                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                            </div>
                        </form>
                    </div>
                </div>
            </td>
            <td>${book.author}</td>
            <td>${book.press}</td>
            <td>${book.pubdate}</td>
            <td>${book.price}</td>
            <td>${book.isbn}</td>
            <td>
                <a href="#myModal${book.id}" role="button" class="btn" data-toggle="modal">编辑</a>
                <div id="myModal${book.id}" class="modal hide fade" tabindex="-1" aria-labelledby="myModalLabel">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 id="myModalLabel">${book.bookname}</h3>
                    </div>
                    <form action="./main.jsp" method="post">
                        <input type="hidden" name="id" value="${book.id}">
                        <div class="modal-body">
                                <label>书名：</label>
                                <input type="text" name="bookname" value="${book.bookname}">
                                <label>作者：</label>
                                <input type="text" name="author" value="${book.author}">
                                <label>出版社：</label>
                                <select class="span3" name="press">
                                    <c:forEach items="${presses}" var="press">
                                        <option value="${press.id}">${press.pressname}</option>
                                    </c:forEach>
                                </select>
                                <label>出版时间：</label>
                                <input type="date" name="pubdate" value="${book.pubdate}">
                                <label>价格：</label>
                                <input type="text" name="price" value="${book.price}">
                                <label>ISBN：</label>
                                <input type="text" name="isbn" value="${book.isbn}">
                        </div>
                        <div class="modal-footer">
                            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                            <button class="btn btn-primary">修改</button>
                        </div>
                    </form>
                </div>
            </td>
            <td>
                <a href="#delModal${book.id}" role="button" class="btn" data-toggle="modal">删除</a>
                <div id="delModal${book.id}" class="modal hide fade" tabindex="-1" aria-labelledby="myModalLabel">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 id="myModalLabel">您确定要删除吗？（这是不可逆的）</h3>
                    </div>
                    <div class="modal-body">
                        <p>确定删除书籍《${book.bookname}》？</p>
                    </div>
                    <div class="modal-footer">
                        <a href="javascript:void(0);" class="btn btn-primary" onclick="deleteBook(${book.id})">删除</a>
                        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                    </div>
                    <script>
                        function deleteBook(id){
                            window.location ="main.jsp?delid="+id;
                            console.log(id);
                        }
                    </script>
                </div>
            </td>
        </tr>
    </c:forEach>
    <script src="./assets/jquery-2.0.0.min.js"></script>
    <script src="./assets/js/bootstrap.min.js"></script>
</table>
</body>
</html>