package com.ll.hfback.domain.member.alert.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlert is a Querydsl query type for Alert
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlert extends EntityPathBase<Alert> {

    private static final long serialVersionUID = 1898350581L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlert alert = new QAlert("alert");

    public final com.ll.hfback.global.jpa.QBaseEntity _super = new com.ll.hfback.global.jpa.QBaseEntity(this);

    public final StringPath alertTypeCode = createString("alertTypeCode");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final EnumPath<Alert.AlertDomain> domain = createEnum("domain", Alert.AlertDomain.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isRead = createBoolean("isRead");

    public final com.ll.hfback.domain.member.member.entity.QMember member;

    public final StringPath navigationData = createString("navigationData");

    public QAlert(String variable) {
        this(Alert.class, forVariable(variable), INITS);
    }

    public QAlert(Path<? extends Alert> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlert(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlert(PathMetadata metadata, PathInits inits) {
        this(Alert.class, metadata, inits);
    }

    public QAlert(Class<? extends Alert> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ll.hfback.domain.member.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

