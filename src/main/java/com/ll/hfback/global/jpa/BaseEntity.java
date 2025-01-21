package com.ll.hfback.global.jpa;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@MappedSuperclass // JPA Entity 클래스들이 이 클래스를 상속할 경우 필드들도 컬럼으로 인식
@NoArgsConstructor(access = PROTECTED) // 기본 생성자 생성, protected 접근 제어
@AllArgsConstructor(access = PROTECTED) // 모든 필드 값을 파라미터로 받는 생성자 생성
@Getter // 모든 필드의 getter 메서드 생성
@SuperBuilder // 상속된 클래스에서 빌더 패턴 사용 가능
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 기능 사용
@ToString // toString 메서드 자동 생성
@EqualsAndHashCode // equals와 hashCode 메서드 자동 생성
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @CreatedDate
    @Getter
    private LocalDateTime createDate;
}