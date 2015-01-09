/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.transaction.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 *
 * @author guillaumeperrin
 */
@Singleton
@Lock(LockType.READ) // allows timers to execute in parallel
@TransactionManagement(TransactionManagementType.BEAN)
public class TaskScheduler {

    private static final String DEADLINE_REACHED = "The deadline of your project has been reached";
    
    @EJB
    private ProjectDao projectDao;
    @EJB
    private NotificationDao notificationDao;
    @EJB
    private FundDao fundDao;
    @EJB
    private UserDao userDao;

    @Resource
    private EJBContext context;
    
    @Schedule(hour="*")
    private void checkDeadLine() {
        System.out.println("Checking deadline...");

        List<Project> projects = projectDao.getAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String today = sdf.format(new Date());
        UserTransaction userTransaction = context.getUserTransaction();
        
        for (Project p : projects) {
            if (sdf.format(p.getEndDate()).equals(today) && !p.alreadyTransferred()) {
                Notification notif = new Notification(
                        p.getIdOwner(),
                        p,
                        DEADLINE_REACHED
                );
                notificationDao.save(notif);
                if (fundDao.getFundLevel(p).compareTo(p.getGoal()) >= 0) {
                    try {
                        //start transaction
                        userTransaction.begin();
                        p.transferDone();
                        projectDao.update(p);
                        User user = p.getIdOwner();
                        user.addBalance(fundDao.getFundLevel(p));
                        userDao.update(user);
                        //commit transaction
                        userTransaction.commit();
                    } catch (Exception e) {
                        try {
                            userTransaction.rollback();
                        } catch (SystemException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
