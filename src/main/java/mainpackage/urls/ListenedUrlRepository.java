package mainpackage.urls;

import mainpackage.users.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListenedUrlRepository extends JpaRepository<ListenedUrl, Long> {

    @Query("SELECT l FROM ListenedUrl l WHERE l.id = (SELECT MAX(l.id) FROM ListenedUrl l WHERE l.user = :user)")
    ListenedUrl findLastEntry(@Param("user") CustomUser user);

}
