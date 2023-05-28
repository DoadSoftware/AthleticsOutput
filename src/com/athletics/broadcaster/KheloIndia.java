package com.athletics.broadcaster;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import com.athletics.containers.Scene;
import com.athletics.model.Match;
import com.athletics.model.NameSuper;
import com.athletics.service.AthleticsService;
import com.athletics.util.AthleticsUtil;

public class KheloIndia extends Scene{

	public String session_selected_broadcaster = "KHELO_INDIA";
	
	public String which_graphics_onscreen = "";
	private String status;
	private String scenes_path = "C:\\DOAD_In_House_Everest\\Everest_Sports\\Everest_Khelo_India_2023\\Scenes\\";
	private String icon_path = "D:\\DOAD_In_House_Everest\\Everest_Sports\\Everest_Khelo_India_2023\\Icons\\";
	
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
				
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-L3-NAMESUPER":
			    populateNameSuper(print_writer, valueToProcess, athleticsService, session_selected_broadcaster);
				break;
			}
			
		case "ANIMATE-IN-NAMESUPER": 
			
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-NAMESUPER": 
				processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
				TimeUnit.MILLISECONDS.sleep(200);
				which_graphics_onscreen = "L3-NAMESUPER";
				break;
			}
		}
		return null;
	}
	public void populateNameSuper(PrintWriter print_writer,String valueToProcess, AthleticsService athleticsService, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "KHELO_INDIA":
			
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