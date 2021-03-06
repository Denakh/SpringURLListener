package mainpackage.listeningresults;

import mainpackage.urls.ListenedUrl;

import java.util.List;

public interface ListeningResultService {

    void addListeningResult(ListeningResult listeningResult);

    void updateListeningResult(ListeningResult listeningResult);

    void deleteListeningResult(Long id);

    List<ListeningResult> getAllResultsByURL(ListenedUrl url);

}
