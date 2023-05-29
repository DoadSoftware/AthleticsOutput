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
import java.util.stream.Collectors;

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
import com.athletics.model.Stat;
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
	public static List<String> filenames;
	
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
		List<String> fileLines = null;
		List<Athlete> athletes;
		List<Stat> attempts_results;
		String file_name = "";
		
		switch (whatToProcess) {
		case "BUG_FREE_TEXT_GRAPHICS_OPTIONS":
			
			fileLines = Files.readAllLines(Paths.get(AthleticsUtil.SPORTS_DIRECTORY + AthleticsUtil.ATHLETICS_DIRECTORY 
					+ AthleticsUtil.BUGS_TXT));
			
			session_match.setFilenames(fileLines);
			return JSONObject.fromObject(session_match).toString();
			
		case "SCHEDULE_GRAPHICS_OPTIONS":
			
			session_match.setSchedules(athleticsService.getSchedules());
			return JSONObject.fromObject(session_match).toString();
		
		case "FINISH_LIST_FIELD_GRAPHICS_OPTIONS": case "L3_FIELD_ATTEMPTS_GRAPHICS_OPTIONS":
			
			List<String> filenames = Files.list(Paths.get(AthleticsUtil.SPORTS_DIRECTORY + AthleticsUtil.ATHLETICS_DIRECTORY
					+ AthleticsUtil.SPREADSHEETS_DIRECTORY))
			    .filter(Files::isRegularFile)
			    .map(p -> p.getFileName().toString())
			    .collect(Collectors.toList());
			
			session_match.setFilenames(filenames);
			return JSONObject.fromObject(session_match).toString();
			
		case "START_LIST_FIELD_GRAPHICS_OPTIONS": case "L3_MEDAL_TRACK_GRAPHICS_OPTIONS": case "L3_MEDAL_FIELD_GRAPHICS_OPTIONS":
		case "START_LIST_TRACK_GRAPHICS_OPTIONS": case "FINISH_LIST_TRACK_GRAPHICS_OPTIONS": case "BUG_DESCIPLINE_GRAPHICS_OPTIONS":
			
			if (sessionEventsNetworkDirectory != null && !sessionEventsNetworkDirectory.trim().isEmpty()) {
				switch (whatToProcess) {
				case "START_LIST_FIELD_GRAPHICS_OPTIONS": case "L3_MEDAL_FIELD_GRAPHICS_OPTIONS":
					fileLines = Files.readAllLines(Paths.get(sessionEventsNetworkDirectory 
							+ AthleticsUtil.FLD_LYNX_EVENT));
					break;
				case "START_LIST_TRACK_GRAPHICS_OPTIONS": case "FINISH_LIST_TRACK_GRAPHICS_OPTIONS":
				case "L3_MEDAL_TRACK_GRAPHICS_OPTIONS": case "BUG_DESCIPLINE_GRAPHICS_OPTIONS":
					fileLines = Files.readAllLines(Paths.get(sessionEventsNetworkDirectory 
							+ AthleticsUtil.LYNX_EVENT));
					break;
				}
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
				switch (whatToProcess) {
				case "BUG_DESCIPLINE_GRAPHICS_OPTIONS":
					fileLines = Files.readAllLines(Paths.get(sessionEventsNetworkDirectory 
							+ AthleticsUtil.FLD_LYNX_EVENT));
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
					break;
				}
				session_match.setAthleteList(atheletesList);
			}
			return JSONObject.fromObject(session_match).toString();
			
		case "NAMESUPER_GRAPHICS-OPTIONS":
			
			session_match.setNameSuper(athleticsService.getNameSupers());
			return JSONObject.fromObject(session_match).toString();
			
		case "POPULATE-L3-NAMESUPER": case "POPULATE-START-LIST-TRACK": case "POPULATE-FINISH-LIST-TRACK":
		case "POPULATE-SCHEDULE": case "POPULATE-START-LIST-FIELD": case "POPULATE-L3-MEDAL-TRACK": 
		case "POPULATE-L3-MEDAL-FIELD": case "POPULATE-BUG-DESCIPLINE": case "POPULATE-FINISH-LIST-FIELD":
		case "POPULATE-BUG-FREE-TEXT": case "L3_FIELD_ATTEMPTS_PLAYERS_OPTIONS": case "POPULATE-L3-FIELD-ATTEMPTS":
			
			switch (whatToProcess) {
			case "POPULATE-FINISH-LIST-FIELD": case "L3_FIELD_ATTEMPTS_PLAYERS_OPTIONS":
				
				attempts_results = new ArrayList<Stat>();
				fileLines = Files.readAllLines(Paths.get(AthleticsUtil.SPORTS_DIRECTORY + AthleticsUtil.ATHLETICS_DIRECTORY 
						+ AthleticsUtil.SPREADSHEETS_DIRECTORY + valueToProcess.split(",")[1]));
				atheletesList = new ArrayList<AthleteList>();
				atheletesList.add(new AthleteList());
				athletes = new ArrayList<Athlete>();
				for (int i=0; i<=fileLines.size()-1; i++) {
					if(fileLines.get(i).contains("=")) {
						if(atheletesList.get(0).getHeader() != null) {
							atheletesList.get(0).setHeader(atheletesList.get(0).getHeader() + "," + fileLines.get(i).split("=")[1]);
						} else {
							atheletesList.get(0).setHeader(fileLines.get(i).split("=")[1]);
						}
					} else {
						athletes.add(new Athlete(fileLines.get(i).split(",")[2], 
							fileLines.get(i).split(",")[1], fileLines.get(i).split(",")[3],
							Integer.valueOf(fileLines.get(i).split(",")[0])));
						switch (whatToProcess) {
						case "L3_FIELD_ATTEMPTS_PLAYERS_OPTIONS":
							attempts_results = new ArrayList<Stat>();
							if(valueToProcess.split(",")[1].equalsIgnoreCase("AthleticsPvHj.txt")) {
								for(int j=4; j<fileLines.get(i).split(",").length; j++) {
									if(j+3 < fileLines.get(i).split(",").length) {
										attempts_results.add(new Stat(attempts_results.size()+1, fileLines.get(i).split(",")[j]
												, fileLines.get(i).split(",")[j+1], fileLines.get(i).split(",")[j+2], 
												fileLines.get(i).split(",")[j+3]));
									} else if(j+2 < fileLines.get(i).split(",").length) {
										attempts_results.add(new Stat(attempts_results.size()+1, fileLines.get(i).split(",")[j]
												, fileLines.get(i).split(",")[j+1], fileLines.get(i).split(",")[j+2],""));
									} else if(j+1 < fileLines.get(i).split(",").length) {
										attempts_results.add(new Stat(attempts_results.size()+1, fileLines.get(i).split(",")[j]
												, fileLines.get(i).split(",")[j+1], "",""));
									} else {
										attempts_results.add(new Stat(attempts_results.size()+1, fileLines.get(i).split(",")[j], "", "",""));
									}
									j = j + 3;
								}
							}else if(valueToProcess.split(",")[1].equalsIgnoreCase("AthleticsThrows.txt")) {
								for(int j=4; j<fileLines.get(i).split(",").length; j++) {
									attempts_results.add(new Stat(attempts_results.size()+1, fileLines.get(i).split(",")[j]));
								}
							}
							if(attempts_results.size() > 0) {
								athletes.get(athletes.size()-1).setAttempts_results(attempts_results);
							}
							break;
						}
					}
				}
				if(athletes.size() > 0) {
					atheletesList.get(atheletesList.size()-1).setAthletes(athletes);
				}
				session_match.setAthleteList(atheletesList);
				System.out.println("athlete list = " + atheletesList);
				switch (whatToProcess) {
				case "L3_FIELD_ATTEMPTS_PLAYERS_OPTIONS":
					return JSONObject.fromObject(session_match).toString();
				}
				break;
				
			case "POPULATE-FINISH-LIST-TRACK":
				
				AthleteList this_athlete_list = session_match.getAthleteList().stream().filter(
					al -> al.getAthleteListId() == Integer.valueOf(valueToProcess.split(",")[1])).findAny().orElse(null);

				atheletesList = new ArrayList<AthleteList>();
				athletes = new ArrayList<Athlete>();
				
				if(this_athlete_list != null) {
					
					if (sessionEventsNetworkDirectory != null && !sessionEventsNetworkDirectory.trim().isEmpty()) {
						
						file_name = String.format("%03d", Integer.valueOf(this_athlete_list.getHeader().split(",")[0])) 
							+ "-" + this_athlete_list.getHeader().split(",")[1] + "-" 
							+ String.format("%02d", Integer.valueOf(this_athlete_list.getHeader().split(",")[2])) + ".lif";
						if(!new File(sessionEventsNetworkDirectory + file_name).exists()) {
							session_match.setStatus("ERROR");
							return JSONObject.fromObject(session_match).toString();
						}
						fileLines = Files.readAllLines(Paths.get(sessionEventsNetworkDirectory + file_name));
						
						for (int i=0; i<=fileLines.size()-1; i++) {
							switch (i) {
							case 0:
								atheletesList.add(new AthleteList(atheletesList.size()+1, fileLines.get(i))); 
								break;
							default:
								if(fileLines.get(i).split(",")[6].isEmpty()) {
									athletes.add(new Athlete(Integer.valueOf(fileLines.get(i).split(",")[1]), 
											fileLines.get(i).split(",")[3], fileLines.get(i).split(",")[4], 
											fileLines.get(i).split(",")[5], fileLines.get(i).split(",")[0]));
								} else {
									athletes.add(new Athlete(Integer.valueOf(fileLines.get(i).split(",")[1]), 
											fileLines.get(i).split(",")[3], fileLines.get(i).split(",")[4], 
											fileLines.get(i).split(",")[5], fileLines.get(i).split(",")[6]));
								}
								break;
							}
						}
						atheletesList.get(atheletesList.size()-1).setAthletes(athletes);
						session_match.setAthleteList(atheletesList);
					}
				}
				break;
			}
			session_match.setStatus("");
			session_scene.setScene_path(kheloIndia.getScenes_path() + valueToProcess.split(",")[0]);
			session_scene.scene_load(print_writer, session_selected_broadcaster);
			kheloIndia.ProcessGraphicOption(whatToProcess, session_match, athleticsService, print_writer, valueToProcess);
			return JSONObject.fromObject(session_match).toString();
			
		case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-LINEUP": case "ANIMATE-IN-BUG": case "ANIMATE_OUT":
			
			kheloIndia.ProcessGraphicOption(whatToProcess, session_match, athleticsService, print_writer, valueToProcess);
			return JSONObject.fromObject(session_match).toString();
			
		default:
			return JSONObject.fromObject(null).toString();
		}
	}
}