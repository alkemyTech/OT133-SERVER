package com.alkemy.ong.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.alkemy.ong.entity.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setName("test");
        member.setImage("A test image");
    }

    @Test
    void whenMemberIsDeleted_isSoftDeleted() throws Exception {
        member = memberRepository.save(member);
        memberRepository.deleteSoftById(member.getId());
        member = memberRepository.findById(member.getId()).orElse(new Member());
        assertTrue(member.isSoftDelete());
    }

}
