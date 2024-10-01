package com.rahul.kafka.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CustomerDto {

	


	private String name;
	private String email;
	private String contactNum;
	
	@Override
	public String toString() {
		return "Consumer [ name=" + name + ", email=" + email + ", contactNum=" + contactNum + "]";
	}
}
