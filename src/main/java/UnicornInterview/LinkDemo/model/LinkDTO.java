package UnicornInterview.LinkDemo.model;

import jakarta.persistence.Lob;
import lombok.*;

/**
 * Created by User: Vu
 * Date: 12.04.2024
 * Time: 21:22
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LinkDTO {
    private Long id;
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
}
