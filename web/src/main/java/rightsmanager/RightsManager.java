/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rightsmanager;

import alerts.Alert;
import alerts.AlertType;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import mybeans.Project;

/**
 * This module manage rights across webpages or controllers
 * @author bemug
 */
public class RightsManager extends HttpServlet {
    
    /**
     * Checks if the user if logged in
     * @param session the session to check in
     * @return true if logged, false otherwise
     */
    public static boolean isNotLogged(HttpSession session) {
        return session.getAttribute("user") == null;
    }
    
    /**
     * Checks if the user if logged in and, if not, display a message and
     * redirects him to the login page
     * @param session the session to check in
     * @param response the response to send the redirect
     * @param alertType the type of alert to display
     * @param message the alert message to display
     */
    public static boolean isNotLoggedRedirect(HttpSession session, HttpServletResponse response, AlertType alertType, String message) { 
        return isNotLoggedRedirectTo(session, response, alertType, message, "index.jsp?nav=login");
    }
    
    /**
     * Checks if the user if logged in and, if not, display a message and
     * redirects him to a custom page specified as parameter
     * @param session the session to check in
     * @param response the response to send the redirect
     * @param alertType the type of alert to display
     * @param message the alert message to display
     * @param url the url to redirect the user
     */
    public static boolean isNotLoggedRedirectTo(HttpSession session, HttpServletResponse response, AlertType alertType, String message, String url) {
        boolean isNotLogged = isNotLogged(session);
        if (isNotLogged) {
            if (alertType != null && (message != null || !message.equals(""))) {
                Alert.addAlert(session, alertType, message);
            }
            try {
                response.sendRedirect(url);
            } catch (IOException ex) {
                Logger.getLogger(RightsManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isNotLogged;
    }
    /**
     * Make the user log in and forward to a given page. 
     * @param session
     * @param response
     * @param alertType the alert type.
     * @param message the message to display.
     * @param fwdUrl the forward URL.
     * @return 
     */
    public static boolean logAndForward(HttpSession session, HttpServletResponse response, AlertType alertType, String message, String fwdUrl) {
        if (isNotLoggedRedirect(session, response, alertType, message)) {
            session.setAttribute("fwd", fwdUrl);
            return true;
        }
        return false;        
    }
    
    /**
     * Check if the user is the owner or not
     * @param session
     * @param project
     * @return  true if not owner
     */
    public static boolean isNotOwner(HttpSession session, Project project) {
        if (!isNotLogged(session)) {
            return session.getAttribute("user") == project.getIdOwner();
        }
        return false;
    }
    
    /**
     * 
     * @param session
     * @param project
     * @param response
     * @param alertType
     * @param message
     * @return 
     */
    public static boolean isNotOwnerRedirect(HttpSession session, Project project, HttpServletResponse response, AlertType alertType, String message) { 
        return isNotOwnerRedirectTo(session, project, response, alertType, message, "index.jsp?nav=login");
    }
    
    /**
     * 
     * @param session
     * @param project
     * @param response
     * @param alertType
     * @param message
     * @param url
     * @return 
     */
    public static boolean isNotOwnerRedirectTo(HttpSession session, Project project, HttpServletResponse response, AlertType alertType, String message, String url) {
        boolean isNotOwner = isNotOwner(session, project);
        if (isNotOwner) {
            if (alertType != null && (message != null || !message.equals(""))) {
                Alert.addAlert(session, alertType, message);
            }
            try {
                response.sendRedirect(url);
            } catch (IOException ex) {
                Logger.getLogger(RightsManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isNotOwner;
    }
    
}
