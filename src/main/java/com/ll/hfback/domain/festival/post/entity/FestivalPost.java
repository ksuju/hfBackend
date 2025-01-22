package com.ll.hfback.domain.festival.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.domain.festival.comment.entity.FestivalComment;
import com.ll.hfback.domain.user.entity.User;
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
public class FestivalPost {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long festival_id;

    @JsonIgnore
    @OneToMany(mappedBy = "festivalPost", cascade = ALL, orphanRemoval = true) // fetch = FetchType.LAZY
    @Builder.Default
    @ToString.Exclude
    private List<FestivalComment> comments = new ArrayList<>();

    public void addComment(User user, String content) {
        FestivalComment comment = FestivalComment.builder()
                .festivalpost(this)
                .user(user)
                .content(content)
                .build();

        comments.add(comment);
    }

    public void removeComment(FestivalComment comment) {
        comments.remove(comment);
    }

}
