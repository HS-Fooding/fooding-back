package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Role;
import hansung.ac.kr.fooding.dto.JoinReqDTO;
import hansung.ac.kr.fooding.exception.X_IdAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NickNameAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NotRegisteredRole;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원 가입
    @Transactional
    public void join(JoinReqDTO req) throws
            X_IdAlreadyExistsException,
            X_NickNameAlreadyExistsException, X_NotRegisteredRole {
        validateSignUpInfo(req);
        List<Role> roleList = new ArrayList<>();

        for (Role role : req.getRole()) {
            if (role.getRoleName().matches("ROLE_ADMIN")) {
                Role roleAdmin = roleRepository.findByRoleName("ROLE_ADMIN");
                roleList.add(roleAdmin);
            } else if (role.getRoleName().matches("ROLE_USER")) {
                Role roleUser = roleRepository.findByRoleName("ROLE_USER");
                roleList.add(roleUser);
            } else {
                // 오류
                throw new X_NotRegisteredRole();
            }
        }

        Account account = new Account();

        for (int i = 0; i < roleList.size(); i++) {
            if(roleList.get(i).getRoleName().matches("ROLE_ADMIN")) {
                account = new Admin(req);
                break;
            }
            if(i == roleList.size()-1)
                account = new Member(req);
        }

        System.out.println(account.getNickName());
        String encryptedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encryptedPassword);
        account.setRoles(roleList); // validation이 없음
        accountRepository.save(account);
    }

    public void validateSignUpInfo(JoinReqDTO request)
            throws X_NickNameAlreadyExistsException,
            X_IdAlreadyExistsException {
        if (accountRepository.existsByNickName(request.getNickName()))
            throw new X_NickNameAlreadyExistsException(request.getNickName());
        if (accountRepository.existsByIdentifier(request.getId()))
            throw new X_IdAlreadyExistsException(request.getId());
    }

}
