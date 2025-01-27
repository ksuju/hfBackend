package com.ll.hfback.domain.festival.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 1959092913L;

    public static final QPost post = new QPost("post");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath festivalArea = createString("festivalArea");

    public final StringPath festivalEndDate = createString("festivalEndDate");

    public final StringPath festivalHallName = createString("festivalHallName");

    public final StringPath festivalId = createString("festivalId");

    public final StringPath festivalName = createString("festivalName");

    public final StringPath festivalStartDate = createString("festivalStartDate");

    public final StringPath festivalState = createString("festivalState");

    public final StringPath festivalUrl = createString("festivalUrl");

    public final StringPath genrenm = createString("genrenm");

    public final StringPath inputType = createString("inputType");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public QPost(String variable) {
        super(Post.class, forVariable(variable));
    }

    public QPost(Path<? extends Post> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPost(PathMetadata metadata) {
        super(Post.class, metadata);
    }

}

