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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import rightsmanager.RightsManager;
import java.util.Map;

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
    private static final String ERROR_EDIT_NOT_ALLOWED = "You must be the owner of the project or an admin to edit a project.";
    private static final String ERROR_FORM = "Please fill the form correctly.";
    private static final String ERROR_DEADLINE = "The deadline can not be in the past.";
    private static final String SUCCESS_CREATE = "Project created succefully!";
    private static final String SUCCESS_REPORT = "Project reported succefully!";
    private static final String SUCCESS_DELETE = "Project deleted succefully!";
    private static final String SUCCESS_EDIT = "Project modified succefully!";
    private static final String ERROR_DB = "Something went wrong when creating your project. Please try again later";
    private static final String ERROR_ID = "Please specify an ID";
    private static final String ERROR_INSPECT = "Please specify a valid project ID";
    private static final String ERROR_EDIT_GOAL = "You must be an administrator to modify the goal of a project.";
    private static final String ERROR_EDIT_DEADLINE = "You must be an administrator to modify the deadline of a project.";
    private static final String NOTIF_PROJECT_MODIFIED = "This project has been modified, have a look";

    @EJB
    private ProjectDao projectDao;
    @EJB
    private FundDao fundDao;
    @EJB
    private CommentDao commentDao;
    @EJB
    private NotificationDao notificationDao;
    @EJB
    private BonusDao bonusDao;
    @EJB
    private PrivateMessageDao privateMessageDao;

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

        String action = request.getParameter("action").trim();
        System.out.println("Action: " + action);
        switch (action) {
            case "create":
                System.out.println("Create");
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
            case "edit":
                doEdit(request, response);
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

    private void doEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        System.err.println("LOL1");
        if (RightsManager.isNotLoggedRedirect(session, response, AlertType.DANGER, ERROR_LOGIN_EDIT)) return;
        System.err.println("LOL2");
        
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_ID);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        Project project = projectDao.getByIdProject(id);
        if (project == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_INSPECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        if (!user.getIsAdmin() && !user.equals(project.getIdOwner())) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_EDIT_NOT_ALLOWED);
            doInspect(request, response);
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String tags = request.getParameter("tags");
        String goalS = request.getParameter("goal");
        String endDateS = request.getParameter("endDate");
        if (title == null || title.equals("")
                || description == null || description.equals("")
                || tags == null || tags.equals("")
                || goalS == null || endDateS == null) {
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

        if (!user.getIsAdmin()) {
            if (project.getGoal().compareTo(goal) != 0) {
                Alert.addAlert(session, AlertType.DANGER, ERROR_EDIT_GOAL);
                request.setAttribute("project", project);
                request.getRequestDispatcher("index.jsp?nav=editproject").forward(request, response);
                return;
            }
            if (!project.getEndDate().equals(endDate)) {
                Alert.addAlert(session, AlertType.DANGER, ERROR_EDIT_DEADLINE);
                request.setAttribute("project", project);
                request.getRequestDispatcher("index.jsp?nav=editproject").forward(request, response);
                return;
            }
        }

        try {
            project.setTitle(title);
            project.setDescription(description);
            project.setTags(tags);
            project.setGoal(goal);
            project.setEndDate(endDate);
            projectDao.update(project);
            Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_EDIT);

            //Send notification
            LinkedHashSet<User> users = new LinkedHashSet<>();
            for (Fund f : project.getFundCollection()) {
                users.add(f.getIdUser());
            }
            for (MemoriseProject mp : project.getMemoriseProjectCollection()) {
                if (!users.contains(mp.getIdUser())) {
                    users.add(mp.getIdUser());
                }
            }
            for (User u : users) {
                Notification notif = new Notification(
                        u,
                        project,
                        NOTIF_PROJECT_MODIFIED
                );
                notificationDao.save(notif);
            }

            response.sendRedirect("index.jsp?nav=project&id=" + id);
        } catch (Exception e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_DB);
            request.setAttribute("project", project);
            request.getRequestDispatcher("index.jsp?nav=editproject").forward(request, response);
        }
    }

    private void doGetEditPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        if (RightsManager.isNotLoggedRedirect(session, response, AlertType.DANGER, ERROR_LOGIN_EDIT)) return;

        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_ID);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        Project project = projectDao.getByIdProject(id);
        if (project == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_INSPECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        if (!user.getIsAdmin() && !user.equals(project.getIdOwner())) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_EDIT_NOT_ALLOWED);
            doInspect(request, response);
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
        if (project == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_INSPECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (RightsManager.isNotLoggedRedirect(session, response, AlertType.DANGER, ERROR_LOGIN_DELETE)) return;

        User projectOwner = project.getIdOwner();
        boolean userIsOwner = user.equals(projectOwner);

        if (!user.getIsAdmin() && !userIsOwner) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_NOT_ADMIN_DELETE);
            doInspect(request, response);
            return;
        }

        // Send a message to the project owner
        if (!userIsOwner) {
            String message = "One of your projects has been deleted: " + project.getTitle() + ".";
            PrivateMessage pm = new PrivateMessage(user, projectOwner, message);

            try {
                privateMessageDao.save(pm);
            } catch (Exception e) {
                Alert.addAlert(session, AlertType.DANGER, ERROR_DB);
            }
        }

        projectDao.delete(project);
        Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_DELETE);
        response.sendRedirect("index.jsp?nav=projects");
    }

    private void doCreate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        if (RightsManager.isNotLoggedRedirect(session, response, AlertType.DANGER, ERROR_LOGIN)) return;

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String tags = request.getParameter("tags");
        String goalS = request.getParameter("goal");
        String endDateS = request.getParameter("endDate");
        if (title == null || title.equals("")
                || description == null || description.equals("")
                || tags == null || tags.equals("")
                || goalS == null || endDateS == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
            request.getRequestDispatcher("index.jsp?nav=createproject").forward(request, response);
            return;
        }

        BigDecimal goal = BigDecimal.ZERO;
        Date endDate = new java.sql.Date(0);
        try {
            goal = BigDecimal.valueOf(Float.parseFloat(goalS));
            endDate = java.sql.Date.valueOf(endDateS);
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
            request.getRequestDispatcher("index.jsp?nav=createproject").forward(request, response);
            return;
        } catch (IllegalArgumentException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_FORM);
            request.getRequestDispatcher("index.jsp?nav=createproject").forward(request, response);
            return;
        }

        if (endDate.before(new Date())) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_DEADLINE);
            request.getRequestDispatcher("index.jsp?nav=createproject").forward(request, response);
            return;
        }

        Project project = new Project(user, goal, title, description, endDate, tags);
        try {
            projectDao.save(project);
            Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_CREATE);
            response.sendRedirect("index.jsp?nav=project&id=" + project.getIdProject());
            return;
        } catch (Exception e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_DB);
            request.getRequestDispatcher("index.jsp?nav=createproject").forward(request, response);
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
        if (project == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_INSPECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        List<Comment> comments = commentDao.getComments(project);
        request.setAttribute("comments", comments);

        List<Bonus> bonus = bonusDao.getBonus(project);
        if (bonus == null) {
            request.setAttribute("bonus", "");
        } else {
            request.setAttribute("bonus", bonus);
        }

        // fund level of the current user on this project (0 if nobody is connected).
        User user = (User) session.getAttribute("user");
        request.setAttribute("userFundLevel", BigDecimal.ZERO);
        if (user != null) {
            Fund fund = fundDao.getFundByUser(user, project);
            if (fund != null) {
                request.setAttribute("userFundLevel", fund.getValue());
            }
        }

        // structure giving the list of backers receiving each bonus.
        if (project.getIdOwner().equals(user)) {
            Map<Bonus, List<User>> userByBonus = new HashMap();

            for (Bonus b : project.getBonusCollection()) {
                List<User> userList = new LinkedList();
                
                for (Fund fund : project.getFundCollection()) {
                    if (fund.getValue().compareTo(b.getValue()) >= 0) {
                        userList.add(fund.getIdUser());
                    }
                }
                
                userByBonus.put(b, userList);
            }
            
            request.setAttribute("userByBonus", userByBonus);
        }

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
        if (project == null) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_INSPECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        project.setFlagged(true);
        projectDao.update(project);
        Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_REPORT);
        response.sendRedirect("index.jsp?nav=project&id=" + id);
    }

}
