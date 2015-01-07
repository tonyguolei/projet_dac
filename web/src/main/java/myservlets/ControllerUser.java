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
import java.util.List;
import javax.servlet.RequestDispatcher;
import mybeans.Project;
import mybeans.Fund;

/**
 *
 * @author tib
 */
@WebServlet(name = "ControllerUser", urlPatterns = {"/ControllerUser"})
public class ControllerUser extends HttpServlet {

    private static final String SUCCESS_LOGOUT = "You are disconnected.";
    private static final String ERROR_LOGIN = "The email you entered is not attached to any account.";
    private static final String ERROR_PASS = "The password you entered is incorrect. Please try again (make sure your caps lock is off).";
    private static final String ERROR_DELETED = "This account has been deleted.";
    private static final String ERROR_BANNED = "This account has been banned.";
    private static final String SUCCESS_LOGIN = "Login succesful, welcome back!";
    private static final String SUCCESS_CREATE = "New user created! Please log in.";
    private static final String ERROR_FORM = "Please fill the form correctly.";
    private static final String ERROR_CREATE = "Can not create the new user. The mail may already be attached to an account.";
    private static final String ERROR_INSPECT = "Please specify a valid user ID";
    private static final String ERROR_ID = "Please specify an ID";
    private static final String ERROR_CONF_PASS = "Passwords missmatch. Please reenter your new password.";
    private static final String SUCCESS_CHANGE_PASS = "Password succesfully modified.";
    private static final String ERROR_EMPTY_PASS = "You need to provide a new password.";
    private static final String SUCCESS_DELETE_ACCOUNT = "Your account as been successfully deleted.";

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
            case "inspect":
                doInspect(request, response);
                break;
            case "changePass":
                doChangePass(request, response);
                break;
            case "deleteAccount":
                doDeleteAccount(request, response);
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
        }
        
        if (!password.equals(user.getPassword())) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_PASS);
            System.out.println("Invalid password");
            response.sendRedirect("index.jsp?nav=login&mail=" + mail);
            return;
        }

        if (user.getDeleted()) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_DELETED);
            response.sendRedirect("index.jsp?nav=login&mail=" + mail);
            return;
        }

        if (user.getBanned()) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_BANNED);
            response.sendRedirect("index.jsp?nav=login&mail=" + mail);
            return;
        }

        session.setAttribute("user", user);
        Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_LOGIN);
        response.sendRedirect("index.jsp");
        return;
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();

        HttpSession newSession = request.getSession();
        Alert.addAlert(newSession, AlertType.SUCCESS, SUCCESS_LOGOUT);
        response.sendRedirect("index.jsp");
    }

    private void doInspect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_ID);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        User inspectedUser = userDao.getByIdUser(id);
        if(inspectedUser == null){
            Alert.addAlert(session, AlertType.DANGER, ERROR_INSPECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }
        request.setAttribute("inspectedUser", inspectedUser);
        
        List<Project> userProjects = userDao.getProjects(inspectedUser);
        List<Fund> fundedProjects = userDao.getFunds(inspectedUser);
        request.setAttribute("userProjects", userProjects);
        request.setAttribute("fundedProjects", fundedProjects);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp?nav=user&id=" + id);
        requestDispatcher.forward(request, response);
        
    }

    /**
     * Change the user password
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void doChangePass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pass = request.getParameter("pass");
        String confpass = request.getParameter("confpass");
        System.err.println(pass + " " + confpass);
        if (!pass.equals(confpass)) {
            //Passwords missmatch
            Alert.addAlert(request.getSession(), AlertType.WARNING, ERROR_CONF_PASS);
        } else if (pass.equals("") || confpass.equals("")) {
            //Empty pass
            Alert.addAlert(request.getSession(), AlertType.WARNING, ERROR_EMPTY_PASS);
        } else {
            //Change password
            User user = (User)request.getSession().getAttribute("user");
            user.setPassword(pass);
            userDao.update(user);
            Alert.addAlert(request.getSession(), AlertType.SUCCESS, SUCCESS_CHANGE_PASS);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp?nav=settings");
        requestDispatcher.forward(request, response);
    }

    /**
     * Delete the user account
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doDeleteAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String pass = request.getParameter("pass");

        if (user == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_INSPECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        if (!pass.equals(user.getPassword())) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_PASS);
            response.sendRedirect("index.jsp?nav=settings");
            return;
        }

        user.setDeleted(true);
        this.userDao.update(user);
        session.invalidate();

        HttpSession newSession = request.getSession();
        Alert.addAlert(newSession, AlertType.SUCCESS, SUCCESS_DELETE_ACCOUNT);
        response.sendRedirect("index.jsp");
    }
}
