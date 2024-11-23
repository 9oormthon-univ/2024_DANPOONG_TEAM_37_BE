package com.example.back.teamate.repository;

import com.example.back.teamate.entity.ApplicationTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationTopicRepository extends JpaRepository<ApplicationTopic, Long> {

}
