package com.intellect.user.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.intellect.user.model.ErrorResponse;
import com.intellect.user.model.User;
import com.intellect.user.model.UserResponse;

@Component
@PropertySource("classpath:application.properties")
public class UserValidation {

	@Autowired
    private Environment env;
	
	private  SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	
	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN =
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	
	public  boolean isFirstNameExists(User user, UserResponse response) {
		
		if(user.getfName() == null || "".equalsIgnoreCase(user.getfName())) {
			
			response.addVarErrors(new ErrorResponse(env.getProperty("error.fname.mandatory.code"), env.getProperty("error.fname.field.name"), 
					env.getProperty("error.fname.mandatory.message")));
			
			return false;
		}
		return true;
	}
	
	public  boolean isLastNameExists(User user, UserResponse response) {
		
		if(user.getlName() == null || "".equalsIgnoreCase(user.getlName())) {
			
			response.addVarErrors(new ErrorResponse(env.getProperty("error.lname.mandatory.code"), env.getProperty("error.lname.field.name"), 
					env.getProperty("error.lname.mandatory.message")));
			
			return false;
		}
		return true;
	}

	public  boolean isEmailIdExists(User user, UserResponse response) {
	
		if(user.getEmail() == null || "".equalsIgnoreCase(user.getEmail())) {
			
			response.addVarErrors(new ErrorResponse(env.getProperty("error.emailman.mandatory.code"), env.getProperty("error.emailman.field.name"), 
					env.getProperty("error.emailman.mandatory.message")));
			
			return false;
		}
		return true;
	}

	public  boolean isPinCodeExists(User user, UserResponse response) {
	
		if(user.getPinCode() == 0) {
			
			response.addVarErrors(new ErrorResponse(env.getProperty("error.pincode.mandatory.code"), env.getProperty("error.pincode.field.name"), 
					env.getProperty("error.pincode.mandatory.message")));
			
			return false;
		}
		return true;
	}

	public  boolean isBirthDayExists(User user, UserResponse response) {
		
		if(user.getBirthDate() == null || "".equalsIgnoreCase(user.getBirthDate())) {
			
			response.addVarErrors(new ErrorResponse(env.getProperty("error.birtdateman.mandatory.code"), env.getProperty("error.birtdateman.field.name"), 
					env.getProperty("error.birtdateman.mandatory.message")));
			
			return false;
		}
		return true;
	}
	
	public  boolean isIdExists(User user, UserResponse response) {
		
		if(user.getId() == null || "".equalsIgnoreCase(user.getId())) {
			
			response.addVarErrors(new ErrorResponse(env.getProperty("error.id.mandatory.code"), env.getProperty("error.id.field.name"), 
					env.getProperty("error.id.mandatory.message")));
			
			return false;
		}
		return true;
	}

	public  boolean idNumericValidate(User user, UserResponse response) {
		
		try {
			if(user.getId() != null && "".equalsIgnoreCase(user.getId())) {
				Integer.parseInt(user.getId());
			}
		}catch(Exception e) {
			response.addVarErrors(new ErrorResponse(env.getProperty("error.id.numberic.code"), env.getProperty("error.id.field.name"), 
					env.getProperty("error.numberic.mandatory.message")));
			
			return false;
		}
		
		return true;
	}

	public  boolean ispinCodeValid(User user, UserResponse response) {
		
		if(String.valueOf(user.getPinCode()).length() != 6) {
			
			response.addVarErrors(new ErrorResponse(env.getProperty("error.pin.length.code"), env.getProperty("error.pincode.field.name"), 
					env.getProperty("error.pin.length.message")));
			
			return false;
		}
		return true;
	}
	
	public  boolean isBirthDayFormatCorrect(User user, UserResponse response) {
		String birthDate = user.getBirthDate();
		
		try {
			 format.parse(birthDate);
		}catch(Exception e) {
			response.addVarErrors(new ErrorResponse(env.getProperty("error.birthdate.invalid.code"), env.getProperty("error.birthdate.field.name"), 
					env.getProperty("error.birthdate.invalid.message")));
			return false;
		}
		
		return true;
	}
	
	public  boolean isEmailFormatCorrect(User user, UserResponse response) {
		String emailId = user.getEmail();
		pattern = Pattern.compile(EMAIL_PATTERN);
		
		matcher = pattern.matcher(emailId);
		
		if(!matcher.matches()) {
			response.addVarErrors(new ErrorResponse(env.getProperty("error.emailformat.invalid.code"), env.getProperty("error.email.field.name"), 
					env.getProperty("error.emailformat.invalid.message")));
			return false;
		}
		return true;
	}
	
	public  boolean isBirthDateisNotFutureDate(User user, UserResponse response) {
		Date now = new Date();
		Date bDate = null;
		try {
			bDate = format.parse(user.getBirthDate());
			if(bDate.compareTo(format.parse(format.format(now))) > 0) {
				response.addVarErrors(new ErrorResponse(env.getProperty("error.birthdatefuture.invalid.code"), env.getProperty("error.birthdate.field.name"), 
						env.getProperty("error.birthdatefuture.invalid.message")));
			}
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
}
