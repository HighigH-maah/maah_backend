package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

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
@Table(name = "hi_card_image")
public class HiCardImageVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int hi_card_image_code;
	private String hi_card_image_front_path;
	private String hi_card_image_rear_path;
	private String hi_card_image_name;
}
