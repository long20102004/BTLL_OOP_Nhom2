package com.example.demo.repository;

import com.example.demo.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    @Query(value = "SELECT u from CommentEntity u where u.postComment.id = ?1")
    public List<CommentEntity> getCommentEntitiesByPostId(int postId);
}
