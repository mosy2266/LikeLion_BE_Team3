package yun.likelion.be_study.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComments is a Querydsl query type for Comments
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComments extends EntityPathBase<Comments> {

    private static final long serialVersionUID = 214837725L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComments comments = new QComments("comments");

    public final QBoards board;

    public final NumberPath<Long> commentId = createNumber("commentId", Long.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final QMembers member;

    public final StringPath nickname = createString("nickname");

    public QComments(String variable) {
        this(Comments.class, forVariable(variable), INITS);
    }

    public QComments(Path<? extends Comments> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComments(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComments(PathMetadata metadata, PathInits inits) {
        this(Comments.class, metadata, inits);
    }

    public QComments(Class<? extends Comments> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoards(forProperty("board"), inits.get("board")) : null;
        this.member = inits.isInitialized("member") ? new QMembers(forProperty("member")) : null;
    }

}

