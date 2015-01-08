/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myservlets;

import alerts.Alert;
import alerts.AlertType;
import mybeans.*;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tib
 */
@WebServlet(name = "ControllerProject", urlPatterns = {"/ControllerProject"})
public class ControllerProject extends HttpServlet {

    private static final String ERROR_LOGIN = "Please log in to create a new project.";
    private static final String ERROR_LOGIN_DELETE = "You must log in to delete a project.";
    private static final String ERROR_LOGIN_EDIT = "You must log in to edit a project.";
    private static final String ERROR_NOT_ADMIN_DELETE = "You must be an admin to delete a project.";
    private static final String ERROR_NOT_ADMIN_EDIT = "You must be an admin to edit a project.";
    private static final String ERROR_FORM = "Please fill the form correctly.";
    private static final String ERROR_DEADLINE = "The deadline can not be in the past.";
    private static final String SUCCESS_CREATE = "Project created succefully!";
    private static final String SUCCESS_REPORT = "Project reported succefully!";
    private static final String SUCCESS_DELETE = "Project deleted succefully!";
    private static final String ERROR_DB = "Something went wrong when creating your project. Please try again later";
    private static final String ERROR_ID = "Please specify an ID";
    private static final String ERROR_INSPECT = "Please specify a valid project ID";


    @EJB
    private ProjectDao projectDao;
    @EJB
    private FundDao fundDao;
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
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        switch (action) {
            case "create":
                doCreate(request, response);
                break;
            case "list":
                doList(request, response);
                break;
            case "inspect":
                doInspect(request, response);
                break;
            case "search":
                doSearch(request, response);
                break;
            case "report":
                doReport(request, response);
                break;
            case "delete":
                doDeleteProject(request, response);
                break;
            case "getEditPage":
                doGetEditPage(request, response);
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

    private void doGetEditPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute("user");
        if (user == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_LOGIN_EDIT);
            response.sendRedirect("index.jsp?nav=login");
            return;
        }
        
        if (!user.getIsAdmin()) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_NOT_ADMIN_EDIT);
            doInspect(request, response);
            return;
        }
        
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_ID);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        Project project = projectDao.getByIdProject(id);
        if(project == null){
            Alert.addAlert(session, AlertType.DANGER, ERROR_INSPECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }
        
        request.setAttribute("project", project);
        request.getRequestDispatcher("index.jsp?nav=editproject").forward(request, response);
    }
    
    private void doDeleteProject(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_ID);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }
        
        Project project = projectDao.getByIdProject(id);
        if(project == null){
            Alert.addAlert(session, AlertType.DANGER, ERROR_INSPECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }
        
        User user = (User)session.getAttribute("user");
        if (user == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_LOGIN_DELETE);
            doInspect(request, response);
            return;
        }
        
        if (!user.getIsAdmin()) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_NOT_ADMIN_DELETE);
            doInspect(request, response);
            return;
        }
        
        projectDao.delete(project);
        Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_DELETE);
        response.sendRedirect("index.jsp?nav=projects");
    }
    
    private void doCreate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute("user");
        if (user == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_LOGIN);
            response.sendRedirect("index.jsp?nav=login");
            return;
        }
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String tags = request.getParameter("tags");
        String goalS = request.getParameter("goal");
        String endDateS = request.getParameter("endDate");
        if (title == null || title.equals("") ||
                description == null || description.equals("") ||
                tags == null || tags.equals("") ||
                goalS == null || endDateS == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
            try {
                request.getRequestDispatcher("index.jsp?nav=createproject").forward(request, response);
            } catch (ServletException e) {
                Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
                response.sendRedirect("index.jsp?nav=createproject");
                return;
            }
            return;
        }

        BigDecimal goal = BigDecimal.ZERO;
        Date endDate = new java.sql.Date(0);
        try {
            goal = BigDecimal.valueOf(Float.parseFloat(goalS));
            endDate = java.sql.Date.valueOf(endDateS);
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
            try {
                request.getRequestDispatcher("index.jsp?nav=createproject").forward(request, response);
            } catch (ServletException ee) {
                Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
                response.sendRedirect("index.jsp?nav=createproject");
                return;
            }
            return;
        } catch (IllegalArgumentException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
            try {
                request.getRequestDispatcher("index.jsp?nav=createproject").forward(request, response);
            } catch (ServletException ee) {
                Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
                response.sendRedirect("index.jsp?nav=createproject");
                return;
            }
            return;
        }

        if (endDate.before(new Date())) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_DEADLINE);
            try {
                request.getRequestDispatcher("index.jsp?nav=createproject").forward(request, response);
            } catch (ServletException e) {
                Alert.addAlert(session, AlertType.DANGER, ERROR_DEADLINE);
                response.sendRedirect("index.jsp?nav=createproject");
                return;
            }
            return;
        }

        Project project = new Project(user, goal, title, description, endDate, tags);
        try {
            projectDao.save(project);
            Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_CREATE);
            response.sendRedirect("index.jsp");
            return;
        } catch (Exception e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_DB);
            try {
                request.getRequestDispatcher("index.jsp?nav=createproject").forward(request, response);
            } catch (ServletException ee) {
                Alert.addAlert(session, AlertType.DANGER, ERROR_DB);
                response.sendRedirect("index.jsp?nav=createproject");
                return;
            }
            return;
        }

    }

    private void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Project> projects = projectDao.getAll();
        request.setAttribute("projects", projects);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp?nav=projects");
        requestDispatcher.forward(request, response);
    }

    private void doSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag = request.getParameter("tag");
        List<Project> projects = projectDao.getAllMatching(tag);
        request.setAttribute("projects", projects);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp?nav=projects");
        requestDispatcher.forward(request, response);
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

        Project project = projectDao.getByIdProject(id);
        if(project == null){
            Alert.addAlert(session, AlertType.DANGER, ERROR_INSPECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        List<Comment> comments = commentDao.getComments(project);
        request.setAttribute("comments", comments);

        BigDecimal fundLevel = fundDao.getFundLevel(project);
        request.setAttribute("fundLevel", fundLevel);
        request.setAttribute("project", project);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp?nav=project&id=" + id);
        requestDispatcher.forward(request, response);
    }

    private void doReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_ID);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        Project project = projectDao.getByIdProject(id);
        if(project == null){
            Alert.addAlert(session, AlertType.DANGER, ERROR_INSPECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        project.setFlagged(true);
        projectDao.update(project);
        Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_REPORT);
        response.sendRedirect("index.jsp?nav=project&id="+id);
    }

}
