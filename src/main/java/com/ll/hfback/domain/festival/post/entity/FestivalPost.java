package com.ll.hfback.domain.festival.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.domain.festival.comment.entity.FestivalComment;
import com.ll.hfback.domain.user.entity.User;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "article", cascade = ALL, orphanRemoval = true) // fetch = FetchType.LAZY
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

    public void removeComment(FestivalComment articleComment) {
        comments.remove(articleComment);
    }

}
