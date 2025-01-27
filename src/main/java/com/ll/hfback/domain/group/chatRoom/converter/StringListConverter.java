package com.ll.hfback.domain.group.chatRoom.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    // DB에 저장하기
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return ""; // null 처리
        }
        return String.join(",", attribute);
    }

    // DB에서 가져오기
    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>(); // 빈 리스트 반환
        }
        return new ArrayList<>(Arrays.asList(dbData.split(","))); // 수정 가능한 ArrayList 반환
    }
}
