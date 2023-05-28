var clock_data;
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
	case 'animate_out_graphic_btn':
		processAthleticsProcedures('ANIMATE_OUT');
		break;
	case 'finish_lineup_graphic_btn':
		processAthleticsProcedures('FINISH_LINEUP_GRAPHICS_OPTIONS');
		break;
	case 'start_lineup_graphic_btn':
		processAthleticsProcedures('START_LINEUP_GRAPHICS_OPTIONS');
		break;
	case 'cancel_graphics_btn':
		$('#select_event_div').empty();
		document.getElementById('select_event_div').style.display = 'none';
		break;
	case 'populate_start_lineup_btn':
		processAthleticsProcedures('POPULATE-START-LINEUP');
		break;
	case 'populate_finish_lineup_btn':
		processAthleticsProcedures('POPULATE-FINISH-LINEUP');
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
	case 'POPULATE-L3-NAMESUPER':
		valueToProcess = 'LT_Medal.sum' + ',' + $('#selectNameSuper option:selected').val() + ',' + $('#selectIcon option:selected').val();
		break;
	case 'POPULATE-START-LINEUP': 
		valueToProcess = 'FF_StartList.sum' + ',' + $('#selectStartingLineUp option:selected').val();
		break;
	case 'POPULATE-FINISH-LINEUP':
		valueToProcess = 'FF_Athletics_Standing.sum' + ',' + $('#selectFinishLineUp option:selected').val();
		break;
	}
	
	$.ajax({    
        type : 'Get',     
        url : 'processAthleticsProcedures.html',     
        data : 'whatToProcess=' + whatToProcess + '&valueToProcess=' + valueToProcess, 
        dataType : 'json',
        success : function(data) {
        	switch(whatToProcess) {
			case 'START_LINEUP_GRAPHICS_OPTIONS': case 'FINISH_LINEUP_GRAPHICS_OPTIONS':
			case 'NAMESUPER_GRAPHICS-OPTIONS': 
				addItemsToList(whatToProcess,data);
				break;
			case 'POPULATE-L3-NAMESUPER': case 'POPULATE-START-LINEUP': case 'POPULATE-FINISH-LINEUP':
				if(confirm('Animate In?') == true){
		        	switch(whatToProcess) {
					case 'POPULATE-L3-NAMESUPER': 
						processAthleticsProcedures('ANIMATE-IN-NAMESUPER');
						break;
					case 'POPULATE-START-LINEUP': case 'POPULATE-FINISH-LINEUP':
						processAthleticsProcedures('ANIMATE-IN-LINEUP');
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
	case 'START_LINEUP_GRAPHICS_OPTIONS': case 'FINISH_LINEUP_GRAPHICS_OPTIONS':

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
		case 'START_LINEUP_GRAPHICS_OPTIONS': 
			select.id = 'selectStartingLineUp';
			break;
		case 'FINISH_LINEUP_GRAPHICS_OPTIONS':
			select.id = 'selectFinishLineUp';
			break;
		}	
		select.name = select.id;
		
		dataToProcess.athleteList.forEach(function(al,index,arr1){
			option = document.createElement('option');
			option.value = al.athleteListId;
			option.text = al.header.split(',')[3];
			select.appendChild(option);
		});
		
		row.insertCell(0).appendChild(select);
		removeSelectDuplicates(select.id);

		option = document.createElement('input');
	    option.type = 'button';
		switch (whatToProcess) {
		case 'START_LINEUP_GRAPHICS_OPTIONS': 
		    option.name = 'populate_start_lineup_btn';
			option.value = 'Populate Starting Line Up';
			break;
		case 'FINISH_LINEUP_GRAPHICS_OPTIONS':
		    option.name = 'populate_finish_lineup_btn';
			option.value = 'Populate Finish Line Up';
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
	    
	    row.insertCell(1).appendChild(div);
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
		
		for(var i=1; i<=3; i++) {
			option = document.createElement('option');
			switch(i){
			case 1:
				option.value = 'gold';
				option.text = 'Gold';
				break;				
			case 2:
				option.value = 'silver';
				option.text = 'Silver';
				break;				
			case 3:
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

