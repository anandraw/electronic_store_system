package com.anand.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageabaleResponse<T> {
	
	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPage;
	private boolean lastPage;

}
