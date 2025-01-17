package li.com.backend_apiconnet.entity;

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
    private String festivalId;

    private final String festivalName;
    private final String festivalStartDate;
    private final String festivalEndDate;
    private final String festivalArea;
    private final String festivalHallName;
    private final String festivalState;
    private final String festivalUrl;

    @CreatedDate
    @Getter
    private LocalDateTime createDate;

    @LastModifiedDate
    @Getter
    private LocalDateTime modifyDate;
}
