package com.ex.data;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class SportsReaction {
	private int reaction_id;
	private int sports_id;
	private String user_id;
	private String emotion_type;
	private LocalDateTime reg;

	private int count;
}