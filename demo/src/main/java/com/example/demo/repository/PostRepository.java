package com.example.demo.repository;

import com.example.demo.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer>{
    @Query("SELECT p FROM PostEntity p WHERE p.title LIKE %?1%")
    public List<PostEntity> findAllByQuery(String query);
}
