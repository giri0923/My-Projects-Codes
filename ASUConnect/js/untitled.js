var userId = $('#username').val();
$('#specialization').val("DBMS");
var baseTestPath = "/asuconnect-data/professors/professor-nodes";
	var dataPath = baseTestPath + "/prof-" + userId;
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