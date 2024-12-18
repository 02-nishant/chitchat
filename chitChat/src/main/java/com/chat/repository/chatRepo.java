package com.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chat.entity.Chat;
import com.chat.entity.User;

public interface chatRepo extends JpaRepository<Chat, Integer> {
	
	@Query("select c from Chat c join c.users u where u.id =:userId")
	public List<Chat> findChatByUserId(@Param("userId") Integer userId);
	
	@Query("select c from Chat c where c.isGroup = false and :user member of c.users and :requser member of c.users")
	public Chat findSingleChat(@Param("user") User user, @Param("requser") User reqUser);

}
