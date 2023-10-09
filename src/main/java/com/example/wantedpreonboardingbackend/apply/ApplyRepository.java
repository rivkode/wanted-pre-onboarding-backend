package com.example.wantedpreonboardingbackend.apply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    @Query(value = "SELECT * FROM apply WHERE user_id = :userId and post_id = :postId", nativeQuery = true)
    Optional<Apply> findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
}
