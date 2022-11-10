
 let baseTable = document.querySelector(".baseTable");
 let wifiInfoBtn = document.querySelector(".wifiInfoBtn");
 
 wifiInfoBtn.addEventListener("click", (event) => {
	event.preventDefault();
	jspLat = document.querySelector("#LAT");
	jspLnt = document.querySelector("#LNT");
	location.href = "http://localhost:8080/Mission1/?lat=" + jspLat.value + "&lnt="+ jspLnt.value;
})