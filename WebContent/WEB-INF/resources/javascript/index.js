var match_data;
function secondsTimeSpanToHMS(s) {
  var h = Math.floor(s / 3600); //Get whole hours
  s -= h * 3600;
  var m = Math.floor(s / 60); //Get remaining minutes
  s -= m * 60;
  return h + ":" + (m < 10 ? '0' + m : m) + ":" + (s < 10 ? '0' + s : s); //zero padding on minutes and seconds
}
function processMatchTime() {
	if(clock_data) {
		if(clock_data.matchTimeStatus.toLowerCase() == 'start' && clock_data.matchTotalSeconds > 0 ) {
			clock_data.matchTotalSeconds = clock_data.matchTotalSeconds - 1;
			processFootballProcedures('LOG_TIME',clock_data.matchTotalSeconds);
		}
		if(document.getElementById('match_time_hdr')) {
			document.getElementById('match_time_hdr').innerHTML = 'MATCH TIME : ' + 
				secondsTimeSpanToHMS(clock_data.matchTotalSeconds);
		}
	}
}
function processWaitingButtonSpinner(whatToProcess) 
{
	switch (whatToProcess) {
	case 'START_WAIT_TIMER': 
		$('.spinner-border').show();
		$(':button').prop('disabled', true);
		break;	case 'END_WAIT_TIMER': 
		$('.spinner-border').hide();
		$(':button').prop('disabled', false);
		break;
	}
}
function afterPageLoad(whichPageHasLoaded)
{
	switch (whichPageHasLoaded) {
	case 'CLOCK':
		break;
	}
}
function initialiseForm(whatToProcess, dataToProcess)
{
	switch (whatToProcess) {
	}
}
function uploadFormDataToSessionObjects(whatToProcess)
{
	var formData = new FormData();
	var url_path;

	$('input, select, textarea').each(
		function(index){  
			if($(this).is("select")) {
				formData.append($(this).attr('id'),$('#' + $(this).attr('id') + ' option:selected').val());  
			} else {
				formData.append($(this).attr('id'),$(this).val());  
			}	
		}
	);
	
	url_path = 'upload_match_setup_data';
	
	$.ajax({    
		headers: {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')},
        url : url_path,     
        data : formData,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',     
        success : function(data) {

        },    
        error : function(e) {    
       	 	console.log('Error occured in uploadFormDataToSessionObjects with error description = ' + e);     
        }    
    });		
	
}
function processUserSelection(whichInput,dataToProcess)
{	
	switch ($(whichInput).attr('name')) {
	case 'selectL3MedalFileOption':
		addItemsToList('LOAD_TRACK_FIELD_PLAYERS',match_data);
		break;
	case 'selectMorningOrEvening':
		if($('#selectMorningOrEvening option:selected').val() == 'Morning') {
			$('#scheduleHeaderTxt').val('ATHLETICS MORNING SCHEDULE'); 
		} else {
			$('#scheduleHeaderTxt').val('ATHLETICS EVENING SCHEDULE'); 
		}
		break;
	case 'animate_out_graphic_btn':
		processAthleticsProcedures('ANIMATE_OUT');
		break;
	case 'bug_sports_type_btn':
		processAthleticsProcedures('BUG_DESCIPLINE_GRAPHICS_OPTIONS');
		break;
	case 'l3_medal_track_graphic_btn':
		processAthleticsProcedures('L3_MEDAL_TRACK_GRAPHICS_OPTIONS');
		break;
	case 'l3_medal_field_graphic_btn':
		processAthleticsProcedures('L3_MEDAL_FIELD_GRAPHICS_OPTIONS');
		break;
	case 'populate_bug_descipline_btn':
		processAthleticsProcedures('POPULATE-BUG-DESCIPLINE');
		break;
	case 'populate_l3_medal_track_btn':
		processAthleticsProcedures('POPULATE-L3-MEDAL-TRACK');
		break;
	case 'populate_l3_medal_field_btn':
		processAthleticsProcedures('POPULATE-L3-MEDAL-FIELD');
		break;
	case 'populate_schedule_btn':
		processAthleticsProcedures('POPULATE-SCHEDULE');
		break;
	case 'schedule_graphic_btn':
		processAthleticsProcedures('SCHEDULE_GRAPHICS_OPTIONS');
		break;
	case 'start_list_field_graphic_btn':
		processAthleticsProcedures('START_LIST_FIELD_GRAPHICS_OPTIONS');
		break;
	case 'finish_list_track_graphic_btn':
		processAthleticsProcedures('FINISH_LIST_TRACK_GRAPHICS_OPTIONS');
		break;
	case 'start_list_track_graphic_btn':
		processAthleticsProcedures('START_LIST_TRACK_GRAPHICS_OPTIONS');
		break;
	case 'cancel_graphics_btn':
		$('#select_event_div').empty();
		document.getElementById('select_event_div').style.display = 'none';
		break;
	case 'populate_start_list_field_btn':
		processAthleticsProcedures('POPULATE-START-LIST-FIELD');
		break;
	case 'populate_start_list_track_btn':
		processAthleticsProcedures('POPULATE-START-LIST-TRACK');
		break;
	case 'populate_finish_list_track_btn':
		processAthleticsProcedures('POPULATE-FINISH-LIST-TRACK');
		break;
	case 'populate_namesuper_btn':
		processAthleticsProcedures('POPULATE-L3-NAMESUPER');
		break;
	case 'namesuper_graphic_btn':
		processAthleticsProcedures('NAMESUPER_GRAPHICS-OPTIONS');
		break;
	case 'load_scene_btn':
	  	document.initialise_form.submit();
		break;
	case 'selectedBroadcaster':
		switch ($('#selectedBroadcaster :selected').val()) {
		case 'athletics': 
			$('#vizPortNumber').attr('value','1980');
			$('#vizIPAddress').attr('value','localhost');
			$('label[for=vizScene], input#vizScene').hide();
			$('label[for=which_scene], select#which_scene').hide();
			$('label[for=which_layer], select#which_layer').hide();
			break;
		}
		break;
	}
	
}
function processAthleticsProcedures(whatToProcess, whichInput)
{
	var valueToProcess; 
	
	switch(whatToProcess) {
	case 'POPULATE-BUG-DESCIPLINE':
		valueToProcess = 'Bug_OneLine.sum' + ',' + $('#selectBugDescipline option:selected').val();
		break;
	case 'POPULATE-SCHEDULE':
		valueToProcess = 'FF_Athletics_Standing.sum' + ',' + $('#selectScheduleStartRecord option:selected').val() 
			+ ',' + $('#selectScheduleEndRecord option:selected').val() + ',' + $('#scheduleHeaderTxt').val()
			+ ',' + $('#scheduleSubHeaderTxt').val();
		break;
	case 'POPULATE-L3-NAMESUPER':
		valueToProcess = 'LT.sum' + ',' + $('#selectNameSuper option:selected').val() 
			+ ',' + $('#selectIcon option:selected').val();
		break;
	case 'POPULATE-L3-MEDAL-TRACK': case 'POPULATE-L3-MEDAL-FIELD': 
		valueToProcess = 'LT.sum' + ',' + $('#selectL3MedalFileOption option:selected').val() 
			+ ',' + $('#selectL3MedalPlayer option:selected').val() 
			+ ',' + $('#selectL3MedalIcon option:selected').val();
		break;
	case 'POPULATE-START-LIST-FIELD':
		valueToProcess = 'FF_StartList.sum' + ',' + $('#selectStartingListField option:selected').val();
		break;
	case 'POPULATE-START-LIST-TRACK': 
		valueToProcess = 'FF_StartList.sum' + ',' + $('#selectStartingListTrack option:selected').val();
		break;
	case 'POPULATE-FINISH-LIST-TRACK':
		valueToProcess = 'FF_Athletics_Standing.sum' + ',' + $('#selectFinishListTrack option:selected').val();
		break;
	}
	
	$.ajax({    
        type : 'Get',     
        url : 'processAthleticsProcedures.html',     
        data : 'whatToProcess=' + whatToProcess + '&valueToProcess=' + valueToProcess, 
        dataType : 'json',
        success : function(data) {
        	switch(whatToProcess) {
			case 'START_LIST_TRACK_GRAPHICS_OPTIONS': case 'FINISH_LIST_TRACK_GRAPHICS_OPTIONS':
			case 'NAMESUPER_GRAPHICS-OPTIONS': case 'SCHEDULE_GRAPHICS_OPTIONS': case 'START_LIST_FIELD_GRAPHICS_OPTIONS':
			case 'L3_MEDAL_TRACK_GRAPHICS_OPTIONS': case 'L3_MEDAL_FIELD_GRAPHICS_OPTIONS':
			case 'BUG_DESCIPLINE_GRAPHICS_OPTIONS':
				addItemsToList(whatToProcess,data);
				match_data = data;
				break;
			case 'POPULATE-L3-NAMESUPER': case 'POPULATE-START-LIST-TRACK': case 'POPULATE-FINISH-LIST-TRACK':
			case 'POPULATE-SCHEDULE': case 'POPULATE-START-LIST-FIELD': case 'POPULATE-L3-MEDAL-TRACK': 
			case 'POPULATE-L3-MEDAL-FIELD': case 'POPULATE-BUG-DESCIPLINE':
				switch(whatToProcess) {
				case 'POPULATE-FINISH-LIST-TRACK':
					if(data.status == 'ERROR') {
						alert('Finish result NOT found');
						return false;
					}
					break;
				}
				if(confirm('Animate In?') == true){
		        	switch(whatToProcess) {
					case 'POPULATE-L3-NAMESUPER': case 'POPULATE-L3-MEDAL-TRACK': case 'POPULATE-L3-MEDAL-FIELD':
						processAthleticsProcedures('ANIMATE-IN-NAMESUPER');
						break;
					case 'POPULATE-SCHEDULE': case 'POPULATE-START-LIST-TRACK': case 'POPULATE-FINISH-LIST-TRACK':
					case 'POPULATE-START-LIST-FIELD':
						processAthleticsProcedures('ANIMATE-IN-LINEUP');
						break;
					case 'POPULATE-BUG-DESCIPLINE':
						processAthleticsProcedures('ANIMATE-IN-BUG');
						break;
					}
				}
				break;
        	}
    		processWaitingButtonSpinner('END_WAIT_TIMER');
	    },    
	    error : function(e) {    
	  	 	console.log('Error occured in ' + whatToProcess + ' with error description = ' + e);     
	    }    
	});
}
function addItemsToList(whatToProcess, dataToProcess)
{
	var div,row,cell,header_text,select,option,tr,th,thead,text,table,tbody;
	
	switch (whatToProcess) {
	case 'SCHEDULE_GRAPHICS_OPTIONS':
		
		$('#select_event_div').empty();

		header_text = document.createElement('h6');
		header_text.innerHTML = 'Select Graphic Options';
		document.getElementById('select_event_div').appendChild(header_text);
		
		table = document.createElement('table');
		table.setAttribute('class', 'table table-bordered');
				
		tbody = document.createElement('tbody');

		table.appendChild(tbody);
		document.getElementById('select_event_div').appendChild(table);
		
		row = tbody.insertRow(tbody.rows.length);
		
		select = document.createElement('select');
		select.id = 'selectScheduleStartRecord';
		select.name = select.id;
		
		dataToProcess.schedules.forEach(function(sc,index,arr1){
			option = document.createElement('option');
			option.value = sc.scheduleId;
			option.text = (index + 1) + '. ' + sc.scheduleDate + ' ' + sc.scheduleTime + ' ' + sc.eventName;
			select.appendChild(option);
		});
		row.insertCell(0).appendChild(select);

		select = document.createElement('select');
		select.id = 'selectScheduleEndRecord';
		select.name = select.id;
		
		dataToProcess.schedules.forEach(function(sc,index,arr1){
			option = document.createElement('option');
			option.value = sc.scheduleId;
			option.text = (index + 1) + '. ' + sc.scheduleDate + ' ' + sc.scheduleTime + ' ' + sc.eventName;
			select.appendChild(option);
		});
		select.selectedIndex = 9;
		row.insertCell(1).appendChild(select);

		table = document.createElement('table');
		table.setAttribute('class', 'table table-bordered');
				
		tbody = document.createElement('tbody');

		table.appendChild(tbody);
		document.getElementById('select_event_div').appendChild(table);

		row = tbody.insertRow(tbody.rows.length);
	    div = document.createElement('div');

		text = document.createElement('input');
		text.type = 'text';
		text.id = 'scheduleHeaderTxt';
		text.name = text.id;
		text.value = 'ATHLETICS MORNING SCHEDULE';
		
		header_text = document.createElement('label');
		header_text.htmlFor = text.id;
		header_text.innerHTML = "Header Text";
		div.append(header_text);
		div.append(text);

		row.insertCell(0).appendChild(div);
		
		div = document.createElement('div');
		
		select = document.createElement('select');
		select.id = 'selectMorningOrEvening';
		select.name = select.id;
	    select.setAttribute('onchange',"processUserSelection(this)");
		
		option = document.createElement('option');
		option.value = 'Morning';
		option.text = 'Morning';
		select.appendChild(option);	
			
		option = document.createElement('option');
		option.value = 'Evening';
		option.text = 'Evening';
		select.appendChild(option);		

		header_text = document.createElement('label');
		header_text.htmlFor = select.id;
		header_text.innerHTML = "Select Morning/Evening";

		div.append(header_text);
		div.append(select);
				
		row.insertCell(1).appendChild(div);
		
		$('#' + text.id).attr('size', '50')
		
		div = document.createElement('div');
		row = tbody.insertRow(tbody.rows.length);
		
		text = document.createElement('input');
		text.type = 'text';
		text.id = 'scheduleSubHeaderTxt';
		text.name = text.id;
		text.value = 'FROM GURU GOBIND SINGH SPORTS COLLEGE ATHLETICS GROUND LUCKNOW';
		
		header_text = document.createElement('label');
		header_text.htmlFor = text.id;
		header_text.innerHTML = "Subheader Text";
		div.append(header_text);
		div.append(text);

		row.insertCell(0).appendChild(div);
		$('#' + text.id).attr('size', '80')

		row = tbody.insertRow(tbody.rows.length);

		option = document.createElement('input');
	    option.type = 'button';
	    option.name = 'populate_schedule_btn';
		option.value = 'Populate Schedule';
	    option.id = option.name;
	    option.setAttribute('onclick',"processUserSelection(this)");
	    
	    div = document.createElement('div');
	    div.append(option);

		option = document.createElement('input');
		option.type = 'button';
		option.name = 'cancel_graphics_btn';
		option.id = option.name;
		option.value = 'Cancel';
		option.setAttribute('onclick','processUserSelection(this)');

	    div.append(option);
	    
	    row.insertCell(0).appendChild(div);
		document.getElementById('select_event_div').style.display = '';
		
		break;
		
	case 'LOAD_TRACK_FIELD_PLAYERS':

		$('#selectL3MedalPlayer').empty();

		select = document.getElementById('selectL3MedalPlayer');

		dataToProcess.athleteList.forEach(function(al,index,arr1){
			if(al.athleteListId == $('#selectL3MedalFileOption option:selected').val()) {
				al.athletes.forEach(function(plyr,index,arr1){
					option = document.createElement('option');
					option.value = plyr.athleteId;
					option.text = plyr.fullName;
					select.appendChild(option);
				});
			}
		});
		
		break;
		
	case 'START_LIST_TRACK_GRAPHICS_OPTIONS': case 'FINISH_LIST_TRACK_GRAPHICS_OPTIONS': case 'START_LIST_FIELD_GRAPHICS_OPTIONS':
	case 'L3_MEDAL_TRACK_GRAPHICS_OPTIONS': case 'L3_MEDAL_FIELD_GRAPHICS_OPTIONS': case 'BUG_DESCIPLINE_GRAPHICS_OPTIONS':

		$('#select_event_div').empty();

		header_text = document.createElement('h6');
		header_text.innerHTML = 'Select Graphic Options';
		document.getElementById('select_event_div').appendChild(header_text);
		
		table = document.createElement('table');
		table.setAttribute('class', 'table table-bordered');
				
		tbody = document.createElement('tbody');

		table.appendChild(tbody);
		document.getElementById('select_event_div').appendChild(table);
		
		row = tbody.insertRow(tbody.rows.length);
		
		select = document.createElement('select');
		switch (whatToProcess) {
		case 'BUG_DESCIPLINE_GRAPHICS_OPTIONS':
			select.id = 'selectBugDescipline';
			break;
		case 'L3_MEDAL_TRACK_GRAPHICS_OPTIONS': case 'L3_MEDAL_FIELD_GRAPHICS_OPTIONS':
			select.id = 'selectL3MedalFileOption';
			select.setAttribute('onchange',"processUserSelection(this)");
			break;
		case 'START_LIST_FIELD_GRAPHICS_OPTIONS':
			select.id = 'selectStartingListField';
			break;
		case 'START_LIST_TRACK_GRAPHICS_OPTIONS': 
			select.id = 'selectStartingListTrack';
			break;
		case 'FINISH_LIST_TRACK_GRAPHICS_OPTIONS':
			select.id = 'selectFinishListTrack';
			break;
		}	
		select.name = select.id;
		
		if(dataToProcess.athleteList) {
			dataToProcess.athleteList.forEach(function(al,index,arr1){
				option = document.createElement('option');
				option.value = al.athleteListId;
				option.text = al.header.split(',')[0] + '.' + al.header.split(',')[1] + '.' + al.header.split(',')[2] + '. ' + al.header.split(',')[3];
				select.appendChild(option);
			});
		}
		
		row.insertCell(0).appendChild(select);
		switch (whatToProcess) {
		case 'L3_MEDAL_TRACK_GRAPHICS_OPTIONS': case 'L3_MEDAL_FIELD_GRAPHICS_OPTIONS':

			header_text = document.createElement('h6');
			header_text.innerHTML = 'Select Player';
			select = document.createElement('select');
			select.id = 'selectL3MedalPlayer';

			row.insertCell(1).appendChild(header_text).appendChild(select);

			header_text = document.createElement('h6');
			header_text.innerHTML = 'Select Medal';
			select = document.createElement('select');
			select.id = 'selectL3MedalIcon';

			option = document.createElement('option');
			option.value = 'none';
			option.text = 'None';
			select.appendChild(option);

			option = document.createElement('option');
			option.value = 'gold';
			option.text = 'Gold';
			select.appendChild(option);

			option = document.createElement('option');
			option.value = 'silver';
			option.text = 'Silver';
			select.appendChild(option);

			option = document.createElement('option');
			option.value = 'bronze';
			option.text = 'Bronze';
			select.appendChild(option);

			row.insertCell(2).appendChild(header_text).appendChild(select);

			break;
		}	

		option = document.createElement('input');
	    option.type = 'button';
		switch (whatToProcess) {
		case 'BUG_DESCIPLINE_GRAPHICS_OPTIONS':
		    option.name = 'populate_bug_descipline_btn';
			option.value = 'Populate Bug Descipline';
			break;
		case 'L3_MEDAL_TRACK_GRAPHICS_OPTIONS': 
		    option.name = 'populate_l3_medal_track_btn';
			option.value = 'Populate L3 Medal Track';
			break;
		case 'L3_MEDAL_FIELD_GRAPHICS_OPTIONS':
		    option.name = 'populate_l3_medal_field_btn';
			option.value = 'Populate L3 Medal Field';
			break;
		case 'START_LIST_FIELD_GRAPHICS_OPTIONS':
		    option.name = 'populate_start_list_field_btn';
			option.value = 'Populate Starting Field';
			break;
		case 'START_LIST_TRACK_GRAPHICS_OPTIONS': 
		    option.name = 'populate_start_list_track_btn';
			option.value = 'Populate Starting Track';
			break;
		case 'FINISH_LIST_TRACK_GRAPHICS_OPTIONS':
		    option.name = 'populate_finish_list_track_btn';
			option.value = 'Populate Finish Track';
			break;
		}	
		
	    option.id = option.name;
	    option.setAttribute('onclick',"processUserSelection(this)");
	    
	    div = document.createElement('div');
	    div.append(option);

		option = document.createElement('input');
		option.type = 'button';
		option.name = 'cancel_graphics_btn';
		option.id = option.name;
		option.value = 'Cancel';
		option.setAttribute('onclick','processUserSelection(this)');

	    div.append(option);
	    
		switch (whatToProcess) {
		case 'L3_MEDAL_TRACK_GRAPHICS_OPTIONS': case 'L3_MEDAL_FIELD_GRAPHICS_OPTIONS':
		    row.insertCell(3).appendChild(div);
			break;
		default:
		    row.insertCell(1).appendChild(div);
			break;
		}
		document.getElementById('select_event_div').style.display = '';
		
		break;
		
	case 'NAMESUPER_GRAPHICS-OPTIONS':

		$('#select_event_div').empty();

		header_text = document.createElement('h6');
		header_text.innerHTML = 'Select Graphic Options';
		document.getElementById('select_event_div').appendChild(header_text);
		
		table = document.createElement('table');
		table.setAttribute('class', 'table table-bordered');
				
		tbody = document.createElement('tbody');

		table.appendChild(tbody);
		document.getElementById('select_event_div').appendChild(table);
		
		row = tbody.insertRow(tbody.rows.length);
		
		select = document.createElement('select');
		select.id = 'selectNameSuper';
		select.name = select.id;
		
		dataToProcess.nameSuper.forEach(function(ns,index,arr1){
			option = document.createElement('option');
			option.value = ns.namesuperId;
			option.text = ns.prompt;
			select.appendChild(option);
		});
		
		row.insertCell(0).appendChild(select);

		header_text = document.createElement('h6');
		header_text.innerHTML = 'Select Icon';
		
		select = document.createElement('select');
		select.id = 'selectIcon';
		select.name = select.id;
		
		for(var i=1; i<=4; i++) {
			option = document.createElement('option');
			switch(i){
			case 1:
				option.value = '';
				option.text = '';
				break;				
			case 2:
				option.value = 'gold';
				option.text = 'Gold';
				break;				
			case 3:
				option.value = 'silver';
				option.text = 'Silver';
				break;				
			case 4:
				option.value = 'bronze';
				option.text = 'Bronze';
				break;				
			}
			select.appendChild(option);
		}		
		
		row.insertCell(1).appendChild(header_text).appendChild(select);

		option = document.createElement('input');
	    option.type = 'button';
	    option.name = 'populate_namesuper_btn';
		option.value = 'Populate Name Super';
		
	    option.id = option.name;
	    option.setAttribute('onclick',"processUserSelection(this)");
	    
	    div = document.createElement('div');
	    div.append(option);

		option = document.createElement('input');
		option.type = 'button';
		option.name = 'cancel_graphics_btn';
		option.id = option.name;
		option.value = 'Cancel';
		option.setAttribute('onclick','processUserSelection(this)');

	    div.append(option);
	    
	    row.insertCell(2).appendChild(div);
		document.getElementById('select_event_div').style.display = '';
		
		break;
	}
}
function removeSelectDuplicates(select_id)
{
	var this_list = {};
	$("select[id='" + select_id + "'] > option").each(function () {
	    if(this_list[this.text]) {
	        $(this).remove();
	    } else {
	        this_list[this.text] = this.value;
	    }
	});
}
function checkEmpty(inputBox,textToShow) {

	var name = $(inputBox).attr('id');
	
	document.getElementById(name + '-validation').innerHTML = '';
	document.getElementById(name + '-validation').style.display = 'none';
	$(inputBox).css('border','');
	if(document.getElementById(name).value.trim() == '') {
		$(inputBox).css('border','#E11E26 2px solid');
		document.getElementById(name + '-validation').innerHTML = textToShow + ' required';
		document.getElementById(name + '-validation').style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}

