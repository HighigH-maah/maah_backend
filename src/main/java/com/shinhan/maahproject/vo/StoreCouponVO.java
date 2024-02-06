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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int storeCode;
	@NonNull
	@Column(nullable = false)
	private String storeName;
	private String storeCategory;
	private String storeAddress;
	private Double storeLatitude;
	private Double storeLongitude;
	private String storeCouponImagePath;
	private String storeImagePath;
	private String storeStampImagePath;
	private String storeCouponPolicy;
	private String storeOperationTime;
}
