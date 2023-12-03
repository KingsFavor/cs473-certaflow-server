package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.UserMessageLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMessageLikeRepository extends JpaRepository<UserMessageLike, String> {

    Optional<UserMessageLike> findById(String mappingId);
    List<UserMessageLike> findByUserMessageLikeUserId(String userId);

}
