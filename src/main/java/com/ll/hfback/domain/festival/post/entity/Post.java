package com.ll.hfback.domain.festival.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.domain.group.room.entity.Room;
import com.ll.hfback.domain.festival.comment.entity.Comment;
import com.ll.hfback.domain.member.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private String festivalId;
    private String festivalName;
    private String festivalStartDate;
    private String festivalEndDate;
    private String festivalArea;
    private String festivalHallName;
    private String festivalState;
    private String festivalUrl;
    private String inputType;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "festivalpost", cascade = ALL, orphanRemoval = true) // fetch = FetchType.LAZY
    @Builder.Default
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Member memberAuthor, String content) {
        Comment comment = Comment.builder()
                .festivalpost(this)
                .member(memberAuthor)
                .content(content)
                .build();

        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }


    @OneToMany
    private List<Room> roomList;

}
