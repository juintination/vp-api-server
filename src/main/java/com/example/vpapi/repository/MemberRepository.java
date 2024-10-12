package com.example.vpapi.repository;

import com.example.vpapi.domain.Member;
import org.springframework.data.jpa.repository.*;
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

}
