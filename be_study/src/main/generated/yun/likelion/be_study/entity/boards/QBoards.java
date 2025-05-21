package yun.likelion.be_study.entity.boards;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoards is a Querydsl query type for Boards
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoards extends EntityPathBase<Boards> {

    private static final long serialVersionUID = -1892622587L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoards boards = new QBoards("boards");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final ListPath<yun.likelion.be_study.entity.comments.Comments, yun.likelion.be_study.entity.comments.QComments> comments = this.<yun.likelion.be_study.entity.comments.Comments, yun.likelion.be_study.entity.comments.QComments>createList("comments", yun.likelion.be_study.entity.comments.Comments.class, yun.likelion.be_study.entity.comments.QComments.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final yun.likelion.be_study.entity.members.QMembers member;

    public final StringPath nickname = createString("nickname");

    public final StringPath title = createString("title");

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QBoards(String variable) {
        this(Boards.class, forVariable(variable), INITS);
    }

    public QBoards(Path<? extends Boards> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoards(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoards(PathMetadata metadata, PathInits inits) {
        this(Boards.class, metadata, inits);
    }

    public QBoards(Class<? extends Boards> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new yun.likelion.be_study.entity.members.QMembers(forProperty("member")) : null;
    }

}

