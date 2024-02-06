package com.shinhan.maahproject.dto;
import java.sql.Timestamp;
import java.util.List;

import com.shinhan.maahproject.vo.ByRelationBenefitVO;

import lombok.Data;

@Data
public class ByCardDTO {
	private int by_code;
	private String by_name;
	private Timestamp by_regdate;
	private Double by_overdue_rate;
	private int by_year_price;
	private int by_min_limit;
	private String by_category_list;
	private int by_status;
	private String by_image_path;
	private Boolean by_is_transport;
	
}
