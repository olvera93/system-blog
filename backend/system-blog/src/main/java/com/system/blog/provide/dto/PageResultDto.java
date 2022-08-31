package com.system.blog.provide.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "PageResult", description = "Contains result of search in page")
public class PageResultDto<T> {

	@NotNull
	@Schema(name = "currentPage", description = "Current page result returned", example = "0")
	private Integer currentPage;
	
	@NotNull
	@Schema(name = "resultsInPage", description = "Total result returned in current page", example = "200")
	private Integer resultsInPage;
	
	@NotNull
	@Schema(name = "totalPages", description = "Total pages required to complete search", example = "6")
	private Integer totalPages;
	
	@NotNull
	@Schema(name = "totalResults", description = "Total results in complete search", example = "1134")
	private Long totalResults;
	
	@NotNull
	@Schema(name = "lastPage", description = "Show is the lastPage", example = "false")
	private Boolean lastPage;
	
	@Builder.Default
	@Schema(name = "results", description = "Objects in current page response", example = "[]")
	private List<T> results = new ArrayList<>();

}