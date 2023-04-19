package moe.iloli.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import moe.iloli.dao.BookDAO;
import moe.iloli.dao.CommentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/comments")
public class Comment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommentDAO commentDAO = new CommentDAO();
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
//      处理json数据
        req.setCharacterEncoding("UTF-8");
        String data = JSON.toJSONString(commentDAO.getAllComment(Integer.parseInt(req.getParameter("id"))));
        printWriter.println(data);
        printWriter.flush();
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        super.doPost(req, resp);
    }
}
