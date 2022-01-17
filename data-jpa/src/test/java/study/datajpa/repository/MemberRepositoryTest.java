package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
//@Rollback(true) // 실제 DB 삽입
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    void saveTest(){
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(saveMember.getId()).get();
        assertThat(findMember.getId()).isEqualTo(saveMember.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // transaction 이 같으면 엔티티는 동일성 보장
        assertThat(findMember).isEqualTo(member);
    }

}
