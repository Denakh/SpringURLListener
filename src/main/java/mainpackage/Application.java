package mainpackage;

import mainpackage.urls.ListenedUrl;
import mainpackage.urls.ListenedUrlService;
import mainpackage.users.CustomUser;
import mainpackage.users.UserRole;
import mainpackage.users.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@EnableScheduling
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(final UserService userService, final ListenedUrlService listenedUrlService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                Date date = new Date();
                CustomUser user = new CustomUser("user", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER);
                userService.addUser(new CustomUser("admin", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.ADMIN));
                userService.addUser(user);
                listenedUrlService.addListenedUrl(new ListenedUrl(user, date, "https://www.dou.ua", "/", "keyword", 5000));

            }
        };
    }
}