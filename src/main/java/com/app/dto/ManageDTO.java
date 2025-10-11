package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ManageDTO {
	private UUID poultryId;
	private String poultryName;
	private String currentWeek;
	private String currentMonth;
	private String currentYear;

}
