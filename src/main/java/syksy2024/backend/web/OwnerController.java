package syksy2024.backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import syksy2024.backend.model.Owner;
import syksy2024.backend.model.SignUpForm;
import syksy2024.backend.repository.OwnerRepository;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;


@Controller
public class OwnerController {

    @Autowired
    private OwnerRepository oRepo;

    @RequestMapping(value = "signup")
    public String signUpOwner(Model model) {
      model.addAttribute("signupform", new SignUpForm());
        return "signup";
    }

        @RequestMapping(value = "saveowner", method = RequestMethod.POST)
        public String save(@Valid @ModelAttribute("signupform") SignUpForm signupForm, BindingResult bindingResult) {
    	if (!bindingResult.hasErrors()) { // validation errors
    		if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { // check password match		
	    		String pwd = signupForm.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPwd = bc.encode(pwd);
	
		    	Owner newOwner = new Owner();
		    	newOwner.setPasswordHash(hashPwd);
		    	newOwner.setUsername(signupForm.getUsername());
				newOwner.setFirstName(signupForm.getFirstName());
            	newOwner.setLastName(signupForm.getLastName());
            	newOwner.setDateOfBirth(signupForm.getDateOfBirth());
		    	newOwner.setRole("USER");
		    	if (oRepo.findByUsername(signupForm.getUsername()) == null) { // Check if user exists
		    		oRepo.save(newOwner);
		    	}
		    	else {
	    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
	    			return "signup";		    		
		    	}
    		}
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
    			return "signup";
    		}
    	}
    	else {
    		return "signup";
    	}
    	return "redirect:/login";    	
    }  
    

}
