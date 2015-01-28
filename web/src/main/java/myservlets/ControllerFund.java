/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myservlets;

import alerts.Alert;
import alerts.AlertType;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import mybeans.*;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import rightsmanager.RightsManager;

/**
 *
 * @author tib
 */
public class ControllerFund extends HttpServlet {

    private static final String ERROR_LOGIN = "Please log in to fund a project.";
    private static final String ERROR_PARAM = "Please specify correct parameters.";
    private static final String ERROR_DEADLINE = "You can not fund a project after its deadline is reached.";
    private static final String SUCCESS_CREATE = "Project funded!";
    private static final String NOTIFICATION_FUNDED = "It's a win, a project you have funded has reached its goal !";
    private static final String NOTIFICATION_FUNDED_OWNER = "It's a win, your project has been funded !";
    private static final String ERROR_INVALID_TOKEN = "Bad credit card.";

    @EJB
    private ProjectDao projectDao;
    @EJB
    private FundDao fundDao;
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

        String token = request.getParameter("stripeToken");
        String idS = request.getParameter("id");
        String valueS = request.getParameter("value");
        int id;
        BigDecimal value;
        try {
            id = Integer.parseInt(idS);
            value = new BigDecimal(Float.parseFloat(valueS));
        } catch (NumberFormatException e) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_PARAM);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }

        if (RightsManager.logAndForward(session, response, AlertType.DANGER, ERROR_LOGIN, "index.jsp?nav=project&id="+id)) return;
        
        if (value.compareTo(BigDecimal.ZERO) <= 0 ||
                (valueS.contains(".") && (valueS.length() - valueS.lastIndexOf(".")) > 3)) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_PARAM);
            response.sendRedirect("index.jsp?nav=project&id=" + id);
            return;
        }

        Project project = projectDao.getByIdProject(id);
        if(project == null){
            Alert.addAlert(session, AlertType.DANGER, ERROR_PARAM);
            response.sendRedirect("index.jsp?nav=projects");
            return;
        }
        
        if (project.getEndDate().before(new Date())) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_DEADLINE);
            response.sendRedirect("index.jsp?nav=project&id=" + id);
            return;
        }
        
        Stripe.apiKey = "sk_test_GIZv9WnqWKyYNYzsBpDhx0GI";
        try {
            Token.retrieve(token);
        } catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException ex) {
            Alert.addAlert(session, AlertType.DANGER, ERROR_INVALID_TOKEN);
            response.sendRedirect("index.jsp?nav=project&id=" + id);
            return;
        }
        
        BigDecimal oldFundLevel = fundDao.getFundLevel(project);
        Fund fund = fundDao.getFundByUser(user, project);
        if (fund == null) {
            fund = new Fund(user, project, value, token);
            fundDao.save(fund);
        } else {
            fund.addValue(value);
            fundDao.update(fund);
        }
        
        
        if (fundDao.getFundLevel(project).compareTo(project.getGoal()) >= 0 &&
                oldFundLevel.compareTo(project.getGoal()) == -1) {
            Notification notif = new Notification(
                    project.getIdOwner(),
                    project,
                    NOTIFICATION_FUNDED_OWNER
            );
            notificationDao.save(notif);
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
                notif = new Notification(
                        u,
                        project,
                        NOTIFICATION_FUNDED
                );
                notificationDao.save(notif);
            }
        }
        
       
        
        Alert.addAlert(session, AlertType.SUCCESS, SUCCESS_CREATE);
        response.sendRedirect("index.jsp?nav=project&id=" + id);
    }

}
