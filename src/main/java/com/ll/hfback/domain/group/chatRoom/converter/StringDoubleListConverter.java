package com.ll.hfback.domain.group.chatRoom.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StringDoubleListConverter implements AttributeConverter<List<List<String>>, String> {
    // DB에 저장하기
    @Override
    public String convertToDatabaseColumn(List<List<String>> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return ""; // null 처리
        }
        // 각 리스트를 쉼표로 구분하고, 이를 하나의 문자열로 결합
        return attribute.stream()
                .map(subList -> String.join(",", subList))  // 각 서브 리스트를 쉼표로 결합
                .collect(Collectors.joining(";"));  // 서브 리스트들을 세미콜론으로 구분
    }

    // DB에서 가져오기
    @Override
    public List<List<String>> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>(); // 빈 리스트 반환
        }
        // 세미콜론으로 구분된 각 서브 리스트를 다시 리스트로 변환
        return Arrays.stream(dbData.split(";"))  // 세미콜론으로 분리
                .map(subData -> new ArrayList<>(Arrays.asList(subData.split(","))))  // 쉼표로 구분된 값을 다시 리스트로
                .collect(Collectors.toList());
    }
}
