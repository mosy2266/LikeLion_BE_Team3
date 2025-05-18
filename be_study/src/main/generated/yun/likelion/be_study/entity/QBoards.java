package yun.likelion.be_study.entity;

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

    private static final long serialVersionUID = -1811996554L;

    public static final QBoards boards = new QBoards("boards");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final ListPath<Comments, QComments> comments = this.<Comments, QComments>createList("comments", Comments.class, QComments.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final StringPath name = createString("name");

    public final StringPath title = createString("title");

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QBoards(String variable) {
        super(Boards.class, forVariable(variable));
    }

    public QBoards(Path<? extends Boards> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoards(PathMetadata metadata) {
        super(Boards.class, metadata);
    }

}

