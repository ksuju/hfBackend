package li.com.backend_apiconnet.repository;

import li.com.backend_apiconnet.entity.KopisFesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KopisRepository extends JpaRepository<KopisFesEntity, Long> {
    //화면에서 입력한 Keyword 포함대상만 추출
    List<KopisFesEntity> findByFestivalNameContaining(String keyword);
}
