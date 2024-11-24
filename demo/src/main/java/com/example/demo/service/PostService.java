package com.example.demo.service;

import com.example.demo.model.PostEntity;
import com.example.demo.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private PostRepository postRepository;

    public void handleUpvote(int postId) {
        PostEntity postEntity = postRepository.getReferenceById(postId);

        if (postEntity.isDownvoted()) {
            postEntity.setDownvoted(false);
            postEntity.setUpvoted(true);
            postEntity.setDownVote(postEntity.getDownVote() - 1);
            postEntity.setUpvote(postEntity.getUpvote() + 1);
        } else if (postEntity.isUpvoted()) {
            postEntity.setUpvoted(false);
            postEntity.setUpvote(postEntity.getUpvote() - 1);
        } else {
            postEntity.setUpvoted(true);
            postEntity.setUpvote(postEntity.getUpvote() + 1);
        }
        postRepository.save(postEntity);
    }

    public void handleDownVote(int postId) {
        PostEntity postEntity = postRepository.getReferenceById(postId);

        if (postEntity.isUpvoted()) {
            postEntity.setUpvoted(false);
            postEntity.setUpvote(postEntity.getUpvote() - 1);
            postEntity.setDownvoted(true);
            postEntity.setDownVote(postEntity.getDownVote() + 1);
        } else if (postEntity.isDownvoted()) {
            postEntity.setDownvoted(false);
            postEntity.setDownVote(postEntity.getDownVote() - 1);
        } else {
            postEntity.setDownvoted(true);
            postEntity.setDownVote(postEntity.getDownVote() + 1);
        }
        postRepository.save(postEntity);
    }

}
