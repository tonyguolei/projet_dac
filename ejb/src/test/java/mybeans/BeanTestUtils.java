/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

/**
 *
 * @author guillaumeperrin
 */
public class BeanTestUtils {

    private static EJBContainer container = null;
    private static Context context = null;
    
    private static Context getContext() {
        if (container == null) {
            container = EJBContainer.createEJBContainer();
            context = container.getContext();
        }
        return context;
    }
    
    public static <T> T lookup(Class<T> type, String className) throws NamingException {
        try {
            return (T) getContext().lookup("java:global/classes/" + className);
        } catch (NamingException ex) {
            // lookup with cobertura
            return (T) getContext().lookup("java:global/cobertura/" + className);
        }
    }

    public static void closeContainer() {
        container.close();
    }
}
