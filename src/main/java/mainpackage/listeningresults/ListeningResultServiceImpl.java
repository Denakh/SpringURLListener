package mainpackage.listeningresults;

import mainpackage.urls.ListenedUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListeningResultServiceImpl implements ListeningResultService {
    @Autowired
    private ListeningResultRepository listeningResultRepository;

    @Override
    @Transactional
    public void addListeningResult(ListeningResult listeningResult) {
        listeningResultRepository.save(listeningResult);
    }

    @Override
    @Transactional
    public void updateListeningResult(ListeningResult listeningResult) {
        listeningResultRepository.save(listeningResult);
    }

    @Override
    @Transactional
    public void deleteListeningResult(Long id) {
        listeningResultRepository.delete(id);
    }

    @Override
    public List<ListeningResult> getAllResultsByURL(ListenedUrl url) {
        return listeningResultRepository.getAllResultsByURL(url);
    }
}
