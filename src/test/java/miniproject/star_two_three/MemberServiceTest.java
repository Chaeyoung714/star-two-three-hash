package miniproject.star_two_three;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
public class MemberServiceTest {

//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @BeforeAll
//    static void setUp() {
//        Dotenv dotenv = Dotenv.configure()
//                .directory("./")
//                .load();
//        System.setProperty("RDS_URL", dotenv.get("RDS_URL"));
//        System.setProperty("RDS_USERNAME", dotenv.get("RDS_USERNAME"));
//        System.setProperty("RDS_PASSWORD", dotenv.get("RDS_PASSWORD"));
//        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
//    }
//
//    @Test
//    void 회원가입_테스트() {
//        JoinRequestDTO requestDTO = new JoinRequestDTO("test", "테스트", "1541");
//        LoginResponseDTO responseDTO = memberService.join(requestDTO);
//
//        Member member = memberRepository.findByLoginId("test");
//
//        assertThat(responseDTO.getMessage()).isEqualTo(ResponseStatus.SUCCESS.getMessage());
//        assertThat(member.getId()).isEqualTo(1);
//        assertThat(member.getNickname()).isEqualTo("테스트");
//        assertThat(member.getPassword()).isEqualTo("1541");
//    }
}
