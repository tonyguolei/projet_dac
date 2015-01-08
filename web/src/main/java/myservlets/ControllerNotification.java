/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myservlets;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mybeans.Notification;
import mybeans.NotificationDao;
import mybeans.User;

/**
 *
 * @author guillaumeperrin
 */
public class ControllerNotification extends HttpServlet {

    @EJB
    private NotificationDao notificationDao;
    
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
            case "getNumber":
                doGetNumber(request, response);
                break;
            case "read":
                doRead(request, response);
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

    private void doGetNumber(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute("user");
        
        request.setAttribute("numberNewNotification", notificationDao.getNonReadNumber(user));
        request.setAttribute("listNotification", notificationDao.getAllByUserId(user));

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp?" + (String)request.getAttribute("fwd"));
        requestDispatcher.forward(request, response);
    }
    
    private void doRead(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute("user");
        int idProject = Integer.parseInt(request.getParameter("idProject"));
        int idNotif = Integer.parseInt(request.getParameter("idNotif"));
        
        Notification notification = notificationDao.getByIdNotification(idNotif);
        if (notification != null) {
            notificationDao.delete(notification);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp?nav=project&id=" + idProject);
        requestDispatcher.forward(request, response);
    }
}
