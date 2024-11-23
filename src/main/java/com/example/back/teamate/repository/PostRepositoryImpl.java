package com.example.back.teamate.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.back.teamate.entity.Post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
	private final EntityManager entityManager;

	@Override
	public List<Post> findFilteredPosts(List<Integer> positions, List<Integer> skills, int modeId, int fieldId) {
		try {
			StringBuilder jpqlBuilder = new StringBuilder("SELECT DISTINCT p FROM Post p ");
			jpqlBuilder.append("LEFT JOIN p.postPositions pp ");
			jpqlBuilder.append("LEFT JOIN pp.position pos ");
			jpqlBuilder.append("LEFT JOIN pp.postSkills ps ");
			jpqlBuilder.append("LEFT JOIN ps.skill skill ");

			boolean hasWhereClause = false;

			if (positions != null && !positions.isEmpty()) {
				jpqlBuilder.append("WHERE pos.positionId IN :positions ");
				hasWhereClause = true;
			}

			if (skills != null && !skills.isEmpty()) {
				if (hasWhereClause) {
					jpqlBuilder.append("OR ");
				} else {
					jpqlBuilder.append("WHERE ");
				}
				jpqlBuilder.append("skill.skillId IN :skills ");
				hasWhereClause = true;
			}

			if (modeId != 0) {
				if (hasWhereClause) {
					jpqlBuilder.append("OR ");
				} else {
					jpqlBuilder.append("WHERE ");
				}
				jpqlBuilder.append("p.modeId = :modeId ");
				hasWhereClause = true;
			}

			if (fieldId != 0) {
				if (hasWhereClause) {
					jpqlBuilder.append("OR ");
				} else {
					jpqlBuilder.append("WHERE ");
				}
				jpqlBuilder.append("p.fieldId = :fieldId ");
			}

			String jpql = jpqlBuilder.toString();
			TypedQuery<Post> query = entityManager.createQuery(jpql, Post.class);

			if (positions != null && !positions.isEmpty()) {
				query.setParameter("positions", positions);
			}
			if (skills != null && !skills.isEmpty()) {
				query.setParameter("skills", skills);
			}
			if (modeId != 0) {
				query.setParameter("modeId", modeId);
			}
			if (fieldId != 0) {
				query.setParameter("fieldId", fieldId);
			}

			return query.getResultList();
		} catch (Exception e) {
			throw new RuntimeException("Database query error occurred while filtering posts.", e);
		}
	}




}

