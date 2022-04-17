
var niceChartData = decodeHtml(timeLineschartData); 										   

var chartJson = JSON.parse(niceChartData);

google.charts.load('current', {'packages':['timeline']});

google.charts.setOnLoadCallback(drawChart);

function drawChart() {
	var container = document.getElementById('chart_timeline');
	var chart = new google.visualization.Timeline(container);
	var dataTable = new google.visualization.DataTable();

	dataTable.addColumn({ type: 'string', id: 'Project' });
	dataTable.addColumn({ type: 'date', id: 'Start' });
	dataTable.addColumn({ type: 'date', id: 'End' });
  
	for(var i = 0; i < chartJson.length; i++) {
		dataTable.addRows([
	    	[ chartJson[i].projectName,  new Date(chartJson[i].startDate),  new Date(chartJson[i].endDate) ]]);
	}
	
	chart.draw(dataTable);
}


function decodeHtml(html) {
	var txt = document.createElement("textarea");
	txt.innerHTML = html
	return txt.value;
}