package br.ufc.sice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import br.ufc.sice.dao.UserDAO;
import br.ufc.sice.model.User;
import br.ufc.sice.network.Answer;
import br.ufc.sice.network.Answer.Cod;

@Controller
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	Gson gson;
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/user/insert")
	public ModelAndView insert(User user) {
		user.setLevel(User.Levels.PARTICIPANT);
		userDAO.save(user);
		
		String message = gson.toJson(new Answer(user)); 
		return new ModelAndView("welcome", "message", message);
	}
	
	@RequestMapping("/user/load")
	public ModelAndView load() {
		List result = userDAO.loadAll();
		String message = gson.toJson(new Answer(result)); 
		
		return new ModelAndView("welcome", "message", message);
	}
	
	@RequestMapping("/user/login")
	public ModelAndView login(User user) {
		System.out.println(user);
		List result = userDAO.loadAll();
		System.out.println(result);
		Answer ans = new Answer();
		
		if(result.contains(user)){
			ans.setResult(Cod.SUCCESS);
			ans.setObject(((User)result.get(result.indexOf(user))));
		}else{
			ans.setResult(Cod.ERROR);
			ans.setObject("User does not exist");
		}
		
		return new ModelAndView("welcome", "message", gson.toJson(ans));
	}

}
