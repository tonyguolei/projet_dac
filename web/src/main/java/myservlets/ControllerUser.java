/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myservlets;

import alerts.Alert;
import alerts.AlertType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityExistsException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mybeans.User;
import mybeans.UserDao;

/**
 *
 * @author tib
 */
@WebServlet(name = "ControllerUser", urlPatterns = {"/ControllerUser"})
public class ControllerUser extends HttpServlet {
    
    private static final String ERROR_LOGIN = "The email you entered does not belong to any account.";
    private static final String ERROR_PASS = "The password you entered is incorrect. Please try again (make sure your caps lock is off).";
    private static final String SUCCESS_LOGIN = "Login succesful, welcome back!";

    @EJB
    private UserDao userDao;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
        switch (action) {
            case "login":
                doLogin(request, response);
                break;
            case "signup":
                doSignup(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void doSignup(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String mail = request.getParameter("mail");
            String password = request.getParameter("password");
            if (mail.equals("") || password.equals("")) {
                //TODO report error
                response.sendRedirect("index.jsp?nav=signup");
            } else {
                User user = new User(mail, password);
                try {
                    this.userDao.save(user);
                    //TODO show message that it worked
                    response.sendRedirect("index.jsp?nav=login");
                } catch (Exception e) {
                    //TODO show message that it didn't worked
                    System.out.println("Can't create user");
                    response.sendRedirect("index.jsp?nav=signup");
                }
            }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(true);
        if (mail.equals("") || password.equals("")) {
            //Not supposed to happen, checked by BootStrap
            response.sendRedirect("index.jsp?nav=login");
        } else {
            try {
                User user = userDao.getByMail(mail);
                if (password.equals(user.getPassword())) {
                    session.setAttribute("user", user);
                    Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_LOGIN);
                    response.sendRedirect("index.jsp");
                } else {
                    Alert.addAlert(session, AlertType.DANGER, ERROR_PASS);
                    System.out.println("Invalid password");
                    response.sendRedirect("index.jsp?nav=login");
                }
            } catch (Exception e) {
                Alert.addAlert(session, AlertType.DANGER, ERROR_LOGIN);
                System.out.println("Invalid user");
                response.sendRedirect("index.jsp?nav=login");
            }
        }
    }

}
