package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.UserTipLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTipLikeRepository extends JpaRepository<UserTipLike, String> {

    List<UserTipLike> findByUserTipLikeUserId(String userId);
    List<UserTipLike> findByUserTipLikeTipId(String tipId);

}
