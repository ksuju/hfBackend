package li.com.backend_apiconnet.repository;

import li.com.backend_apiconnet.entity.KopisFesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KopisRepository extends JpaRepository<KopisFesEntity, Long> {

}
