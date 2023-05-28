package com.athletics.broadcaster;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import com.athletics.containers.Scene;
import com.athletics.model.AthleteList;
import com.athletics.model.Match;
import com.athletics.model.NameSuper;
import com.athletics.service.AthleticsService;
import com.athletics.util.AthleticsUtil;

public class KheloIndia extends Scene{

	public String session_selected_broadcaster = "KHELO_INDIA";
	
	public String which_graphics_onscreen = "";
	private String status;
	private String scenes_path = "C:\\DOAD_In_House_Everest\\Everest_Sports\\Everest_Khelo_India_2023\\Scenes\\";
	private String icon_path = "D:\\Everest_Khelo_India_2023\\Icons\\";
	
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
		case "POPULATE-L3-NAMESUPER": 
		    populateNameSuper(print_writer, valueToProcess, athleticsService);
			break;
		case "POPULATE-START-LINEUP": case "POPULATE-FINISH-LINEUP":
			populateLineUp(whatToProcess,print_writer, match, valueToProcess);
			break;
		case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-LINEUP": case "ANIMATE_OUT":
			
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
	public void populateLineUp(String whatToProcess, PrintWriter print_writer,Match match, String valueToProcess) throws InterruptedException
	{
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGameIcon " + icon_path + "ATHLETICS" + AthleticsUtil.PNG_EXTENSION + ";");
		
		switch (whatToProcess.toUpperCase()) {
		case "POPULATE-START-LINEUP": 
			
			for (AthleteList al : match.getAthleteList()) {
				if(al.getAthleteListId() == Integer.valueOf(valueToProcess.split(",")[1])) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " 
						+ al.getHeader().split(",")[3].toUpperCase().replace("RUN", "").trim() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubText01 " 
						+ "START LIST ROUND " + al.getHeader().split(",")[1] + ", HEAT " + al.getHeader().split(",")[2] + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 NAME;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 UNIVERSITY;");
					for (int i=0; i < al.getAthletes().size(); i++) {
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
							+ " " + al.getAthletes().get(i).getStats().toUpperCase() + ";");
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
			
		case "POPULATE-FINISH-LINEUP":
			
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
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPos0" + (i + 1) + " " + (i+1) + ".;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + (i + 1) 
						+ " " + al.getAthletes().get(i).getFullName().toUpperCase() 
						+ " " + al.getAthletes().get(i).getSurname().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUni0" + (i + 1) 
						+ " " + al.getAthletes().get(i).getTeamname().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTime0" + (i + 1) 
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
	public void populateNameSuper(PrintWriter print_writer,String valueToProcess, 
			AthleticsService athleticsService) throws InterruptedException
	{
		String name = "";
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon " + icon_path + valueToProcess.split(",")[2] + AthleticsUtil.PNG_EXTENSION + ";");
		for (NameSuper ns : athleticsService.getNameSupers()) {
			if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
				name = ns.getFirstname().toUpperCase().trim();
				if(!name.trim().isEmpty()) {
					name = name + " " + ns.getSurname().toUpperCase().trim();
				} else {
					name = ns.getSurname().toUpperCase().trim();
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName " + name +";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOtherInfo " + ns.getSubHeader().toUpperCase() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + ns.getSubLine().toUpperCase() + ";");
				break;
			}
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