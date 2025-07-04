package com.springsecurity.SecurityApp.SecurityApp.repositories;

import com.springsecurity.SecurityApp.SecurityApp.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

}
