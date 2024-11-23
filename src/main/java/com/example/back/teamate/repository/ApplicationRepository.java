package com.example.back.teamate.repository;

import com.example.back.teamate.entity.Application;
import com.example.back.teamate.entity.Role;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Page<Application> findByRoleIn(List<Role> roles, Pageable pageable);
}
