package br.ufc.sice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import br.ufc.sice.dao.ParticipationDAO;
import br.ufc.sice.dao.UserDAO;
import br.ufc.sice.model.Participation;
import br.ufc.sice.model.User;
import br.ufc.sice.network.Answer;

@Controller
public class ParticipationController {
	
	@Autowired
	UserDAO userDAO;

	@Autowired
	ParticipationDAO participationDAO;
	
	@Autowired
	Gson gson;

	@RequestMapping("/participation/insert")
	public ModelAndView insert(Long idUser, Long idSubEvent) {
		Participation participation = new Participation();
		participation.setIdSubEvent(idSubEvent);
		participation.setIdUser(idUser);
		
		participationDAO.save(participation);
		User user = userDAO.load(idUser);
		
		String message = gson.toJson(new Answer(user)); 
		return new ModelAndView("welcome", "message", message);
	}

}
