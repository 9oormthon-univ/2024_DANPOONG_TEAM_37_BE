package com.example.back.teamate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.back.teamate.entity.Field;
import com.example.back.teamate.enums.FieldName;
import com.example.back.teamate.repository.FieldRepository;

@SpringBootTest
@Transactional
public class FieldRepositoryTest {

	@Autowired
	private FieldRepository fieldRepository;

	@Test
	@Rollback(value = false)
	public void createFieldTest() {
		// Enum 값을 기반으로 Field 엔티티 생성 및 저장
		for (FieldName fieldName : FieldName.values()) {
			Field field = new Field();
			field.setFieldId(fieldName.getFieldId()); // Enum에서 ID 값 가져오기
			field.setFieldName(fieldName); // Enum 값 설정

			fieldRepository.save(field);
		}
	}
}

