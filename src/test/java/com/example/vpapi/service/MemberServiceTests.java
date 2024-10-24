package com.example.vpapi.service;

import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.vpapi.domain.MemberRole;
import com.example.vpapi.dto.MemberDTO;
import com.example.vpapi.util.CustomServiceException;

@SpringBootTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    private final Faker faker = new Faker();

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(memberService, "MemberService should not be null");
        log.info(memberService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {
        String email = "sample@example.com";

        MemberDTO memberDTO = MemberDTO.builder()
                .email(email)
                .password(faker.internet().password())
                .nickname("SampleUser")
                .role(MemberRole.USER)
                .build();

        Long mno = null;
        try {
            mno = memberService.register(memberDTO);
        } catch (CustomServiceException e) {
            if ("EMAIL_ALREADY_EXISTS".equals(e.getMessage())) {
                memberDTO.setEmail(faker.internet().emailAddress());
                memberDTO.setNickname(faker.name().name());
                mno = memberService.register(memberDTO);
            }
        }

        log.info("mno: " + mno);
        log.info(memberService.get(mno));
    }

    @Test
    public void testGet() {
        Long mno = 1L;
        MemberDTO memberDTO = memberService.get(mno);
        log.info(memberDTO);
    }

    @Test
    public void testModify() {
        Long mno = 1L;
        MemberDTO memberDTO = MemberDTO.builder()
                .mno(mno)
                .email("Modified@example.com")
                .password("NewPassword")
                .nickname("ModifiedUser")
                .role(MemberRole.MANAGER)
                .build();
        memberService.modify(memberDTO);

        MemberDTO result = memberService.get(mno);
        log.info(result);
    }

    @Test
    public void testRemove() {
        String email = "sample@example.com";
        Long mno = memberService.getMno(email);
        memberService.remove(mno);
        Assertions.assertThrows(CustomServiceException.class, () -> memberService.get(mno));
    }

}
