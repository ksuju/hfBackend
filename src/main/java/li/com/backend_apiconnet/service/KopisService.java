package li.com.backend_apiconnet.service;

import li.com.backend_apiconnet.entity.KopisFesEntity;
import li.com.backend_apiconnet.repository.KopisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KopisService {

    private final KopisRepository kopisRepository;

    @Transactional
    public void save(List<KopisFesEntity> kopisFesEntity) {

        kopisRepository.saveAll(kopisFesEntity);

    }
}
