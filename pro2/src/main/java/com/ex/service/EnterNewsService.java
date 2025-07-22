package com.ex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.data.EnterNewsDTO;
import com.ex.repository.EnterNewsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnterNewsService {
	private final EnterNewsMapper enterNewsMapper;

	public List<EnterNewsDTO> getTop10DailyNews() {
		List<EnterNewsDTO> list = enterNewsMapper.getTop10DailyNews();
		return list;
	}

	public List<EnterNewsDTO> getPagedEnterNews(int offset, int limit) {
		List<EnterNewsDTO> list = enterNewsMapper.getPagedEnterNews(offset, limit);
		return list;
	}

	public List<EnterNewsDTO> getMostReadNews(int limit){
		List<EnterNewsDTO> list = enterNewsMapper.getMostReadNews(limit);
		return list;
	}
	
	// HTML에서 <img src="..."> 중 첫 번째 이미지의 src 추출
	private String extractThumbnail(String content) {
		if (content == null)
			return null;
		int imgStart = content.indexOf("<img");
		if (imgStart == -1)
			return null;

		int srcStart = content.indexOf("src=\"", imgStart);
		if (srcStart == -1)
			return null;

		srcStart += 5; // src=" 다음 위치
		int srcEnd = content.indexOf("\"", srcStart);
		if (srcEnd == -1)
			return null;

		return content.substring(srcStart, srcEnd);
	}
	
	public int insertNews(EnterNewsDTO dto) {
	    String thumbnail = extractThumbnail(dto.getContent());
	    dto.setThumbnail(thumbnail != null ? thumbnail : "/upload/default.png");
	    return enterNewsMapper.insertNews(dto);
	}
	
	public void increaseReadCount(int num) {
		enterNewsMapper.increaseReadCount(num);
	}
	
	public EnterNewsDTO readEnterNews(int num) {
		return enterNewsMapper.readEnterNews(num);
	}
	
	public void insertReadLog(int newsId, String ip) {
	    enterNewsMapper.insertReadLog(newsId, ip);
	}
	
	public void deleteNews(int num){
	    enterNewsMapper.softDelete(num);
	}
	
	public List<EnterNewsDTO> getNewsByCategory(String category) {
		return enterNewsMapper.getNewsByCategory(category);
	}

	public List<EnterNewsDTO> getNewsByCategoryPaged(String category, int offset, int limit) {
	    return enterNewsMapper.getNewsByCategoryPaged(category, offset, limit);
	}



}
