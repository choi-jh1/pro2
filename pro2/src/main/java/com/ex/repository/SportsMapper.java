package com.ex.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.data.ReactionCountDTO;
import com.ex.data.SportsCateDTO;
import com.ex.data.SportsDTO;

@Mapper
@Repository
public interface SportsMapper {
	// 스포츠기사 등록
	public int boardWrite(SportsDTO dto);
	// 스포츠기사 카테고리 출력
	public List<SportsCateDTO> cate();
	// 스포츠기사 카테고리 이름 출력
	public String catename(int id);
	// 스포츠기사 목록
	public List<SportsDTO> sportsList();
	// 카테고리별 스포츠기사 목록
	public List<SportsDTO> sportsCateList(@Param("cateId") int cateId,@Param("offset") int offset,@Param("pageSize") int size);
	// 내용 출력 이미지 제외
	public List<String> content();
	// 스포츠기사 내용 출력
	public SportsDTO sportsContent(int boardNum);
	// 스포츠기사 조회수 +1
	public void sportsReadCountUp(int boardNum);
	// 스포츠기사 좋아요넣기
	public void reactionInsert(@Param("sports_id") int num,@Param("user_id") String id,@Param("emotion_type") String type);
	// 좋아요 아이디 체크( 유저가 좋아요 누른 타입 )
	public String idCheck(@Param("sports_id") int num,@Param("user_id") String id);
	// 좋아요 취소
	public void removeReaction(@Param("sports_id") int num,@Param("user_id") String id,@Param("emotion_type") String type);
	public void deleteReaction(@Param("sports_id") int num,@Param("user_id") String id);
	
	// 좋아요 개수
	List<ReactionCountDTO> reactionCount1(int num);

	
	// 유저가 좋아요 누른 타입
	public String userReaction(@Param("sports_id") int num,@Param("user_id") String id);
	// 게시글 좋아요 총 개수
	public int reactionAllCount(int num);
	public List<Map<String,Object>> reactionCount(int num);
	// 스포츠기사 조회순
	public List<SportsDTO> sportsReadCount();
	// 스포츠기사 반응순
	public List<SportsDTO> sportsReaction();
	// 기자가 쓴 스포츠기사
	public List<SportsDTO> sportsReporter(String id);
}

