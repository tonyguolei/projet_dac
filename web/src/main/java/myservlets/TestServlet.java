/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myservlets;

import mybeans.User;
import mybeans.UserDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {

    @EJB
    private UserDao userDao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String msg = "";
        String para = request.getParameter("method");
        System.out.println("papa: " + para);

        if (para.equals("insert")) {
            String userid = request.getParameter("userid");
            String username = request.getParameter("username");
            String usercode = request.getParameter("usercode");
            String password = request.getParameter("password");
            User user = new User();
            user.setUserid(Integer.parseInt(userid));
            user.setUsercode(usercode);
            user.setUsername(username);
            user.setPassword(password);
            this.userDao.save(user);
            msg = "insert successfully：" + username;
        }else if (para.equals("delete")) {
            String usercode = request.getParameter("usercode");
            User user = this.userDao.getByUserCode(usercode);
            if (user != null) {
                this.userDao.delete(user);
            }
            msg = "delete successfully：" + user.getUsername();
        }else if (para.equals("findall")) {
            System.out.println("test");
            List<User> list = this.userDao.getAll();
            for (User user : list) {
                msg += "   " + user.getUsername();
            }
        }else{
            msg = "method Unknown";
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println(msg);
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method");
        if (method.equals("insert")) {
            processRequest(request, response);
        }
    }
}
