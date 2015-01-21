/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myservlets;

import alerts.Alert;
import alerts.AlertType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mybeans.PrivateMessage;
import mybeans.PrivateMessageDao;
import mybeans.User;
import mybeans.UserDao;
import rightsmanager.RightsManager;

/**
 *
 * @author guillaumeperrin
 */
public class ControllerPrivateMessage extends HttpServlet {

    private static final String ERROR_LOGIN = "Please log in to use the private message functionality.";
    private static final String ERROR_FORM = "Please fill the form correctly.";
    private static final String UNKNOWN_USER = "The recipient is unknown !";
    private static final String SUCCESS_SEND = "Message sent!";
    private static final String ERROR_DB = "Something went wrong when submiting your project. Please try again later.";

    @EJB
    private PrivateMessageDao privateMessageDao;
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
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        switch (action) {
            case "newmessage":
                doNewMessage(request, response);
                break;
            case "conversations":
                doListConversation(request, response);
                break;
            case "conversation":
                doConversation(request, response);
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

    private void doListConversation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute("user");

        if (RightsManager.isNotLoggedRedirect(session, response, AlertType.DANGER, ERROR_LOGIN)) return;
        
        List<PrivateMessage> listAllPm = privateMessageDao.getConversations(user);
        
        request.setAttribute("listAllPrivateMessage", listAllPm);
        
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp?nav=conversations");
        requestDispatcher.forward(request, response);
    }

    private void doNewMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        if (RightsManager.isNotLoggedRedirect(session, response, AlertType.DANGER, ERROR_LOGIN)) return;
        
        String dest = request.getParameter("dest");
        String message = request.getParameter("message");
        if (dest == null || dest.equals("")
                || message == null || message.equals("")) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
            request.getRequestDispatcher("index.jsp?nav=newmessage").forward(request, response);
            return;
        }
        
        User userDest = userDao.getByMail(dest);
        if (userDest == null) {
            Alert.addAlert(session, AlertType.DANGER, UNKNOWN_USER);
            request.getRequestDispatcher("index.jsp?nav=newmessage").forward(request, response);
            return;
        }
        
        PrivateMessage pm = new PrivateMessage(user, userDest, message);
        try {
            privateMessageDao.save(pm);
            Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_SEND);
            response.sendRedirect("index.jsp?nav=conversation&dest=" + pm.getDest().getMail());
            return;
        } catch (Exception e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_DB);
            request.getRequestDispatcher("index.jsp?nav=newmessage").forward(request, response);
            return;
        }
    }

    private void doConversation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        if (RightsManager.isNotLoggedRedirect(session, response, AlertType.DANGER, ERROR_LOGIN)) return;
        
        String dest = (String) request.getParameter("dest");
        if (dest == null || dest.equals("")) {
            Alert.addAlert(session, AlertType.DANGER, UNKNOWN_USER);
            request.getRequestDispatcher("index.jsp?nav=conversations").forward(request, response);
            return;
        }
        
        User userDest = userDao.getByMail(dest);
        if (userDest == null) {
            Alert.addAlert(session, AlertType.DANGER, UNKNOWN_USER);
            request.getRequestDispatcher("index.jsp?nav=conversations").forward(request, response);
            return;
        }
        
        List<PrivateMessage> listMessageConversation = privateMessageDao.getConversation(user, userDest);
        request.setAttribute("listMessageConversation", listMessageConversation);
        
        for(PrivateMessage pm : listMessageConversation) {
            if (!pm.getIsRead() && pm.getDest().equals(user)) {
                PrivateMessage pmCopy = new PrivateMessage(pm);
                pmCopy.setIsRead(true);
                privateMessageDao.update(pmCopy);
            }
        }
        
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp?nav=conversation");
        requestDispatcher.forward(request, response);
    }

}
