package com.example.back.teamate.repository;

import com.example.back.teamate.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT a FROM Application a JOIN a.role r WHERE r.user = :userId")
    Page<Application> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
