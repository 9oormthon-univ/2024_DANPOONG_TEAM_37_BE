package com.example.back.teamate.repository;

import com.example.back.teamate.entity.Position;
import com.example.back.teamate.enums.PositionName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByPositionName(PositionName positionName);
}
