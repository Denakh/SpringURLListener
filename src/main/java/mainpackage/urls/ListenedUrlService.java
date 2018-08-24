package mainpackage.urls;

import mainpackage.users.CustomUser;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListenedUrlService {

    void addListenedUrl(ListenedUrl listenedUrl);

    void updateListenedUrl(ListenedUrl listenedUrl);

    void deleteListenedUrl(Long id);

    List<ListenedUrl> getAllListenedUrls();

    List<ListenedUrl> getAllURLsByUser(CustomUser user);

    public ListenedUrl getURLById(long id);
}
