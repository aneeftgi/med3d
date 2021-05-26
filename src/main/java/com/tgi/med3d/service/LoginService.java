package com.tgi.med3d.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.jasypt.digest.StringDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgi.med3d.constant.ErrorCode;
import com.tgi.med3d.constant.ErrorMessages;
import com.tgi.med3d.enums.UserStatus;
import com.tgi.med3d.exception.InvalidDataValidation;
import com.tgi.med3d.exception.InvalidUserValidation;
import com.tgi.med3d.model.RoleMaster;
import com.tgi.med3d.model.User;
import com.tgi.med3d.model.LoginRequestDto;
import com.tgi.med3d.model.LoginResponseDto;
import com.tgi.med3d.repository.RoleMasterRepository;
import com.tgi.med3d.repository.UserRepository;
import com.tgi.med3d.utility.DateFileConverter;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.Library;

import io.jsonwebtoken.JwtException;
import jdk.internal.org.jline.utils.Log;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class LoginService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;	
	
	@Autowired
	RoleMasterRepository roleMasterRepository;

	
	@Autowired
	RestTemplate restTemplate;
	
	
	 @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userRepository.findByUserName(username);
	    	if(user==null) {
				throw new RuntimeException("User not found: " + username);
			}
	        GrantedAuthority authority = new SimpleGrantedAuthority(roleMasterRepository.getById(user.getRoleId()).getRoleName());
	        
	        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), Arrays.asList(authority));
	    }
	
	@SuppressWarnings("unused")
	public GenericResponse login(LoginRequestDto userLoginRequestDto, HttpSession httpSession,
			HttpServletRequest httpServletRequest) throws JSONException, JsonMappingException, JsonProcessingException,InvalidUserValidation {
		
		LoginResponseDto userLoginResponseDto = new LoginResponseDto();
        User user = userRepository.findByUserName(userLoginRequestDto.getUserName());
        if(user!=null) {
        String userStaus = user.getStatus();
        if(!Library.isNullOrEmpty(userStaus) && userStaus.equals(UserStatus.Active.name()) ) {
        	
		log.info("========== INSIDE login METHOD Bearer ==========");

		String username = userLoginRequestDto.getUserName();
 
		String password = userLoginRequestDto.getPassword();

		log.info("username : " + username);
		HttpHeaders headers = new HttpHeaders();

		String access_token_url = "http://localhost:8080/oauth/token?";
		access_token_url += "grant_type=password";
		access_token_url += "&username=";
		access_token_url +=username;
		access_token_url +="&password=";
		access_token_url +=password;
		ResponseEntity<String> response = null;

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth("med3d", "secret");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		response = restTemplate.exchange(access_token_url, HttpMethod.POST, entity, String.class);
		System.out.println("Access Token Response ---------" + response.getBody());
		if(response!=null) {
			log.info(response.getBody());	

			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(response.getBody());
			String token = "Bearer "+node.path("access_token").asText();
			userLoginResponseDto.setUserId(user.getId());
			userLoginResponseDto.setAccessToken(token);
			userLoginResponseDto.setExpiresIn(node.path("expires_in").asText());
			userLoginResponseDto.setRefreshToken(node.path("refresh_token").asText());
			userLoginResponseDto.setUserName(username);
			userLoginResponseDto.setRoleName(roleMasterRepository.getById(user.getRoleId()).getRoleName());
					return Library.getSuccessfulResponse(userLoginResponseDto,
							ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.LOGIN_AUTHORIZED);		}
		else {
			return Library.getSuccessfulResponse(null, ErrorCode.UNAUTHORIZED.getErrorCode(),
					ErrorMessages.LOGIN_UNAUTHORIZED);
		}
        }
		else {
			throw new InvalidDataValidation("User is not active");
		}
		
        }
		else {
			throw new InvalidDataValidation("User is not available");
		}
	}


}
