package com.anand.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import com.anand.dto.PageabaleResponse;

public class Helper {
	
	@Autowired
	private ModelMapper mapper;
	
	//U id entity and V is dto
	public static <U,V> PageabaleResponse<V> getPageableResponse(Page<U> page,Class<V> type){
		
		List<U> content = page.getContent();
		
	    // now data convert into dto
		List<V> dtoList = content.stream().map(object -> new ModelMapper().map(object, type))
				.collect(Collectors.toList());
		
		PageabaleResponse<V> pageabaleResponse = new PageabaleResponse<>();
		pageabaleResponse.setContent(dtoList);
		pageabaleResponse.setPageNumber(page.getNumber()+1);
		pageabaleResponse.setPageSize(page.getSize());
		pageabaleResponse.setTotalElements(page.getTotalElements());
		pageabaleResponse.setTotalPage(page.getTotalPages());
		pageabaleResponse.setLastPage(page.isLast());
		return pageabaleResponse;
	}

}
