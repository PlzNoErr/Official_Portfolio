package com.ssafy.marathon.db.repository;

import com.ssafy.marathon.db.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByOrderByRegistDateDesc(Pageable pageable);

    Page<Board> findAllByTitleContainingOrderByRegistDateDesc(String condition, Pageable pageable);

    Page<Board> findAllByContentContainingOrderByRegistDateDesc(String condition,
        Pageable pageable);

    Page<Board> findAllByContentContainingOrTitleContainingOrderByRegistDateDesc(
        String contentCondition, String titleCondition, Pageable pageable);
}
