package miniproject.star_two_three;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import miniproject.star_two_three.domain.Member;
import miniproject.star_two_three.dto.JoinRequestDTO;
import miniproject.star_two_three.dto.LoginResponseDTO;
import miniproject.star_two_three.dto.ResponseStatus;
import miniproject.star_two_three.repository.MemberRepository;
import miniproject.star_two_three.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원가입_테스트() {
        JoinRequestDTO requestDTO = new JoinRequestDTO("test", "테스트", "1541");
        LoginResponseDTO responseDTO = memberService.join(requestDTO);

        Member member = memberRepository.findByLoginId("test");

        assertThat(responseDTO.getMessage()).isEqualTo(ResponseStatus.SUCCESS.getMessage());
        assertThat(member.getNickname()).isEqualTo("테스트");
        assertThat(member.getPassword()).isEqualTo("1541");
    }
}
