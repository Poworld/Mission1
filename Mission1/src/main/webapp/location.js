let locationBtn = document.querySelector(".locationBtn");
let jspLat = document.querySelector("#LAT");
let jspLnt = document.querySelector("#LNT");
	
let onclickLocation = (event) => {
	event.preventDefault();
	navigator.geolocation.getCurrentPosition(pos => {
	    let lat = pos.coords.latitude;
		let lnt = pos.coords.longitude;
		jspLat.value = lat;
		jspLnt.value = lnt;
	})	
}
locationBtn.addEventListener("click", onclickLocation);