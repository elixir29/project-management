//niceChartData will have the readable format of the chartData variable
var niceChartData = decodeHtml(chartData); //chartData is there in the home.html page.
										   //This variable is available here beacause this file is getting
										   //called in the canvas section, just below which the chartData
										   //variable exists.

//Now niceChartData is a string returned by the decodeHtml function. (See it in the console, how it looks)
//We need to convert it into a Java script object to actually work with the data.									
var chartJson = JSON.parse(niceChartData); //Print chartJson in  the console to see it as well
										   //It's basically an array.
										   
//Now we need to traverse over this array to get the project count and stage at each index. 
//We will store the project count in the numericData array and
//we will store the stage in the labelData array.
var numericData =[];
var labelData = [];

for(var i = 0; i < chartJson.length ; i++) {
	numericData[i] = chartJson[i].value;
	labelData[i] = chartJson[i].label;
}										   

//Now we use numericData and labelData arrays in the actual pie chart below



//Pie chart starting here.
const data = {
	labels: labelData,
	datasets: [{
		  label: 'DataSet',
		  					//COMPLETED              INPROGRESS             NOTSTARTED
		  backgroundColor: ['#003f5c', '#bc5090', '#ffa600'],
		  data: numericData
	}]
};

const config = {
	type: 'pie',
	data: data,
	options: {
		plugins: {
			title: {
				display: true,
				text: 'Project statuses'
			}
		}
	}
};

const myChart = new Chart(
	document.getElementById('myPieChart'),
	config
);
//Pie chart ending here.



//The chartData variable that is defined in the home.html page comes in a cryptic format.
//You can see the cryptic format in the console in google chrome. Just type in charData in the console
//We need to convert it into a readable format hence we need to kind of decrypt it, which is what the 
//below function is gonna do
function decodeHtml(html) { //in this html argument we will pass in the chartData variable
	var txt = document.createElement("textarea");
	txt.innerHTML = html
	return txt.value;
}