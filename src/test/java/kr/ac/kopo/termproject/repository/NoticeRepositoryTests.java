package kr.ac.kopo.termproject.repository;

import kr.ac.kopo.termproject.entity.Notice;
import kr.ac.kopo.termproject.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class NoticeRepositoryTests {
    @Autowired
    NoticeRepository noticeRepository;

    @Test
    public void insertNotice() {
        IntStream.rangeClosed(1, 10).forEach(i ->{
            Member member = Member.builder()
                    .email("user" + i + "@kopo.ac.kr")
                    .build();
            Notice notice = Notice.builder()
                    .title("Title" + i)
                    .content("Content" + i)
                    .writer(member)
                    .build();
            noticeRepository.save(notice);
        });
    }

    @Transactional
    @Test
    public void testRead(){
        Optional<Notice> result = noticeRepository.findById(5L);
        Notice notice = result.get();
        System.out.println(notice);
        System.out.println(notice.getWriter());
    }
    @Test
    public void testReadWithWriter(){
        Object result = noticeRepository.getNoticeWithWriter(5L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testRead2(){
        Object result = noticeRepository.getNoticeByBno(99L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }
}
