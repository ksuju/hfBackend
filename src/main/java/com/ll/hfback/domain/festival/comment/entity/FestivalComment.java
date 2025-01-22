package com.ll.hfback.domain.festival.comment.entity;

import com.ll.hfback.domain.festival.post.entity.FestivalPost;
import com.ll.hfback.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class FestivalComment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long comment_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private FestivalPost festivalpost;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;

    private String content;
    private LocalDateTime create_date;
    private Boolean comment_state;
    private Long super_comment_id;
}
