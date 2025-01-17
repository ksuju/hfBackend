package li.com.backend_apiconnet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@SuperBuilder
@ToString(callSuper = true)
public class KopisFesEntity{

    @Id
    @Column(name = "mt20id")
    private String mt20id;

    private final String prfnm;
    private final String prfpdfrom;
    private final String prfpdto;
    private final String fcltynm;
    private final String poster;
    private final String area;
    private final String genrenm;
    private final String openrun;
    private final String prfstate;

    @CreatedDate
    @Getter
    private LocalDateTime createDate;

    @LastModifiedDate
    @Getter
    private LocalDateTime modifyDate;
}
