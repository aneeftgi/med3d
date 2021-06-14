package com.tgi.med3d.model;

import java.util.List;


import lombok.Data;

@Data
public class PaginationResponseDTO {
	Long TotalElements;

	Integer NumberOfElements;

	Integer TotalPages;

	List<Hospital> Contents;

}
