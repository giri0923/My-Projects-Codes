function addUser(userId){
	bgAuthenticate();
	// Set base path
	var successMsg;
	if(userId == null){
  		userId = uniqueId();
  		successMsg = "Account has been created successfully.";
	}
	else{
		successMsg = "Account has been created successfully."
	}
	var baseTestPath = "/asuconnect-data/users/user-nodes";
	var dataPath = baseTestPath + "/user-" + userId;
	var path = dataPath + "/" + userId;
	// Get the data from the form
	var params = $('#signupForm').serialize();
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
	 	alert(successMsg); 
	 	window.location = "/ASUConnect/design/login.html"; 				 	    	 
	});
	 
	request.fail(function( jqXHR, textStatus ) {
		
	});
}

function uniqueId() {
	var i = new Date().getTime();
	i = i & 0xffff; 
	return i;
}

function registerSignUpBtn(){
	$('#signupbtn').click(function(){
		console.log("signupbtn clicked");
		var userId = $('#username').val();
		addUser(userId);
	})
}

function registerLoginBtn(){
	$('#loginbtn').click(function(){
		var userId = $('#username').val();
		var password = $('#password').val();
		authenticateUser(userId,password);
	})
}

function bgAuthenticate(){
	var loginform = $('#loginform')[0];
		loginform.submit();
		console.log("admin authenticated");
}

function authenticateUser(userId, password){
	var isAuthenticated = false;
	var relocationUrl = "/ASUConnect/design/student-home.html"
	var basePath = "/asuconnect-data/users/user-nodes";
	var userDataPath = basePath+"/user-"+userId+"/"+userId;
	var userNode = Sling.getContent(userDataPath , 1);
	if(userNode != null && userNode != undefined){
		var nodePassword = userNode.password;
		if(nodePassword == password)
			isAuthenticated = true;
	}
	else{
		var basePath = "/asuconnect-data/professors/professor-nodes";
		var userDataPath = basePath+"/prof-"+userId+"/"+userId;
		var userNode = Sling.getContent(userDataPath , 1);
		if(userNode != null && userNode != undefined){
			var nodePassword = userNode.password;
			if(nodePassword == password)
				isAuthenticated = true;
				relocationUrl = "/ASUConnect/design/prof-home.html"
		}
	}
	if(isAuthenticated){
		bgAuthenticate();
		window.location=relocationUrl;
		localStorage.setItem("currentUser", JSON.stringify(userNode));	
		localStorage.setItem("currUserId", userId);
	}
	else{
		alert("Invalid Credentails!");
	}
}

function setStudLevel(level){
	$('#level').val(level);
}