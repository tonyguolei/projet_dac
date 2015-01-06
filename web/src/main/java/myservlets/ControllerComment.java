/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myservlets;

import alerts.Alert;
import alerts.AlertType;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mybeans.Comment;
import mybeans.CommentDao;
import mybeans.Project;
import mybeans.ProjectDao;
import mybeans.User;

/**
 *
 * @author tib
 */
public class ControllerComment extends HttpServlet {
    
    private static final String ERROR_LOGIN = "Please log in to comment on a project.";
    private static final String ERROR_PARAM = "Please specify correct parameters.";
    private static final String SUCCESS_CREATE = "Commented!";
    private static final String ERROR_EMPTY_COMMENT = "Please enter a comment.";
    
    @EJB
    private ProjectDao projectDao;
    @EJB
    private CommentDao commentDao;

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
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute("user");
        if (user == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_LOGIN);
            response.sendRedirect("index.jsp?nav=login");
            return;
        }

        String idS = request.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(idS);
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_PARAM);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        Project project = projectDao.getByIdProject(id);
        if(project == null){
            Alert.addAlert(session, AlertType.DANGER, ERROR_PARAM);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }
        
        String content = request.getParameter("content");
        if (content.equals("")) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_EMPTY_COMMENT);
            response.sendRedirect("index.jsp?nav=project&id="+id);
            return;
        }
        
        Comment comment = new Comment(user, project, content);
        commentDao.save(comment);
        Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_CREATE);
        response.sendRedirect("index.jsp?nav=project&id="+id);
    }

}
