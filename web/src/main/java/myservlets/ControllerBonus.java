package myservlets;

import alerts.Alert;
import alerts.AlertType;
import mybeans.*;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by guolei on 1/13/15.
 */
public class ControllerBonus extends HttpServlet {

    private static final String ERROR_LOGIN = "Please log in to comment on a project.";
    private static final String ERROR_PROJECT_ID = "Project id doesn't exist, internal server error.";
    private static final String ERROR_PROJECT = "Project doesn't exist, internal server error.";
    private static final String ERROR_EMPTY_VALUE = "Please enter a value.";
    private static final String ERROR_EMPTY_TITLE = "Please enter a title.";
    private static final String ERROR_EMPTY_DESCRIPTION = "Please enter a description.";
    private static final String ERROR_PARAM = "Please specify correct parameters.";
    private static final String ERROR_CREATE = "Error occurred! internal server error";
    private static final String SUCCESS_CREATE = "Create successfully a bonus!";
    
    @EJB
    private BonusDao bonusDao;

    @EJB
    private ProjectDao projectDao;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
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
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

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
            Alert.addAlert(session, AlertType.DANGER, ERROR_PROJECT_ID);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        Project project = projectDao.getByIdProject(id);
        if(project == null){
            Alert.addAlert(session, AlertType.DANGER, ERROR_PROJECT);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        String valueS = request.getParameter("value");
        if (valueS == null || valueS.equals("")) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_EMPTY_VALUE);
            response.sendRedirect("index.jsp?nav=project&id="+id);
            return;
        }

        BigDecimal value;
        try {
            value = new BigDecimal(Float.parseFloat(valueS));
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_PARAM);
            response.sendRedirect("index.jsp?nav=projects&id="+id);
            return;
        }
        
        String title = request.getParameter("title");
        if (title == null || title.equals("")) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_EMPTY_TITLE);
            response.sendRedirect("index.jsp?nav=project&id="+id);
            return;
        }

        String description = request.getParameter("description");
        if (description == null || description.equals("")) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_EMPTY_DESCRIPTION);
            response.sendRedirect("index.jsp?nav=project&id="+id);
            return;
        }

        try {
            Bonus bonus = new Bonus(value, title, description, project);
            bonusDao.save(bonus);
            Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_CREATE);
            response.sendRedirect("index.jsp?nav=project&id=" + id);
        } catch (EJBException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_CREATE);
            response.sendRedirect("index.jsp?nav=project&id=" + id);
        }
    }
}
