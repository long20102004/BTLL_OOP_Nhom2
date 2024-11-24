package com.example.demo.repository;

import com.example.demo.model.User;
import com.example.demo.model.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Integer> {
    Optional<UserFollow> findByFollowerAndFollowing(User follower, User following);
    List<UserFollow> findByFollower(User follower);
    List<UserFollow> findByFollowing(User following);
    void deleteByFollowerAndFollowing(User follower, User following);
}
