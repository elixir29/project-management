package com.piy.pma.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.piy.pma.dao.IUserAccountsRepository;
import com.piy.pma.entities.UserAccount;
import com.piy.pma.helper.Message;

@Controller
public class SecurityController {
	
	@Autowired
	IUserAccountsRepository userAccountsRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/register")
	public String register(Model model) {
		UserAccount userAccount = new UserAccount();
		model.addAttribute("userAccount", userAccount);
		return "security/registration-page";
	}
	
	@PostMapping("/register/save")
	public String saveUser(@Valid UserAccount user, BindingResult result, Model model, HttpSession session) {
		
		
		//When new user is being created.
		if(user.getUserID() == 0 && !result.hasErrors()) {
			
			UserAccount userFromRepoForEmailVerification = userAccountsRepository.getUserByEmail(user.getEmail());
			
			UserAccount userFromRepoForUserNameVerification = userAccountsRepository.getUserByuserName(user.getUserName());

			
			//Check if entered email already exists in DB.
			if(userFromRepoForEmailVerification != null && userFromRepoForEmailVerification.getEmail().equals(user.getEmail())) {
				model.addAttribute("errorMessage", "User registration failed. This email address already belongs to an other user.");
				return "security/user-creation-error";
			}
			//Check if entered username already exists in DB.
			else if(userFromRepoForUserNameVerification != null && userFromRepoForUserNameVerification.getUserName().equals(user.getUserName())) {
				model.addAttribute("errorMessage", "User registration failed. This username already belongs to an other user.");
				return "security/user-creation-error";
			}
		}
		else if(result.hasErrors()) {
			return "security/user-creation-error";
		}
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userAccountsRepository.save(user);
		session.setAttribute("message", new Message("success", "Registration successful"));
		return "redirect:/";
	}
}
