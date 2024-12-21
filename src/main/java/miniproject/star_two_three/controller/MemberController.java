package miniproject.star_two_three.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import miniproject.star_two_three.dto.JoinRequestDTO;
import miniproject.star_two_three.dto.LoginRequestDTO;
import miniproject.star_two_three.dto.LoginResponseDTO;
import miniproject.star_two_three.jwt.JwtProvider;
import miniproject.star_two_three.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup/create")
    public LoginResponseDTO join(
            @RequestBody JoinRequestDTO requestDTO
            ) {
        return memberService.join(requestDTO);
    }

    @GetMapping("/signin")
    public LoginResponseDTO login(
            HttpServletRequest request
            , @RequestBody LoginRequestDTO requestDTO
    ) {
        return memberService.login(requestDTO);
    }

}
