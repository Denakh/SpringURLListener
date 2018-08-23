package mainpackage.urls;

import mainpackage.listeningresults.ListeningResult;
import mainpackage.users.CustomUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ListenedUrl {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;

    @OneToMany(mappedBy = "listenedUrl", cascade = CascadeType.ALL)
    private List<ListeningResult> listeningResults = new ArrayList<>();

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    private String server;
    private String uri;
    private String keyword;
    private long limitedTime;

    public ListenedUrl() {
    }

    public ListenedUrl(CustomUser user, Date date, String server, String uri, String keyword, long limitedTime) {
        this.user = user;
        this.date = date;
        this.server = server;
        this.uri = uri;
        this.keyword = keyword;
        this.limitedTime = limitedTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public List<ListeningResult> getListeningResults() {
        return listeningResults;
    }

    public void setListeningResults(List<ListeningResult> listeningResults) {
        this.listeningResults = listeningResults;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public long getLimitedTime() {
        return limitedTime;
    }

    public void setLimitedTime(long limitedTime) {
        this.limitedTime = limitedTime;
    }
}
