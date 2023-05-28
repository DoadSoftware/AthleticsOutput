package com.athletics.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.text.ParseException;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.athletics.broadcaster.KheloIndia;
import com.athletics.containers.Scene;
import com.athletics.model.Match;
import com.athletics.service.AthleticsService;
import com.athletics.util.AthleticsUtil;

import net.sf.json.JSONObject;

@Controller
public class IndexController
{
	@Autowired
	AthleticsService athleticsService;
	
	public static KheloIndia kheloIndia;
	public static Match session_match;
	public static PrintWriter print_writer;
	public static Socket session_socket;
	public static String session_selected_broadcaster;
	public static Scene session_scene;
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model)
		throws JAXBException, IOException, ParseException 
	{
		return "initialise";
	}

	@RequestMapping(value = {"/athletics"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String clockPage(ModelMap model,
			@RequestParam(value = "selectedBroadcaster", required = false, defaultValue = "") String selectedBroadcaster,
			@RequestParam(value = "vizIPAddress", required = false, defaultValue = "") String vizIPAddresss,
			@RequestParam(value = "vizPortNumber", required = false, defaultValue = "") String vizPortNumber,
			@RequestParam(value = "vizScene", required = false, defaultValue = "") String vizScene) 
					throws JAXBException, IOException, ParseException 
	{
		session_selected_broadcaster = selectedBroadcaster;
		session_match = new Match();
		kheloIndia = new KheloIndia();
		
		if(!vizIPAddresss.isEmpty()) {
			session_socket = new Socket(vizIPAddresss, Integer.valueOf(vizPortNumber));
			print_writer = new PrintWriter(session_socket.getOutputStream(), true);
		}
		
		model.addAttribute("session_selected_broadcaster", session_selected_broadcaster);
		model.addAttribute("session_socket",session_socket);
		
		return "athletics";
	}
	
	@RequestMapping(value = {"/processAthleticsProcedures"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processAthleticsProcedures(
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess)
					throws JAXBException, IllegalAccessException, InvocationTargetException, IOException, 
					NumberFormatException, InterruptedException
	{	
		switch (whatToProcess) {
		case "NAMESUPER_GRAPHICS-OPTIONS":
			session_match.setNameSuper(athleticsService.getNameSupers());
			return JSONObject.fromObject(session_match).toString();
		case "POPULATE-L3-NAMESUPER":
			session_scene = new Scene(kheloIndia.getScenes_path() + valueToProcess.split(",")[0],AthleticsUtil.ONE);
			session_scene.scene_load(print_writer, session_selected_broadcaster);
			kheloIndia.ProcessGraphicOption(whatToProcess, session_match, athleticsService, print_writer, valueToProcess);
		case "ANIMATE-IN-NAMESUPER":
			kheloIndia.ProcessGraphicOption(whatToProcess, session_match, athleticsService, print_writer, valueToProcess);
			return JSONObject.fromObject(session_match).toString();
		default:
			return JSONObject.fromObject(null).toString();
		}
	}
}