package kr.ac.kopo.termproject.security;

import kr.ac.kopo.termproject.entity.Member;
import kr.ac.kopo.termproject.entity.MemberRole;
import kr.ac.kopo.termproject.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTests {
    @Autowired
    private MemberRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void insertDummies() {
        // user1 - user80: USER
        // user81 - user90: USER, MANAGER
        // user91 - user99: USER, MANAGER, ADMIN
        IntStream.rangeClosed(1, 99).forEach(i -> {
            Member clubMember = Member.builder()
                    .email("user"+i+"@kopo.ac.kr")
                    .name("사용자"+i)
                    .password(passwordEncoder.encode("1234"))
                    .fromSocial(false)
                    .build();
            clubMember.addMemberRole(MemberRole.USER);
            if(i > 80){
                clubMember.addMemberRole(MemberRole.MANAGER);
            }
            if(i > 90){
                clubMember.addMemberRole(MemberRole.ADMIN);
            }
            repository.save(clubMember);
        });
    }
    @Test
    public void testRead() {
        Optional<Member> result = repository.findByEmail("user99@kopo.ac.kr", false);
        Member clubMember = result.get();
        System.out.println("★★★" + clubMember);
    }
}
