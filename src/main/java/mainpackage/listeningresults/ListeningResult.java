package mainpackage.listeningresults;

import mainpackage.urls.ListenedUrl;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ListeningResult {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "url_id")
    private ListenedUrl listenedUrl;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    private long respTime;
    private int respCode;
    private long contentLength;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean keywordInclusion;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean respTimeExcess;

    public ListeningResult() {
    }

    public ListeningResult(ListenedUrl listenedUrl, Date date, long respTime, int respCode, long contentLength, boolean keywordInclusion, boolean respTimeExcess) {
        this.listenedUrl = listenedUrl;
        this.date = date;
        this.respTime = respTime;
        this.respCode = respCode;
        this.contentLength = contentLength;
        this.keywordInclusion = keywordInclusion;
        this.respTimeExcess = respTimeExcess;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ListenedUrl getListenedUrl() {
        return listenedUrl;
    }

    public void setListenedUrl(ListenedUrl listenedUrl) {
        this.listenedUrl = listenedUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getRespTime() {
        return respTime;
    }

    public void setRespTime(long respTime) {
        this.respTime = respTime;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isKeywordInclusion() {
        return keywordInclusion;
    }

    public void setKeywordInclusion(boolean keywordInclusion) {
        this.keywordInclusion = keywordInclusion;
    }

    public boolean isRespTimeExcess() {
        return respTimeExcess;
    }

    public void setRespTimeExcess(boolean respTimeExcess) {
        this.respTimeExcess = respTimeExcess;
    }
}

