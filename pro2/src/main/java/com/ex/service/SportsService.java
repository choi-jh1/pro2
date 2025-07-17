package com.ex.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.ex.data.SportsCateDTO;
import com.ex.data.SportsDTO;
import com.ex.repository.SportsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SportsService {
	private final SportsMapper sportsMapper;
	
	// 첫번째 이미지 추출
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
	
	
	// 스포츠기사 목록
	public List<SportsDTO> sportsList(){
		return sportsMapper.sportsList();
	}
	
	// 카테고리별 스포츠 기사 목록
	public List<SportsDTO> sportsCateList(int cate,int offset,int pageSize){
        List<SportsDTO> list = sportsMapper.sportsCateList(cate,offset,pageSize); // DB에서 가져오기

        for (SportsDTO dto : list) {
            dto.setCleanContent(removeImages(dto.getContent()));
        }
		return list;
	}
	
	// 이미지 제외
    public String removeImages(String html) {
        if (html == null) return "";
        return html.replaceAll("<[^>]*>", "");
    }
	
}
