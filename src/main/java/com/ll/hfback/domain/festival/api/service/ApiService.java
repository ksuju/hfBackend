package com.ll.hfback.domain.festival.api.service;

import com.ll.hfback.domain.festival.post.entity.Post;

import java.util.List;

public interface ApiService {
    // Apis의 데이터를 Post에 저장
    void saveForApis(String jsonResponse);

    // Kopis의 데이터를 Post에 저장
    void saveForKopis(List<Post> posts);

    // KOPIS에서 받아온 xml데이터를 Post객체로 변환
    List<Post> parseXmlToEntity(String xml) throws Exception;
}
