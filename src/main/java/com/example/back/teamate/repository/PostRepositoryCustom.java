package com.example.back.teamate.repository;

import java.util.List;
import com.example.back.teamate.entity.Post;

public interface PostRepositoryCustom {
	List<Post> findFilteredPosts(List<Integer> positions, List<Integer> skills, int modeId, int fieldId);
}