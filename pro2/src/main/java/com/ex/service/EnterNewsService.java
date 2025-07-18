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
		for (EnterNewsDTO dto : list) {
			dto.setThumbnail(extractThumbnail(dto.getContent()));
		}
		return list;
	}

	public List<EnterNewsDTO> getPagedEnterNews(int offset, int limit) {
		List<EnterNewsDTO> list = enterNewsMapper.getPagedEnterNews(offset, limit);
		for (EnterNewsDTO dto : list) {
			dto.setThumbnail(extractThumbnail(dto.getContent()));
		}
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
		dto.setThumbnail(extractThumbnail(dto.getContent()));
		int result=enterNewsMapper.insertNews(dto);
		return result;
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

	public int updateNews(EnterNewsDTO dto) {
		dto.setThumbnail(extractThumbnail(dto.getContent()));
		int result=enterNewsMapper.updateEnterNews(dto);
		return result;
	}
	
	public List<EnterNewsDTO> getNewsByCategory(String category) {
		return enterNewsMapper.getNewsByCategory(category);
	}

}
