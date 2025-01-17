package li.com.backend_apiconnet.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class KopisFesEntity extends BaseEntity {

    @Id
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
}
