/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myservlets;

import alerts.Alert;
import alerts.AlertType;
import mybeans.User;
import mybeans.UserDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author tib
 */
@WebServlet(name = "ControllerUser", urlPatterns = {"/ControllerUser"})
public class ControllerUser extends HttpServlet {

    private static final String SUCCESS_LOGOUT = "You are disconnected.";
    private static final String ERROR_LOGIN = "The email you entered is not attached to any account.";
    private static final String ERROR_PASS = "The password you entered is incorrect. Please try again (make sure your caps lock is off).";
    private static final String SUCCESS_LOGIN = "Login succesful, welcome back!";
    private static final String SUCCESS_CREATE = "New user created! Please log in.";
    private static final String ERROR_FORM = "Please fill the form correctly.";
    private static final String ERROR_CREATE = "Can not create the new user. The mail may already be attached to an account.";

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
            case "logout":
                doLogout(request, response);
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
        HttpSession session = request.getSession();
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");
        if (mail.equals("") || password.equals("")) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
            response.sendRedirect("index.jsp?nav=signup");
            return;
        }

        User user = new User(mail, password);
        try {
            this.userDao.save(user);
            Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_CREATE);
            response.sendRedirect("index.jsp?nav=login");
            return;
        } catch (Exception e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_CREATE);
            System.out.println("Can't create user");
            response.sendRedirect("index.jsp?nav=signup");
            return;
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");
        if (mail.equals("") || password.equals("")) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
            response.sendRedirect("index.jsp?nav=login");
            return;
        }

        User user = userDao.getByMail(mail);
        if(user == null){
            Alert.addAlert(session, AlertType.DANGER, ERROR_LOGIN);
            System.out.println("Invalid user");
            response.sendRedirect("index.jsp?nav=login&mail="+mail);
            return;
        }else {
            if (password.equals(user.getPassword())) {
                session.setAttribute("user", user);
                Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_LOGIN);
                response.sendRedirect("index.jsp");
                return;
            } else {
                Alert.addAlert(session, AlertType.DANGER, ERROR_PASS);
                System.out.println("Invalid password");
                response.sendRedirect("index.jsp?nav=login&mail=" + mail);
                return;
            }
        }
/*        try {
            User user = userDao.getByMail(mail);
            if (password.equals(user.getPassword())) {
                session.setAttribute("user", user);
                Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_LOGIN);
                response.sendRedirect("index.jsp");
                return;
            } else {
                Alert.addAlert(session, AlertType.DANGER, ERROR_PASS);
                System.out.println("Invalid password");
                response.sendRedirect("index.jsp?nav=login&mail="+mail);
                return;
            }
        } catch (Exception e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_LOGIN);
            System.out.println("Invalid user");
            response.sendRedirect("index.jsp?nav=login&mail="+mail);
            return;
        }*/
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();

        HttpSession newSession = request.getSession();
        Alert.addAlert(newSession, AlertType.SUCCESS, SUCCESS_LOGOUT);
        response.sendRedirect("index.jsp");
    }

}
