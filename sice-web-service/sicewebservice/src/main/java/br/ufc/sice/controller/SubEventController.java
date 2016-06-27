package br.ufc.sice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import br.ufc.sice.dao.EventDAO;
import br.ufc.sice.dao.SubEventDAO;
import br.ufc.sice.model.Event;
import br.ufc.sice.model.SubEvent;
import br.ufc.sice.network.Answer;

@Controller
public class SubEventController {
	
	@Autowired
	SubEventDAO subEventDAO;
	
	@Autowired
	Gson gson;
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/subevent/insert")
	public ModelAndView insert(SubEvent event) {
		subEventDAO.save(event);
		
		String message = gson.toJson(new Answer(event)); 
		return new ModelAndView("welcome", "message", message);
	}
	
	public Date getDate(String date){
		try {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/subevent/insertweb")
	public ModelAndView insertWeb(SubEvent event, String begin1, String end1) {
		event.setBegin(getDate(begin1));
		event.setEnd(getDate(end1));
		subEventDAO.save(event);
		
		String message = gson.toJson(new Answer(event)); 
		
		ModelAndView mv = new ModelAndView("welcome", "message", message);
		return mv;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/subevent/load")
	public ModelAndView load(long id_event) {
		List result = subEventDAO.loadAllFromEvent(id_event);
		String message = gson.toJson(new Answer(result)); 
		
		return new ModelAndView("welcome", "message", message);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/subevent/get_participants")
	public ModelAndView loadParticipants(long id) {
		List list = subEventDAO.loadUsersFromSubEvents(id);
		
		String message = gson.toJson(new Answer(list)); 
		
		return new ModelAndView("welcome", "message", message);
	}
}
