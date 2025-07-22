package com.ex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import com.ex.data.ReactionCountDTO;
import com.ex.data.SportsCateDTO;
import com.ex.data.SportsDTO;
import com.ex.repository.SportsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SportsService {
	private final SportsMapper sportsMapper;
	
	// 첫번째 이미지 추출용
	public String firstImg(String img) {
		if(img == null) {
			return null;
		}
		
		Pattern pattern = Pattern.compile("<img[^>]+src=['\\\"]([^'\\\"]+)['\\\"]");
		Matcher matcher = pattern.matcher(img);
		
		if(matcher.find()) {
			return matcher.group(1);	// 첫 번째 이미지
		}
		return null;
	}
	// 스포츠기사 작성
	public int boardWrite(SportsDTO dto) {
		String thumbnail = firstImg(dto.getContent());
		dto.setThumbnail(thumbnail);
		return sportsMapper.boardWrite(dto);
	}
	
	// 스포츠기사 카테고리
	public List<SportsCateDTO> cate() {
		return sportsMapper.cate();
	}
	// 스포츠기사 카테고리 이름
	public String catename(int id) {
		return sportsMapper.catename(id);
	}
	
	// 스포츠기사 목록
	public List<SportsDTO> sportsList(){
		return sportsMapper.sportsList();
	}
	
	// 카테고리별 스포츠 기사 목록
	public List<SportsDTO> sportsCateList(int cate,int offset,int pageSize){
        List<SportsDTO> list = sportsMapper.sportsCateList(cate,offset,pageSize); // DB에서 가져오기

        for (SportsDTO dto : list) {
            dto.setCleanContent(cleanText(dto.getContent()));
        }
		return list;
	}
    // 텍스트만 출력용 (글내용)
    public String cleanText(String html) {
        if (html == null) return "";
        return Jsoup.parse(html).text(); // 태그 제거 + HTML 엔티티 자동 디코딩
    }

    // 스포츠기사 내용 출력
    public SportsDTO sportsContent(int boardNum) {
    	sportsMapper.sportsReadCountUp(boardNum);
    	return sportsMapper.sportsContent(boardNum);
    }


    // 스포츠기사 좋아요
    public void reactionInsert(int num,String id,String type) {
    	// 먼저 기존 반응 삭제
    	String idCheck = sportsMapper.idCheck(num, id);
        if(idCheck != null) {
    		sportsMapper.deleteReaction(num, id);
        }
    		sportsMapper.reactionInsert(num,id,type);
    	
    }
    // 스포츠기사 좋아요 취소
    public void removeReaction(int num,String id,String type) {
    	sportsMapper.removeReaction(num,id,type);
    }
    // 스포츠기사 좋아요 개수
    public Map<String, Integer> reactionCount1(int num){
    	
        List<ReactionCountDTO> list = sportsMapper.reactionCount1(num);

        Map<String, Integer> result = new HashMap<>();
        for (ReactionCountDTO dto : list) {
            result.put(dto.getEmotionType(), dto.getCount());
        }
        return result;
    }
    
    // 유저가 좋아요 누른 타입
    public String userReaction(int num,String id) {
    	return sportsMapper.idCheck(num, id);
    }
    //id 중복 체크
    public String reactionType(int num,String id) {
    	return sportsMapper.idCheck(num, id);
    }
    // 게시글 좋아요 총 개수
    public int reactionAllCount(int num) {
    	return sportsMapper.reactionAllCount(num);
    }
    // 스포츠기사 조회순 10개
    public List<SportsDTO> sportsReadCount(){
    	return sportsMapper.sportsReadCount();
    }
    // 스포츠기사 반응순
    public List<SportsDTO> sportsReaction(){
    	return sportsMapper.sportsReaction();
    }
    // 기자가 쓴 스포츠기사 2개
    public List<SportsDTO> sportsReporter(String id){
    	return sportsMapper.sportsReporter(id);
    }
}


