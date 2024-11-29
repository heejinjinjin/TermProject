package kr.ac.kopo.termproject.repository;

import kr.ac.kopo.termproject.entity.Borad;
import kr.ac.kopo.termproject.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Borad, Long>, SearchBoardRepository {
    @Query("select b, w from Borad b left join b.writer w where b.bno=:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select b, r from Borad b left join Reply r ON r.board = b where b.bno=:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value = "select b, w, count(r) from Borad b " +
            "left join b.writer w " +
            "left join Reply r on r.board = b " +
            "group by b, w",
            countQuery = "select count(b) from Borad b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query("select b, w, count(r) " +
            "from Borad b left join b.writer w " +
            "left outer join Reply r ON r.board=b " +
            "where b.bno=:bno group by b, w")
    Object getBoardByBno(@Param("bno")Long bno);
}
