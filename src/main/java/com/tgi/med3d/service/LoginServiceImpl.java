package com.tgi.med3d.service;


import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgi.med3d.constant.ErrorCode;
import com.tgi.med3d.constant.ErrorMessages;
import com.tgi.med3d.exception.InvalidDataValidation;
import com.tgi.med3d.exception.InvalidUserValidation;
import com.tgi.med3d.model.LoginRequestDto;
import com.tgi.med3d.model.LoginResponseDto;
import com.tgi.med3d.model.User;
import com.tgi.med3d.repository.RoleRepository;
import com.tgi.med3d.repository.UserRepository;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.Library;

@Service
public class LoginServiceImpl implements UserDetailsService, LoginService{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	UserRepository userRepository;	
	
	@Autowired
	RoleRepository roleMasterRepository;

	
	@Autowired
	RestTemplate restTemplate;
	
	 @Value("${server.port}")
	   private String portNo;
	
//	 @Value("${server.address}")
//	   private String ipAddress;
	 
	   @Value("${jwt.clientId}")
	   private String clientId;

	   @Value("${jwt.client-secret}")
	   private String clientSecret; 
	
	
	 @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userRepository.findByUserName(username);
	    	if(user==null) {
				throw new RuntimeException("User not found: " + username);
			}
	        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());
	        
	        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), Arrays.asList(authority));
	    }
	
	@SuppressWarnings("unused")
	public GenericResponse login(LoginRequestDto userLoginRequestDto, HttpSession httpSession,
			HttpServletRequest httpServletRequest) throws JSONException, JsonMappingException, JsonProcessingException,InvalidUserValidation {
		logger.debug("login starts");
		
		LoginResponseDto userLoginResponseDto = new LoginResponseDto();
        User user = userRepository.findByUserName(userLoginRequestDto.getUserName());
        if(user!=null) {
        if(user.isStatus()) {
        	
        	logger.info("========== INSIDE login METHOD Bearer ==========");

		String username = userLoginRequestDto.getUserName();
 
		String password = userLoginRequestDto.getPassword();

		logger.info("username : " + username);
		HttpHeaders headers = new HttpHeaders();

		String access_token_url = "http://";
		access_token_url += "localhost";
		access_token_url +=":";
		access_token_url += portNo;
		access_token_url +="/oauth/token?";
		access_token_url += "grant_type=password";
		access_token_url += "&username=";
		access_token_url +=username;
		access_token_url +="&password=";
		access_token_url +=password;
		ResponseEntity<String> response = null;

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth(clientId, clientSecret);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		response = restTemplate.exchange(access_token_url, HttpMethod.POST, entity, String.class);
		System.out.println("Access Token Response ---------" + response.getBody());
		if(response!=null) {
			logger.info(response.getBody());	

			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(response.getBody());
			String token = "Bearer "+node.path("access_token").asText();
			userLoginResponseDto.setUserId(user.getId());
			userLoginResponseDto.setAccessToken(token);
			userLoginResponseDto.setExpiresIn(node.path("expires_in").asText());
			userLoginResponseDto.setRefreshToken(node.path("refresh_token").asText());
			userLoginResponseDto.setUserName(username);
			userLoginResponseDto.setRoleName(user.getRole().getRoleName());
			
			}
		else {
			logger.info("UNAUTHORIZED");
			return Library.getSuccessfulResponse(null, ErrorCode.UNAUTHORIZED.getErrorCode(),
					ErrorMessages.LOGIN_UNAUTHORIZED);
		}
        }
		else {
			logger.error("User not active");
			throw new InvalidDataValidation("User is not active");
		}
		
        }
		else {
			logger.error("User not found");
			throw new InvalidDataValidation("User is not available");
		}
        logger.debug("login ends");
		return Library.getSuccessfulResponse(userLoginResponseDto,
	ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.LOGIN_AUTHORIZED);		
	}


}
