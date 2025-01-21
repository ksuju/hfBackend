package com.ll.hfback.domain.festival.comment.entity;

import com.ll.hfback.domain.festival.post.entity.FestivalPost;
import com.ll.hfback.domain.user.entity.User;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class FestivalComment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private FestivalPost festivalpost;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String content;
    private LocalDateTime create_date;
    private Boolean comment_state;
}
