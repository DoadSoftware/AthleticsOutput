package com.athletics.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
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
import com.athletics.model.Athlete;
import com.athletics.model.AthleteList;
import com.athletics.model.Configuration;
import com.athletics.model.Match;
import com.athletics.service.AthleticsService;
import com.athletics.util.AthleticsUtil;

import net.sf.json.JSONObject;

@Controller
public class IndexController
{
	@Autowired
	AthleticsService athleticsService;
	
	public static Configuration session_configuration;
	public static KheloIndia kheloIndia;
	public static Match session_match;
	public static PrintWriter print_writer;
	public static Socket session_socket;
	public static String session_selected_broadcaster;
	public static Scene session_scene;
	public static String sessionEventsNetworkDirectory;
	public static List<AthleteList> atheletesList = new ArrayList<AthleteList>(); 
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model)
		throws JAXBException, IOException, ParseException 
	{
		if(new File(AthleticsUtil.SPORTS_DIRECTORY + AthleticsUtil.ATHLETICS_DIRECTORY + AthleticsUtil.CONFIGURATIONS_DIRECTORY + AthleticsUtil.OUTPUT_XML).exists()) {
			session_configuration = (Configuration) JAXBContext.newInstance(Configuration.class).createUnmarshaller().unmarshal(
				new File(AthleticsUtil.SPORTS_DIRECTORY + AthleticsUtil.ATHLETICS_DIRECTORY + AthleticsUtil.CONFIGURATIONS_DIRECTORY + AthleticsUtil.OUTPUT_XML));
		} else {
			session_configuration = new Configuration();
			JAXBContext.newInstance(Configuration.class).createMarshaller().marshal(session_configuration, 
					new File(AthleticsUtil.SPORTS_DIRECTORY + AthleticsUtil.ATHLETICS_DIRECTORY + AthleticsUtil.CONFIGURATIONS_DIRECTORY + AthleticsUtil.OUTPUT_XML));
		}
		
		model.addAttribute("session_configuration", session_configuration);
		return "initialise";
	}

	@RequestMapping(value = {"/athletics"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String clockPage(ModelMap model,
			@RequestParam(value = "selectedBroadcaster", required = false, defaultValue = "") String selectedBroadcaster,
			@RequestParam(value = "vizIPAddress", required = false, defaultValue = "") String vizIPAddresss,
			@RequestParam(value = "vizPortNumber", required = false, defaultValue = "") String vizPortNumber,
			@RequestParam(value = "eventsNetworkDirectory", required = false, defaultValue = "") String eventsNetworkDirectory) 
					throws JAXBException, IOException, ParseException 
	{
		session_selected_broadcaster = selectedBroadcaster;
		session_match = new Match();
		kheloIndia = new KheloIndia();
		sessionEventsNetworkDirectory = eventsNetworkDirectory + "\\";
		session_scene = new Scene("",AthleticsUtil.ONE);
		
		if(!vizIPAddresss.isEmpty()) {
			session_socket = new Socket(vizIPAddresss, Integer.valueOf(vizPortNumber));
			print_writer = new PrintWriter(session_socket.getOutputStream(), true);
		}
		session_configuration = new Configuration(eventsNetworkDirectory, session_selected_broadcaster, 
				vizIPAddresss, vizPortNumber);
		JAXBContext.newInstance(Configuration.class).createMarshaller().marshal(session_configuration, 
				new File(AthleticsUtil.SPORTS_DIRECTORY + AthleticsUtil.ATHLETICS_DIRECTORY + AthleticsUtil.CONFIGURATIONS_DIRECTORY + AthleticsUtil.OUTPUT_XML));
		
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
		List<String> fileLines;
		List<Athlete> athletes;
		String file_name = "";
		
		switch (whatToProcess) {
		case "START_LINEUP_GRAPHICS_OPTIONS": case "FINISH_LINEUP_GRAPHICS_OPTIONS":
			
			if (sessionEventsNetworkDirectory != null && !sessionEventsNetworkDirectory.trim().isEmpty()) {
				fileLines = Files.readAllLines(Paths.get(sessionEventsNetworkDirectory 
					+ AthleticsUtil.LYNX_EVENT));
				atheletesList = new ArrayList<AthleteList>();
				athletes = new ArrayList<Athlete>();
				for (int i=0; i<=fileLines.size()-1; i++) {
					if(!fileLines.get(i).split(",")[0].trim().isEmpty()) {
						if(athletes.size() > 0) {
							atheletesList.get(atheletesList.size()-1).setAthletes(athletes);
							athletes = new ArrayList<Athlete>();
						}
						atheletesList.add(new AthleteList(atheletesList.size()+1, fileLines.get(i))); 
					} else {
						athletes.add(new Athlete(Integer.valueOf(fileLines.get(i).split(",")[1]), 
							fileLines.get(i).split(",")[3], fileLines.get(i).split(",")[4], 
							fileLines.get(i).split(",")[5]));
					}
				}
				if(athletes.size() > 0) {
					atheletesList.get(atheletesList.size()-1).setAthletes(athletes);
				}
				session_match.setAthleteList(atheletesList);
			}
			return JSONObject.fromObject(session_match).toString();
			
		case "NAMESUPER_GRAPHICS-OPTIONS":
			
			session_match.setNameSuper(athleticsService.getNameSupers());
			return JSONObject.fromObject(session_match).toString();
			
		case "POPULATE-L3-NAMESUPER": case "POPULATE-START-LINEUP": case "POPULATE-FINISH-LINEUP":
			
			switch (whatToProcess) {
			case "POPULATE-FINISH-LINEUP":
				
				AthleteList this_athlete_list = session_match.getAthleteList().stream().filter(
					al -> al.getAthleteListId() == Integer.valueOf(valueToProcess.split(",")[1])).findAny().orElse(null);

				atheletesList = new ArrayList<AthleteList>();
				athletes = new ArrayList<Athlete>();
				
				if(this_athlete_list != null) {
					if (sessionEventsNetworkDirectory != null && !sessionEventsNetworkDirectory.trim().isEmpty()) {
						
						file_name = String.format("%03d", Integer.valueOf(this_athlete_list.getHeader().split(",")[0])) 
							+ "-" + this_athlete_list.getHeader().split(",")[1] + "-" 
							+ String.format("%02d", Integer.valueOf(this_athlete_list.getHeader().split(",")[2])) + ".lif";
						fileLines = Files.readAllLines(Paths.get(sessionEventsNetworkDirectory + file_name));
						
						for (int i=0; i<=fileLines.size()-1; i++) {
							switch (i) {
							case 0:
								atheletesList.add(new AthleteList(atheletesList.size()+1, fileLines.get(i))); 
								break;
							default:
								athletes.add(new Athlete(Integer.valueOf(fileLines.get(i).split(",")[1]), 
									fileLines.get(i).split(",")[3], fileLines.get(i).split(",")[4], 
									fileLines.get(i).split(",")[5], fileLines.get(i).split(",")[6]));
								break;
							}
						}
						atheletesList.get(atheletesList.size()-1).setAthletes(athletes);
						session_match.setAthleteList(atheletesList);
					}
				}
				break;
			}
			
			session_scene.setScene_path(kheloIndia.getScenes_path() + valueToProcess.split(",")[0]);
			session_scene.scene_load(print_writer, session_selected_broadcaster);
			kheloIndia.ProcessGraphicOption(whatToProcess, session_match, athleticsService, print_writer, valueToProcess);
			return JSONObject.fromObject(session_match).toString();
			
		case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-LINEUP": case "ANIMATE_OUT":
			
			kheloIndia.ProcessGraphicOption(whatToProcess, session_match, athleticsService, print_writer, valueToProcess);
			return JSONObject.fromObject(session_match).toString();
			
		default:
			return JSONObject.fromObject(null).toString();
		}
	}
}