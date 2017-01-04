package com.bui3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class JasmineController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@ModelAttribute("users")
	public List<User> populateUsers() {
		return userRepository.findAll();
	}
	
	@ModelAttribute("roles")
	public List<Role> populateRoles() {
		return roleRepository.findAll();
	}

	@RequestMapping(method=RequestMethod.GET)
	public String homePage() {
		return "homePage";
	}
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String adminPage(User user) {
		return "adminPage";
	}
	
	@RequestMapping(value="/admin", method=RequestMethod.POST)
	public String addUser(User user, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors())
			return "adminPage";
		
		userRepository.save(user);
		return "redirect:/admin";
	}
	
	@RequestMapping(value="/admin/delete", method=RequestMethod.GET)
	public String deleteUser(
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="phase", required=true) String phase,
			Model model) {
		
		User user = userRepository.findOne(username);
		
		if (phase.equals("stage") && null != user) {
			model.addAttribute("user", user);
			return "userDeletePage";
		}
		
		if (phase.equals("confirm")) {
			if (null != user)
				userRepository.delete(user);
		}
		
		return "redirect:/admin";
	}
	
	@RequestMapping(value="/admin/edit", method=RequestMethod.GET)
	public String editUser(
			@RequestParam(value="username", required=true) String username,
			Model model) {
		
		User user = userRepository.findOne(username);
		
		if (null != user) {
			model.addAttribute("user", user);
			return "userEditPage";
		}
		return "redirect:/admin";
	}
	
	@RequestMapping(value="/admin/edit", method=RequestMethod.POST)
	public String editingUser(
			@RequestParam(value="action", required=true) String action,
			@ModelAttribute User user, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors() && action.equals("save"))
			return ("userEditPage");
		
		if (action.equals("save")) {
			if (user.getPassword().isEmpty() || null == user.getPassword())
				user.setPassword(
						userRepository.findOne(user.getUsername()).getPassword());
			
			userRepository.save(user);
		}
		
		return "redirect:/admin";
	}
}
