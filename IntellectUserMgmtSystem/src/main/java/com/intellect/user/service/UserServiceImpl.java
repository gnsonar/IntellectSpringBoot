package com.intellect.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.intellect.user.model.ErrorResponse;
import com.intellect.user.model.User;
import com.intellect.user.model.UserResponse;
import com.intellect.user.validate.UserValidation;

@Service
@PropertySource("classpath:application.properties")
public class UserServiceImpl implements UserService{

	private List<User> usersRepository = new ArrayList<>();
	int id = 1234;
	
	@Autowired
    private Environment env;
	@Autowired
	UserValidation userValidation;
	
	@Override
	public UserResponse addUser(User user) {
		
		UserResponse response = new UserResponse();
		
		validateCreateUserDetails(user,response);
		
		if(response.getVarErrors().size() == 0) {
			String idVal = String.valueOf(id);
			id = id + 1;
			user.setActive(true);
			user.setId(idVal);
			usersRepository.add(user);
			response.setUserId(idVal);
			response.setResMsg(env.getProperty("success.user.added.msg"));
		}else {
			response.setResMsg(env.getProperty("error.validate.create.msg"));
		}
		return response;
	}
	

	@Override
	public UserResponse updateUser(User user) {

		UserResponse response = new UserResponse();
		
		validateUpdateUserDetails(user,response);
		
		boolean dataUpdated = false;
		boolean pinudated = false;
		boolean emailupdated = false;
		
		if(response.getVarErrors().size() == 0) {
			for(int i = 0;i<usersRepository.size();i++) {
				User userDb = usersRepository.get(i);
				if(user.getId() == userDb.getId()) {
					if(user.getPinCode() != 0 && user.getPinCode() != userDb.getPinCode()) {
						userDb.setPinCode(user.getPinCode());
						pinudated = true;
					}
					if((user.getEmail() != null && !"".equalsIgnoreCase(user.getEmail())) && 
							!user.getEmail().equalsIgnoreCase(userDb.getEmail())) {
						userDb.setEmail(user.getEmail());
						emailupdated = true;
					}
					dataUpdated = true;
					break;
				}
			}
			if(pinudated || emailupdated) {
				response.setResMsg(env.getProperty("success.user.updated.msg"));
			}else {
				response.setResMsg(env.getProperty("success.user.updated.msg.nodata"));
			}
			
		}else {
			response.setResMsg(env.getProperty("error.validate.update.msg"));
		}
		if(!dataUpdated) {
			response.setResMsg(env.getProperty("error.user.notexists.msg"));
		}
		return response;
	}


	@Override
	public UserResponse deleteUser(User user) {

		UserResponse response = new UserResponse();
		boolean dataUpdated = false;
		validateDeleteUserDetails(user,response);
		if(response.getVarErrors().size() == 0) {
			for(int i = 0;i<usersRepository.size();i++) {
				User userDb = usersRepository.get(i);
				if(user.getId() == userDb.getId()) {
					userDb.setActive(false);
					dataUpdated = true;
					break;
				}
			}
			response.setResMsg(env.getProperty("success.user.deleted.msg"));
		}else {
			response.setResMsg(env.getProperty("error.validate.delete.msg"));
		}
		
		if(!dataUpdated) {
			response.setResMsg(env.getProperty("error.user.notexists.msg"));
		}
		
		return response;
	}

	private void validateCreateUserDetails(User user, UserResponse response) {
		
		userValidation.isFirstNameExists(user, response);
		userValidation.isLastNameExists(user, response);
		userValidation.isEmailIdExists(user, response);
		userValidation.isPinCodeExists(user, response);
		userValidation.isBirthDayExists(user, response);
		
		if(response.getVarErrors().size() == 0) {
			userValidation.ispinCodeValid(user, response);
			userValidation.isBirthDayFormatCorrect(user, response);
			userValidation.isEmailFormatCorrect(user, response);
		}
		
		if(response.getVarErrors().size() == 0) {
			userValidation.isBirthDateisNotFutureDate(user, response);
		}
		if(response.getVarErrors().size() == 0) {
			for(int i=0;i<usersRepository.size();i++) {
				if(user.getEmail().equals(usersRepository.get(i).getEmail()) && usersRepository.get(i).isActive()) {
					response.addVarErrors(new ErrorResponse(env.getProperty("error.email.duplicate.code"), env.getProperty("error.email.filed.name"), 
							env.getProperty("error.email.duplicate.message")));
				}
			}
		}
		
	}
	

	private void validateUpdateUserDetails(User user, UserResponse response) {
		
		userValidation.isIdExists(user, response);
		
		if(response.getVarErrors().size() == 0) {
			if(user.getPinCode() != 0) {
				userValidation.ispinCodeValid(user, response);
			}
			if(user.getEmail() != null && !"".equalsIgnoreCase(user.getEmail())) {
				userValidation.isEmailFormatCorrect(user, response);
			}
			if(user.getPinCode() == 0 && (user.getEmail() != "" || "".equalsIgnoreCase(user.getEmail()))) {
				response.addVarErrors(new ErrorResponse(env.getProperty("error.update.nodata.code"), env.getProperty("error.update.nodata.name"), 
						env.getProperty("error.update.nodata.message")));
			}
		}
	}


	private void validateDeleteUserDetails(User user, UserResponse response) {
		userValidation.isIdExists(user, response);
		
	}
}
