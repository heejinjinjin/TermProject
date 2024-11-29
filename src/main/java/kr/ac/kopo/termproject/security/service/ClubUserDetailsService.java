package kr.ac.kopo.termproject.security.service;

import kr.ac.kopo.termproject.entity.Member;
import kr.ac.kopo.termproject.repository.MemberRepository;
import kr.ac.kopo.termproject.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
    private final MemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" ♣ ClubUserDetailsService loadUserByUsername:" + username);
        Optional<Member> result = clubMemberRepository.findByEmail(username, false);
        if(result.isEmpty()) {
            throw new UsernameNotFoundException("Check Email or social");
        }
        Member clubMember = result.get();
        log.info(clubMember);
        AuthMemberDTO clubAuthMemberDTO = new AuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
        );
        clubAuthMemberDTO.setName(clubMember.getName());
//        clubAuthMemberDTO.setFromSocial(clubMember.isFromSocial());
        return clubAuthMemberDTO;
    }
}
