package yun.likelion.be_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yun.likelion.be_study.entity.Members;

import java.util.Optional;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {

    Optional<Members> findByUsername(String username);
}
