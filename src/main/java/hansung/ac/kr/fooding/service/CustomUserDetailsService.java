package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired private MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByIdentifier(userId);
        org.springframework.security.core.userdetails.User result = createUser(member.getIdentifier(), member);

        /*System.out.println("!!!!!!!!!!!!!!!!!!!!I'm here bro!!!!!!!!!!!!!!!");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(user.getRole())));

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getUserPassword(), authorities);*/
        return result;
    }

    private org.springframework.security.core.userdetails.User createUser(String userId, Member member) {
        List<GrantedAuthority> grantedAuthorities = member.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(member.getIdentifier(),
                member.getPassword(), grantedAuthorities);
    }
}
