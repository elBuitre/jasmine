package com.bui3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class JasmineController {

	@Value("${users-list-size}")
	private int usersListSize;
	
	@Value("${pager-size}")
	private int pagerSize;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	LocationRepository locationRepository;
	
	public Page<User> populateUsers(Integer pageNumber, String filter) {
		PageRequest request =
				new PageRequest(pageNumber-1, usersListSize,
						Sort.Direction.ASC, "username");
		
		if (null == filter || filter.equals(""))
			return userRepository.findAll(request);
		
		return userRepository.findByStringFieldsLike(filter, request);
	}

	@ModelAttribute("roles")
	public List<Role> populateRoles() {
		return roleRepository.findAll();
	}

	@ModelAttribute("locations")
	public List<Location> populateLocations() {
		return locationRepository.findByParentIsNull();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String homePage() {
		return "homePage";
	}
	
	@RequestMapping(value="/admin/search", method=RequestMethod.POST)
	public String adminSearch(@RequestParam(name="filter", required=false) String filter,
			Model model, User user) {
		return adminPage(null, filter, model, user);
	}
	
	@RequestMapping(value="/admin{pageNumber}", method=RequestMethod.GET)
	public String adminPage(@PathVariable Integer pageNumber,
			@RequestParam(name="filter", required=false) String filter,
			Model model,
			User user) {
		
		Page<User> page = populateUsers(
				((null != pageNumber) ? pageNumber : 1),
				filter);
		
		int pages = page.getTotalPages();
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - (pagerSize - 1));
		int end = Math.min(begin + (pagerSize - 1), pages);

		model.addAttribute("users", page);
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPages", pages);
		
		if (null != filter)
			model.addAttribute("filter", filter);
		
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
