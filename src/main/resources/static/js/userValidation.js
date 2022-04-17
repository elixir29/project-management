

if(errorMessageJS) {
	var niceErrorMessage = decodeHtml(errorMessageJS);
	document.getElementById('validationError').innerHTML = niceErrorMessage;
}

function decodeHtml(html) {
	var txt = document.createElement("textarea");
	txt.innerHTML = html
	return txt.value;
}