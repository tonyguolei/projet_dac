/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;


/**
 *
 * @author guillaumeperrin
 */
@Singleton
@Lock(LockType.READ) // allows timers to execute in parallel
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
    
    @Schedule(hour="*")
    private void checkDeadLine() {
        System.out.println("Checking deadline...");
        
        List<Project> projects = projectDao.getAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
	String today = sdf.format(new Date()); 
        
        for (Project p : projects) {
            if (sdf.format(p.getEndDate()).equals(today)  && !p.alreadyTransferred()) {
                Notification notif = new Notification(
                        p.getIdOwner(),
                        p,
                        DEADLINE_REACHED
                );
                notificationDao.save(notif);
                if (fundDao.getFundLevel(p).compareTo(p.getGoal()) >= 0) {
                    User user = p.getIdOwner();
                    user.addBalance(fundDao.getFundLevel(p));
                    userDao.update(user);
                    p.transferDone();
                    projectDao.update(p);
                }
            }
        }
    }
    
}
