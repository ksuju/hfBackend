package com.ll.hfback.domain.group.chatRoom.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = -1310074764L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final com.ll.hfback.global.jpa.QBaseEntity _super = new com.ll.hfback.global.jpa.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath festivalId = createString("festivalId");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<java.util.List<String>, SimplePath<java.util.List<String>>> joinMemberIdNickNameList = this.<java.util.List<String>, SimplePath<java.util.List<String>>>createList("joinMemberIdNickNameList", java.util.List.class, SimplePath.class, PathInits.DIRECT2);

    public final com.ll.hfback.domain.member.member.entity.QMember member;

    public final StringPath roomContent = createString("roomContent");

    public final NumberPath<Long> roomMemberLimit = createNumber("roomMemberLimit", Long.class);

    public final NumberPath<Long> roomState = createNumber("roomState", Long.class);

    public final StringPath roomTitle = createString("roomTitle");

    public final ListPath<java.util.List<String>, SimplePath<java.util.List<String>>> waitingMemberIdNickNameList = this.<java.util.List<String>, SimplePath<java.util.List<String>>>createList("waitingMemberIdNickNameList", java.util.List.class, SimplePath.class, PathInits.DIRECT2);

    public QChatRoom(String variable) {
        this(ChatRoom.class, forVariable(variable), INITS);
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoom(PathMetadata metadata, PathInits inits) {
        this(ChatRoom.class, metadata, inits);
    }

    public QChatRoom(Class<? extends ChatRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ll.hfback.domain.member.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

