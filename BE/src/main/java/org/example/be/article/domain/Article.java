package org.example.be.article.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.be.comment.domain.Comment;
import org.example.be.user.domain.UserEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name="article")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

//soft delete
@SQLDelete(sql="UPDATE article SET is_deleted=true WHERE id=?")
@Where(clause = "is_deleted=false")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id")
  private UserEntity user;

  @Column(name="title")
  private String title;

  @Column(name="content")
  private String content;

  @OneToMany(mappedBy = "article", cascade= CascadeType.ALL, orphanRemoval = true)
  private List<Comment> commentList;

  @Column(name="like_count")
  private Integer likeCount;

  @Column(name="view_count")
  private int viewCount;

  @Column(name="is_deleted")
  private Boolean isDeleted;

  @Column(name="author")
  private String author;

}
