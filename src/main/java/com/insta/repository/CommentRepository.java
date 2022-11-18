package com.insta.repository;

import com.insta.domain.Article;
import com.insta.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "where c.article = :article and c.parent is null and c.id < :id " +
            "order by c.id desc")
    Slice<Comment> findAllOrderByIdDesc(@Param("id") Long id, @Param("article") Article article, Pageable pageable);

    @Query("select c from Comment c " +
            "where c.parent = :comment and c.id < :replyId " +
            "order by c.id desc")
    Slice<Comment> findAllRepliesByParent(@Param("comment") Comment comment, @Param("replyId") Long replyId, Pageable ofSize);
}
