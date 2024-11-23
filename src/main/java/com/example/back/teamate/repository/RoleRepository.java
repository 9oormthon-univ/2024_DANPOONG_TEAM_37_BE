package com.example.back.teamate.repository;

import com.example.back.teamate.enums.TeamRole;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back.teamate.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByPosts_PostId(Long postId);
	List<Role> findByUser_Id(Long userId);
	List<Role> findByUser_IdAndTeamRole(Long userId, TeamRole teamRole);
	@Query("SELECT r FROM Role r WHERE r.user.id = :userId AND r.teamRole = 'TEAM_LEADER'")
	Optional<Role> findTeamLeaderByUserId(@Param("userId") Long userId);
}
