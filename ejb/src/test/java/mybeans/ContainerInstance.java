/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import javax.ejb.embeddable.EJBContainer;

/**
 *
 * @author guillaumeperrin
 */
public class ContainerInstance {

    private static EJBContainer container = null;
    
    public static EJBContainer getContainer() {
        if (container == null) {
            container = EJBContainer.createEJBContainer();
        }
        return container;
    }

    public static void closeContainer() {
        container.close();
    }
}
