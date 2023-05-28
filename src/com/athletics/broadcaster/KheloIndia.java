package com.athletics.broadcaster;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import com.athletics.containers.Scene;
import com.athletics.model.Athlete;
import com.athletics.model.AthleteList;
import com.athletics.model.Match;
import com.athletics.model.NameSuper;
import com.athletics.model.Schedule;
import com.athletics.service.AthleticsService;
import com.athletics.util.AthleticsUtil;

public class KheloIndia extends Scene{

	public String session_selected_broadcaster = "KHELO_INDIA";
	
	public String which_graphics_onscreen = "";
	private String status = "";
	private String scenes_path = "C:\\DOAD_In_House_Everest\\Everest_Sports\\Everest_Khelo_India_2023\\Scenes\\";
	private String icon_path = "D:\\Everest_Khelo_India_2023\\Icons\\";
	private int container_id = 0;
	public AthleteList athleteList = null;
	
	public KheloIndia() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScenes_path() {
		return scenes_path;
	}

	public void setScenes_path(String scenes_path) {
		this.scenes_path = scenes_path;
	}

	public Object ProcessGraphicOption(String whatToProcess, Match match, AthleticsService athleticsService, 
			PrintWriter print_writer, String valueToProcess) throws InterruptedException 
	{
		switch (whatToProcess.toUpperCase()) {
		case "POPULATE-BUG-DESCIPLINE":
		    populateBug(whatToProcess, print_writer, match, valueToProcess);
			break;
		case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-MEDAL-TRACK": case "POPULATE-L3-MEDAL-FIELD":
		    populateNameSuper(whatToProcess, print_writer, valueToProcess, athleticsService, match);
			break;
		case "POPULATE-START-LIST-TRACK": case "POPULATE-FINISH-LIST-TRACK": case "POPULATE-SCHEDULE":
		case "POPULATE-START-LIST-FIELD":
			populateLineUp(whatToProcess,print_writer, match, valueToProcess);
			break;
		case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-LINEUP": case "ANIMATE-IN-BUG": case "ANIMATE_OUT":
			
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE_OUT":
				processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster,1);
				break;
			default:
				processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
				break;
			}
			TimeUnit.MILLISECONDS.sleep(200);

			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-NAMESUPER":
				which_graphics_onscreen = "L3-NAMESUPER";
				break;
			case "ANIMATE-IN-LINEUP": 
				which_graphics_onscreen = "LINEUP";
				break;
			case "ANIMATE-IN-BUG":
				which_graphics_onscreen = "BUG";
				break;
			case "ANIMATE_OUT":
				which_graphics_onscreen = "";
				break;
			}
		}
		return null;
	}
	public String getOrdinalText(int numberToProcess) {
		switch ((Math.abs(numberToProcess) % 10)) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}
	public void populateBug(String whatToProcess, PrintWriter print_writer,Match match, 
			String valueToProcess) throws InterruptedException
	{
		athleteList = match.getAthleteList().stream().filter(
				ath -> ath.getAthleteListId() == Integer.valueOf(valueToProcess.split(",")[1])).findAny().orElse(null);
		if(athleteList != null) {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " 
					+ athleteList.getHeader().split(",")[3].toUpperCase().trim() + ";");
		}

		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 110.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
		this.status = "SUCCESS";	
		
	}
	public void populateLineUp(String whatToProcess, PrintWriter print_writer,Match match, String valueToProcess) throws InterruptedException
	{
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGameIcon " + icon_path + "ATHLETICS" + AthleticsUtil.PNG_EXTENSION + ";");
		
		switch (whatToProcess.toUpperCase()) {
		case "POPULATE-SCHEDULE":

			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " 
					+ valueToProcess.split(",")[3].toUpperCase().trim() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubText01 " 
				+ valueToProcess.split(",")[4].toUpperCase().trim() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPTS01 TIME;");
			
			container_id = 0;
			for (Schedule schedule : match.getSchedules()) {
				if(schedule.getScheduleId() >= Integer.valueOf(valueToProcess.split(",")[1])
						&& schedule.getScheduleId() <= Integer.valueOf(valueToProcess.split(",")[2])) {
					status = "";
					container_id = container_id + 1;
					switch (container_id) {
					case 1: case 2: case 3: case 4:
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$A$" + container_id + getOrdinalText(container_id) + "*CONTAINER SET ACTIVE 1;");
						break;
					default:
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$B$" + container_id + getOrdinalText(container_id) + "*CONTAINER SET ACTIVE 1;");
						break;
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPos" + String.format("%02d",container_id) + " " + (container_id) + ".;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam" + String.format("%02d",container_id) 
						+ " " + schedule.getEventName().toUpperCase().trim() + ";");
					if(schedule.getSpecification() != null && !schedule.getSpecification().isEmpty()) {
						status = schedule.getSpecification();
					}
					if(!status.trim().isEmpty()) {
						status = status + " - " + schedule.getRound().toUpperCase().trim();
					} else {
						status = schedule.getRound().toUpperCase().trim();
					}
					if(schedule.getGender() != null && !schedule.getGender().isEmpty()) {
						switch (schedule.getGender().toUpperCase()) {
						case "M":
							status = status + " (MENS)";
							break;
						case "F":
							status = status + " (WOMENS)";
							break;
						}
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUni" + String.format("%02d",container_id) + " " + status + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTime" + String.format("%02d",container_id) 
						+ " " + schedule.getScheduleTime().toUpperCase().trim() + ";");
				}
			}
			for (int i=container_id + 1; i <= 10; i++) {
				switch (i) {
				case 1: case 2: case 3: case 4:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$A$" + i + getOrdinalText(i) + "*CONTAINER SET ACTIVE 0;");
					break;
				default:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$B$" + i + getOrdinalText(i) + "*CONTAINER SET ACTIVE 0;");
					break;
				}
			}
			break;
			
		case "POPULATE-START-LIST-TRACK": case "POPULATE-START-LIST-FIELD":
			
			for (AthleteList al : match.getAthleteList()) {
				if(al.getAthleteListId() == Integer.valueOf(valueToProcess.split(",")[1])) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " 
						+ al.getHeader().split(",")[3].toUpperCase().replace("RUN", "").trim() + ";");
					switch (whatToProcess.toUpperCase()) {
					case "POPULATE-START-LIST-TRACK": 
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubText01 " 
								+ "START LIST ROUND " + al.getHeader().split(",")[1] + ", HEAT " + al.getHeader().split(",")[2] + ";");
						break;
					case "POPULATE-START-LIST-FIELD":
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubText01 " 
								+ "ROUND " + al.getHeader().split(",")[1] + ", HEAT " + al.getHeader().split(",")[2] + ";");
						break;
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 NAME;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 UNIVERSITY;");
					for (int i=0; i < al.getAthletes().size(); i++) {
						System.out.println("al.getAthletes().get(i) = " + al.getAthletes().get(i).toString());
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$LineUp$StartingXI$" 
							+ (i + 1) + getOrdinalText(i + 1) + "*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$LineUp$Subs$" 
							+ (i + 1) + getOrdinalText(i + 1) + "*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayer0" + (i + 1) 
							+ " " + al.getAthletes().get(i).getFullName().toUpperCase() 
							+ " " + al.getAthletes().get(i).getSurname().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole0" + (i + 1)
							+ " " + al.getAthletes().get(i).getAthleteId()+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubs0" + (i + 1) 
							+ " " + al.getAthletes().get(i).getTeamname().toUpperCase() + ";");
					}
					for (int i=al.getAthletes().size(); i <= 11; i++) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$LineUp$StartingXI$" 
								+ (i + 1) + getOrdinalText(i+1) + "*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$LineUp$Subs$" 
									+ (i + 1) + getOrdinalText(i+1) + "*CONTAINER SET ACTIVE 0;");
					}
					break;
				}
			}
			break;
			
		case "POPULATE-FINISH-LIST-TRACK":
			
			for (AthleteList al : match.getAthleteList()) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " 
						+ al.getHeader().split(",")[3].toUpperCase().replace("RUN", "").trim() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubText01 " 
					+ "FINISH LIST ROUND " + al.getHeader().split(",")[1] + ", HEAT " + al.getHeader().split(",")[2] + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPTS01 TIME;");
				for (int i=0; i < al.getAthletes().size(); i++) {
					switch (i + 1) {
					case 1: case 2: case 3: case 4:
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$A$" + (i + 1) + getOrdinalText(i + 1) + "*CONTAINER SET ACTIVE 1;");
						break;
					default:
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$B$" + (i + 1) + getOrdinalText(i + 1) + "*CONTAINER SET ACTIVE 1;");
						break;
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPos" + String.format("%02d",(i + 1)) + " " + (i+1) + ".;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam" + String.format("%02d",(i + 1)) 
						+ " " + al.getAthletes().get(i).getFullName().toUpperCase() 
						+ " " + al.getAthletes().get(i).getSurname().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUni" + String.format("%02d",(i + 1)) 
						+ " " + al.getAthletes().get(i).getTeamname().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTime" + String.format("%02d",(i + 1))
						+ " " + al.getAthletes().get(i).getStats().toUpperCase() + ";");
				}
				for (int i=al.getAthletes().size(); i <= 11; i++) {
					switch (i + 1) {
					case 1: case 2: case 3: case 4:
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$A$" + (i + 1) + getOrdinalText(i + 1) + "*CONTAINER SET ACTIVE 0;");
						break;
					default:
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$B$" + (i + 1) + getOrdinalText(i + 1) + "*CONTAINER SET ACTIVE 0;");
						break;
					}
				}
			}
			break;
		}
	
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 110.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
		this.status = "SUCCESS";	
	}	
	public void populateNameSuper(String whatToProcess, PrintWriter print_writer,String valueToProcess, 
			AthleticsService athleticsService, Match match) throws InterruptedException
	{
		String name = "";
		
		switch (whatToProcess.toUpperCase()) {
		case "POPULATE-L3-MEDAL-TRACK": case "POPULATE-L3-MEDAL-FIELD":
			
			if(valueToProcess.split(",")[3].equalsIgnoreCase("NONE")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon " + icon_path 
					+ "Athletics" + AthleticsUtil.PNG_EXTENSION + ";");
			} else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon " + icon_path 
					+ valueToProcess.split(",")[3] + AthleticsUtil.PNG_EXTENSION + ";");
			}
			
			athleteList = match.getAthleteList().stream().filter(
					ath -> ath.getAthleteListId() == Integer.valueOf(valueToProcess.split(",")[1])).findAny().orElse(null);
			if(athleteList != null) {
				for (Athlete athlete : athleteList.getAthletes()) {
					if(athlete.getAthleteId() == Integer.valueOf(valueToProcess.split(",")[2])) {
						name = athlete.getFullName().toUpperCase().trim();
						if(!name.trim().isEmpty()) {
							name = name + " " + athlete.getSurname().toUpperCase().trim();
						} else {
							name = athlete.getSurname().toUpperCase().trim();
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName " + name +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOtherInfo ;");
						if(valueToProcess.split(",")[3].equalsIgnoreCase("NONE")) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " 
								+ athlete.getTeamname().toUpperCase() + ";");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " 
								+ athleteList.getHeader().split(",")[3].toUpperCase() + ";");
						}
					}
				}
			}
			
			break;

		default:
		
			if(valueToProcess.split(",").length >= 3) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon " + icon_path + valueToProcess.split(",")[2] + AthleticsUtil.PNG_EXTENSION + ";");
			} else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon " + icon_path + "ATHLETICS" + AthleticsUtil.PNG_EXTENSION + ";");
			}
			for (NameSuper ns : athleticsService.getNameSupers()) {
				if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
					name = ns.getFirstname().toUpperCase().trim();
					if(!name.trim().isEmpty()) {
						name = name + " " + ns.getSurname().toUpperCase().trim();
					} else {
						name = ns.getSurname().toUpperCase().trim();
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName " + name +";");
					if(ns.getSubHeader() != null) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOtherInfo " + ns.getSubHeader().toUpperCase() + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOtherInfo ;");
					}
					if(ns.getSubLine() != null) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + ns.getSubLine().toUpperCase() + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails ;");
					}
					break;
				}
			}
			break;
		}

		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
		this.status = "SUCCESS";	
	}	
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster,int which_layer)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "KHELO_INDIA":
			switch(which_layer) {
			case 1:
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				break;
				
			case 2:
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				break;
			}
			break;
		}
		
	}
}