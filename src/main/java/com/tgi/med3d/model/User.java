package com.tgi.med3d.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
@Table(name = "user")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8456161920720838558L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_name")
	private String userName;          //email Id
	
	@Column(name = "phone_number")
	private long phoneNumber;

	@JsonIgnore
	@Column(name = "password")
	private String password;		
	
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "status")
	private String status;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_details_id")
	private UserDetails userDetails;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="hospital_details_id")
	private HospitalDetails hospitalDetails;
	
}
