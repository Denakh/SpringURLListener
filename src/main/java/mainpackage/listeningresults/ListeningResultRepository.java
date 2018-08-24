package mainpackage.listeningresults;

import mainpackage.urls.ListenedUrl;
import mainpackage.users.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListeningResultRepository extends JpaRepository<ListeningResult, Long> {

    @Query("SELECT l FROM ListeningResult l WHERE l.listenedUrl = :url ORDER BY l.id ASC")
    List<ListeningResult> getAllResultsByURL(@Param("url") ListenedUrl url);

}
