package yun.likelion.be_study.entity.comments;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentLikes is a Querydsl query type for CommentLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentLikes extends EntityPathBase<CommentLikes> {

    private static final long serialVersionUID = -628867762L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentLikes commentLikes = new QCommentLikes("commentLikes");

    public final QComments comment;

    public final NumberPath<Long> commentLikesId = createNumber("commentLikesId", Long.class);

    public final yun.likelion.be_study.entity.members.QMembers member;

    public QCommentLikes(String variable) {
        this(CommentLikes.class, forVariable(variable), INITS);
    }

    public QCommentLikes(Path<? extends CommentLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentLikes(PathMetadata metadata, PathInits inits) {
        this(CommentLikes.class, metadata, inits);
    }

    public QCommentLikes(Class<? extends CommentLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComments(forProperty("comment"), inits.get("comment")) : null;
        this.member = inits.isInitialized("member") ? new yun.likelion.be_study.entity.members.QMembers(forProperty("member")) : null;
    }

}

