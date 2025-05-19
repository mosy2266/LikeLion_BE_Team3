package org.example.be.like.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.be.article.domain.Article;

@Entity
@Table(name="article_like")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLike {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @ManyToOne(fetch= FetchType.LAZY)
  @JoinColumn(name="article_id")
  private Article article;

}
