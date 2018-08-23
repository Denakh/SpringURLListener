package mainpackage;

import mainpackage.users.CustomUser;
import mainpackage.users.UserRole;
import mainpackage.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    @Autowired
    private UserService userService;

    @Scheduled(initialDelayString = "${scheduler.delay}", fixedDelayString = "${scheduler.delay}")
    public void doWork() {

        userService.addUser(new CustomUser("usernext", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));

     /*
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     */
    }

}
