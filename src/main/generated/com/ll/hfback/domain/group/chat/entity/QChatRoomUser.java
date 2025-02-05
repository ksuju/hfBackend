package com.ll.hfback.domain.group.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomUser is a Querydsl query type for ChatRoomUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomUser extends EntityPathBase<ChatRoomUser> {

    private static final long serialVersionUID = 1753022820L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomUser chatRoomUser = new QChatRoomUser("chatRoomUser");

    public final com.ll.hfback.global.jpa.QBaseEntity _super = new com.ll.hfback.global.jpa.QBaseEntity(this);

    public final com.ll.hfback.domain.group.chatRoom.entity.QChatRoom chatRoom;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> lastReadMessageId = createNumber("lastReadMessageId", Long.class);

    public final com.ll.hfback.domain.member.member.entity.QMember member;

    public final EnumPath<com.ll.hfback.domain.group.chat.enums.ChatRoomUserStatus> userLoginStatus = createEnum("userLoginStatus", com.ll.hfback.domain.group.chat.enums.ChatRoomUserStatus.class);

    public QChatRoomUser(String variable) {
        this(ChatRoomUser.class, forVariable(variable), INITS);
    }

    public QChatRoomUser(Path<? extends ChatRoomUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomUser(PathMetadata metadata, PathInits inits) {
        this(ChatRoomUser.class, metadata, inits);
    }

    public QChatRoomUser(Class<? extends ChatRoomUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new com.ll.hfback.domain.group.chatRoom.entity.QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.member = inits.isInitialized("member") ? new com.ll.hfback.domain.member.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

