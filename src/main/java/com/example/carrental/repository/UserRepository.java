package com.example.carrental.repository;

import com.example.carrental.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String name);

    Optional<User> findBySessionId(String sessionId);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.sessionId = :sessionId WHERE u.username = :username")
    int updateSessionId(@Param("username") String username, @Param("sessionId") String sessionId);
}

