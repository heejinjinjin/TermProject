package kr.ac.kopo.termproject.repository.search;

import kr.ac.kopo.termproject.entity.Borad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SearchBoardRepository {
    Borad search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
