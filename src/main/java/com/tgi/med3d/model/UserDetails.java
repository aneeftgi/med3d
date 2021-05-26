package com.tgi.med3d.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "user_details")

public class UserDetails  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5119850901256139850L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name = "salutation_id")
	private Long salutationId;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;
		
	@Column(name = "address_line_1")
	private String addressLine1;	
	
	@Column(name = "address_line_2")
	private String addressLine2;	
	
	@Column(name = "state_id")
	private Long stateId;
	
	@Column(name = "district_id")
	private Long districtId;	
	
	@Column(name = "taluk_id")
	private Long talukId;	
	
	@Column(name = "nationality")
	private Long nationality;	
			
}
