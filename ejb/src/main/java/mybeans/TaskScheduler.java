/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.transaction.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillaumeperrin
 */
@Singleton
@Lock(LockType.READ) // allows timers to execute in parallel
@TransactionManagement(TransactionManagementType.BEAN)
public class TaskScheduler {

    private static final String DEADLINE_REACHED = "The deadline of a project has been reached";
    private static final String DEADLINE_REACHED_OWNER = "The deadline of your project has been reached";

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

    @Schedule(hour = "*")
    public void checkDeadLine() {
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
                        DEADLINE_REACHED_OWNER
                );
                notificationDao.save(notif);
                LinkedHashSet<User> users = new LinkedHashSet<>();
                for (Fund f : p.getFundCollection()) {
                    users.add(f.getIdUser());
                }
                for (MemoriseProject mp : p.getMemoriseProjectCollection()) {
                    if (!users.contains(mp.getIdUser())) {
                        users.add(mp.getIdUser());
                    }
                }
                for (User u : users) {
                    notif = new Notification(
                            u,
                            p,
                            DEADLINE_REACHED
                    );
                    notificationDao.save(notif);
                }

                Stripe.apiKey = "sk_test_GIZv9WnqWKyYNYzsBpDhx0GI";

                p.transferDone();
                projectDao.update(p);

                User user = p.getIdOwner();
                if (fundDao.getFundLevel(p).compareTo(p.getGoal()) >= 0) {
                    for (Fund f : p.getFundCollection()) {
                        HashMap<String, Object> chargeMap = new HashMap<String, Object>();
                        chargeMap.put("amount", f.getValue().multiply(new BigDecimal(100)).intValue());
                        chargeMap.put("currency", "usd");
                        chargeMap.put("card", f.getToken());
                        Charge charge = null;
                        try {
                            userTransaction.begin();
                            charge = Charge.create(chargeMap);
                            user.addBalance(f.getValue());
                            userDao.update(user);
                            userTransaction.commit();
                        } catch (Exception e) {
                            try {
                                userTransaction.rollback();
                                if (charge != null && charge.getPaid()) {
                                    charge.getRefunds().create(new HashMap<String,Object>());
                                } else {
                                    System.out.println("The token was not valid for fund " + f.getIdFund());
                                }
                            } catch (Exception e1) {
                                e.printStackTrace();
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
