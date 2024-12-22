package miniproject.star_two_three.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

//    private final MemberRepository memberRepository;
//    private final JwtProvider jwtProvider;
//
//    public LoginResponseDTO join(JoinRequestDTO request) {
//        //TODO password encode하기
//        Member member = new Member(request.getLoginId(), request.getNickname(), request.getPassword());
//        memberRepository.save(member);
//
//        LoginResponseDTO response = new LoginResponseDTO(
//                ResponseStatus.SUCCESS,
//                jwtProvider.createToken(member.getId(), TokenType.ACCESS),
//                jwtProvider.createToken(member.getId(), TokenType.REFRESH)
//        );
//        return response;
//    }
//
//    public LoginResponseDTO login(LoginRequestDTO request) {
//        try {
//            Member member = memberRepository.findByLoginId(request.getLoginId());
//            if (member.matchesPassword(request.getPassword())) {
//                return new LoginResponseDTO(
//                        ResponseStatus.SUCCESS,
//                        jwtProvider.createToken(member.getId(), TokenType.ACCESS),
//                        jwtProvider.createToken(member.getId(), TokenType.REFRESH)
//                );
//            }
//            throw new IllegalArgumentException();
//        } catch (NoResultException | IllegalArgumentException e) { //아이디 없을 때, 비번 틀렸을 때
//            return new LoginResponseDTO(
//                    ResponseStatus.FAIL,
//                    "",
//                    ""
//            );
//        }
//    }
}
