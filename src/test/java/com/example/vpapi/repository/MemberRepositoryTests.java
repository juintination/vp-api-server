package com.example.vpapi.repository;

import com.example.vpapi.domain.Member;
import com.example.vpapi.domain.MemberRole;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker();

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(memberRepository, "MemberRepository should not be null");
        log.info(memberRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsertMember() {
        String email = "sample@example.com";
        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(faker.internet().password()))
                .nickname(faker.name().name())
                .memberRole(MemberRole.USER)
                .build();
        if (!memberRepository.existsByEmail(email)) {
            memberRepository.save(member);
        }
    }

    @Test
    public void testRead() {
        Long mno = 1L;
        Optional<Member> member = memberRepository.findById(mno);

        if (member.isPresent()) {
            log.info(member);
        }
    }

    @Test
    public void testReadByEmail() {
        String email = "sample@example.com";
        Member member = memberRepository.findByEmail(email);

        log.info(member);
    }

    @Test
    public void testModify() {
        Long mno = 1L;
        Optional<Member> result = memberRepository.findById(mno);

        if (result.isPresent()) {
            Member member = result.get();

            String newEmail = "modified@example.com";
            member.changeEmail(newEmail);

            String newNickname = "ModifiedNickname";
            member.changeNickname(newNickname);

            MemberRole newRole = MemberRole.MANAGER;
            member.changeRole(newRole);

            memberRepository.save(member);

            Optional<Member> modifiedResult = memberRepository.findById(mno);
            Assertions.assertTrue(modifiedResult.isPresent());
            Assertions.assertEquals(newNickname, modifiedResult.get().getNickname());
        } else {
            Assertions.fail("Member not found");
        }

        Optional<Member> modifiedResult = memberRepository.findById(mno);
        modifiedResult.ifPresent(log::info);
    }

}
