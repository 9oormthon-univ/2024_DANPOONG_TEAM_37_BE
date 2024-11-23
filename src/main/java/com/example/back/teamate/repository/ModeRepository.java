package com.example.back.teamate.repository;

import java.util.Optional;

import com.example.back.teamate.entity.Mode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeRepository extends JpaRepository<Mode, Long> {
	Optional<Mode> findByModeId(int modeId);
}

