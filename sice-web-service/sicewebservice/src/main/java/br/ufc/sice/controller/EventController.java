package br.ufc.sice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import br.ufc.sice.dao.EventDAO;
import br.ufc.sice.model.Event;
import br.ufc.sice.network.Answer;

@Controller
public class EventController {
	
	@Autowired
	EventDAO eventDAO;
	
	@Autowired
	Gson gson;
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/event/insert")
	public ModelAndView insert(Event event) {
		eventDAO.save(event);
		
		String message = gson.toJson(new Answer(event)); 
		
		ModelAndView mv = new ModelAndView("welcome", "message", message);
		return mv;
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
	@RequestMapping("/event/insertweb")
	public ModelAndView insertWeb(Event event, String begin1, String end1) {
		event.setBegin(getDate(begin1));
		event.setEnd(getDate(end1));
		eventDAO.save(event);
		
		String message = gson.toJson(new Answer(event));
		
		ModelAndView mv = new ModelAndView("welcome", "message", message);
		return mv;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/event/load")
	public ModelAndView load() {
		List result = eventDAO.loadAll();
		String message = gson.toJson(new Answer(result)); 
		
		return new ModelAndView("welcome", "message", message);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/event/load_filter")
	public ModelAndView loadFilter(String filter) {
		List result = eventDAO.loadAllFilter(filter);
		String message = gson.toJson(new Answer(result)); 
		
		return new ModelAndView("welcome", "message", message);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/event/summary")
	public ModelAndView summary(long id) {
		List result = eventDAO.summary(id);
		String message = gson.toJson(new Answer(result)); 
		
		return new ModelAndView("welcome", "message", message);
	}
}
