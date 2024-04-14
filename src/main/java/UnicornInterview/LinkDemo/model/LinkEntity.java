package UnicornInterview.LinkDemo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

/**
 * Created by User: Vu
 * Date: 12.04.2024
 * Time: 21:21
 */
@Entity
@Table(name ="link_entity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Audited
public class LinkEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
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
