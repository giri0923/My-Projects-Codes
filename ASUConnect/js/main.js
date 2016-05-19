$(document).ready(function(){

	addTimePicker();
	addDatePicker();

	/* setup sessions click */
	$('#setupSessions').click(function(){
		var conf = {
			onElementValidate : function(valid, $el, $form, errorMess) {
				if(!valid) {
					var element = $el;
					if(element[0].id == "eventImgBase64"){
						$('#eventImgBanner').addClass('imgInvalid');
					}
					else {
						$el.addClass('error');
					}
					errors.push({el: $el, error: errorMess});
				}
			}
		}
		var lang = {};
		var errors = [];
		if( !$('#eventForm').isValid(lang, conf, false) ) {
			//form invalid - show error msg
			showErrorMsg(".right", "All fields are mandatory. Please fill all the fields to proceed.");
		} 
		else{
			// form valid - proceed with the flow 
			addEvent();
			//addDatePicker();
			addTimePicker();
			$('.fx.step2').hide();
			$('#progressbar .item1').addClass('active');
			$('#progressbar .item2').addClass('current');
			$('.steps.step1').hide(1000);
			$('.steps.step2').show(1000);
			$('.fx.skip-sessions').css('display','inline-flex');
		}	
	});

	/* save and continue later click */
	$('#savencontinue').click(function(){
		var conf = {
			onElementValidate : function(valid, $el, $form, errorMess) {
				if(!valid) {
					var element = $el;
					if(element[0].id == "eventImgBase64"){
						$('#eventImgBanner').addClass('imgInvalid');
					}
					else {
						$el.addClass('error');
					}
					errors.push({el: $el, error: errorMess});
				}
			}
		}
		var lang = {};
		var errors = [];
		if( !$('#eventForm').isValid(lang, conf, false) ) {
			//form invalid - show error msg
			showErrorMsg(".right", "All fields are mandatory. Please fill all the fields to proceed.");
		} 
		else{
			// form valid - proceed with the flow 
			addEvent();
			redirectPage("/IgniteAdmin/design/home.html");
		}		
	});

	/* edit sessions click */
	$('#editSessions').click(function(){
		var conf = {
			onElementValidate : function(valid, $el, $form, errorMess) {
				if(!valid) {
					var element = $el;
					if(element[0].id == "eventImgBase64"){
						$('#eventImgBanner').addClass('imgInvalid');
					}
					else {
						$el.addClass('error');
					}
					errors.push({el: $el, error: errorMess});
				}
			}
		}
		var lang = {};
		var errors = [];
		if( !$('#eventForm').isValid(lang, conf, false) ) {
			//form invalid - show error msg
			showErrorMsg(".right", "All fields are mandatory. Please fill all the fields to proceed.");
		} 
		else{
			// form valid - proceed with the flow 
			addEvent(localStorage.getItem('editEventId'), false);
			//addDatePicker();
			addTimePicker();

			$('.fx.step2').hide();
			$('#progressbar .item1').addClass('active');
			$('#progressbar .item2').addClass('current');
			$('.steps.step1').hide(1000);
			$('.steps.step2').show(1000);
			$('.fx.skip-sessions').css('display','inline-flex');
		}	
	});

	/* skip setup sessions click */
	$('#skipSessionsSetup').click(function(){
		console.log('called skip sessions setup');
		loadSponsorsTable();
		$('.fx.step3').hide();
		$('.steps.step2').hide(1000);
		$('.steps.step3').show(1000);
		$('#progressbar .item3').addClass('current');
		$('#progressbar .item2').addClass('active');
		$('.fx.skip-sessions').hide();
		$('.fx.skip-sponsors').css('display','inline-flex');
	});

	/* skip update sessions click */
	$('#skipSessionsEdit').click(function(){
		console.log('called skip sessions edit');
		loadSponsorsTable();
		$('.fx.step3').hide();
		$('.steps.step2').hide(1000);
		$('.steps.step3').show(1000);
		$('#progressbar .item3').addClass('current');
		$('#progressbar .item2').addClass('active');
		$('.fx.skip-sessions').hide();
		$('.fx.skip-sponsors').css('display','inline-flex');
	});

	/* setup partners click */
	$('#setupPartners').click(function(){
		console.log('called setup partners');
		loadSponsorsTable();
		$('.fx.step3').hide();
		$('.steps.step2').hide(1000);
		$('.steps.step3').show(1000);
		$('#progressbar .item3').addClass('current');
		$('.fx.skip-sponsors').css('display','inline-flex');
	});

	/* update partners click */
	$('#updatePartners').click(function(){
		console.log('called edit partners');
		loadSponsorsTable();
		$('.fx.step3').hide();
		$('.steps.step2').hide(1000);
		$('.steps.step3').show(1000);
		$('#progressbar .item3').addClass('current');
		$('.fx.skip-sponsors').css('display','inline-flex');
	});

	/* skip setup partners click */
	$('#skipSetupPartners').click(function(){
		console.log('called skipSetupPartners');
		$('.fx.skip-sponsors').hide();
		$('.fx.submit').css('display','inline-flex');
		$('#progressbar .item3').addClass('active');
	});

	/* skip edit partners click */
	$('#skipEditPartners').click(function(){
		console.log('called skipEditPartners');
		$('.fx.skip-sponsors').hide();
		$('.fx.submit').css('display','inline-flex');
		$('#progressbar .item3').addClass('active');
	});

	/* create event click */
	$('#submitMasterEvent').click(function(){
		console.log("create event submit");
		addEvent(localStorage.getItem("curEventId"),false);
		redirectPage("/IgniteAdmin/design/home.html");
	});

	/* update event click */
	$('.fx.submit #editMasterEvent').click(function(){
		console.log("edit event submit");
		addEvent(localStorage.getItem("editEventId"), false);
		redirectPage("/IgniteAdmin/design/home.html");
	});

	$("#eventImg").change(function(){
	    readImage(this);
	}); 

	$("#dialog").dialog({
		autoOpen: false,
		modal: true,
		width: 800,
		height: 500,
		dialogClass: "dlg-no-close",
		buttons: {
			"Cancel": function() {
				$(this).dialog("close");
			},

			"Save": function() {
				$(this).dialog("close");
			}
		},
		create:function () {
			console.log('create called');
			$(this).closest(".ui-dialog").find(".ui-button").removeClass();
	        $(".ui-dialog-buttonset button:first").addClass("cancel-speaker-dlgbtn dlgbtn btn btn-small btn-black");
	        $(".ui-dialog-buttonset button").eq(1).addClass("save-speaker-dlgbtn dlgbtn btn btn-small btn-light-blue");
	    }
	});
	
});  /* end of document.ready */

function addTimePicker(){
	$('.time').each(function(){
		$(this).timepicker({ 
			'scrollDefault': 'now',
			'showDuration': true,
			'timeFormat': 'h:i A',
		});
	});
}

function addDatePicker(){
	$('.date').each(function(){
		$(this).datepicker({ minDate: 0, maxDate: "+2M" });
	});
}

function constructMultiSelectMarkupForSpeakers(speakers,targetNode){
	var multiselectMarkup = "";
	console.log('speakers = %o',speakers);
	console.log('targetNode = %o',targetNode);
	$('.fx.error').hide();
	for(i in speakers){
		var speakerName = speakers[i].firstName+" "+speakers[i].lastName;
		multiselectMarkup += "<option value='"+speakers[i].speakerId+"'>"+speakerName+"</option>";
	}
	if(targetNode == "#undo_redo" && multiselectMarkup == ""){
		if($('#speakersList').val()!=""){
			console.log('if : left side is empty');
			$('.fx.error').show();
			$('.fx.error span').html("You have already selected all speakers in the system");
		}else{
			console.log('if : both sides are empty');
			$('.col').hide();
			$('.fx.error').show();
			$('.fx.error span').html("No speakers in the system");
		}
	}else{
		console.log('else : markup loaded');
		$(targetNode).html(multiselectMarkup);
	}
}

function registerChooseSpeakersLink(){
	console.log("registerChooseSpeakersLink");
	$('#choose-speakers-link').click(function(){
		console.log('choose speakers clicked');
		$('#dialog').html("");
		$("#dialog").dialog("option", "title", "Loading...").parent().css({position:"fixed"}).end().dialog("open");
		$('#dialog').load("partial/multiselect.html", function() {
			// Construct dialog markup
			var allspeakers = getAllSpeakers();
			var selectedSpeakers = $('#speakersList').val();
			var speakersList_hidden = JSON.parse("["+selectedSpeakers+"]"); 
			console.log("hidden field values speakerlist %o",speakersList_hidden);
			var speakers_left = [];
			var speakers_right = [];
			if(speakersList_hidden!=""){
				for(i in allspeakers){
					if(speakersList_hidden.indexOf(parseInt(allspeakers[i].speakerId))==-1){
						speakers_left.push(allspeakers[i]);
					}else{
						speakers_right.push(allspeakers[i]);
					}
				}
				constructMultiSelectMarkupForSpeakers(speakers_left, "#undo_redo");
				constructMultiSelectMarkupForSpeakers(speakers_right, "#undo_redo_to");
			
			} else{
				constructMultiSelectMarkupForSpeakers(allspeakers, "#undo_redo");
			}
			
			// Activate multiselect plugin
			$('#undo_redo').multiselect();

			// Save the selected speakers from this dialog
			$('.save-speaker-dlgbtn').click(function(){
				var selectedSpeakers = [];
				console.log('save dialog button clicked');
				$('#undo_redo_to option').each(function(){
					selectedSpeakers.push($(this).val());
				});
				$('#speakersList').val(selectedSpeakers.toString());
				$('.session-speaker-container #successPlaceholder').show();
				$('.session-speaker-container #successPlaceholder .successText').html("You have added speakers for your session");
				// reload speakers table
				loadSpeakersTable();
			});
		});
	});
}

function populateEventsData(){
	var basePath = "/event-data/event/event-nodes";
	var events_nodes = Sling.getContent(basePath, 3);
	var events = [];
	for(var i in events_nodes) {
		if(i == undefined) break;
		var eventArr = [];
		var eventskey = i.split('-')[1];
	    var curEvent = events_nodes[i][eventskey];
	    var eventJson = escape(JSON.stringify(curEvent));
	    var eventStr = '<div class="left pr20"><img width="300" height="150" src="' + curEvent.imgBase64 + '"></div>'+
	    			'<div class="right action-links">'+
					'<div class="right">'+
					'<a class="link clblue" onclick="editEvent('+ eventskey +')" id="cancelDeleteSessionBtn"><i class="fa fa-pencil pr5"></i></a>'+
					'</div>'+
					'<div class="left pr20">'+
					'<a class="link clblue" onclick="deleteEvent('+ eventskey +')" id="deleteSessionBtn"><i class="fa fa-trash pr5"></i></a>'+
					'</div></div>'+
                    '<ul>'+ 
                    '<li class="sessionName">'+ curEvent.shortTitle +'</li>'+
                    '<li class="sessionVenue">' + curEvent.addressText + '</li>'+
                    '<li id=eventState_'+ eventskey +' class="sessionVenue">' + curEvent.eventState + '</li>'+
                    '<li class="sessionVenue">' + curEvent.startDate + ' - '+ curEvent.endDate + '</li>'+
                    '</ul>'+
                    "<div class='right mt50'><a class='btn btn-light-blue' onclick=viewEvent("+ eventskey +")><i class='fa fa-send-o pr5'></i>Publish</a></div>";
	    /*
	    eventArr.push(curEvent.shortTitle);
	    eventArr.push(curEvent.eventState);
		eventArr.push(curEvent.startDate);*/
		eventArr.push(eventskey);
		eventArr.push(eventStr);
		events.push(eventArr);
	}
	return events;     
}

function loadEventsTable(){
	$('#eventsTableWrapper').empty()
	$('#eventsTableWrapper').append("<table class=\"display\" width=\"100%\" id=\"eventsDTable\"></table>");
	$('#eventsDTable').DataTable({
    	"bLengthChange": false,
    	"pageLength": 5,
        data: populateEventsData(),
        columns: [
        	{ title : "EventId"},
        	{ title: "Events" }
        ],
        "columnDefs": [
	        {
		        "targets": [ 0 ],
		        "visible": false,
		        "searchable": false
	    	},
	        { 
		        "targets": [ 1 ],
		        "render": function ( data, type, row ) {
		    	    return data;
		    	}
            }
        ]
    });
   customizeDTableSearch('eventsDTable', 'Search Events');
}

function viewEvent(eventId){
	localStorage.setItem('publishEventId', eventId);
	localStorage.setItem('editEventId', eventId);
	//var status = $('#'+eventId).parent().parent()[0].childNodes[1].innerHTML;
	var status = $('#eventState_'+eventId).html();
	console.log('status = '+status);
	if(status == "Ready to publish" ){
		$('#publishEventDialog').parent().css({position:"fixed"}).end().dialog('open');
	} else if(status == "Published"){
		$('#errorPlaceholder .errorText').html("You have already published this event. Update the event to publish again.");
		$('#errorPlaceholder').show();
	} else{
		$('#errorPlaceholder .errorText').html("You can't publish the event in draft state");
		$('#errorPlaceholder').show();
	}
}

function publishEventAction(){
	$('#publishEventDialog .fx.submit').hide(1000);
	$('#publishEventDialog .section-title .msg').html("Publishing Your Event <i class='fa fa-send-o pl10'></i>");
	$('#publishEventDialog .warningText').html("<i class='fa fa-warning pr5'></i>This operation might take few minutes");
	$("#upload-progress-container").show();
	publishEvent(localStorage.getItem('publishEventId'));
}

function publishDone(){
	// Show msg on home page
	$('#publishEventDialog').dialog('close');
	$('#successPlaceholder .successText').html("Your event has been published. In case of any failure or other data issues, edit and publish the event again.");
	$('#successPlaceholder').show();
}

function cancelPublishEvent(){
	$('#publishEventDialog').dialog('close');
}

function publishEvent(eventId){
	publishEventDetails(eventId);
	publishSessions(eventId);
	publishPartners(eventId);
	publishSpeakers(eventId);
	publishSessionCategories();
	publishAllImages();
	loadEditEventFormData(true);
	addEvent(eventId, true);
}

function editEvent(eventId){
	localStorage.setItem("editEventId", eventId);
	redirectPage('/IgniteAdmin/design/edit-event.html');
}

function deleteEvent(eventId){
	localStorage.setItem("deleteEventId", eventId);
	$('#deleteEventDialog').parent().css({position:"fixed"}).end().dialog('open');
}

function deleteEventAction(){
	var deleteEventId = localStorage.getItem('deleteEventId');
	var deleteEventsUrl = "/event-data/event/event-nodes/event-"+deleteEventId;
	var delete_eventsform = $('#delete_eventsform')[0];
	delete_eventsform.action =  deleteEventsUrl;
	delete_eventsform.submit();
	$('#deleteEventDialog').dialog('close');
	// Show msg on home page
	showSuccessMsg(".right", "Event has been deleted.");
	// Refresh events data table
	setTimeout(function(){
	 	loadEventsTable();
	}, 1000);
}

function cancelDeleteEvent(){
	$('#deleteEventDialog').dialog('close');
}

function populateSessionsData(){
	var curEventId = localStorage.getItem("curEventId");
	var basePath = "/event-data/sessions/session-nodes";
    var session_nodes = Sling.getContent(basePath, 3);
    var sessions = [];
	var sessionIds = [];
	for(var i in session_nodes) {
    	if(i == undefined) break;
    	var sessionArr = [];
    	var sessionkey = i.split('-')[1];
	    var session = session_nodes[i][sessionkey];
	    if(session.eventId == curEventId){
	    	var sessionStr = '<div class="left pr20"><img width="350" height="150" src="' + session.imgBase64 + '"></div>'+
	    			'<div class="right action-links">'+
					'<div class="right">'+
					'<a class="link clblue" onclick="editSession('+ sessionkey +')" id="cancelDeleteSessionBtn"><i class="fa fa-pencil pr5"></i></a>'+
					'</div>'+
					'<div class="left pr20">'+
					'<a class="link clblue" onclick="deleteSession('+ sessionkey +')" id="deleteSessionBtn"><i class="fa fa-trash pr5"></i></a>'+
					'</div></div>'+
                    '<ul>'+ 
                    '<li class="sessionName">'+ session.shortTitle +'</li>'+
                    '<li class="sessionVenue">' + session.addressText + ' ' + session.hall + '</li>'+
                    '<li class="sessionVenue">' + session.startDate + ', ' + session.startTime + ' - '+ session.endTime + '</li>'+
                    '</ul>';
	    	//sessionArr.push(sessionkey);
	    	//sessionArr.push(session.shortTitle);
	    	//sessionArr.push(session.startDate);
	    	//sessionArr.push(session.hall)
	    	//sessionArr.push(sessionkey);
	    	sessionArr.push(sessionStr);
	    	sessions.push(sessionArr);
	    	//update sessions array of event form
	    	if(sessionIds.indexOf(parseInt(sessionkey)) == -1){	
				sessionIds.push(sessionkey);	
			}
	    }
    }
    $('#sessionsList').val(sessionIds.toString());
    return sessions;		       
}

/* function loadSessionsTable(){
	$('#sessionsTableWrapper').empty()
	$('#sessionsTableWrapper').append("<table class=\"display\" width=\"100%\" id=\"sessionsDTable\"></table>");		
	$('#sessionsDTable').DataTable( {
    	"bLengthChange": false,
        data: populateSessionsData(),
        columns: [
        	{ title: "SessionId" },
            { title: "Title" },
            { title: "Date" },
            { title: "Hall" },
            {title : "Actions"}
        ],
        "columnDefs": [
            {
                "targets": [ 0 ],
                "visible": false,
                "searchable": false
            },
            {
                "targets": [ 4 ],
                "searchable": false,
                "render": function ( data, type, row ) {
                    return '<a class="link clblue" onClick="editSession('+ data +')"><i class="fa fa-pencil fs20 pl20"></i></a> <a class="link clblue" onClick="deleteSession('+ data +')"><i class="fa fa-trash-o fs20 pl20"></i></a>';
                }
            }
        ]
    });
    customizeDTableSearch('sessionsDTable','Search sessions');
} */

function loadSessionsTable(){
	$('#sessionsTableWrapper').empty()
	$('#sessionsTableWrapper').append("<table class=\"display\" width=\"100%\" id=\"sessionsDTable\"></table>");		
	$('#sessionsDTable').DataTable( {
    	"bLengthChange": false,
    	"pageLength": 5,
        data: populateSessionsData(),
        columns: [
        	{ title: "Sessions" }
        ],
        "columnDefs": [
            {
                "targets": [ 0 ],
                "searchable": false,
                "render": function ( data, type, row ) {
                    return data;
                }
            }
        ]
    });
    customizeDTableSearch('sessionsDTable','Search sessions');
}

function showSessionForm(){
	$('#sessionForm').show(1000);
	$('#sessionTimingsForm').show(1000);
	$('#session-details-container').show(1000);
	$('#session-details-container .body').show(1000);
	$('#session-details-container .right .toggle').text('Hide');
	$('#session-location-container').show(1000);
	$('#session-location-container .body').hide(1000);
	$('#session-location-container .right .toggle').text('Show');
	$('#session-banner-container').show(1000);
	$('#session-banner-container .body').hide(1000);
	$('#session-banner-container .right .toggle').text('Show');
	$('#session-speaker-container').show(1000);
	$('#session-speaker-container .body').hide(1000);
	$('#session-speaker-container .right .toggle').text('Show');
	$('#session-action-links-wrapper').show(1000);
	$('.fx.step3').css('display','none');
	$('.fx.skip-sessions').css('display','none');
}

function editSession(sessionId){
	console.log('edit session' + sessionId);
	clearMsgs("#session-speaker-container");
	$('#editSessions').hide();
	localStorage.setItem("editSessionId", sessionId);
	$('.session-home-view #successPlaceholder').hide();
	$('.session-home-view #errorPlaceholder').hide();
	loadEditSessionFormData(sessionId);
	$('html, body').animate({
        scrollTop: $("#session-details-container").offset().top
	}, 1000);
}

function deleteSession(sessionId){
	localStorage.setItem("deleteSessionId", sessionId);
	$('#deleteSessionDialog').parent().css({position:"fixed"}).end().dialog('open');
}

function deleteSessionAction(){
	var deleteSessionId = localStorage.getItem('deleteSessionId');
	var deleteSessionUrl = "/event-data/sessions/session-nodes/session-"+deleteSessionId;
	var delete_form = $('#delete_form')[0];
	delete_form.action =  deleteSessionUrl;
	delete_form.submit();
	$('#deleteSessionDialog').dialog('close');
	// Show msg on home page
	$('#successPlaceholder .successText').html("Your session has been deleted");
	$('#successPlaceholder').show();
	// Refresh events data table
	setTimeout(function(){
	 	loadSessionsTable();
	}, 1000);
}

function cancelDeleteSession(){
	$('#deleteSessionDialog').dialog('close');
}

function populateSpeakersData(isSpeakersPage){
	var speakers = [];
	if(isSpeakersPage){
		var basePath = "/event-data/speaker/speaker-nodes";
	    var speaker_nodes = Sling.getContent(basePath, 3);	   
	    for(var i in speaker_nodes) {
    		if(i == undefined) break;
    		var speakerkey = i.split('-')[1];
        	var speaker = speaker_nodes[i][speakerkey];
			var speakerArr = [];
			speakerArr.push(speakerkey);
			speakerArr.push(speaker.firstName+ " " +speaker.lastName);
			speakerArr.push(speaker.designation);
			speakerArr.push(speakerkey);
			speakers.push(speakerArr);
    	}
	}
	else {
		var speakersList = $('#speakersList').val();
		var selSpeakers = JSON.parse("[" + speakersList + "]");
		if(selSpeakers.length != 0){
			var basePath = "/event-data/speaker/speaker-nodes";
		    var speaker_nodes = Sling.getContent(basePath, 3);	   
		    for(var i in speaker_nodes) {
	    		if(i == undefined) break;
	    		var speakerkey = i.split('-')[1];
	        	var speaker = speaker_nodes[i][speakerkey];
	    		if(selSpeakers.indexOf(parseInt(speakerkey)) != -1){
	    			var speakerArr = [];
	    			speakerArr.push(speakerkey);
	    			speakerArr.push(speaker.firstName+ " " +speaker.lastName);
	    			speakerArr.push(speaker.designation);
	    			speakerArr.push(speakerkey);
	    			speakers.push(speakerArr);
	    		}
	    	}
		}
	}
	return speakers;
}

function loadSpeakersTable(isSpeakersPage){
	$('#speakersTableWrapper').empty();
	$('#speakersTableWrapper').append("<table class=\"display\" width=\"100%\" id=\"speakersDTable\"></table>");		
	$('#speakersDTable').DataTable( {
    	"bLengthChange": false,
    	"pageLength": 10,
        data: populateSpeakersData(isSpeakersPage),
        columns: [
        	{ title: "speakerId" },
            { title: "Name" },
            { title: "Designation" },
            {title : "Actions"}
        ],
        "columnDefs": [
            {
                "targets": [ 0 ],
                "visible": false,
                "searchable": false
            },
            {
                "targets": [ 3 ],
                "searchable": false,
                "render": function ( data, type, row ) {
                    return '<a href="#" class="link clblue links-right" onClick="editSpeaker('+ data + ','+ isSpeakersPage + ')"><i class="fa fa-pencil fs20 pl20"></i></a> <a href="#" class="link clblue links-right" onClick="deleteSpeaker('+ data +')"><i class="fa fa-trash-o fs20 pl20"></i></a>';
                }
            }
        ]
    });
    // customize search bar
    customizeDTableSearch('speakersDTable','Search speakers');
}

function editSpeaker(speakerId, isSpeakersPage){
	console.log("edit speaker" + speakerId);
	localStorage.setItem("editSpeakerId", speakerId);
	loadEditSpeakerFormData(speakerId);
	if(isSpeakersPage){
		showSpeakerForm();
		$('#save-speaker-link').hide();
		$('#update-speaker-link').show();
		$('#cancel-speaker-link').show();
		$('html, body').animate({
		    scrollTop: $("#speaker-details-container").offset().top
		}, 1000);
	}
	else {
		$('.session-speaker-container #successPlaceholder').hide();
		$('.session-speaker-container #errorPlaceholder').hide();
		$("#addSpeakerDialog").parent().css({position:"fixed"}).end().dialog("open");
	}
}

function deleteSpeaker(speakerId){
	localStorage.setItem("deleteSpeakerId", speakerId);
	$('#deleteSpeakerDialog').parent().css({position:"fixed"}).end().dialog('open');
}

function deleteSpeakerAction(){
	var deleteSpeakerId = localStorage.getItem('deleteSpeakerId');
	var deleteSpeakerUrl = "/event-data/speaker/speaker-nodes/speaker-"+deleteSpeakerId;
	var delete_form = $('#delete_form')[0];
	delete_form.action =  deleteSpeakerUrl;
	delete_form.submit();
	$('#deleteSpeakerDialog').dialog('close');
	// Show msg on home page
	showSuccessMsg(".right", "Speaker has been deleted.");
	// Refresh events data table
	setTimeout(function(){
	 	loadSpeakersTable();
	}, 1000);
}

function deleteSpeakerSpeakersPage(){
	var deleteSpeakerId = localStorage.getItem('deleteSpeakerId');
	var deleteSpeakerUrl = "/event-data/speaker/speaker-nodes/speaker-"+deleteSpeakerId;
	var delete_form = $('#delete_form')[0];
	delete_form.action =  deleteSpeakerUrl;
	delete_form.submit();
	$('#deleteSpeakerDialog').dialog('close');
	// Show msg on home page
	$('#successPlaceholder .successText').html("Your speaker has been deleted");
	$('#successPlaceholder').show();
	// Refresh events data table
	setTimeout(function(){
	 	loadSpeakersTable(true);
	}, 1000);
}

function cancelDeleteSpeaker(){
	$('#deleteSpeakerDialog').dialog('close');
}

function populateSponsorsData(isSponsorsPage){
	var sponsors = [];
	if(isSponsorsPage){
		var basePath = "/event-data/partner/partner-nodes";
		var sponsor_nodes = Sling.getContent(basePath, 3);
		for(var i in sponsor_nodes) {
			var sponsorArr = [];
		  	if(i == undefined) break;
	        var sponsorkey = i.split('-')[1];
	        var sponsor = sponsor_nodes[i][sponsorkey];
	        sponsorArr.push(sponsorkey);
	        sponsorArr.push(sponsor.name);	       
	        sponsorArr.push(sponsor.sponsorTitle);
	        sponsorArr.push(sponsorkey);
	        sponsors.push(sponsorArr);
	    }
	}
	else {
		var curEventId = localStorage.getItem("curEventId");
		var basePath = "/event-data/partner/partner-nodes";
		var sponsor_nodes = Sling.getContent(basePath, 3);
	 	var partnerIds = [];
		for(var i in sponsor_nodes) {
			var sponsorArr = [];
		  	if(i == undefined) break;
	        var sponsorkey = i.split('-')[1];
	        var sponsor = sponsor_nodes[i][sponsorkey];
	        if(sponsor.eventId == curEventId){
		        sponsorArr.push(sponsorkey);
		        sponsorArr.push(sponsor.name);	       
		        sponsorArr.push(sponsor.sponsorTitle);
		        sponsorArr.push(sponsorkey);
		        sponsors.push(sponsorArr);

		        if(partnerIds.indexOf(parseInt(sponsorkey)) == -1)
					partnerIds.push(sponsorkey);	
	    	}
		}
		//update partners array of event form
		$('#partnersList').val(partnerIds.toString());
	}
	return sponsors;
} 

function loadSponsorsTable(isSponsorsPage){
	$('#sponsorTableWrapper').empty()
	$('#sponsorTableWrapper').append("<table class=\"display\" width=\"100%\" id=\"sponsorsDTable\"></table>");	
	$('#sponsorsDTable').DataTable( {
    	"bLengthChange": false,
    	"pageLength": 10,
        data: populateSponsorsData(isSponsorsPage),
        columns: [
        	{title: "sponsorId" },
        	{title: "Title" },
        	{title: "Name"},            
            {title : "Actions"}
        ],
        "columnDefs": [
            {
                "targets": [ 0 ],
                "visible": false,
                "searchable": false
            },
            {
                "targets": [ 3 ],
                "searchable": false,
                "render": function ( data, type, row ) {
                    return '<a href="#" class="link clblue links-right" onClick="editSponsor('+ data + ',' + isSponsorsPage + ')"><i class="fa fa-pencil fs20 pl20"></i></a> <a href="#" class="link clblue links-right"  onClick="deleteSponsor('+ data +')"><i class="fa fa-trash-o fs20 pl20"></i></a>';
                }
            }
        ]
    });
    // customize search bar
    customizeDTableSearch('sponsorsDTable', 'Search Sponsors');
}

function deleteSponsor(sponsorId){
	localStorage.setItem("deleteSponsorId", sponsorId);
	$('#deleteSponsorDialog').parent().css({position:"fixed"}).end().dialog('open');
}

function deleteSponsorAction(){
	var deleteSponsorId = localStorage.getItem('deleteSponsorId');
	var deleteSponsorUrl = "/event-data/partner/partner-nodes/partner-"+deleteSponsorId;
	var delete_form = $('#delete_form')[0];
	delete_form.action =  deleteSponsorUrl;
	delete_form.submit();
	$('#deleteSponsorDialog').dialog('close');
	showSuccessMsg('.partners-home-view', "Partner has been deleted.");
	// Refresh events data table
	setTimeout(function(){
	 	loadSponsorsTable();
	}, 1000);
}

function deleteSponsorSponsorPage(){
	var deleteSponsorId = localStorage.getItem('deleteSponsorId');
	var deleteSponsorUrl = "/event-data/partner/partner-nodes/partner-"+deleteSponsorId;
	var delete_form = $('#delete_form')[0];
	delete_form.action =  deleteSponsorUrl;
	delete_form.submit();
	$('#deleteSponsorDialog').dialog('close');
	showSuccessMsg('.right', "Partner has been deleted.");
	setTimeout(function(){
	 	loadSponsorsTable(true);
	}, 1000);
}

function cancelDeleteSponsor(){
	$('#deleteSponsorDialog').dialog('close');
}

function populateQuestionsData(){
	var basePath = "/event-data/questions/question-nodes";
	var question_nodes = Sling.getContent(basePath, 3);
	var questions = [];
	for(var i in question_nodes) {
		var questionArr = [];
	  	if(i == undefined) break;
        var questionkey = i.split('-')[1];
        var question = question_nodes[i][questionkey];
	        questionArr.push(questionkey);
	        questionArr.push(question.question);	       
	        questionArr.push(questionkey);
	        questions.push(questionArr);
	}
	return questions;
} 

function loadQuestionsTable(){
	$('#questionsTableWrapper').empty()
	$('#questionsTableWrapper').append("<table class=\"display\" width=\"100%\" id=\"questionsDTable\"></table>");
	
	$('#questionsDTable').DataTable( {
		"bLengthChange": false,
		"pageLength": 10,
	    data: populateQuestionsData(),
	    columns: [
	    	{title: "qId" },
	    	{title: "Question" },       
	        {title : "Actions"}
	    ],
	    "columnDefs": [
	        {
	            "targets": [ 0 ],
	            "visible": false,
	            "searchable": false
	        },
	        {
	            "targets": [ 2 ],
	            "searchable": false,
	            "render": function ( data, type, row ) {
	                return '<a href="#" class="link clblue links-right" onClick="editQuestion('+ data +')"><i class="fa fa-pencil fs20 pl20"></i></a> <a href="#" class="link clblue links-right" onClick="deleteQuestion('+ data +')"><i class="fa fa-trash-o fs20 pl20"></i></a>';
	            }
	        }
	    ]
	});
	customizeDTableSearch('questionsDTable','Search questions');
	if($('#questionsTableWrapper .dataTables_empty').length != 0){
		$('#publish-survey-questions').hide();
	}
}

function populatePromotionsData(){
	var basePath = "/event-data/promotions/promotion-nodes";
	var promotion_nodes = Sling.getContent(basePath, 3);
	var promotions = [];
	for(var i in promotion_nodes) {
		var promotionArr = [];
	  	if(i == undefined) break;
        var promotionkey = i.split('-')[1];
        var promotion = promotion_nodes[i][promotionkey];
	        promotionArr.push(promotionkey);
	        promotionArr.push(promotion.promotionTitle);	       
	        promotionArr.push(promotionkey);
	        promotions.push(promotionArr);
	}
	return promotions;
} 

function loadPromotionsTable(){
	$('#promotionsTableWrapper').empty()
	$('#promotionsTableWrapper').append("<table class=\"display\" width=\"100%\" id=\"promotionsDTable\"></table>");
	$('#promotionsDTable').DataTable( {
		"bLengthChange": false,
		"pageLength": 10,
		data: populatePromotionsData(),
		columns: [
			{title: "promotionId" },
			{title: "Title" },       
		    {title : "Actions"}
		],
		"columnDefs": [
		    {
		        "targets": [ 0 ],
		        "visible": false,
		        "searchable": false
		    },
		    {
		        "targets": [ 2 ],
		        "searchable": false,
		        "render": function ( data, type, row ) {
		            return '<a href="#" class="link clblue" onClick="editPromotion('+ data +')"><i class="fa fa-pencil fs20 pl20"></i></a> <a href="#" class="link clblue" onClick="deletePromotion('+ data +')"><i class="fa fa-trash-o fs20 pl20"></i></a>';
		        }
		    }
		]
	});

	customizeDTableSearch('promotionsDTable','Search promotions');
	if($('#promotionsTableWrapper .dataTables_empty').length != 0){
		$('#publish-promotions').hide();
	}
}

function editPromotion(promotionId){
	console.log("edit promotion" + promotionId);
	localStorage.setItem("editPromotionId", promotionId);
	loadEditPromotionFormData(promotionId);
	$('.fx.publish-prom').hide();
	$('html, body').animate({
        scrollTop: $("#promotion-details-container").offset().top
	}, 1000);
}


function deletePromotion(promotionId){
	localStorage.setItem("deletePromotionId", promotionId);
	$('#deletePromotionDialog').parent().css({position:"fixed"}).end().dialog('open');
}

function deletePromotionAction(){
	var deletePromotionId = localStorage.getItem('deletePromotionId');
	var deletePromotionUrl = "/event-data/promotions/promotion-nodes/promotion-"+deletePromotionId;
	var delete_promotionform = $('#delete_promotionform')[0];
	delete_promotionform.action =  deletePromotionUrl;
	delete_promotionform.submit();
	$('#deletePromotionDialog').dialog('close');
	// Show msg on home page
	$('#successPlaceholder .successText').html("Your promotion has been deleted");
	$('#successPlaceholder').show();
	// Refresh events data table
	setTimeout(function(){
	 	loadPromotionsTable();
	}, 1000);
}

function cancelDeletePromotion(){
	$('#deletePromotionDialog').dialog('close');
}

function editSponsor(sponsorId, isSponsorsPage){
	localStorage.setItem("editSponsorId", sponsorId);
	if(isSponsorsPage){
		loadEditSponsorFormData(sponsorId);
		$('#add-new-sponsor').show();
		$('#sponsor-action-links-wrapper').show();
		$('#update-sponsor-btn').show();
		$('#save-sponsor-btn').hide();
		$('html, body').animate({
	        scrollTop: $("#add-new-sponsor").offset().top
		}, 1000);
	}
	else {
		$('.fx.submit').hide();	
		$('.partners-home-view #successPlaceholder').hide();
		$('.partners-home-view #errorPlaceholder').hide();
		loadEditSponsorFormData(sponsorId);
		$('html, body').animate({
	        scrollTop: $("#partner-details-container").offset().top
		}, 1000);
	}
}

function editQuestion(questionId){
	console.log("edit question", questionId);
	localStorage.setItem("editQuestionId", questionId);
	loadEditQuestionFormData(questionId);
	$('.fx.publish-question').hide();
	$('html, body').animate({
        scrollTop: $("#event-details-container").offset().top
	}, 1000);
}

function deleteQuestion(questionId){
	localStorage.setItem("deleteQuestionId", questionId);
	$('#deleteQuestionDialog').parent().css({position:"fixed"}).end().dialog('open');
}

function deleteQuestionAction(){
	var deleteQuestionId = localStorage.getItem('deleteQuestionId');
	var deleteQuestionUrl = "/event-data/questions/question-nodes/question-"+deleteQuestionId;
	var delete_questionform = $('#delete_questionform')[0];
	delete_questionform.action =  deleteQuestionUrl;
	delete_questionform.submit();
	$('#deleteQuestionDialog').dialog('close');
	showSuccessMsg(".right", "Question has been deleted.");
	// Refresh events data table
	setTimeout(function(){
	 	loadQuestionsTable();
	}, 1000);
}

function cancelDeleteQuestion(){
	$('#deleteQuestionDialog').dialog('close');
}

function registerToggleContainer(reference){
	$(reference).click(function(){
			console.log('called');
			var mode = this.text;
			var targetNode = $(this).data('reference')+" .body";
			console.log(reference);
			if(mode=="Hide"){
				$(targetNode).hide(1000);
				//this.text = "Show";
				$(this).html("<i class='fa fa-expand clblue pr5'></i>Show");
			} else{
				$(targetNode).show(1000);
				//this.text = "Hide";
				$(this).html("<i class='fa fa-compress clblue pr5'></i>Hide");
			}
	});
}

function registerAddNewSpeakerBtn(){
	$('#add-new-speaker-btn').click(function(){
		console.log('called add speaker');
		resetForm('speakerForm');
		resetImageForm('speakerphoto_form');
		// reload speakers table
		loadSpeakersTable();
		$('#save-speaker-link').css('display', 'inline');
		$('#update-speaker-link').css('display', 'none');
		$('#add-new-speaker-inline').show();
		$("#addSpeakerDialog").parent().css({position:"fixed"}).end().dialog("open");
	});
}

function registerAddSpeakerBtn(){
	$('#add-new-speaker-link').click(function(){
		console.log("add speaker");
		resetForm('speakerForm');
		resetImageForm('speakerphoto_form', 'speakerImgBanner');
		clearMsgs(".right");
		showSpeakerForm();
		$('#save-speaker-link').show();
		$('#update-speaker-link').hide();
		$('#cancel-speaker-link').show();
		$('html, body').animate({
		    scrollTop: $("#speaker-details-container").offset().top
		}, 1000);
	});
}

function showSpeakerForm(){
	$('#add-new-speaker').show();
	$('#speakerForm').show(1000);
	$('#speaker-details-container').show(1000);
	$('#speaker-details-container .body').show(1000);
	$('#speaker-details-container .right .toggle').text('Hide');
	$('#speaker-banner-container').show(1000);
	$('#speaker-banner-container .body').hide(1000);
	$('#speaker-banner-container .right .toggle').text('Show');
	$('#speaker-action-links-wrapper').show(1000);
}

function registerAddNewSessionBtn(){
	$('#add-new-session-link').click(function(){
		resetForm('sessionForm');
		resetImageForm('sessionbanner_form','sessionImgBanner');
		resetSelectivityData();
		resetSessionTimings();
		$('#speakersList').val("");
		$('#sessionForm #addressText').val($('#eventForm #addressText').val());
		$('#sessionForm #locationLat').val($('#eventForm #locationLat').val());
		$('#sessionForm #locationLong').val($('#eventForm #locationLong').val());
		loadSpeakersTable();
		showSessionForm();
		$('#save-session-link').css('display', 'inline');
		$('#update-session-link').css('display', 'none');
		// Hide previous success/error msg
		$('.session-home-view #successPlaceholder').hide();
		$('.session-home-view #errorPlaceholder').hide();
		$('html, body').animate({
		    scrollTop: $("#session-details-container").offset().top
		}, 1000);
	});
}

function resetSessionTimings(){
	var currIndex = 0;
	var markup = '<ul class="datetime left"><li class="dt-date"><input type="text" class="date startDate" placeholder="Start Date" id="startDate' + currIndex +'" name="startDate'+ currIndex +'" data-validation="date" data-validation-format="mm/dd/yyyy"/><i class="fa fa-calendar right pr5"></i></li><li class="dt-time"><input type="text" class="time" placeholder="Start Time" id="startTime'+ currIndex +'" name="startTime'+ currIndex +'" data-validation="custom" data-validation-regexp="^\d\d:\d\d [APap][mM]$"/><i class="fa fa-clock-o right pr5"></i></li><li class="dt-time"><input type="text" class="time" placeholder="End Time" id="endTime'+ currIndex +'" name="endTime'+ currIndex +'" data-validation="custom" data-validation-regexp="^\d\d:\d\d [APap][mM]$"/><i class="fa fa-clock-o right pr5"></i></li></ul><input type="text" id="addressText'+ currIndex +'" placeholder="Lookup for location" name="addressText'+ currIndex +'" data-validation="required"/><input type="text" id="hall'+ currIndex + '" class="mt15 mb20" placeholder="Enter Hall/Building Name" name="hall'+ currIndex +'" data-validation="required"/>';
	$("#sessionTimings").html(markup);
	addDatePicker();
	addTimePicker();
	$('#addressText'+currIndex).val($('#eventForm #addressText').val());
}

function registerAddNewSessionTimingsLink(){
	$('#add-session-timings-link').click(function(){
		console.log("add new session timings");
		var currIndex = parseInt(localStorage.getItem("currSessionIndex")) + 1;
		var markup = '<hr/><ul class="datetime left"><li class="dt-date"><input type="text" class="date startDate" placeholder="Start Date" id="startDate' + currIndex +'" name="startDate'+ currIndex +'" data-validation="date" data-validation-format="mm/dd/yyyy"/><i class="fa fa-calendar right pr5"></i></li><li class="dt-time"><input type="text" class="time" placeholder="Start Time" id="startTime'+ currIndex +'" name="startTime'+ currIndex +'" data-validation="custom" data-validation-regexp="^\d\d:\d\d [APap][mM]$"/><i class="fa fa-clock-o right pr5"></i></li><li class="dt-time"><input type="text" class="time" placeholder="End Time" id="endTime'+ currIndex +'" name="endTime'+ currIndex +'" data-validation="custom" data-validation-regexp="^\d\d:\d\d [APap][mM]$"/><i class="fa fa-clock-o right pr5"></i></li></ul><input type="text" id="addressText'+ currIndex +'" placeholder="Lookup for location" name="addressText'+ currIndex +'" data-validation="required"/><input type="text" id="hall'+ currIndex + '" class="mt15 mb20" placeholder="Enter Hall/Building Name" name="hall'+ currIndex +'" data-validation="required"/>';
		$("#sessionTimings").append(markup);
		addDatePicker();
		addTimePicker();
		$('#addressText'+currIndex).val($('#eventForm #addressText').val());
		localStorage.setItem("currSessionIndex", currIndex);
	});
}

function registerAddNewPromotionBtn(){
	$('#add-new-promotion-link').click(function(){
		console.log("add promo");
		resetForm('promotionForm');
		resetImageForm('promotionbanner_form', 'promotionImgBanner');
		clearMsgs(".right");
		loadEvents();
		showPromotionForm();
		$('#save-promotion-link').show();
		$('#update-promotion-link').hide();
		$('#cancel-promotion-link').show();
		$('.fx.publish-prom').hide();
		$('html, body').animate({
        	scrollTop: $("#promotion-details-container").offset().top
		}, 1000);
	});
}

function showPromotionForm(){
	$('#add-new-promotion').show();
	$('#promotionForm').show(1000);
	$('#promotion-details-container').show(1000);
	$('#promotion-details-container .body').show(1000);
	$('#promotion-details-container .right .toggle').text('Hide');
	$('#promotion-banner-container').show(1000);
	$('#promotion-banner-container .body').hide(1000);
	$('#promotion-banner-container .right .toggle').text('Show');
	$('#promotion-action-links-wrapper').show(1000);
}

function registerAddNewQuestionBtn(){
	$('#add-new-question-link').click(function(){
		resetForm('surveyForm');
		$('#save-question-link').css('display', 'inline');
		$('#update-question-link').css('display', 'none');
		$('#cancel-question-link').css('display', 'inline');
		$('#add-new-question').show(1000);
		$('#question-action-links-wrapper').show();
		$('#publish-survey-questions').hide();
		$('html, body').animate({
	        scrollTop: $("#event-details-container").offset().top
		}, 1000);
	});
}

function registerAddNewSponsorBtn(){
	$('#add-new-sponsor-btn').click(function(){
		console.log('called add sponsor');
		resetForm('sponsorForm');
		resetImageForm('sponsorphoto_form', 'sponsorImgBanner');
		$('#add-new-sponsor-inline').show(1000);
		$('.fx.skip-sponsors').hide();
		$('#save-sponsor-btn').css('display','inline');
		$('#update-sponsor-btn').css('display','none');
		$('.fx.submit').hide();
		// Hide previous success/error msg
		$('.partners-home-view #successPlaceholder').hide();
		$('.partners-home-view #errorPlaceholder').hide();
		$('html, body').animate({
		    scrollTop: $("#partner-details-container").offset().top
		}, 1000);
	});
}

function registerAddNewSponsorLink(){
	$('#add-new-sponsor-link').click(function(){
		resetForm('sponsorForm');
		resetImageForm('sponsorphoto_form', 'sponsorImgBanner');
		loadEvents();
		$('#add-new-sponsor').show();
		$('#sponsor-action-links-wrapper').show();
		$('#save-sponsor-btn').show();
		$('#update-sponsor-btn').hide();
		$('html, body').animate({
		    scrollTop: $("#add-new-sponsor").offset().top
		}, 1000);
	});
}

function registerSpeakerSaveBtn(){
	$('#save-speaker-link').click(function(){
		$('#addSpeakerDialog').dialog('close');
		addSpeaker();
	});
}

function registerQuestionSaveBtn(){
	$('#save-question-link').click(function(){
		var conf = {
			onElementValidate : function(valid, $el, $form, errorMess) {
				if(!valid) {
					$el.addClass('error');
					errors.push({el: $el, error: errorMess});
				}
			}
		}
		var lang = {};
		var errors = [];
		if( !$('#surveyForm').isValid(lang, conf, false) ) {
			//form invalid - show error msg
			showErrorMsg(".right", "All fields are mandatory. Please fill all the fields to proceed.");
		} 
		else{
			// form valid - proceed with the flow 
			addQuestion();
			$('#save-question-link').css('display', 'none');
			$('#add-new-question').hide(1000);
			$('#save-question-link').hide();
			$('#publish-survey-questions').show();
		}		
	});
}

function registerQuestionUpdateBtn(){
	$('#update-question-link').click(function(){
		var conf = {
			onElementValidate : function(valid, $el, $form, errorMess) {
				if(!valid) {
					$el.addClass('error');
					errors.push({el: $el, error: errorMess});
				}
			}
		}
		var lang = {};
		var errors = [];
		if( !$('#surveyForm').isValid(lang, conf, false) ) {
			//form invalid - show error msg
			showErrorMsg(".right", "All fields are mandatory. Please fill all the fields to proceed.");
		} 
		else{
			// form valid - proceed with the flow 
			addQuestion(localStorage.getItem("editQuestionId"));
			$('#save-question-link').css('display', 'none');
			$('#add-new-question').hide(1000);
			$('#save-question-link').hide();
			$('#publish-survey-questions').show();
			$('#question-action-links-wrapper').hide();	
		}			
	});
}

function registerQuestionPublishBtn(){
	$('#publish-survey-questions').click(function(){
		$('#publishQuestionDialog').parent().css({position:"fixed"}).end().dialog('open');
	});
}

function publishQuestionAction(){
	$('#publishQuestionDialog .fx.submit').hide(1000);
	$('#publishQuestionDialog .section-title .msg').html("Publishing the questions <i class='fa fa-send-o pl10'></i>");
	$('#publishQuestionDialog .warningText').html("<i class='fa fa-warning pr5'></i>This operation might take few minutes");
	$("#upload-progress-container").show();
	publishSurveyQuestions();
}

function publishQuestionDone(){
	// Show msg on home page
	$('#publishQuestionDialog').dialog('close');
	$('#successPlaceholder .successText').html("Questions are published. In case of any failure or other data issues, edit and publish the event again.");
	$('#successPlaceholder').show();
	redirectPage('/IgniteAdmin/design/create-surveyquestions.html');
}

function cancelPublishQuestion(){
	$('#publishQuestionDialog').dialog('close');
}

function registerPromotionPublishBtn(){
	$('#publish-promotions').click(function(){
		$('#publishPromotionDialog').parent().css({position:"fixed"}).end().dialog('open');
	});
}

function publishPromotionAction(){
	$('#publishPromotionDialog .fx.submit').hide(1000);
	$('#publishPromotionDialog .section-title .msg').html("Publishing the promotions <i class='fa fa-send-o pl10'></i>");
	$('#publishPromotionDialog .warningText').html("<i class='fa fa-warning pr5'></i>This operation might take few minutes");
	$("#upload-progress-container").show();
	publishPromotions();
}

function publishPromotionDone(){
	// Show msg on home page
	$('#publishPromotionDialog').dialog('close');
	$('#successPlaceholder .successText').html("Promotions are published. In case of any failure or other data issues, edit and publish the event again.");
	$('#successPlaceholder').show();
	redirectPage('/IgniteAdmin/design/create-promotions.html');
}

function cancelPublishPromotion(){
	$('#publishPromotionDialog').dialog('close');
}

function registerPromotionSaveBtn(){
	$('#save-promotion-link').click(function(){
		var conf = {
			onElementValidate : function(valid, $el, $form, errorMess) {
				if(!valid) {
					var element = $el;
					if(element[0].id == "promotionImgBase64"){
						$('#promotionImgBanner').addClass('imgInvalid');
					}
					else {
						$el.addClass('error');
					}
					errors.push({el: $el, error: errorMess});
				}
			}
		}
		var lang = {};
		var errors = [];
		if( !$('#promotionForm').isValid(lang, conf, false) ) {
			//form invalid - show error msg
			showErrorMsg(".right", "All fields are mandatory. Please fill all the fields to proceed.");
		} 
		else{
			// form valid - proceed with the flow 
			addPromotion();
			$('#promotion-details-container').hide(1000);		
			$('#promotion-banner-container').hide(1000);
			$('#promotion-action-links-wrapper').hide(1000);
			$('.fx.publish-prom').show();
			$('#publish-promotions').show();
		}	
	});
} 

function registerPromotionUpdateBtn(){
	$('#update-promotion-link').click(function(){
		addPromotion(localStorage.getItem("editPromotionId"));
		$('#promotion-details-container').hide(1000);		
		$('#promotion-banner-container').hide(1000);
		$('#promotion-action-links-wrapper').hide(1000);
		$('.fx.publish-prom').show();
		$('#publish-promotions').show();	
	});
}

function registerSpeakerUpdateBtn(){
	$('#update-speaker-link').click(function(){
		$('#addSpeakerDialog').dialog('close');
		addSpeaker(localStorage.getItem("editSpeakerId"));
	});
}

function registerSpeakerCancelBtn(){
	$('#cancel-speaker-link').click(function(){
		$('#addSpeakerDialog').dialog('close')
	});
}

/**  functions for speakers page **/
function registerSpeakerUpdateLink(){
	$('#update-speaker-link').click(function(){
		addSpeaker(localStorage.getItem("editSpeakerId"), true);
		$('#speaker-details-container').hide(1000);		
		$('#speaker-banner-container').hide(1000);
		$('#speaker-action-links-wrapper').hide(1000);
	});
}

function registerSpeakerCancelLink(){
	$('#cancel-speaker-link').click(function(){
		$('#speaker-details-container').hide(1000);		
		$('#speaker-banner-container').hide(1000);
		$('#speaker-action-links-wrapper').hide(1000);
	});
}

function registerSpeakerSaveLink(){
	$('#save-speaker-link').click(function(){
		addSpeaker(null, true);
		$('#speaker-details-container').hide(1000);		
		$('#speaker-banner-container').hide(1000);
		$('#speaker-action-links-wrapper').hide(1000);
	});
}

function registerSponsorSaveBtn(isSponsorsPage){
	$('#save-sponsor-btn').click(function(){
		var conf = {
			onElementValidate : function(valid, $el, $form, errorMess) {
				if(!valid) {
					var element = $el;
					if(element[0].id == "sponsorImgBase64"){
						$('#sponsorImgBanner').addClass('imgInvalid');
					}
					else {
						$el.addClass('error');
					}
					errors.push({el: $el, error: errorMess});
				}
			}
		}
		var lang = {};
		var errors = [];
		if( !$('#sponsorForm').isValid(lang, conf, false) ) {
			//form invalid - show error msg
			if(isSponsorsPage)
				showErrorMsg(".right", "All fields are mandatory. Please fill all the fields to proceed.");
			else 
				showErrorMsg(".partners-home-view", "All fields are mandatory. Please fill all the fields to proceed.");
		} 
		else{
			// form valid - proceed with the flow 
			if(isSponsorsPage){
				addSponsor(null, isSponsorsPage);
				$('#add-new-sponsor').hide();
				$('#sponsor-action-links-wrapper').hide();
			}
			else{
				$('#add-new-sponsor-inline').hide(1000);
				$('.fx.submit').css('display','inline-flex');
				addSponsor();
				$('.fx.skip-sponsors').hide();
				$('#nosponsors').hide();
				$('#allsponsors').show(1000);
				$('#progressbar .item3').addClass('active');
			}
		}	
	});
}

function registerSponsorUpdateBtn(isSponsorsPage){
	$('#update-sponsor-btn').click(function(){
		var conf = {
			onElementValidate : function(valid, $el, $form, errorMess) {
				if(!valid) {
					var element = $el;
					if(element[0].id == "sponsorImgBase64"){
						$('#sponsorImgBanner').addClass('imgInvalid');
					}
					else {
						$el.addClass('error');
					}
					errors.push({el: $el, error: errorMess});
				}
			}
		}
		var lang = {};
		var errors = [];
		if( !$('#sponsorForm').isValid(lang, conf, false) ) {
			//form invalid - show error msg
			if(isSponsorsPage)
				showErrorMsg(".right", "All fields are mandatory. Please fill all the fields to proceed.");
			else 
				showErrorMsg(".partners-home-view", "All fields are mandatory. Please fill all the fields to proceed.");
		} 
		else{
			// form valid - proceed with the flow 
			if(isSponsorsPage){
				addSponsor(localStorage.getItem("editSponsorId"), isSponsorsPage);
				$('#add-new-sponsor').hide();
				$('#sponsor-action-links-wrapper').hide();
			}
			else {
				$('#add-new-sponsor-inline').hide(1000);
				$('.fx.submit').css('display','inline-flex');
				addSponsor(localStorage.getItem("editSponsorId"));
				$('#progressbar .item3').addClass('active');
				$('.fx.skip-sponsors').hide();
			}		
		}	
	});
}

function registerSponsorCancelBtn(){
	$('#cancel-sponsor-btn').click(function(){
		$('#add-new-sponsor-inline').hide(1000);
		if($('#sponsorTableWrapper .dataTables_empty').length != 0){
			console.log('display skip');
			$('.fx.skip-sponsors').css('display','inline-flex');
		}
		else{
			console.log('display submit buttons');
			$('.fx.submit').css('display','inline-flex');
		}
	});
}

function registerSponsorCancelLink(){
	$('#cancel-sponsor-btn').click(function(){
		$('#add-new-sponsor').hide();
		$('#sponsor-action-links-wrapper').hide();
	});
}

function registerEventPublishBtn(){
	$('#publishMasterEvent').click(function(){
		console.log("publish evene click in final stage");
	});
}

function registerSessionSaveBtn(){
	$('#save-session-link').click(function(){
		var isSpeakersEmpty = false;
		var errorMsg = "All fields are mandatory. Please fill all the fields to proceed.";
		var sessionCatLength = $('#sessionCategoriesDropdown').selectivity("data").length;
		var conf = {
			onElementValidate : function(valid, $el, $form, errorMess) {
				if(!valid) {
					var element = $el;
					console.log("element %o", element);
					if(element[0].id == "sessionImgBase64"){
						$('#sessionImgBanner').addClass('imgInvalid');
					}
					else if(element[0].id == "speakersList"){
						errorMsg = "Please add at least one speaker to continue.";
					}
					else {
						$el.addClass('error');
					}
					errors.push({el: $el, error: errorMess});
				}
			}
		}
		var lang = {};
		var errors = [];
		if($('#sessionForm').isValid(lang, conf, false) && sessionCatLength > 0){
			// form valid - proceed with the flow 
			$('#session-details-container').hide(1000);
			$('#session-location-container').hide(1000);
			$('#session-banner-container').hide(1000);
			$('#session-speaker-container').hide(1000);
			$('#session-action-links-wrapper').hide(1000);
			$('.fx.step3').css('display','inline-flex');
			$('#progressbar .item2').addClass('active');
			var itemArray = $('#sessionCategoriesDropdown').selectivity('data');
	 		var categoryIds = [];
	 		for(i in itemArray){
	 			categoryIds.push(itemArray[i].id);
	 		}
	 		$('#categoriesList').val(categoryIds.toString());
	 		var noOfSessionTimings = $('#sessionTimingsForm :input[class="date startDate hasDatepicker"]').length;
	 		for(var i=0 ; i< noOfSessionTimings; i++){
	 			$('#sessionForm #startDate').val($('#sessionTimingsForm #startDate'+i).val());
	 			$('#sessionForm #endDate').val($('#sessionTimingsForm #endDate'+i).val());
	 			$('#sessionForm #startTime').val($('#sessionTimingsForm #startTime'+i).val());
	 			$('#sessionForm #endTime').val($('#sessionTimingsForm #endTime'+i).val());
	 			$('#sessionForm #hall').val($('#sessionTimingsForm #hall'+i).val());
	 			$('#sessionForm #addressText').val($('#sessionTimingsForm #addressText'+i).val());
	 			addSession();
	 		}
			$('.fx.skip-sessions').css('display','none');
		} 
		else{
			//form invalid - show error msg
			showErrorMsg(".session-home-view", errorMsg);
			$('html, body').animate({
			    scrollTop: $(".session-home-view").offset().top
			}, 1000);
		}	
	});
}

function registerSessionUpdateBtn(){
	$('#update-session-link').click(function(){
		var isSpeakersEmpty = false;
		var errorMsg = "All fields are mandatory. Please fill all the fields to proceed.";
		var sessionCatLength = $('#sessionCategoriesDropdown').selectivity("data").length;
		var conf = {
			onElementValidate : function(valid, $el, $form, errorMess) {
				if(!valid) {
					var element = $el;
					console.log("element %o", element);
					if(element[0].id == "sessionImgBase64"){
						$('#sessionImgBanner').addClass('imgInvalid');
					}
					else if(element[0].id == "speakersList"){
						errorMsg = "Please add at least one speaker to continue.";
					}
					else {
						$el.addClass('error');
					}
					errors.push({el: $el, error: errorMess});
				}
			}
		}
		var lang = {};
		var errors = [];
		if($('#sessionForm').isValid(lang, conf, false) && sessionCatLength > 0) {
			// form valid - proceed with the flow 
			$('#session-details-container').hide(1000);
			$('#session-location-container').hide(1000);
			$('#session-banner-container').hide(1000);
			$('#session-speaker-container').hide(1000);
			$('#session-action-links-wrapper').hide(1000);
			$('.fx.step3').css('display','inline-flex');
			$('#progressbar .item2').addClass('active');
			var itemArray = $('#sessionCategoriesDropdown').selectivity('data');
			console.log('itemArray = %o',itemArray);
	 		var categoryIds = [];
	 		for(i in itemArray){
	 			categoryIds.push(itemArray[i].id);
	 		}
	 		console.log('category ids = '+categoryIds);
	 		$('#categoriesList').val(categoryIds.toString());

	 		var noOfSessionTimings = $('#sessionTimingsForm :input[class="date startDate hasDatepicker valid"]').length;
	 		for(var i=0 ; i< noOfSessionTimings; i++){
	 			$('#sessionForm #startDate').val($('#sessionTimingsForm #startDate'+i).val());
	 			$('#sessionForm #startTime').val($('#sessionTimingsForm #startTime'+i).val());
	 			$('#sessionForm #endTime').val($('#sessionTimingsForm #endTime'+i).val());
	 			$('#sessionForm #hall').val($('#sessionTimingsForm #hall'+i).val());
	 			$('#sessionForm #addressText').val($('#sessionTimingsForm #addressText'+i).val());
	 			if(i==0)
	 				addSession(localStorage.getItem("editSessionId"));
	 			else 
	 				addSession();
	 		}	
			$('.fx.skip-sessions').css('display','none');	
		} 
		else{
			//form invalid - show error msg
			showErrorMsg(".session-home-view", errorMsg);
			$('html, body').animate({
			    scrollTop: $(".session-home-view").offset().top
			}, 1000);
		}	
	});
}

function registerSessionCancelBtn(){
	$('#cancel-session-link').click(function(){
		$('#session-details-container').hide(1000);		
		$('#session-location-container').hide(1000);
		$('#session-banner-container').hide(1000);
		$('#session-speaker-container').hide(1000);
		$('#session-action-links-wrapper').hide(1000);
		if($('#sessionsTableWrapper .dataTables_empty').length != 0){
			console.log('display skip');
			$('.fx.skip-sessions').css('display','inline-flex');
		}
		else{
			console.log('display setup partners');
			$('.fx.step3').css('display','inline-flex');
		}
	});	
}

function registerPromotionCancelBtn(){
	$('#cancel-promotion-link').click(function(){
		$('#promotion-details-container').hide(1000);		
		$('#promotion-banner-container').hide(1000);
		$('#promotion-action-links-wrapper').hide(1000);
		$('.fx.publish-prom').show();
	});
}

function registerQuestionCancelBtn(){
	$('#cancel-question-link').click(function(){
		$('#add-new-question').hide(1000);
		$('#question-action-links-wrapper').hide();
		$('#publish-survey-questions').show();
	});
}

/* Generates Unique Id*/
function uniqueId() {
	var i = new Date().getTime();
	i = i & 0xffff; 
	return i;
}

/* Reads image data and loads base64 string to hidden filed */
function readImage(input) {
	console.log('readImage ' + input.id);
    if (input.files && input.files[0]) {
	    var FR= new FileReader();
	    FR.readAsDataURL(input.files[0]);
	     FR.onload = function(e) {
	     	$('#' + input.id +'Base64').val(e.target.result);
	     	$('#' + input.id + 'Banner').attr('src', e.target.result);
	     	base64Str = e.target.result;
	     	$('#' + input.id + 'Id').val(uniqueId());
	     	$('#' + input.id + 'Banner').removeClass('imgInvalid');
        };    
	}
}

function addPromotion(promotionId){
	var msg;
	if(promotionId == null){
  		promotionId = uniqueId();
  		msg = "Promotion has been created successfully.";
	}
	else {
		msg = "Promotion has been updated successfully.";
	}
	// Set the event details for the selected event in the form
  	var eventId = $('#events_dropdown').val();
  	if(eventId != "0"){
  	  associateEvent(eventId);
  	}
  	else{
  		console.log("no event selected");
  		return;
  	}
  	// Set base path
	var baseTestPath = "/event-data/promotions/promotion-nodes";
	var dataPath = baseTestPath + "/promotion-" + promotionId;
	var path = dataPath + "/" + promotionId;
	// Get the data from the form
	var params = $('#promotionForm').serialize();
	// Post the data to Sling JCR Repository
	var request = $.ajax({
	  url: path,
	  method: "POST",
	  data: params,
	  headers:{
	  	"Content-type":"application/x-www-form-urlencoded",
	  	"Content-length":params.length,
	  	"Connection":"close"
	  }
	});
	request.done(function(status) {
		// upload image
		var promotionphoto_form = $('#promotionbanner_form')[0];
		promotionphoto_form.action =  path;
		promotionphoto_form.submit();
		loadPromotionsTable();
		showSuccessMsg(".right", msg);
	});	 
	request.fail(function( jqXHR, textStatus ) {
	  console.log( "Request failed: " + textStatus );
	  showErrorMsg(".right", "Something went wrong. Please try again later.");
	});
}

function addQuestion(questionId){
	// Set base path
	var successMsg;
	if(questionId == null){
  		questionId = uniqueId();
  		successMsg = "Question has been created successfully.";
	}
	else{
		successMsg = "Question has been updated successfully."
	}
	var baseTestPath = "/event-data/questions/question-nodes";
	var dataPath = baseTestPath + "/question-" + questionId;
	var path = dataPath + "/" + questionId;
	// Get the data from the form
	var params = $('#questionForm').serialize();
	// Post the data to Sling JCR Repository
	var request = $.ajax({
		url: path,
		method: "POST",
		data: params,
		headers:{
			"Content-type":"application/x-www-form-urlencoded",
			"Content-length":params.length,
			"Connection":"close"
		}
	});

	request.done(function( msg ) {
		// reload questions table 
		loadQuestionsTable();
		$('#cancel-question-link').hide(); 	
		showSuccessMsg(".right", successMsg);  	  	    				 	    	 
	});
	 
	request.fail(function( jqXHR, textStatus ) {
		alert( "Request failed: " + textStatus );
		showErrorMsg(".right", "Something went wrong. Please try again later.");
	});
}

/* Save sponsor details in JCR */
function addSponsor(sponsorId, isSponsorsPage) {
	// Set current eventId
	$('#eventIdSponsor').val(localStorage.getItem("curEventId"));
	var displayText = "Partner created successfully. Add more partners or proceed to next step.";
	// Set base path
	if(sponsorId == null)
  		sponsorId = uniqueId();
  	else
  		displayText = "Partner updated successfully. Add more partners or proceed to next step.";
  	if(isSponsorsPage){
  		$('#eventIdSponsor').val($('#events_dropdown').val());
  	}
	var baseTestPath = "/event-data/partner/partner-nodes";
	var testPath = baseTestPath + "/partner-" + sponsorId;
	var path = testPath + "/" + sponsorId;
	// Get the data from the form
	var params = $('#sponsorForm').serialize();
	// Post the data to Sling JCR Repository
	var request = $.ajax({
		url: path,
		method: "POST",
		data: params,
		headers:{
			"Content-type":"application/x-www-form-urlencoded",
			"Content-length":params.length,
			"Connection":"close"
		}
	});

	request.done(function( msg ) {
		// upload image
		var sponsorphoto_form = $('#sponsorphoto_form')[0];
		sponsorphoto_form.action =  path;
		sponsorphoto_form.submit();

		if(isSponsorsPage){
			loadSponsorsTable(isSponsorsPage);
			showSuccessMsg('.right', displayText);
		}
		else {
			//update partners array of event form
			var partnersList = $('#partnersList').val();
		 	var partnerIds = [];
		 	if(partnersList != null && partnersList != undefined)
				partnerIds = JSON.parse("[" + partnersList + "]");
			if(partnerIds.indexOf(parseInt(sponsorId)) == -1)
				partnerIds.push(sponsorId);	
			$('#partnersList').val(partnerIds.toString());
			// reload sponsor table 
			loadSponsorsTable();
			// Show success msg
			showSuccessMsg('.partners-home-view', displayText);
		}	    	  	    				 	    	 
	});
	request.fail(function( jqXHR, textStatus ) {
		alert( "Request failed: " + textStatus );
		showErrorMsg('.partners-home-view','Something went wrong. Please try again later.')
	});
}

/* Save session details in JCR */
function addSession(sessionId) {
	// Set current eventId
	$('#eventIdSession').val(localStorage.getItem("curEventId"));
	var displayText = "Session created successfully. Add more sessions or proceed to next step.";
	if(sessionId == null)
  		sessionId = uniqueId();
  	else
  		displayText = "Session updated successfully. Add/edit more sessions or proceed to next step.";
  	// Set base path
	var baseTestPath = "/event-data/sessions/session-nodes";
	var sessionDataPath = baseTestPath + "/session-" + sessionId;
	var path = sessionDataPath + "/" + sessionId;
	// Get the data from the form
	var params = $('#sessionForm').serialize();
	// Post the data to Sling JCR Repository
	var request = $.ajax({
	  url: path,
	  method: "POST",
	  data: params,
	  headers:{
	  	"Content-type":"application/x-www-form-urlencoded",
	  	"Content-length":params.length,
	  	"Connection":"close"
	  }
	});

	request.done(function( msg ) {
		// lazy upload image 
		var sessionbanner_form = $('#sessionbanner_form')[0];
		sessionbanner_form.action =  path;
		sessionbanner_form.submit();

		//update sessions array of event form
		var sessionsList = $('#sessionsList').val();
		var sessionIds = [];
	 	if(sessionsList != null && sessionsList != undefined)
			sessionIds = JSON.parse("[" + sessionsList + "]");
		if(sessionIds.indexOf(parseInt(sessionId)) == -1){	
			console.log("new sessionId kkj");	
			sessionIds.push(sessionId);	
		}
		$('#sessionsList').val(sessionIds.toString());

		// reload sessions table
		loadSessionsTable();
		resetForm("sessionForm");
		// Show success msg
		showSuccessMsg(".session-home-view", displayText);
	});
	 
	request.fail(function( jqXHR, textStatus ) {
		alert( "Request failed: " + textStatus );
		showErrorMsg(".session-home-view", "Something went wrong. Please try again later.");
	});
}

/* Save speaker details in JCR */
function addSpeaker(speakerId, isSpeakersPage) {
	var displayText = "Speaker created successfully";
	$('#eventIdSpeaker').val(localStorage.getItem("curEventId"));
	if(speakerId == null)
  		speakerId = uniqueId();
  	else
  		displayText = "Speaker updated successfully";
  	// Set base path
	var baseTestPath = "/event-data/speaker/speaker-nodes";
	var testPath = baseTestPath + "/speaker-" + speakerId;
	var path = testPath + "/" + speakerId;
	// Get the data from the form
	var params = $('#speakerForm').serialize();
	// Post the data to Sling JCR Repository
	var request = $.ajax({
	  url: path,
	  method: "POST",
	  data: params,
	  headers:{
	  	"Content-type":"application/x-www-form-urlencoded",
	  	"Content-length":params.length,
	  	"Connection":"close"
	  }
	});

	request.done(function( msg ) {
		// upload image
		var speakerphoto_form = $('#speakerphoto_form')[0];
		speakerphoto_form.action =  path;
		speakerphoto_form.submit();

		//update speakers array of event form
		var speakersList = $('#speakersList').val();
		var speakerIds = [];
	 	if(speakersList != null && speakersList != undefined)
			speakerIds = JSON.parse("[" + speakersList + "]");
		if(speakerIds.indexOf(parseInt(speakerId)) == -1)
			speakerIds.push(speakerId);	
		$('#speakersList').val(speakerIds.toString());

		// reload speakers table
		if(isSpeakersPage)
			loadSpeakersTable(isSpeakersPage);
		else
			loadSpeakersTable();
		//resetFormvalue
		resetForm("speakerForm");
		// Show success msg
		if(isSpeakersPage)
			showSuccessMsg(".right", displayText);
		else
			showSuccessMsg("#session-speaker-container", displayText);
	});
	 
	request.fail(function( jqXHR, textStatus ) {
	  alert( "Request failed: " + textStatus );
	});		
}

/* Save event details in JCR */
function addEvent(eventId, isPublished) {
	var successMsg;
	if(eventId == null){
		var eventId = uniqueId();
		successMsg = "";
	}
	else {
		successMsg = "";
	}
  	// Set base path
	var baseTestPath = "/event-data/event/event-nodes";
	var testPath = baseTestPath + "/event-" + eventId;
	var path = testPath + "/" + eventId;

	// check sessions and partners and define the status of the event
	setEventState(isPublished);

	// Get the data from the form
	var params = $('#eventForm').serialize();
	// Post the data to Sling JCR Repository
	var request = $.ajax({
	  url: path,
	  method: "POST",
	  data: params,
	  headers:{
	  	"Content-type":"application/x-www-form-urlencoded",
	  	"Content-length":params.length,
	  	"Connection":"close"
	  }
	});

	request.done(function( msg ) {
		// upload image
		var eventbanner_form = $('#eventbanner_form')[0];
		eventbanner_form.action =  path;
		eventbanner_form.submit();
		localStorage.setItem("curEventId", eventId);
		if(isPublished){
			// in home page - load only events table
			loadEventsTable();
		}
		else{
			loadSessionsTable();
			loadSpeakersTable();
		}
		clearMsgs(".right");
	});
	 
	request.fail(function( jqXHR, textStatus ) {
	  alert( "Request failed: " + textStatus );
	});
}

function getAllSpeakers(){
	  var basePath = "/event-data/speaker/speaker-nodes";
	  var speaker_nodes = Sling.getContent(basePath, 3);
	  console.log("speaker nodes = %o",speaker_nodes);
	  var speakers = [];
	  for(var i in speaker_nodes) {
	  	if(i == undefined) break;
	    var speakerkey = i.split('-')[1];
	    speaker = speaker_nodes[i][speakerkey];
	    speaker.speakerId = speakerkey;
	    if(speaker.photoImage != undefined){
	    	delete speaker.photoImage;
	    	speaker.photoImageHttpUrl = "http://"+location.host+basePath+"/"+i+"/"+speakerkey+"/photoImage";
	    	speaker.photoImageHttpsUrl = "https://"+location.host+basePath+"/"+i+"/"+speakerkey+"/photoImage";
	    } 
	    speakers.push(speaker);
	  }
	  console.log("final speakers object = %o",speakers);
	  return speakers;
}

function redirectPage(url){
	window.location = url;
}

function loadEditEventFormData(isPublished){
	var eventId = localStorage.getItem("editEventId");
	console.log("eventId " + eventId);
	var basePath = "/event-data/event/event-nodes";
	if(eventId != null && eventId != undefined){
		var editeventDataPath = basePath+"/event-"+eventId+"/"+eventId;
		var event_node = Sling.getContent(editeventDataPath , 1);
		console.log("events nodes = %o",event_node);
		if(event_node != null && event_node != undefined){
			$('#shortTitle').val(event_node.shortTitle);
			$('#edit-event-title').html(event_node.shortTitle +" - Edit Event Details");
			$('#longTitle').val(event_node.longTitle);
			$('#shortDesc').val(event_node.shortDesc);
			$('#longDesc').val(event_node.longDesc);
			$('#hall').val(event_node.hall);
			$('#startDate').val(event_node.startDate);
			$('#startTime').val(event_node.startTime);
			$('#endDate').val(event_node.endDate);
			$('#endTime').val(event_node.endTime);
			$('#location').val(event_node.location);
			$('#venue').val(event_node.venue);
			$('#city').val(event_node.city);
			$('#eventId').val(event_node.eventId);
			if(event_node.partners != null)
				$('#partnersList').val(event_node.partners.toString());
			if(event_node.sessionIds != null)
				$('#sessionsList').val(event_node.sessionIds.toString());
			$('#eventImgBanner').attr('src',event_node.imgBase64);
			$('#eventImgId').val(event_node.image);
			$('#eventImgBase64').val(event_node.imgBase64);
			$('#addressText').val(event_node.addressText);
			$('#locationLat').val(event_node.locationLat);
			$('#locationLong').val(event_node.locationLong);
			$('#eventState').val(event_node.eventState);
		}
		else{
			redirectPage("/IgniteAdmin/design/home.html");
		}
	}
	else{
		redirectPage("/IgniteAdmin/design/home.html");
	}
}

function loadEditSessionFormData(sessionId){
	//var sessionId = localStorage.getItem("editSessionId");
	resetForm('sessionForm');
	console.log("sessionId " + sessionId);
	var basePath = "/event-data/sessions/session-nodes";
	if(sessionId != null && sessionId != undefined){
		var editSessionDataPath = basePath+"/session-"+sessionId+"/"+sessionId;
		var session = Sling.getContent(editSessionDataPath , 1);
		console.log("session = %o",session);
		if(session != null && session != undefined){
			//load form details
			$('#sessionForm #shortTitle').val(session.shortTitle);
			$('#eventIdSession').val(session.eventId);
			$('#sessionForm #longTitle').val(session.longTitle);
			$('#sessionForm #shortDesc').val(session.shortDesc);
			$('#sessionForm #longDesc').val(session.longDesc);
			$('#sessionForm #hall').val(session.hall);
			$('#sessionForm #sessionStartDate').val(session.startDate);
			$('#sessionForm #startTime').val(session.startTime);
			$('#sessionForm #endTime').val(session.endTime);
			$('#sessionid').val(session.sessionId);
			$('#speakersList').val(session.presenters);
			$('#categoriesList').val(session.categories);
			var categoriesArray = JSON.parse("["+session.categories+"]");
			$('#sessionCategoriesDropdown').selectivity('value',categoriesArray);
			$('#sessionImgId').val( session.image); 
			$('#sessionImgBase64').val(session.imgBase64);
			$('#sessionForm #addressText').val(session.addressText);
			$('#sessionForm #locationLat').val(session.locationLat);
			$('#sessionForm #locationLong').val(session.locationLong);			
			$('#sessionImgBanner').attr('src', session.imgBase64);

			//load sessionTimingsForm 
			$('#sessionTimingsForm #hall0').val(session.hall);
			$('#sessionTimingsForm #startDate0').val(session.startDate);
			$('#sessionTimingsForm #startTime0').val(session.startTime);
			$('#sessionTimingsForm #endTime0').val(session.endTime);
			$('#sessionTimingsForm #addressText0').val(session.addressText);

			// reload speakers table
			loadSpeakersTable();

			// show session form
			showSessionForm();
			$('#save-session-link').css('display', 'none');
			$('#update-session-link').css('display', 'inline');
		}	 
	}
} 

function loadEditSpeakerFormData(speakerId){
	console.log("speakerId " + speakerId);
	var basePath = "/event-data/speaker/speaker-nodes";
	console.log("speaker = %o",speaker);
	if(speakerId != null && speakerId != undefined){
		var editSessionDataPath = basePath+"/speaker-"+speakerId+"/"+speakerId;
		var speaker = Sling.getContent(editSessionDataPath , 1);
		console.log("speaker = %o",speaker);
		if(speaker != null && speaker != undefined){
			//load form details
			$('#firstName').val(speaker.firstName);
			$('#lastName').val(speaker.lastName);
			$('#speakerForm #shortDesc').val(speaker.shortDesc);
			$('#speakerForm #longDesc').val(speaker.longDesc);
			$('#email').val(speaker.email);
			$('#phone').val(speaker.phone);
			$('#designation').val(speaker.designation);
			$('#linkedInLink').val(speaker.linkedInLink);
			$('#twitterLink').val(speaker.twitterLink);
			$('#speakerid').val(speaker.presenterId);
			$('#speakerImgId').val(speaker.image);
			$('#eventIdSpeaker').val(speaker.eventId);
			$('#speakerImgBase64').val(speaker.imgBase64);
			if(speaker.imgBase64 != undefined){
				$('#speakerImgBanner').attr('src',speaker.imgBase64);	
			}

			//show speaker form
			$('#save-speaker-link').css('display', 'none');
			$('#update-speaker-link').css('display', 'inline');
			$('#add-new-speaker-inline').show(1000);
		}
	} 
}

function loadEditSponsorFormData(sponsorId){
	resetForm('sponsorForm');
	clearMsgs(".partners-home-view");
	console.log("sponsorId " + sponsorId);
	var basePath = "/event-data/partner/partner-nodes";
	if(sponsorId != null && sponsorId != undefined){
		var editSponsorDataPath = basePath+"/partner-"+sponsorId+"/"+sponsorId;
		var sponsor = Sling.getContent(editSponsorDataPath , 1);
		console.log("sponsor = %o",sponsor);
		if(sponsor != null && sponsor != undefined){
			$('#sponsorTitle').val(sponsor.sponsorTitle);
			$('#sponsorId').val(sponsor.sponsorId);
			$('#name').val(sponsor.name);
			$('#websiteLink').val(sponsor.websiteLink);
			$('#sponsorImgBanner').attr('src',sponsor.imgBase64);
			$('#sponsorImgId').attr('value',sponsor.image);
			$('#sponsorImgBase64').attr('value', sponsor.imgBase64);
			loadEvents();
			$("option[value='"+sponsor.eventId+"']").attr('selected','selected');
			//show form
			$('#add-new-sponsor-inline').show(1000);
			$('.fx.skip-sponsors').hide();
			$('#save-sponsor-btn').css('display','none');
			$('#update-sponsor-btn').css('display','inline');
		}
	}
}

function loadEditQuestionFormData(questionId){
	resetForm('surveyForm');
	console.log("questionId " + questionId);
	var basePath = "/event-data/questions/question-nodes";
	if(questionId != null && questionId != undefined){
		var editQuestionDataPath = basePath+"/question-"+questionId+"/"+questionId;
		var question = Sling.getContent(editQuestionDataPath , 1);
		console.log("question = %o",question);
		if(question != null && question != undefined){
			$('#question').val(question.question);
			$('#option1').val(question.option1);
			$('#option2').val(question.option2);
			$('#option3').val(question.option3);
			//show form
			$('#save-question-link').css('display', 'none');
			$('#update-question-link').css('display', 'inline');
			$('#cancel-question-link').css('display', 'inline');
			$('#add-new-question').show(1000);
			$('#question-action-links-wrapper').show();
			$('#publish-survey-questions').hide();
		}
	}
}

function loadEditPromotionFormData(promotionId){
	resetForm('promotionForm');
	clearMsgs(".right");
	$('#promotionImgBanner').removeClass('imgInvalid');
	var basePath = "/event-data/promotions/promotion-nodes";
	if(promotionId != null && promotionId != undefined){
		var dataPath = basePath+"/promotion-"+promotionId+"/"+promotionId;
		var promotion = Sling.getContent(dataPath , 1);
		console.log("promotion = %o",promotion);
		if(promotion != null && promotion != undefined){
			$('#promotionId').val(promotion.promotionId);
			$('#promotionTitle').val(promotion.promotionTitle);
			$('#showFrom').val(promotion.showFrom);
			$('#showTo').val(promotion.showTo);
			$('#rsvpLink').val(promotion.rsvpLink);
			loadEvents();
			console.log($("option[value='"+promotion.calendarEventId+"']"));
			$("option[value='"+promotion.calendarEventId+"']").attr('selected','selected');
			$('#calendarEventId').val(promotion.calendarEventId);
			$('#eventTitle').val(promotion.eventTitle);
			$('#fromTime').val(promotion.fromTime);
			$('#toTime').val(promotion.toTime);
			$('#fromDate').val(promotion.fromDate);
			$('#toDate').val(promotion.toDate);
			$('#venue').val(promotion.venue);
			$('#location').val(promotion.location);
			$('#promotionImgBanner').attr('src',promotion.imgBase64);
			$('#promotionImgId').attr('value',promotion.image);
			$('#promotionImgBase64').attr('value',promotion.imgBase64);
		}
		showPromotionForm();
		$('#save-promotion-link').css('display', 'none');
		$('#update-promotion-link').css('display', 'inline');
		$('#cancel-promotion-link').css('display', 'inline');
	}
} 

/* Session categories */
function addSessionCategory() {
	  	var sessionCategoryId = uniqueId();
	  	// Set base path
		var baseTestPath = "/event-data/sessioncategory/sessioncategory-nodes";
		var testPath = baseTestPath + "/sessioncategory-" + sessionCategoryId;
		var path = testPath + "/" + sessionCategoryId;
		// Get the data from the form
		var params = $('#sessioncategoryForm').serialize();
		// Post the data to Sling JCR Repository
		var request = $.ajax({
		  url: path,
		  method: "POST",
		  data: params,
		  headers:{
		  	"Content-type":"application/x-www-form-urlencoded",
		  	"Content-length":params.length,
		  	"Connection":"close"
		  }
		});
 
		request.done(function( msg ) {
			console.log('session category added');
		});
		 
		request.fail(function( jqXHR, textStatus ) {
		  window.location = "/index.html"; 
		});
		return sessionCategoryId;
}

function getSessionCategoryNodes(){
	  var basePath = "/event-data/sessioncategory/sessioncategory-nodes";
	  var sessioncategory_nodes = Sling.getContent(basePath, 3);
	  console.log("sessioncategories nodes = %o",sessioncategory_nodes);
	  var sessioncategories = [];
	  for(var i in sessioncategory_nodes) {
	  	if(i == undefined) break;
	    var sessioncategorykey = i.split('-')[1];
	    sessioncategory = sessioncategory_nodes[i][sessioncategorykey];
	    sessioncategory.categoryId = parseInt(sessioncategorykey);
	    delete sessioncategory['jcr:primaryType'];
	    sessioncategories.push(sessioncategory);
	  }
	  return sessioncategories;
}

function constructSelectivityDataForSessionCategories(sessionCategories){
	var pluginItems = [];
	for(i=0;i<sessionCategories.length;i++){
		var pluginItem = {
			id: sessionCategories[i].categoryId,
			text: sessionCategories[i].categoryName
		};
		pluginItems.push(pluginItem);
	}
	return pluginItems;
}

function initializeSelectivityForSessionCategories(){
	console.log('initializeSelectivityForSessionCategories');
	 var sessionCategoryNodes = getSessionCategoryNodes();
	 var items = constructSelectivityDataForSessionCategories(sessionCategoryNodes);
	 $('#sessionCategoriesDropdown').selectivity({
		 items: items,
		 multiple: true,
	   	 placeholder: 'Which category your session belongs to?',
	   	 createTokenItem: function(token){
	   	 	console.log('create token item called %o',token);
	   	 	$('.selectivity-multiple-input').val("");
	   	 	var itemArray = $('#sessionCategoriesDropdown').selectivity('data');
	   	 	// When there are no categories in the system
	   	 	if(itemArray == ""){
	   	 		console.log('added session cat if');
	   	 		$('#sessioncategoryForm #categoryName').val(token);
   	 			var sessionCategoryId = addSessionCategory();
		   	 	var pluginItem = {
					id: sessionCategoryId,
					text: token
				};
				// Refresh the selectivty since new session category has been added to the system
				initializeSelectivityForSessionCategories();
				return pluginItem;
	   	 	}
	   	 	// Session categories are available : Some are already selected
	   	 	else{
	   	 		// Get the item text values from itemArray
	   	 		var itemTexts = [];
	   	 		for(i in itemArray){
	   	 			itemTexts.push(itemArray[i].text);
	   	 		}
	   	 		// Check if the token is already selected/avaialble in the system
	   	 		if(itemTexts.indexOf(token)!=-1){
	   	 			// Don't add, clear the input field
	   	 			return null;
	   	 		} else {
	   	 			// Add it to JCR
	   	 			$('#sessioncategoryForm #categoryName').val(token);
	   	 			var sessionCategoryId = addSessionCategory();
			   	 	var pluginItem = {
						id: sessionCategoryId,
						text: token
					};
					// Refresh the selectivty since new session category has been added to the system
					initializeSelectivityForSessionCategories();
					return pluginItem;
	   	 		}
	   	 	}
	   	 	
	   	}
	});	
}

function accessControl(){
	// Check if the user is logged in
	var sessionInfo = Sling.getSessionInfo();
	if(sessionInfo.userID == "anonymous"){
		window.location = "/index.html";
	}
}

function loadEvents(){
	var basePath = "/event-data/event/event-nodes";
	var events_nodes = Sling.getContent(basePath, 3);
	console.log("events nodes = %o",events_nodes);
	if(events_nodes == "" || events_nodes == null || events_nodes == undefined){
		$('#events_dropdown').html("<option value='0'>No Events</option>");
		return;
	}
	var events = [];
	$('#events_dropdown').empty();
	 $('#events_dropdown').append("<option value='0' disabled>Select Event</option>");
	for(var i in events_nodes) {
	  	if(i == undefined) break;
	    var eventskey = i.split('-')[1];
	    event = events_nodes[i][eventskey];
	    event.eventId = eventskey;
	    events.push(event);
	    var eventsJson = escape(JSON.stringify(events));
	    $('#events_dropdown').append("<option value='"+eventskey+"'>"+event.shortTitle+"</option>");
	}	
}

function associateEvent(eventId){
  	event = Sling.getContent('/event-data/event/event-nodes/event-'+eventId,2);
  	$('#calendarEventId').val(eventId);
  	$('#eventTitle').val(event[eventId].shortTitle);
  	$('#startDate').val(event[eventId].startDate);
  	$('#endDate').val(event[eventId].endDate);
  	$('#startTime').val(event[eventId].startTime);
  	$('#endTime').val(event[eventId].endTime);
  	$('#venue').val(event[eventId].venue);
  	$('#locationLat').val(event[eventId].locationLat);
  	$('#locationLong').val(event[eventId].locationLong);
}

/**** Publish Methods ****/
function publishSurveyQuestions(){
	var basePath = "/event-data/questions/question-nodes";
	var question_nodes = Sling.getContent(basePath, 2);
	console.log("question nodes = %o",question_nodes);
	var questions = [];
	for(var i in question_nodes) {
		if(i == undefined) break;
		var questionId = i.split('-')[1];
		var question = question_nodes[i][questionId];
		question.qId = questionId;	
		delete question['jcr:primaryType'];
		questions.push(question);
	}
	console.log("publish questions %o", JSON.stringify(questions));

	$.ajax({
	    type: "POST",
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    data: JSON.stringify(questions),
	    url: "http://eventsapp.mybluemix.net/SurveyQuestionsBulkUpdate",
	    complete: function(){
	    	console.log("success");
	    	$('#publishQuestionDialog .pubquestions.text').html("Questions published <i class='green fa fa-check pl10 hide'></i>");
		    $('#publishQuestionDialog .pubquestions.text').addClass('green');
	    }
	});
}

function publishPromotions(){
	var basePath = "/event-data/promotions/promotion-nodes";
	var promotions_nodes = Sling.getContent(basePath, 3);
	console.log("promotions nodes = %o",promotions_nodes);
	var promotions = [];
	for(var i in promotions_nodes) {
		if(i == undefined) break;
		var promotionskey = i.split('-')[1];
		promotion = promotions_nodes[i][promotionskey];
		promotion.promotionId = parseInt(promotionskey);
		promotions.push(promotion);
		var localFromTime = promotion.fromTime;
		var localToTime = promotion.toTime;
		// Set fromtime and to time
		var fromTimeISO = generateISODate(promotion.startDate, promotion.startTime);
		var toTimeISO = generateISODate(promotion.endDate, promotion.endTime);
		promotion.fromTime = {"__type": "Date", "iso": fromTimeISO};
		promotion.toTime = {"__type": "Date", "iso": toTimeISO};
		// Set showFrom and showTo
		var showFromISO = generateISODate(promotion.fromDate, localFromTime);
		var showToISO = generateISODate(promotion.toDate, localToTime);
		promotion.showFrom = {"__type": "Date", "iso": showFromISO};
		promotion.showTo = {"__type": "Date", "iso": showToISO};
		promotion.location = {"__type": "Location",
		"coordinates": [parseFloat(promotion.locationLat),parseFloat(promotion.locationLong)],
		"altitude": 99.0  
		};
		delete promotion.locationLat;
		delete promotion.locationLong;
		delete promotion.imgBase64;
		delete promotion['jcr:primaryType'];
		delete promotion.startDate;
		delete promotion.fromDate;
		delete promotion.startTime;
		delete promotion.endTime;
		delete promotion.endDate;
		delete promotion.toDate;
		delete promotion.promotionTitle;
		for(i in promotion){
			if(i.indexOf('.')!=-1) delete promotion[i];
		}
	}
	console.log("publish promotions %o", JSON.stringify(promotions));
	$.ajax({
	    type: "POST",
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    data: JSON.stringify(promotions),
	    url: "http://eventsapp.mybluemix.net/PromotionBulkUpdate",
	    complete: function(){
	    	console.log("success");
	    	$('#publishPromotionDialog .pubpromotions.text').html("Promotions published <i class='green fa fa-check pl10 hide'></i>");
		    $('#publishPromotionDialog .pubpromotions.text').addClass('green');
	    }
	});
}

/** 
	Query all images 
**/
function queryAllImages(basePath){
	console.log("basePath in caller", basePath);
	var nodes = Sling.getContent(basePath, 3);
	var imagePayloadArr = [];
	for(var i in nodes){
    	if(i == undefined) break;
    	var sessionKey = i.split('-')[1];
	    session = nodes[i][sessionKey];
	    for(var img in session){
	    	if(img.indexOf('.')!=-1){
	    		var imagePayload = {
	    			"imageId" :  parseInt(session.image),
	    			"imageData" : session.imgBase64,
	    			"imageName" : img.split('.')[0],
					"imageExtension" : img.split('.')[1],
					"imageHeight" : 290,
					"imageWidth" : 600,
					"uploadedDate" : {
						"__type": "Date",
             			"iso": new Date(session[img]['jcr:created']).toISOString()
					},
					"modifiedDate" : {
						"__type": "Date",
             			"iso": new Date(session[img]['jcr:created']).toISOString()
					}	
	    		}
	    	console.log("imagePayload %o", imagePayload);
	    	imagePayloadArr.push(imagePayload);
	    	} 
	    } 
    }
    return imagePayloadArr;
}

function publishAllImages(){
	var basePathArr = ["/event-data/sessions/session-nodes", "/event-data/speaker/speaker-nodes", "/event-data/event/event-nodes", "/event-data/promotions/promotion-nodes", "/event-data/partner/partner-nodes"];
	var imageMock = "/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAABkAAD/4QOBaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjYtYzAxNCA3OS4xNTY3OTcsIDIwMTQvMDgvMjAtMDk6NTM6MDIgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdFJlZj0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlUmVmIyIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bXBNTTpPcmlnaW5hbERvY3VtZW50SUQ9InhtcC5kaWQ6ZjcwZjU4NjAtNGI0Ny00OGRlLTg0ZDctZWZkNmUwNjI5NjA3IiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkUzMERFQUY0QUY0MjExRTQ4ODE0OUU4MzMzQkRGMEUwIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkUzMERFQUYzQUY0MjExRTQ4ODE0OUU4MzMzQkRGMEUwIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE0IChNYWNpbnRvc2gpIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6ZjcwZjU4NjAtNGI0Ny00OGRlLTg0ZDctZWZkNmUwNjI5NjA3IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOmY3MGY1ODYwLTRiNDctNDhkZS04NGQ3LWVmZDZlMDYyOTYwNyIvPiA8L3JkZjpEZXNjcmlwdGlv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";
	imageBinary = [], imageDataPoc = [], imageMetaData = [];
	for(i = 0; i< basePathArr.length; i++){
		console.log("basePath", basePathArr[i]);
		var imagePayloadArr = queryAllImages(basePathArr[i]);
		console.log("imagePayloadArr %o", imagePayloadArr);
		for(j = 0; j< imagePayloadArr.length; j++){
			var base64ImageData = imagePayloadArr[j].imageData;
			base64ImageData = base64ImageData.split('base64,')[1];
			//imagePayloadArr[j].imageData = imageMock;
			var temp1 = {
		    	"imageId" :  imagePayloadArr[j].imageId,
		    	"imageData" : {
					"__type": "Bytes",
			    	"base64": base64ImageData
		        }
	    	};
		    var temp2 = {
		    	"imageId" :  imagePayloadArr[j].imageId,
		    	"imageData" : base64ImageData
		    };
		    var temp3 = {
		    	"imageId" :  imagePayloadArr[j].imageId,
    			"imageName" :imagePayloadArr[j].imageName,
				"imageExtension" : imagePayloadArr[j].imageExtension,
				"imageHeight" : 290,
				"imageWidth" : 600,
				"uploadedDate" : imagePayloadArr[j].uploadedDate,
				"modifiedDate" : imagePayloadArr[j].modifiedDate
		    };
     		imageBinary.push(temp1);
     		imageDataPoc.push(temp2);
     		imageMetaData.push(temp3);
		}
		console.log("%o", JSON.stringify(imageDataPoc));
	}
	var isBinaryCompleted = false;
	var isDataPocCompleted = false;
	var isMetadataCompleted = false;

	//post imageBinary data 
	$.ajax({
	    type: "POST",
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    data: JSON.stringify(imageBinary),
	    url: "http://eventsapp.mybluemix.net/ImageBinaryBulkUpdate",
	    complete: function(){
	    	console.log("success");
	    	isBinaryCompleted = true;
	    	if(isDataPocCompleted && isMetadataCompleted){
	    		$('#publishEventDialog .pubimages.text').html("Images published <i class='green fa fa-check pl10 hide'></i>");
		    	$('#publishEventDialog .pubimages.text').addClass('green');
	    	}
	    }
	}); 

	//post imagePoc data
	$.ajax({
	    type: "POST",
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    data: JSON.stringify(imageDataPoc),
	    url: "http://eventsapp.mybluemix.net/ImageDataPocBulkUpdate",
	    complete: function(){
	    	isDataPocCompleted = true;
	    	if(isBinaryCompleted && isMetadataCompleted){
	    		$('#publishEventDialog .pubimages.text').html("Images published <i class='green fa fa-check pl10 hide'></i>");
		    	$('#publishEventDialog .pubimages.text').addClass('green');
	    	}
	    }
	});

	//post imageMetaData
	$.ajax({
	    type: "POST",
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    data: JSON.stringify(imageMetaData),
	    url: "http://eventsapp.mybluemix.net/ImageMetaDataBulkUpdate",
	    complete: function(){
	    	isMetadataCompleted = true;
	    	if(isDataPocCompleted && isBinaryCompleted){
	    		$('#publishEventDialog .pubimages.text').html("Images published <i class='green fa fa-check pl10 hide'></i>");
		    	$('#publishEventDialog .pubimages.text').addClass('green');
	    	}
	    }
	});
}
function publishEventDetails(eventId){
	console.log("publish event" + eventId);
	var events = [];
	var basePath = "/event-data/event/event-nodes";
	if(eventId != null && eventId != undefined){
		var dataPath = basePath+"/event-"+eventId+"/"+eventId;
		var event_node = Sling.getContent(dataPath , 1);
		console.log("events node = %o",event_node);
		event_node.eventId = parseInt(eventId);
		var sessionIds = JSON.parse("["+event_node.sessionIds+"]");
		var partners = JSON.parse("["+event_node.partners+"]");
		event_node.sessionIds = sessionIds;
		event_node.partners = partners;
		event_node.calendarIds = JSON.parse(event_node.calendarIds);
		var fromTimeISO = generateISODate(event_node.startDate, event_node.startTime);
		var toTimeISO = generateISODate(event_node.endDate, event_node.endTime);
		event_node.fromTime = {"__type": "Date", "iso": fromTimeISO};
		event_node.toTime = {"__type": "Date", "iso": toTimeISO};
		var locationLat = new Number(parseFloat(event_node.locationLat).toFixed(6));
		var locationLong = new Number(parseFloat(event_node.locationLong).toFixed(6));
		event_node.location = {"__type": "Location",
			"coordinates": [locationLat,locationLong],
			"altitude": 99.1,
			"time": {"__type":"Date", "iso": "2014-02-23T11:00:500.272000-08:00"} 
			};
		delete event_node.locationLat;
		delete event_node.locationLong;
		delete event_node.startDate;
		delete event_node.startTime;
		delete event_node.endTime;
		delete event_node.endDate;
		delete event_node.imgBase64;
		delete event_node['jcr:primaryType'];
		delete event_node.addressText;
		delete event_node.eventState;
		for(i in event_node){
			if(i.indexOf('.')!=-1) delete event_node[i];
		}
		events.push(event_node);
		console.log("events json %o", JSON.stringify(events));

		$.ajax({
		    type: "POST",
		    contentType: 'application/json; charset=utf-8',
		    dataType: 'json',
		    data: JSON.stringify(events),
		    url: "http://eventsapp.mybluemix.net/EventBulkUpdate",
		    complete: function(){
		    	console.log("publish event success");
		    	$('#publishEventDialog .pubevent.text').html("Event details published <i class='green fa fa-check pl10 hide'></i>");
		    	$('#publishEventDialog .pubevent.text').addClass('green');
		    }
		});
	}
}

function publishSessions(eventId){
	var basePath = "/event-data/sessions/session-nodes";
	var session_nodes = Sling.getContent(basePath, 3);
	console.log("session nodes = %o",session_nodes);
	var sessions = [];
	for(var i in session_nodes) {
		if(i == undefined) break;
		var sessionkey = i.split('-')[1];
		var session = session_nodes[i][sessionkey];
		if(session.eventId == eventId){
			session.sessionId = parseInt(sessionkey);
			session.isHidden = false;
			var fromTimeISO = generateISODate(session.startDate, session.startTime);
			var toTimeISO = generateISODate(session.startDate, session.endTime);
			session.fromTime = {"__type": "Date", "iso": fromTimeISO};
			session.toTime = {"__type": "Date", "iso": toTimeISO};
			var locationLat = new Number(parseFloat(session.locationLat).toFixed(6));
			var locationLong = new Number(parseFloat(session.locationLong).toFixed(6));
			session.location = {"__type": "Location",
			"coordinates": [locationLat,locationLong],
			"altitude": 99.1,
			"time": {"__type":"Date", "iso": "2014-02-23T11:00:500.272000-0800"}  
			};
			delete session.locationLat;
			delete session.locationLong;
			delete session.startDate;
			delete session.startTime;
			delete session.endTime;
			delete session.imgBase64;
			delete session['jcr:primaryType'];
			delete session.addressText;
			delete session.eventId;
			delete session.categories;
			session.presenters = JSON.parse("["+session.presenters+"]");
			for(i in session){
				if(i.indexOf('.')!=-1) delete session[i];
			}
			sessions.push(session);
		}
	}
	console.log("session json %o", JSON.stringify(sessions));

	$.ajax({
	    type: "POST",
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    data: JSON.stringify(sessions),
	    url: "http://eventsapp.mybluemix.net/SessionBulkUpdate",
	    complete: function(){
	    	console.log("success");
	    	$('#publishEventDialog .pubsession.text').html("Sessions published <i class='green fa fa-check pl10 hide'></i>");
		    $('#publishEventDialog .pubsession.text').addClass('green');
	    }
	});
}

function publishPartners(eventId){
	var basePath = "/event-data/partner/partner-nodes";
	var sponsor_nodes = Sling.getContent(basePath, 3);
	console.log("sponsors nodes = %o",sponsor_nodes);
	var sponsors = [];
	for(var i in sponsor_nodes) {
		if(i == undefined) break;
		var sponsorkey = i.split('-')[1];
		var sponsor = sponsor_nodes[i][sponsorkey];
		if(sponsor.eventId == eventId){
			sponsor.sponsorId = parseInt(sponsorkey);
			sponsor.logo = sponsor.image;
			for(i in sponsor){
				if(i.indexOf('.')!=-1) delete sponsor[i];
				}
			delete sponsor.imgBase64;
			delete sponsor.sponsorTitle;
			delete sponsor['jcr:primaryType'];
			delete sponsor.image;
			delete sponsor.eventId;
			for(i in sponsor){
				if(i.indexOf('.')!=-1) delete sponsor[i];
			}
			sponsors.push(sponsor);
		}
	}
	console.log("publish sponsors %o", JSON.stringify(sponsors));

	$.ajax({
	    type: "POST",
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    data: JSON.stringify(sponsors),
	    url: "http://eventsapp.mybluemix.net/SponsorBulkUpdate",
	    complete: function(){
	    	console.log("success");
	    	$('#publishEventDialog .pubpartners.text').html("Partners published <i class='green fa fa-check pl10 hide'></i>");
		    $('#publishEventDialog .pubpartners.text').addClass('green');
	    }
	});
}

function publishSpeakers(eventId){
	var basePath = "/event-data/speaker/speaker-nodes";
	var speaker_nodes = Sling.getContent(basePath, 3);
	console.log("speaker nodes = %o",speaker_nodes);
	var speakers = [];
	for(var i in speaker_nodes) {
		if(i == undefined) break;
	var speakerkey = i.split('-')[1];
	var speaker = speaker_nodes[i][speakerkey];
	if(speaker.eventId == eventId){
		speaker.presenterId = parseInt(speakerkey);
		for(i in speaker){
			if(i.indexOf('.')!=-1) delete speaker[i];
		}
		delete speaker.imgBase64;
		delete speaker['jcr:primaryType'];
		delete speaker.eventId;
		speakers.push(speaker);
		}
	}
	console.log("publish speakers %o", JSON.stringify(speakers));

	$.ajax({
	    type: "POST",
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    data: JSON.stringify(speakers),
	    url: "http://eventsapp.mybluemix.net/PresenterBulkUpdate",
	    complete: function(){
	    	console.log("kjkkkk");
	    	$('#publishEventDialog .pubspeakers.text').html("Speakers published <i class='green fa fa-check pl10 hide'></i>");
		    $('#publishEventDialog .pubspeakers.text').addClass('green');
	    }
	});
}

function publishSessionCategories(eventId){
	var basePath = "/event-data/sessions/session-nodes";
	var session_nodes = Sling.getContent(basePath, 3);
	console.log("session nodes = %o",session_nodes);

	var basePath = "/event-data/sessioncategory/sessioncategory-nodes";
	var sessioncategory_nodes = Sling.getContent(basePath, 3);
	console.log("sessioncategories nodes = %o",sessioncategory_nodes);
	var sessioncategories = [];
	for(var i in sessioncategory_nodes) {
		if(i == undefined) break;
		var sessioncategorykey = i.split('-')[1];
		var sessioncategory = sessioncategory_nodes[i][sessioncategorykey];
		sessioncategory.categoryId = parseInt(sessioncategorykey);
		console.log("sessioncategorykey "+ sessioncategorykey);
		var sessionsArr = [];
		for(var j in session_nodes){
			if(j == undefined) break;
			var sessionkey = j.split('-')[1];
			var session = session_nodes[j][sessionkey];
			var categoryIds = JSON.parse("[" + session.categories + "]");
			console.log("categoryIds %o", categoryIds);
			if(categoryIds.indexOf(parseInt(sessioncategorykey)) != -1){
				sessionsArr.push(sessionkey);
				console.log("matching session found");
			}
		}
		console.log("sessionsArr %o", sessionsArr);
		sessioncategory.sessions = sessionsArr.toString();
		delete sessioncategory['jcr:primaryType']; 
		sessioncategories.push(sessioncategory);
	}
	console.log("publish sessionCategories %o", JSON.stringify(sessioncategories));
	$.ajax({
	    type: "POST",
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    data: JSON.stringify(sessioncategories),
	    url: "http://eventsapp.mybluemix.net/SessionCategoryBulkUpdate",
	    complete: function(){
	    	console.log("success");
	    	$('#publishEventDialog .pubsessioncats.text').html("Session categories published <i class='green fa fa-check pl10 hide'></i>");
		    $('#publishEventDialog .pubsessioncats.text').addClass('green');
	    }
	});
}

/**** Utility methods *****/

/* Set the event state based on partners and sessions array */
function setEventState(isPublished){
	var sessionsList = $('#sessionsList').val();
	var partnersList = $('#partnersList').val();
	var eventState = $('#eventState');
	if(isPublished)
		eventState.val("Published");
	else if(sessionsList != "" && partnersList != "")
		eventState.val("Ready to publish");
	else 
		eventState.val("Draft");
}

function resetForm(form){
  	$('#' + form).find(':input').each(function(){
  		$(this).val('');
	});
	$('#' + form)[0].reset();
}

function resetImageForm(form, formBanner){
	$('#'+ form + ' img').attr('src','../images/noimage.png');
	$('#' + form).find(':input').each(function(){
  		$(this).val('');
	});
	$('#' + formBanner).removeClass("imgInvalid");
}

function resetSelectivityData(){
	$('#sessionCategoriesDropdown').selectivity("value",[]);
}

// customize datatable search bar
function customizeDTableSearch(dTable, placeholderText){
	$('#' + dTable + '_filter input').attr('placeholder', placeholderText);
	var div=$("#" + dTable + "_filter label")[0];
	if(div.childNodes.length){
	   for(var i=0;i<div.childNodes.length;i++)
	   {    console.log(div.childNodes[i]);
	       if(div.childNodes[i].nodeType===3)
	           div.removeChild(div.childNodes[i]);
	   }
	}
	$('#' + dTable + '_filter label').append("<i class='fa fa-search'></i>");
}

function generateISODate(dateStr, timeStr){
	var date = new Date(dateStr);
	var parts = timeStr.match(/(\d+)\:(\d+)(\w+)/);
    var hours = /am/i.test(parts[3]) ? parseInt(parts[1], 10) : parseInt(parts[1], 10) + 12;
    var minutes = parseInt(parts[2], 10);
	date.setHours(hours);
	date.setMinutes(minutes);
	var isoDateStr = date.toISOString();
	isoDateStr = isoDateStr.replace('Z', '000-00:00');
	return isoDateStr;
}

var suggestLocation = function(event){
	console.log('in suggestLocation');
	var givenAddress = $("input[name='addressText']").val();
	if(givenAddress.trim().length > 3) {
		console.log('givenAddress='+givenAddress);
		var xhrArgs = {
			url : 'https://maps.googleapis.com/maps/api/geocode/json',
			dataType : 'json',
			data : { 'address' : givenAddress, 'key' : 'AIzaSyC0lael149fcf5rNl0PPS8ZX2fGPkNHdus'},
			success : function(data){
				console.log(data);								
				if(data.status == 'OK') {					
					if(data.results) {
						if(data.results.length == 1) {
							var location = data.results[0];
							var lat = location.geometry.location.lat;
							var lon = location.geometry.location.lng;
							console.log('latitude' + lat + 'longtitude'  + lon);
							$('#eventForm #locationLat').val(lat);
							$('#eventForm #locationLong').val(lon);
							$('#sessionForm #locationLat').val(lat);
							$('#sessionForm #locationLong').val(lon);
						}
						if(data.results.length > 1) {
							// autocomplete
							var suggestions = [];
							for( var index in data.results) {
								var location = data.results[index];
								suggestions.push(location.formatted_address);
							}
							$("input[name='addressText']").autocomplete({
							  source: suggestions
							});
							$("input[name='addressText']").autocomplete("search");
						}
					} 
				}
			}
		};
		$.get(xhrArgs.url, xhrArgs.data).done(xhrArgs.success);
	}
}

function clearMsgs(targetParent){
	$(targetParent + ' #errorPlaceholder').hide();
	$(targetParent + ' #successPlaceholder').hide();
}

function showErrorMsg(targetParent, msg){
	$(targetParent+ ' #errorPlaceholder .errorText').html(msg);
	$(targetParent + ' #errorPlaceholder').show();
	$(targetParent + ' #successPlaceholder').hide();
}

function showSuccessMsg(targetParent, msg){
	$(targetParent + ' #successPlaceholder .successText').html(msg);
	$(targetParent + ' #successPlaceholder').show();
	$(targetParent + ' #errorPlaceholder').hide();
}

function cancelNavigation(){
	$('#navigationDialog').dialog('close');
}

function continueNavigation(){
	window.location = $('#targetUrl').val();
}

function checkNavFlow(targetUrl){
	$('#targetUrl').val(targetUrl);
	$('#navigationDialog').dialog('open');
}
