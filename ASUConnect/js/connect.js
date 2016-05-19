
$(document).ready(function(){
});

function accessControl(){
	// Check if the user is logged in
	/*var sessionInfo = Sling.getSessionInfo();
	if(sessionInfo.userID == "anonymous"){
		window.location = "/index.html";
	}*/
}

function initializeSelectivityForSessionCategories(){
	console.log('initializeSelectivityForSessionCategories');
	 var sessionCategoryNodes = getSessionCategoryNodes();
	 var items = constructSelectivityDataForSessionCategories(sessionCategoryNodes);
	 $('#sessionCategoriesDropdown').selectivity({
		 items: items,
		 multiple: true,
	   	 placeholder: 'Add tags',
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

function constructQuestionTags(taglist){
	var tagArr = taglist.split(",");
	console.log("%o", tagArr);
	var tags = [];
	var tagStr = "";
	var allTags = getSessionCategoryNodes();
	for(var i in allTags){
		if(tagArr.indexOf(allTags[i].categoryId.toString()) != -1 ){
			tags.push(allTags[i].categoryName);
		}
	}
	for (var j in tags){
		tagStr += '<a href="#" class="post-tag js-gps-track" title="" rel="tag">'+ tags[j] + '</a>'
	}
	return tagStr;
}

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

function registerPostQuestionBtn(){
	$('#save-question-link').click(function(){
		addQuestion();
	});
}

function registerPostAnswerBtn(){
	$('#save-answer-link').click(function(){
		addAnswer();
	});
}

function addQuestion(questionId){
	// Set base path
	var successMsg;
	if(questionId == null){
  		questionId = uniqueId();
  		successMsg = "Question has been posted successfully.";
	}
	else{
		successMsg = "Question has been updated successfully."
	}

	// load categories
	var itemArray = $('#sessionCategoriesDropdown').selectivity('data');
	console.log('itemArray = %o',itemArray);
	var categoryIds = [];
	for(i in itemArray){
		categoryIds.push(itemArray[i].id);
	}
	console.log('category ids = '+categoryIds);
	$('#categoriesList').val(categoryIds.toString());

	//load category
	var category = $('#category_dropdown').val();
	$('#category').val(category);

	//load username
	var currUser = JSON.parse(localStorage.getItem("currentUser"));
	$('#user').val(localStorage.getItem("currUserId"));
	$('#studentLevel').val(currUser.studlevel);
	$('#level').val(currUser.level);

	$('#timestamp').val(new Date().toString());

	var baseTestPath = "/asuconnect-data/questions/question-nodes";
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
		showSuccessMsg(".right", successMsg); 
		$('#questionForm')[0].reset();
		initializeSelectivityForSessionCategories();
		$('html, body').animate({
        	scrollTop: $(".navbar").offset().top
		}, 200);
		updateUserQuestionStats(category);
	});
	 
	request.fail(function( jqXHR, textStatus ) {
		alert( "Request failed: " + textStatus );
		showErrorMsg(".right", "Something went wrong. Please try again later.");
	});
}

function addAnswer(){
	var questionId = localStorage.getItem("currQuestion");
	var basePath = "/asuconnect-data/questions/question-nodes";
	var dataPath = basePath+"/question-"+questionId+"/"+questionId;
	var answerId = uniqueId();
	var path = dataPath + "/answer-nodes/answer-" + answerId + "/" + answerId;
	var successMsg = "Answer has been posted successfully.";
	//load username
	$('#user').val(localStorage.getItem("currUserId"));
	$('#timestamp').val(new Date().toString());
	// Get the data from the form
	var params = $('#answerForm').serialize();
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
		showSuccessMsg(".right", successMsg); 
		$('#answerForm')[0].reset();
		$('html, body').animate({
        	scrollTop: $(".navbar").offset().top
		}, 200);
		loadAnswersTable();
		updateUserAnswerStats(questionId);
		updateUserAnswerStats_Difficulty(questionId);
		updateQuestionStats(questionId);
	});
	 
	request.fail(function( jqXHR, textStatus ) {
		alert( "Request failed: " + textStatus );
		showErrorMsg(".right", "Something went wrong. Please try again later.");
	});
}

function uniqueId() {
	var i = new Date().getTime();
	i = i & 0xffff; 
	return i;
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

function loadQuestionsTable(filterCriteria, filterText){
	$('#questionsTableWrapper').empty()
	$('#questionsTableWrapper').append("<table class=\"display\" width=\"100%\" id=\"questionsDTable\"></table>");
	$('#questionsDTable').DataTable({
    	"bLengthChange": false,
    	"pageLength": 5,
        data: populateQuestionsData(filterCriteria, filterText),
        columns: [
        	{ title : "EventId"},
        	{ title: "Questions" }
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
   customizeDTableSearch('questionsDTable', 'Search Questions');
}

// customize datatable search bar
function customizeDTableSearch(dTable, placeholderText){
	$('#' + dTable + '_filter input').attr('placeholder', placeholderText);
	var div=$("#" + dTable + "_filter label")[0];
	if(div.childNodes.length){
	   for(var i=0;i<div.childNodes.length;i++)
	   {   
	       if(div.childNodes[i].nodeType===3)
	           div.removeChild(div.childNodes[i]);
	   }
	}
	$('#' + dTable + '_filter label').append("<i class='fa fa-search'></i>");
}

function populateQuestionsData(filterCriteria, filterText){
	var isFilter = false;
	var currUser = JSON.parse(localStorage.getItem("currentUser"));
	var userLevel = currUser.level;
	var basePath = "/asuconnect-data/questions/question-nodes";
	var question_nodes = Sling.getContent(basePath, 3);
	var questions = [];
	if(filterCriteria != null && filterText != null)
		isFilter = true;
	console.log(isFilter, filterText, filterCriteria);
	var j = 0;
	var connectionsTxt = '';
	for(var i in question_nodes) {
		if(j%2 == 1)
			connectionsTxt = '<div class="endorse_img"><img src="../images/connection.png" width="50px" height="50px"/></div>';
		else
			connectionsTxt = '';
		j = j+1;
		console.log("ct" + connectionsTxt);
		if(i == undefined) break;
		var filteredQues = null;
		var colorCode = "darkgreen";
		var quesStr = "";
		var ratingStr = "";
		var difficultyStr = "";
		var questionArr = [];
		var questionkey = i.split('-')[1];
	    var curQuestion = question_nodes[i][questionkey];
	    var questionJson = escape(JSON.stringify(curQuestion));
	    if(curQuestion.difficulty || (curQuestion.level < userLevel)){
	    	if(isFilter){
	    		if(filterText == curQuestion[filterCriteria]){
	    			filteredQues = curQuestion;
	    		}
	    	}
	    	else{
	    		filteredQues = curQuestion;
	    	}

	    	// set rating string 
	    	if((curQuestion.level < userLevel) && curQuestion['difficulty'] == undefined)
	    		ratingStr = '<div class="rating" onclick="rateQuestion_stud('+ questionkey +')" style="display:inline-block"><span><i class="fa fa-star"></i></span><span><i class="fa fa-star"></i></span><span><i class="fa fa-star"></i></span><span><i class="fa fa-star"></i></span><span><i class="fa fa-star"></i></span></div>';
	    	else
	    		ratingStr = "";

	    	if(curQuestion['difficulty'] != undefined){
	    		difficultyStr = " / " + curQuestion['difficulty'];
	    		quesStr = '<a class="quesLink" onclick="openQuestion_stud('+ questionkey +')"><b>'+ curQuestion.title +'</b></a>';
	    	}
	    	else{
	    		difficultyStr = "";
	    		quesStr = '<a class="quesLink"><b>'+ curQuestion.title +'</b></a>';
	    	}

	    	if(filteredQues.studentLevel == "Graduate")
	    		colorCode = "darkgoldenrod";
	    	else if(filteredQues.studentLevel == "Under Graduate")
	    		colorCode = "darkred";
	    	else if(filteredQues.studentLevel == "Ph.D")
				colorCode = "darkgreen";

	    	console.log("filteredQues %o", filteredQues);
	    	if(filteredQues != null){
			    var tagStr = constructQuestionTags(curQuestion.categories);
			    var questionStr = '<div class="left pr20">' +  
		                    '<div class="quesTitle" style="color:' + colorCode +'">'+ quesStr +'</div>'+
		                    '<div class="quesDesc">' + curQuestion.description + '</div>'+
		                    '<div class="quesCategory"><i>' + curQuestion.category + difficultyStr + '</i></div>'+
		                    '<div class="post-taglist">' + tagStr + '</div>' + 
		                    '</div>'+
			    			'<div class="right action-links">'+ ratingStr + connectionsTxt +
			    			'<div class="post-signature owner" style="display:inline-block">' + 
		                    '<div class="user-info ">' + 
		                    '<div class="user-action-time">' + 
		                    'asked <span title="2016-02-22 14:02:45Z" class="relativetime">15 mins ago</span>' + 
		                    '</div>' + 
		                    '<div class="user-gravatar32">' + 
		                    '<a href="#"><div class="gravatar-wrapper-32"><img src="https://www.gravatar.com/avatar/335a9ae9364e36c131fb599feaf0e540?s=32&amp;d=identicon&amp;r=PG&amp;f=1" alt="" width="32" height="32"></div></a>' +
		                    '</div>' +
		                    '<div class="user-details">' + 
		                    '<a href="#">'+ curQuestion.user +'</a>' + 
		                    '<span class="studlevel">' + curQuestion.studentLevel +  '<span>' + 
		                    '</div>' + 
		                    '</div></div>'
							'</div>';
			  
				questionArr.push(questionkey);
				questionArr.push(questionStr);
				questions.push(questionArr);
			}
	    }
	}
	return questions;     
}
 
function openQuestion(questionKey){
	localStorage.setItem("currQuestion", questionKey);
	window.location = "/ASUConnect/design/prof-questionAnswers.html"
}

function openQuestion_stud(questionKey){
	localStorage.setItem("currQuestion", questionKey);
	window.location = "/ASUConnect/design/stud-questionAnswers.html"
}

function loadAnswersTable(){
	var questionId = localStorage.getItem("currQuestion");
	console.log("questionId", questionId);
	$('#answersTableWrapper').empty()
	$('#answersTableWrapper').append("<table class=\"display\" width=\"100%\" id=\"answersDTable\"></table>");
	$('#answersDTable').DataTable({
    	"bLengthChange": false,
    	"pageLength": 5,
        data: populateAnswersData(),
        columns: [
        	{ title : "EventId"},
        	{ title: "Answers" }
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
   customizeDTableSearch('answersDTable', 'Search Answers');

}

function populateAnswersData(){
	var questionId = localStorage.getItem("currQuestion");
	var basePath = "/asuconnect-data/questions/question-nodes/question-" + questionId + "/" + questionId + "/answer-nodes";
	var answer_nodes = Sling.getContent(basePath, 3);
	answers = [];
	var endorseTxt = "";
	var j = 0;
	for(var i in answer_nodes) {
		if(i == undefined) break;
		var answerArr = [];
		// TODO - update this
		if(j == 2)
			endorseTxt = '<div class="endorse_img"><img src="../images/endorsed.png" width="50px" height="50px"/></div>';
		else
			endorseTxt = '';
		j = j + 1; 
		var answerkey = i.split('-')[1];
	    var curAnswer = answer_nodes[i][answerkey];
	    var answerJson = escape(JSON.stringify(curAnswer));
	    var answerStr = '<div class="left pr20">' +  
                    '<div class="quesTitle">' + curAnswer.answer + '</div>'+
                    '</div>'+
	    			'<div class="right action-links">'+ endorseTxt +
	    			'<div class="post-signature owner" style="display: inline-block;">' + 
                    '<div class="user-info ">' + 
                    '<div class="user-action-time">' + 
                    'posted <span title="2016-02-22 14:02:45Z" class="relativetime">15 mins ago</span>' + 
                    '</div>' + 
                    '<div class="user-gravatar32">' + 
                    '<a href="#"><div class="gravatar-wrapper-32"><img src="https://www.gravatar.com/avatar/335a9ae9364e36c131fb599feaf0e540?s=32&amp;d=identicon&amp;r=PG&amp;f=1" alt="" width="32" height="32"></div></a>' +
                    '</div>' +
                    '<div class="user-details">' + 
                    '<a href="#">'+ curAnswer.user +'</a>' + 
                    '</div>' + 
                    '</div></div>'
					'</div>';
	  	
		answerArr.push(answerkey);
		answerArr.push(answerStr);
		answers.push(answerArr);
	}
	return answers;
}

function loadQuestionDetails(){
	var questionId = localStorage.getItem("currQuestion");
	var basePath = "/asuconnect-data/questions/question-nodes";
	var dataPath = basePath+"/question-"+questionId+"/"+questionId;
	var questionNode = Sling.getContent(dataPath , 1);
	var tagStr = constructQuestionTags(questionNode.categories);
	if(questionNode != null && questionNode != undefined){
		var questionStr = '<div class="left pr20">' +  
                    '<div class="quesTitle"><a class="quesLink" onclick="openQuestion('+ questionId +')"><b>'+ questionNode.title +'</b></a></div>'+
                    '<div class="quesDesc">' + questionNode.description + '</div>'+
                    '<div class="quesCategory"><i>' + questionNode.category + '</i></div>'+
                    '<div class="post-taglist">' + tagStr + '</div>' + 
                    '</div>'+
	    			'<div class="right action-links pb10">'+
	    			'<div class="post-signature owner">' + 
                    '<div class="user-info ">' + 
                    '<div class="user-action-time">' + 
                    'asked <span title="2016-02-22 14:02:45Z" class="relativetime">15 mins ago</span>' + 
                    '</div>' + 
                    '<div class="user-gravatar32">' + 
                    '<a href="#"><div class="gravatar-wrapper-32"><img src="https://www.gravatar.com/avatar/335a9ae9364e36c131fb599feaf0e540?s=32&amp;d=identicon&amp;r=PG&amp;f=1" alt="" width="32" height="32"></div></a>' +
                    '</div>' +
                    '<div class="user-details">' + 
                    '<a href="#">'+ questionNode.user +'</a>' + 
                    '</div>' + 
                    '</div></div>'
					'</div>';
		$('#questionDetails').html(questionStr);
	}
}

function loadQuestionsTable_Prof(filterCriteria, filterText){
	$('#questionsTableWrapper').empty()
	$('#questionsTableWrapper').append("<table class=\"display\" width=\"100%\" id=\"questionsDTable\"></table>");
	$('#questionsDTable').DataTable({
    	"bLengthChange": false,
    	"pageLength": 5,
        data: populateQuestionsData_Prof(filterCriteria, filterText),
        columns: [
        	{ title : "EventId"},
        	{ title: "Questions" }
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
   customizeDTableSearch('questionsDTable', 'Search Questions');
}

function populateQuestionsData_Prof(filterCriteria, filterText){
	var isFilter = false;
	var currProf = JSON.parse(localStorage.getItem("currentUser"));
	var specialization = currProf.specialization;
	var basePath = "/asuconnect-data/questions/question-nodes";
	var question_nodes = Sling.getContent(basePath, 3);
	var questions = [];
	var connectionsTxt = '';
	var j = 0;
	if(filterCriteria != null && filterText != null)
		isFilter = true;
	for(var i in question_nodes) {
		if(i == undefined) break;
		var filteredQues;
		var ratingStr = "";
		var colorCode ="darkgreen";
		var questionArr = [];
		var questionkey = i.split('-')[1];
	    var curQuestion = question_nodes[i][questionkey];
	    var questionJson = escape(JSON.stringify(curQuestion));
	    //console.log("%o", curQuestion.categories);
	    if(specialization == curQuestion.category){
	    	if(isFilter){
	    		if(filterText == curQuestion[filterCriteria]){
	    			filteredQues = curQuestion;
	    		}
	    	}
	    	else{
	    		filteredQues = curQuestion;
	    	}
	    	console.log("filteredQues %o", filteredQues);

	    	// set rating string 
	    	if(curQuestion['difficulty'] == undefined)
	    		ratingStr = '<div class="rating" onclick="rateQuestion('+ questionkey +')" style="display:inline-block"><span><i class="fa fa-star"></i></span><span><i class="fa fa-star"></i></span><span><i class="fa fa-star"></i></span><span><i class="fa fa-star"></i></span><span><i class="fa fa-star"></i></span></div>';
	    	else
	    		ratingStr = "";

	    	if(filteredQues.studentLevel == "Graduate")
	    		colorCode = "darkgoldenrod";
	    	else if(filteredQues.studentLevel == "Under Graduate")
	    		colorCode = "darkred";
	    	else if(filteredQues.studentLevel == "Ph.D")
				colorCode = "darkgreen";

	    	if(filteredQues != null){
			    var tagStr = constructQuestionTags(curQuestion.categories);
			    var questionStr = '<div class="left pr20">' +  
		                    '<div class="quesTitle" style="color:'+ colorCode +';"><a class="quesLink" onclick="openQuestion('+ questionkey +')"><b>'+ filteredQues.title +'</b></a></div>'+
		                    '<div class="quesDesc">' + filteredQues.description + '</div>'+
		                    '<div class="quesCategory"><i>' + filteredQues.category + '</i></div>'+
		                    '<div class="post-taglist">' + tagStr + '</div>' + 
		                    '</div>'+
			    			'<div class="right action-links">'+ ratingStr + 
			    			'<div class="post-signature owner" style="display:inline-block">' + 
		                    '<div class="user-info ">' + 
		                    '<div class="user-action-time">' + 
		                    'asked <span title="2016-02-22 14:02:45Z" class="relativetime">15 mins ago</span>' + 
		                    '</div>' + 
		                    '<div class="user-gravatar32">' + 
		                    '<a href="#"><div class="gravatar-wrapper-32"><img src="https://www.gravatar.com/avatar/335a9ae9364e36c131fb599feaf0e540?s=32&amp;d=identicon&amp;r=PG&amp;f=1" alt="" width="32" height="32"></div></a>' +
		                    '</div>' +
		                    '<div class="user-details">' + 
		                    '<a href="#">'+ filteredQues.user +'</a>' + 
		                    '<span class="studlevel">' + filteredQues.studentLevel +  '<span>' + 
		                    '</div>' + 
		                    '</div></div>'
							'</div>';
			  
				questionArr.push(questionkey);
				questionArr.push(questionStr);
				questions.push(questionArr);
			}	
		}
	}
	return questions;     
}

function registerRateQuestionBtn(){
	$('#rate-question-link').click(function(){
		addDifficulty();
	});
}

function addDifficulty(){
	var successMsg = "Your rating for the question has been successfully added";
	var difficulty = $('#difficulty').val();
	var questionId = localStorage.getItem("currQuestion");
	var baseTestPath = "/asuconnect-data/questions/question-nodes";
	var dataPath = baseTestPath + "/question-" + questionId;
	var path = dataPath + "/" + questionId;
	// Get the data from the form
	var params = "difficulty=" + difficulty;
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
		showSuccessMsg(".right", successMsg); 
		$('html, body').animate({
        	scrollTop: $(".navbar").offset().top
		}, 200);
		updateUserQuestionStats_Difficulty(difficulty,questionId);
	});
	 
	request.fail(function( jqXHR, textStatus ) {
		alert( "Request failed: " + textStatus );
		showErrorMsg(".right", "Something went wrong. Please try again later.");
	});
}

function rateQuestion(questionKey){
	localStorage.setItem("currQuestion", questionKey);
	window.location = "/ASUConnect/design/prof-rateQuestion.html"
}

function rateQuestion_stud(questionKey){
	localStorage.setItem("currQuestion", questionKey);
	window.location = "/ASUConnect/design/stud-rateQuestion.html"
}

function filterQuestions_Prof(filterLevel){
	console.log("level" + filterLevel);
	loadQuestionsTable_Prof("level", filterLevel);
}

function filterQuestions_Stud(filterCategory){
	console.log("category" + filterCategory);
	loadQuestionsTable("category", filterCategory);
}

function loadPieChart(){
	google.charts.load("current", {packages:["corechart"]});
    google.charts.setOnLoadCallback(drawChart_questions);
    google.charts.setOnLoadCallback(drawChart_answers); 
    google.charts.setOnLoadCallback(drawChart_questions_difficulty);
    google.charts.setOnLoadCallback(drawChart_answers_difficulty);  
}

function drawChart_questions() {
	var currentUser = localStorage.getItem("currUserId");
	var baseTestPath = "/asuconnect-data/users/user-nodes";
	var dataPath = baseTestPath + "/user-" + currentUser + "/" + currentUser;
	var path = dataPath + "/questionStats";
	if(Sling.getContent(path)){
		var currQuestionStat = Sling.getContent(path, 3);
		var chartData = [];
		//chartData.push(['Category','No']);
		for(var i in currQuestionStat ) {
			var tempArr = [];
	    	if (currQuestionStat.hasOwnProperty(i)){
	    		tempArr.push(i);
	       		tempArr.push(parseInt(currQuestionStat[i]));
	    	}
	    	chartData.push(tempArr);
		}

		var data = google.visualization.arrayToDataTable(chartData, true);

	    var options = {
	      is3D: true,
	    };

	    var chart = new google.visualization.PieChart(document.getElementById('piechart_3d_questions'));
	    chart.draw(data, options);
	}
}

function drawChart_questions_difficulty() {
	var currentUser = localStorage.getItem("currUserId");
	var baseTestPath = "/asuconnect-data/users/user-nodes";
	var dataPath = baseTestPath + "/user-" + currentUser + "/" + currentUser;
	var path = dataPath + "/questionStats_Diff";
	if(Sling.getContent(path)){
		var currQuestionStat = Sling.getContent(path, 3);
		var chartData = [];
		//chartData.push(['Category','No']);
		for(var i in currQuestionStat ) {
			var tempArr = [];
	    	if (currQuestionStat.hasOwnProperty(i)){
	    		tempArr.push(i);
	       		tempArr.push(parseInt(currQuestionStat[i]));
	    	}
	    	chartData.push(tempArr);
		}

		 var data = google.visualization.arrayToDataTable(chartData, true);

	    var options = {
	      is3D: true,
	    };

	    var chart = new google.visualization.PieChart(document.getElementById('piechart_3d_questions_diff'));
	    chart.draw(data, options);
	}
}

function drawChart_answers() {
	var currentUser = localStorage.getItem("currUserId");
	var baseTestPath = "/asuconnect-data/users/user-nodes";
	var dataPath = baseTestPath + "/user-" + currentUser + "/" + currentUser;
	var path = dataPath + "/answerStats";
	if(Sling.getContent(path)){
		var currQuestionStat = Sling.getContent(path, 3);
		var chartData = [];
		//chartData.push(['Category','No']);
		for(var i in currQuestionStat ) {
			var tempArr = [];
	    	if (currQuestionStat.hasOwnProperty(i)){
	    		tempArr.push(i);
	       		tempArr.push(parseInt(currQuestionStat[i]));
	    	}
	    	chartData.push(tempArr);
		}

		var data = google.visualization.arrayToDataTable(chartData,true);

	    var options = {
	      is3D: true,
	    };

	    var chart = new google.visualization.PieChart(document.getElementById('piechart_3d_answers'));
	    chart.draw(data, options);
	}
}

function drawChart_answers_difficulty() {
	var currentUser = localStorage.getItem("currUserId");
	var baseTestPath = "/asuconnect-data/users/user-nodes";
	var dataPath = baseTestPath + "/user-" + currentUser + "/" + currentUser;
	var path = dataPath + "/answerStats_Diff";
	if(Sling.getContent(path)){
		var currQuestionStat = Sling.getContent(path, 3);
		var chartData = [];
		//chartData.push(['Category','No']);
		for(var i in currQuestionStat ) {
			var tempArr = [];
	    	if (currQuestionStat.hasOwnProperty(i)){
	    		tempArr.push(i);
	       		tempArr.push(parseInt(currQuestionStat[i]));
	    	}
	    	chartData.push(tempArr);
		}

		var data = google.visualization.arrayToDataTable(chartData,true);

	    var options = {
	      is3D: true,
	    };

	    var chart = new google.visualization.PieChart(document.getElementById('piechart_3d_answers_diff'));
	    chart.draw(data, options);
	}
}

function updateUserQuestionStats(category){
	var currentUser = localStorage.getItem("currUserId");
	var baseTestPath = "/asuconnect-data/users/user-nodes";
	var dataPath = baseTestPath + "/user-" + currentUser + "/" + currentUser;
	var path = dataPath + "/questionStats";
	var catCount = 1;
	
	if(Sling.getContent(path)){
		var currQuestionStat = Sling.getContent(path, 3);
		var currCount = currQuestionStat[category]; 
		if(currCount != null && currCount != undefined){
			catCount = parseInt(currCount) + 1;
		}
	}

	var params = category+'='+catCount;
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

}

function updateUserQuestionStats_Difficulty(difficulty,questionId){
	var currentUser = Sling.getContent("/asuconnect-data/questions/question-nodes/question-"+questionId+"/"+questionId).user;
	var baseTestPath = "/asuconnect-data/users/user-nodes";
	var dataPath = baseTestPath + "/user-" + currentUser + "/" + currentUser;
	var path = dataPath + "/questionStats_Diff";
	var catCount = 1;
	
	if(Sling.getContent(path)){
		var currQuestionStat = Sling.getContent(path, 3);
		var currCount = currQuestionStat[difficulty]; 
		if(currCount != null && currCount != undefined){
			catCount = parseInt(currCount) + 1;
		}
	}
	var params = difficulty+'='+catCount;
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

}

function updateUserAnswerStats(questionId){
	var currentUser = localStorage.getItem("currUserId");
	var category = Sling.getContent("/asuconnect-data/questions/question-nodes/question-"+questionId+"/"+questionId).category;
	var baseTestPath = "/asuconnect-data/users/user-nodes";
	var dataPath = baseTestPath + "/user-" + currentUser + "/" + currentUser;
	var path = dataPath + "/answerStats";
	var catCount = 1;

	if(Sling.getContent(path)){
		var currQuestionStat = Sling.getContent(path, 3);
		var currCount = currQuestionStat[category]; 
		if(currCount != null && currCount != undefined){
			catCount = parseInt(currCount) + 1;
		}
	}

	var params = category+'='+catCount;
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
}


function updateUserAnswerStats_Difficulty(questionId){
	var currentUser = localStorage.getItem("currUserId");
	var difficulty = Sling.getContent("/asuconnect-data/questions/question-nodes/question-"+questionId+"/"+questionId).difficulty;
	var baseTestPath = "/asuconnect-data/users/user-nodes";
	var dataPath = baseTestPath + "/user-" + currentUser + "/" + currentUser;
	var path = dataPath + "/answerStats_Diff";
	var catCount = 1;

	if(Sling.getContent(path)){
		var currQuestionStat = Sling.getContent(path, 3);
		var currCount = currQuestionStat[difficulty]; 
		if(currCount != null && currCount != undefined){
			catCount = parseInt(currCount) + 1;
		}
	}
	console.log("diff answer");
	var params = difficulty+'='+catCount;
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
}

function loadQuestionsTable_StudProfile(filterText){
	$('#questionsTableWrapper').empty()
	$('#questionsTableWrapper').append("<table class=\"display\" width=\"100%\" id=\"questionsDTable\"></table>");
	$('#questionsDTable').DataTable({
    	"bLengthChange": false,
    	"pageLength": 5,
        data: populateQuestionsData_StudProfile(filterText),
        columns: [
        	{ title : "EventId"},
        	{ title: "Questions" }
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
   customizeDTableSearch('questionsDTable', 'Search Questions');
}


function populateQuestionsData_StudProfile(filterText){
	var currUser = JSON.parse(localStorage.getItem("currentUser"));
	var userLevel = currUser.level;
	var basePath = "/asuconnect-data/questions/question-nodes";
	var question_nodes = Sling.getContent(basePath, 3);
	var questions = [];
	for(var i in question_nodes) {
		if(i == undefined) break;
		var filteredQues = null;
		var quesStr = "";
		var colorCode = "darkgreen";
		var ratingStr = "";
		var difficultyStr = "";
		var questionArr = [];
		var questionkey = i.split('-')[1];
	    var curQuestion = question_nodes[i][questionkey];
	    var answeredUsers;
	    if(curQuestion.answeredUsers != null)
	    	answeredUsers= curQuestion.answeredUsers.split(',');
	    var userIndex = $.inArray(currUser.username,answeredUsers);
	    if(curQuestion.user == currUser.username || userIndex > -1){
	    	
	    	if(filterText != null){
	    		if(filterText == 'questions'){
		    		if(curQuestion.user == currUser.username){
		    			filteredQues = curQuestion;
		    		}
	    		}
	    	else if(filterText == 'answers'){
	    		if(userIndex > -1){
	    			filteredQues = curQuestion;
	    		}
	    	}
	    	}
	    	else {
	    		filteredQues = curQuestion;
	    	}
	    	

	    	if(curQuestion['difficulty'] != undefined){
	    		difficultyStr = " / " + curQuestion['difficulty'];
	    		quesStr = '<a class="quesLink" onclick="openQuestion_stud('+ questionkey +')"><b>'+ curQuestion.title +'</b></a>';
	    	}
	    	else{
	    		difficultyStr = "";
	    		quesStr = '<a class="quesLink"><b>'+ curQuestion.title +'</b></a>';
	    	}

	    	if(filteredQues.studentLevel == "Graduate")
	    		colorCode = "darkgoldenrod";
	    	else if(filteredQues.studentLevel == "Under Graduate")
	    		colorCode = "darkred";
	    	else if(filteredQues.studentLevel == "Ph.D")
				colorCode = "darkgreen";

	    	if(filteredQues != null){
			    var tagStr = constructQuestionTags(curQuestion.categories);
			    var questionStr = '<div class="left pr20">' +  
		                    '<div class="quesTitle" style="color:'+ colorCode +';">'+ quesStr +'</div>'+
		                    '<div class="quesDesc">' + curQuestion.description + '</div>'+
		                    '<div class="quesCategory"><i>' + curQuestion.category + difficultyStr + '</i></div>'+
		                    '<div class="post-taglist">' + tagStr + '</div>' + 
		                    '</div>'+
			    			'<div class="right action-links">'+ ratingStr + 
			    			'<div class="post-signature owner" style="display:inline-block">' + 
		                    '<div class="user-info ">' + 
		                    '<div class="user-action-time">' + 
		                    'asked <span title="2016-02-22 14:02:45Z" class="relativetime">15 mins ago</span>' + 
		                    '</div>' + 
		                    '<div class="user-gravatar32">' + 
		                    '<a href="#"><div class="gravatar-wrapper-32"><img src="https://www.gravatar.com/avatar/335a9ae9364e36c131fb599feaf0e540?s=32&amp;d=identicon&amp;r=PG&amp;f=1" alt="" width="32" height="32"></div></a>' +
		                    '</div>' +
		                    '<div class="user-details">' + 
		                    '<a href="#">'+ curQuestion.user +'</a>' + 
		                    '<span class="studlevel">' + curQuestion.studentLevel +  '<span>' + 
		                    '</div>' + 
		                    '</div></div>'
							'</div>';
			  
				questionArr.push(questionkey);
				questionArr.push(questionStr);
				questions.push(questionArr);
			}
	    }
	}
	return questions;     
}

function filterQuestions_StudProfile(filterText){
	loadQuestionsTable_StudProfile(filterText);
}

function updateQuestionStats(questionId){
	var basePath = "/asuconnect-data/questions/question-nodes";
	var dataPath = basePath+"/question-"+questionId+"/"+questionId;
	var questData = Sling.getContent(dataPath,3);
	var params;
	if(questData.answeredUsers == undefined){
		params = 'answeredUsers=' + localStorage.getItem("currUserId");
	}
	else{
		params = 'answeredUsers=' + questData.answeredUsers + ',' + localStorage.getItem("currUserId");
	}
	// Post the data to Sling JCR Repository
	var request = $.ajax({
		url: dataPath,
		method: "POST",
		data: params,
		headers:{
			"Content-type":"application/x-www-form-urlencoded",
			"Content-length":params.length,
			"Connection":"close"
		}
	});
}

function format ( d ) {
    // `d` is the original data object for the row
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
        '<tr>'+
            d.answer
        '</tr>'+
    '</table>';
}

function loadCourseTable(){
	var table = $('#courseMaterialTable').DataTable( {
        "ajax": "../data/objects.txt",
        "columns": [
            {
                "className":      'details-control',
                "orderable":      false,
                "data":           null,
                "defaultContent": ''
            },
            { "data": "question" }
        ],
        "order": [[1, 'asc']]
    } );
     
    // Add event listener for opening and closing details
    $('#courseMaterialTable tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row( tr );
 
        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child( format(row.data()) ).show();
            tr.addClass('shown');
        }
    });
}