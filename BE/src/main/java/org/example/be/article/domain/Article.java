package org.example.be.article.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.be.comment.domain.Comment;
import org.example.be.like.domain.ArticleLike;

@Entity
@Table(name="article")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @Column(name="author")
  private String author;

  @Column(name="title")
  private String title;

  @Column(name="content")
  private String content;

  @OneToMany(mappedBy = "article", cascade= CascadeType.ALL, orphanRemoval = true)
  private List<Comment> commentList;

  @Column(name="like_count")
  private Integer likeCount;

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval=true)
  private List<ArticleLike> likeList;

}
