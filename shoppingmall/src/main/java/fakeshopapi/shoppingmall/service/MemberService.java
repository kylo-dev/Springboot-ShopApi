package fakeshopapi.shoppingmall.service;

import fakeshopapi.shoppingmall.domain.Member;
import fakeshopapi.shoppingmall.domain.Role;
import fakeshopapi.shoppingmall.repository.MemberRepository;
import fakeshopapi.shoppingmall.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    // email-pwd로 로그인시 회원 여부 확인
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );
    }

    // 회원가입한 사용자에게 권한 부여하기
    @Transactional
    public Member addMember(Member member) {
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        member.addRole(userRole.get());
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    public Optional<Member> getMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> getMember(String email) {
        return memberRepository.findByEmail(email);
    }
}
