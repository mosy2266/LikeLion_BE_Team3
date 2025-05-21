package yun.likelion.be_study.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
}
