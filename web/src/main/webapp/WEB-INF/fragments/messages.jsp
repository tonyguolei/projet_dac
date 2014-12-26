<%-- 
    Document   : messages
    Created on : Dec 26, 2014, 10:38:42 PM
    Author     : bemug
--%>

<%@page import="java.util.Iterator"%>
<%@page import="alerts.AlertType"%>
<%@page import="java.util.ArrayList"%>

<% 
for (AlertType type : AlertType.values()) {
    String typeName = type.toString();
    ArrayList<String> messages = (ArrayList<String>)session.getAttribute("msg-"+typeName);
    if (messages != null) {
        //Iterator to avoid ConcurrentModificationException
        Iterator<String> iter = messages.iterator();
        while (iter.hasNext()) {
            String msg = iter.next();
            //Display the message
            %>
            <div class="alert alert-<%=typeName%>" role="alert"><%=msg%></div>
            <%
            //Removes it to not display it again
            iter.remove();
        }
    }
}
%>
