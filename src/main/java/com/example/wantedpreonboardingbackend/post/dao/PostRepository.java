package com.example.wantedpreonboardingbackend.post.dao;

import com.example.wantedpreonboardingbackend.post.domain.Post;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    List<Post> findAll(Specification<Post> spec);

    @Query(value = "SELECT post_id FROM post WHERE company_id = :companyId", nativeQuery = true)
    List<Long> findPostIdsByCompanyId(@Param("companyId") Long companyId);
}
