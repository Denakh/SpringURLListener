package mainpackage.urls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListenedUrlServiceImpl implements ListenedUrlService {
    @Autowired
    private ListenedUrlRepository listenedUrlRepository;

    @Override
    @Transactional
    public void addListenedUrl(ListenedUrl listenedUrl) {
        listenedUrlRepository.save(listenedUrl);
    }

    @Override
    @Transactional
    public void updateListenedUrl(ListenedUrl listenedUrl) {
        listenedUrlRepository.save(listenedUrl);
    }

    @Override
    @Transactional
    public void deleteListenedUrl(Long id) {
        listenedUrlRepository.delete(id);
    }

}
