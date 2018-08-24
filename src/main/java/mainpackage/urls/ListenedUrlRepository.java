package mainpackage.urls;

import mainpackage.users.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListenedUrlRepository extends JpaRepository<ListenedUrl, Long> {

    @Query("SELECT l FROM ListenedUrl l WHERE l.user = :user ORDER BY l.id ASC")
    List<ListenedUrl> getAllURLsByUser(@Param("user") CustomUser user);

}
