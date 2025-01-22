package com.ll.hfback.domain.festival.controller;

import com.ll.hfback.domain.festival.entity.KopisFesEntity;
import com.ll.hfback.domain.festival.service.KopisService;
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


    @GetMapping("/slideData")
    public List<KopisFesEntity> getSlideDataList(){

        /*slide에서 처음 뿌려주는 값은 keyword가 포함되어선 안되고,
        * 건수는 시작일, 종료일이 오늘에 해당하는 대상을 추려내야 할것같다.
        * */
        List<KopisFesEntity> result =  kopisService.selectListForSlide();
        return result;

    }

    @GetMapping("")
    public List<KopisFesEntity> getSearchKeyWordList(@RequestParam(value = "keyword" ,required = false)String keyword){

        /*
            이건 festivalNameContaining이라, "공주"지역을 검색했는데, "팥쥐공주"처럼
            축제, 공연명의 결과값을 조회할 수 있다.
            근데 그렇다고 festivalHallNameContaining을 하자니,
            518기념관같은경우는 광주로 검색해도 표시되지 않는다.
        */
        List<KopisFesEntity> result =  kopisService.selectList(keyword);
        return result;
    }


}
