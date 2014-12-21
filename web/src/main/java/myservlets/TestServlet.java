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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.persistence.PersistenceException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;

@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {

    @EJB
    private UserDao userDao;

    public String htmlTableUser() {
        String msg = "";
        List<User> list = this.userDao.getAll();
        msg += "<br/><br/><form action=\"/web/TestServlet\">\n";
        msg += "  <input type=\"hidden\" name=\"method\" value=\"insert\"/>\n";
        msg += "  mail : <input type=\"text\" name=\"mail\"/>\n";
        msg += "  password : <input type=\"text\" name=\"password\"/>\n";
        msg += "  <input type=\"submit\" value=\"Ajouter\"/>\n";
        msg += "</form><br/><br/>";

        msg += "<table><thead>\n";
        msg += "  <th>Pk</th>\n";
        msg += "  <th>mail</th>\n";
        msg += "  <th>balance</th>\n";
        msg += "  <th>isAdmin</th>\n";
        msg += "</thead><tbody>\n";
        for (User user : list) {
            msg += "  <tr><form action=\"/web/TestServlet\">\n";
            msg += "    <input type=\"hidden\" name=\"method\" value=\"update\">\n";
            msg += "    <input type=\"hidden\" name=\"pk\" value=\"" + user.getIdUser() + "\">\n";
            msg += "    <td>" + user.getIdUser() + "</td>\n";
            msg += "    <td>" + user.getMail() + "</td>\n";
            msg += "    <td><input type=\"number\" name=\"balance\" value=\"" + user.getBalance() + "\" min=\"0\"/></td>\n";
            msg += "    <td>" + user.getIsAdmin() + "</td>\n";
            msg += "    <td><input type=\"submit\" value=\"Modifier\"/></td>\n";
            msg += "    <td><a href=\"/web/TestServlet?method=delete&amp;pk=" + user.getIdUser() + "\">Supprimer</a></td>\n";
            msg += "  </form></tr>\n";
        }
        msg += "</tbody></table>\n";
        return msg;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String msg = "";
        String para = request.getParameter("method");
        System.out.println("papa: " + para);

        if (para.equals("insert")) {
            String mail = request.getParameter("mail");
            String password = request.getParameter("password");

            User user = new User();
            user.setMail(mail);
            user.setPassword(password);
            try {
                this.userDao.save(user);
                msg = "insert successful : " + user.getIdUser();
            } catch (EJBException e) {
                // going to the interesting exception in the exception stack.
                Throwable cause = e.getCause().getCause();
                if (cause instanceof PersistenceException) {
                    PersistenceException pe = (PersistenceException) cause;
                    String message = pe.getMessage();
                    // put some elseif to check which constraint crashed.
                    if (message.contains("login_UNIQUE")) {
                        msg = "Insert failed : mail already exists.";
                    } else {
                        msg = "Insert failed : unknown reason.";
                    }
                } else {
                    msg = "Insert failed : unknown reason.";
                }
            }
            msg += "\n <br/>\n" + htmlTableUser();

        } else if (para.equals("delete")) {
            int pk = Integer.parseInt(request.getParameter("pk"));
            try {
                User user = this.userDao.getByIdUser(pk);
                userDao.delete(user);
                msg = "delete successful : " + pk;
            } catch (EJBException e) {
                //msg = "delete failed : invalid pk.";
            }
            msg += htmlTableUser();
        } else if (para.equals("findall")) {
            msg = htmlTableUser();
        } else if (para.equals("update")) {
            int pk = Integer.parseInt(request.getParameter("pk"));
            int balance = Integer.parseInt(request.getParameter("balance"));
            
            try {
                User user = userDao.getByIdUser(pk);
                user.setBalance(balance);
                
                userDao.update(user);
                msg = "Update successful.";
            } catch (EJBException e) {
                msg = "Update failed.";
            }
            msg += htmlTableUser();
        } else {
            msg = "method Unknown <br/>\n" + htmlTableUser();
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
