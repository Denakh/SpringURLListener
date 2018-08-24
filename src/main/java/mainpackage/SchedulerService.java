package mainpackage;

import mainpackage.listeningresults.ListeningResult;
import mainpackage.listeningresults.ListeningResultService;
import mainpackage.urls.ListenedUrl;
import mainpackage.urls.ListenedUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private ListenedUrlService listenedUrlService;
    @Autowired
    private ListeningResultService listeningResultService;

    @Scheduled(initialDelayString = "${scheduler.delay}", fixedDelayString = "${scheduler.delay}")
    public void urlsListen() {
        List<ListenedUrl> listenedUrls = listenedUrlService.getAllListenedUrls();
        if (!listenedUrls.isEmpty()) {
            for (ListenedUrl listenedUrl : listenedUrls) {
                String server = listenedUrl.getServer();
                String uri = listenedUrl.getUri();
                Date dateStart = new Date();
                ResponseEntity<String> responseEntity = this.get(server, uri);
                Date dateFinish = new Date();
                HttpHeaders hh = responseEntity.getHeaders();
                long respTime = dateFinish.getTime() - dateStart.getTime();
                int respCode = responseEntity.getStatusCode().value();
                long contentLength = hh.getContentLength();
                boolean keywordInclusion = false;
                String respBody;
                if (responseEntity.hasBody()) {
                    respBody = responseEntity.getBody();
                    if (respBody.indexOf(listenedUrl.getKeyword()) > -1) keywordInclusion = true;
                }
                boolean respTimeExcess = respTime > listenedUrl.getLimitedTime() ? true : false;
                ListeningResult listeningResult = new ListeningResult(listenedUrl, dateStart, respTime, respCode, contentLength,
                        keywordInclusion, respTimeExcess);
                listeningResultService.addListeningResult(listeningResult);
            }
        }
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

}
