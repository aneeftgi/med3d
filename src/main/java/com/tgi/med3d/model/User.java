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

import org.hibernate.annotations.Type;
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
public class User extends Trackable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8456161920720838558L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_name")
	private String userName;          
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "gender")
	private String gender;

	@JsonIgnore
	@Column(name = "password")
	private String password;		
		
	@Column(name = "status")
	private boolean status;
	
	@Column(name = "address_1")
	private String address1;	
	
	@Column(name = "address_2")
	private String address2;	
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="role_id")
	private Role role;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="hospital_id")
	private Hospital hospital;
	
}
