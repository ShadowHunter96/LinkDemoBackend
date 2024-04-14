package UnicornInterview.LinkDemo.model;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by User: Vu
 * Date: 14.04.2024
 * Time: 16:22
 */
@Getter
@Setter
public class LinkChangeDTO {
    private Long id;
    private Integer revisionType;
    private Date revisionDate;
    private String name;
    private String url;
    @Lob
    private byte[] image;
    private String imageBase64;
    private String description;
    private boolean availableInFirefox;
    private boolean availableInChrome;
    private boolean isActive;
    private boolean openInNewWindow;
    private boolean deleted;
    private int rev;

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }
}
