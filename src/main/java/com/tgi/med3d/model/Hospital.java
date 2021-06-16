package com.tgi.med3d.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "hospital")
public class Hospital extends Trackable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8529358320553278895L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "hospital_name")
	private String hospitalName;	
		
	@Column(name = "status")
	private boolean hospitalStatus;
	
	@Column(name = "address_1")
	private String address1;	
	
	@Column(name = "address_2")
	private String address2;		
	
	@Column(name = "contact_number")
	private String contactNumber;	

	@Column(name = "hospital_logo")
	private String hospitalLogo;
	
	@Column(name = "hospital_desc")
	private String hospitalDescription;

}
