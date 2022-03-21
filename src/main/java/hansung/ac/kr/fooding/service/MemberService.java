package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Role;
import hansung.ac.kr.fooding.dto.JoinReqDTO;
import hansung.ac.kr.fooding.exception.X_IdAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NickNameAlreadyExistsException;
import hansung.ac.kr.fooding.repository.MemberRepository;
import hansung.ac.kr.fooding.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    // 회원 가입
    @Transactional
    public void join(JoinReqDTO req) throws
            X_IdAlreadyExistsException,
            X_NickNameAlreadyExistsException {
        validateSignUpInfo(req);
        for (Role role : req.getRole()) {
            roleRepository.save(role);
        }
        Member member = new Member(req);
        member.setRoles(req.getRole());
        memberRepository.save(member);

        /*String encryptedPassword = passwordEncoder.encode(user.getUserPassword());
        member.setUserPassword(encryptedPassword);
        member.setRoles(req.getRole());
        userRepository.save(member);*/
    }

    public void validateSignUpInfo(JoinReqDTO request)
            throws X_NickNameAlreadyExistsException,
            X_IdAlreadyExistsException {
        if(memberRepository.existsByNickName(request.getNickName()))
            throw new X_NickNameAlreadyExistsException(request.getNickName());
        if(memberRepository.existsByIdentifier(request.getId()))
            throw new X_IdAlreadyExistsException(request.getId());
    }

}
