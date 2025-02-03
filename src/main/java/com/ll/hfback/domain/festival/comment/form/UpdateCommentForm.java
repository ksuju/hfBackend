package com.ll.hfback.domain.festival.comment.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentForm {
    @NotEmpty
    @Size(max = 500)
    private String content;
}
