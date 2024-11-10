package com.example.demo.repository;

import com.example.demo.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    @Query(value = "SELECT u FROM MessageEntity u where u.sender.id = ?1 AND u.receiver.id = ?2 " +
            "OR u.sender.id = ?2 AND u.receiver.id = ?1 ORDER BY u.timeSend")
    public List<MessageEntity> findAllBySenderIdAndReceiverId(int senderId, int receiverId);
}
