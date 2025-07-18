package com.ex.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import com.ex.data.SportsCateDTO;
import com.ex.data.SportsDTO;
import com.ex.data.SportsReaction;
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
    	return sportsMapper.sportsContent(boardNum);
    }
    // 스포츠기사 조회수 +1
    public void sportsReadCount(int boardNum) {
    	sportsMapper.sportsReadCount(boardNum);
    }

    // 스포츠기사 좋아요
    public void reactionInsert(int num,String id,String type) {
    		sportsMapper.reactionInsert(num,id,type);
    	
    }
    // 스포츠기사 좋아요 취소
    public void removeReaction(int num,String id,String type) {
    	sportsMapper.removeReaction(num,id,type);
    }
    // 스포츠기사 좋아요 개수
    public int reactionCount(String type,int num) {
    	return sportsMapper.reactionCount(type, num);
    }
    
}
