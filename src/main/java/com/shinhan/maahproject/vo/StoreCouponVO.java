package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode //모든 칼럼을 비교하여 내용 같아야 함
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "store_and_coupon")
public class StoreCouponVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String store_code;
	@NonNull
	@Column(nullable = false)
	private String store_name;
	private String store_category;
	private String store_address;
	private Double store_latitude;
	private Double store_longitude;
	private String store_coupon_image_path;
	private String store_image_path;
	private String store_stamp_image_path;
	private String store_coupon_policy;
	private String store_operation_time;
}
