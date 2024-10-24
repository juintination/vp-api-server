package com.example.vpapi.repository;

import com.example.vpapi.domain.Member;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m " +
            " FROM Member m LEFT JOIN FETCH m.profileImage p " +
            " WHERE m.email = :email")
    Member findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    @Query("SELECT m, p " +
            " FROM Member m LEFT JOIN m.profileImage p " +
            " WHERE m.mno = :mno" +
            " GROUP BY m, p")
    Object getMemberByMno(@Param("mno") Long mno);

}
