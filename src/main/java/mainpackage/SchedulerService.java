package mainpackage;

import mainpackage.listeningresults.ListeningResult;
import mainpackage.listeningresults.ListeningResultService;
import mainpackage.urls.ListenedUrl;
import mainpackage.urls.ListenedUrlService;
import mainpackage.users.CustomUser;
import mainpackage.users.UserRole;
import mainpackage.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private UserService userService;
    @Autowired
    private ListenedUrlService listenedUrlService;
    @Autowired
    private ListeningResultService listeningResultService;

    @Scheduled(initialDelayString = "${scheduler.delay}", fixedDelayString = "${scheduler.delay}")
    public void doWork() {

        //CustomUser user = this.getCurrentUser();
        List<ListenedUrl> listenedUrls = listenedUrlService.getAllListenedUrls();
        if (!listenedUrls.isEmpty()) {
            for (ListenedUrl listenedUrl : listenedUrls) {
                Date date = new Date();
                String server = listenedUrl.getServer();
                String uri = listenedUrl.getUri();
                ResponseEntity<String> responseEntity = this.get(server, uri);
                HttpHeaders hh = responseEntity.getHeaders();
                long respTime = hh.getDate() - date.getTime();
                int respCode = responseEntity.getStatusCode().value();
                long contentLength = hh.getContentLength();
                boolean keywordInclusion = false;
                boolean respTimeExcess = respTime > listenedUrl.getLimitedTime() ? true : false;
                ListeningResult listeningResult = new ListeningResult(listenedUrl, date, respTime, respCode, contentLength,
                keywordInclusion, respTimeExcess);
                listeningResultService.addListeningResult(listeningResult);
            }
        }
     /*
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     */
    }

    private ResponseEntity<String> get(String server, String uri) {
        HttpHeaders headers = new HttpHeaders();
        //headers.add("Content-Type", "application/json");
        headers.add("Accept", "text/*");
        RestTemplate rest = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
        return responseEntity;
    }

    private CustomUser getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        return userService.getUserByLogin(login);
    }

}
