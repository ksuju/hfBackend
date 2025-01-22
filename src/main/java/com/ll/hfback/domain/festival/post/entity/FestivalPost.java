package com.ll.hfback.domain.festival.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.domain.festival.comment.entity.FestivalComment;
import com.ll.hfback.domain.group.room.entity.Room;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.CascadeType.ALL;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class FestivalPost extends BaseEntity {
    private String festival_name;
    private LocalDateTime festival_start_date;
    private LocalDateTime festival_end_date;
    private String festival_area;
    private String festival_hall_name;
    private boolean festival_status;
    private String festival_url;
    private boolean festival_state;
    private String input_type;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "festivalpost", cascade = ALL, orphanRemoval = true) // fetch = FetchType.LAZY
    @Builder.Default
    @ToString.Exclude
    private List<FestivalComment> comments = new ArrayList<>();

    public void addComment(Member memberAuthor, String content) {
        FestivalComment comment = FestivalComment.builder()
                .festivalpost(this)
                .member(memberAuthor)
                .content(content)
                .build();

        comments.add(comment);
    }

    public void removeComment(FestivalComment comment) {
        comments.remove(comment);
    }


    @OneToMany
    private List<Room> roomList;

}
