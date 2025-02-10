package com.ll.hfback.domain.member.member.repository;

import com.ll.hfback.domain.member.member.entity.Friend;
import com.ll.hfback.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    // 두 회원 간의 친구 관계 조회 (양방향)
    @Query("SELECT f FROM Friend f WHERE " +
        "(f.requester = :user1 AND f.receiver = :user2) OR " +
        "(f.requester = :user2 AND f.receiver = :user1)")
    Optional<Friend> findFriendshipBetween(
        @Param("user1") Member user1, @Param("user2") Member user2
    );
}
