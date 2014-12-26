/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alerts;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 * Handle alerts for Controllers
 * @author bemug
 */
public class Alert {
    
    /**
     * Add an alert, it will be displayed on the next loaded page using the message.jsp page.
     * See AlertType for available alert types.
     * @param session the session where we add the message
     * @param type message type (for display)
     * @param msg the message to display, should be a static final in the controller calling it
     */
    public static void addAlert(HttpSession session, AlertType type, String msg) {
        System.err.println("Adding a "+type+" message.");
        String alertListName = "msg-"+type;
        ArrayList<String> messages = (ArrayList<String>)session.getAttribute(alertListName);
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(msg);
        session.setAttribute(alertListName, messages);
    }
}
