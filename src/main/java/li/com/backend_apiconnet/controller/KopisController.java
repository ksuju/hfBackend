package li.com.backend_apiconnet.controller;

import li.com.backend_apiconnet.entity.KopisFesEntity;
import li.com.backend_apiconnet.service.KopisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kopis")
public class KopisController {

    private final KopisService kopisService;

    /*
    * 처음부터 모든 데이터를 뿌려주는건 낭비이며, 화면에 전부 표시할수도 없다.
    * 그렇다면, 일부데이터만 화면에 표시후, 더보기 같은 버튼을 사용해야 하나?
    * 아니면 카카오 지도에 검색하지 않으면 어떤 결과도 표시되지 않게 해야하나?
    * */
    @GetMapping("")
    public List<KopisFesEntity> getSearchKeyWordList(@RequestParam(value = "keyword" ,required = false)String keyword){
        List<KopisFesEntity> result =  kopisService.selectList(keyword);
        return result;
    }

}
