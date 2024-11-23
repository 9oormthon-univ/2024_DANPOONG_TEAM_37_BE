package com.example.back.teamate.repository;

import java.util.Optional;

import com.example.back.teamate.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
	Optional<Field> findByFieldId(int fieldId);
}
