package com.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chat.entity.Message;

public interface messageRepo extends JpaRepository<Message, Integer> {
	
	@Query("select m from Message m join m.chat c where c.id = :chatId")
	public List<Message> findByChatId(@Param("chatId") Integer chatId);
	
	@Query("SELECT m FROM Message m WHERE LOWER(m.content) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Message> searchMessagesByContent(@Param("query") String query);

}
