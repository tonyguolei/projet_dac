/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mybeans.Project;
import mybeans.ProjectDao;
import mybeans.User;

/**
 *
 * @author tib
 */
@WebServlet(name = "ControllerProject", urlPatterns = {"/ControllerProject"})
public class ControllerProject extends HttpServlet {

    @EJB
    private ProjectDao projectDao;
    
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
            case "create":
                doCreate(request, response);
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

    private void doCreate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("user");
        if (user == null) {
            //TODO show a message
            response.sendRedirect("login.jsp");
            return;
        }
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String tags = request.getParameter("tags");
        String goalS = request.getParameter("goal");
        String endDateS = request.getParameter("endDate");
        System.out.println(title);
        System.out.println(description);
        System.out.println(goalS);
        System.out.println(endDateS);
        System.out.println(tags);
        if (title == null || title.equals("") ||
                description == null || description.equals("") ||
                tags == null || tags.equals("") ||
                goalS == null || endDateS == null) {
            //TODO show message
            response.sendRedirect("index.jsp?nav=createproject");
            return;
        }
        
        BigDecimal goal = BigDecimal.ZERO;
        Date endDate = new java.sql.Date(0);
        try {
            goal = BigDecimal.valueOf(Integer.parseInt(goalS));
            endDate = java.sql.Date.valueOf(endDateS);
        } catch (NumberFormatException e) {
            //TODO show a message (goal)
            response.sendRedirect("index.jsp?nav=createproject");
            return;
        } catch (IllegalArgumentException e) {
            //TODO show a message (date)
            response.sendRedirect("index.jsp?nav=createproject");
            return;
        }
        
        Project project = new Project(user, goal, title, description, endDate, tags);
        try {
            projectDao.save(project);
            //TODO show message success
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            //TODO show message
            response.sendRedirect("index.jsp?nav=createproject");
            return;
        }
        
    }

}
