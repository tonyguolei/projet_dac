/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myservlets;

import alerts.Alert;
import alerts.AlertType;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mybeans.MemoriseProject;
import mybeans.MemoriseProjectDao;
import mybeans.Project;
import mybeans.ProjectDao;
import mybeans.User;
import mybeans.UserDao;

/**
 *
 * @author tib
 */
public class ControllerMemorise extends HttpServlet {
   

    private static final String ERROR_LOGIN = "Please log in to save a project.";
    private static final String ERROR_PARAM = "Please specify correct parameters.";
    private static final String ERROR_ID = "Please specify a correct user ID.";
    private static final String SUCCESS_CREATE = "Project remembered!";
    private static final String SUCCESS_DELETE = "Project forgotten!";
    private static final String ERROR_CREATE = "Project already remembered.";
    private static final String ERROR_DELETE = "Project already forgotten.";
    
    @EJB
    private ProjectDao projectDao;
    @EJB
    private MemoriseProjectDao memoriseProjectDao;
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
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        switch (action) {
            case "create":
                doCreate(request, response);
                break;
            case "remove":
                doRemove(request, response);
                break;
            case "list":
                doList(request, response);
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
        
        MemoriseProject memoriseProject = new MemoriseProject(user, project);
        try {
            memoriseProjectDao.save(memoriseProject);
            Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_CREATE);
            response.sendRedirect("index.jsp?nav=project&id=" + id);
        } catch (EJBException e) {
            Alert.addAlert(session, AlertType.INFO, ERROR_CREATE);
            response.sendRedirect("index.jsp?nav=project&id=" + id);
        }
    }
    
    
    private void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String idS = request.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_ID);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }
        
        User user = userDao.getByIdUser(id);
        if (user == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_ID);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }
        
        List<Project> projects = memoriseProjectDao.getByUser(user);
        request.setAttribute("projects", projects);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp?nav=projects");
        requestDispatcher.forward(request, response);
    }

    private void doRemove(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        
        MemoriseProject memoriseProject = new MemoriseProject(user, project);
        try {
            memoriseProjectDao.remove(memoriseProject);
            Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_DELETE);
            response.sendRedirect("index.jsp?nav=project&id=" + id);
        } catch (EJBException e) {
            Alert.addAlert(session, AlertType.INFO, ERROR_DELETE);
            response.sendRedirect("index.jsp?nav=project&id=" + id);
        }
    }
}
