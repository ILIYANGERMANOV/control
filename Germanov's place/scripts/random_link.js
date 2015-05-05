function Randomlink() {
	Url = new Array;
	Url[0] = "cpp.html";
	Url[1] = "android_developing.html";
	Url[2] = "web_design.html";
	Url[3] = "python_django.html";


	Chooselink = Math.floor(Math.random() * (Url.length + 1));
	window.open(Url[Chooselink],'_blank');
}
