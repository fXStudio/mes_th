ver = parseInt(navigator.appVersion)
ie4 = (ver > 3 && navigator.appName != "Netscape") ? 1 : 0;
ns4 = (ver > 3 && navigator.appName == "Netscape") ? 1 : 0;
ns3 = (ver == 3 && navigator.appName == "Netscape") ? 1 : 0;

/**
 * ≤•∑≈“Ù∆µ
 */
function playSoundTWO() {
	if (ie4) {
		document.all['BGSOUND_IDTWO'].src = document.all['BGSOUND_IDTWO']["alt"];
	}
	if ((ns4 || ns3) && navigator.javaEnabled()
			&& navigator.mimeTypes['audio/x-midi']
			&& self.document.Bach.IsReady()) {
		self.document.BachTWO.play()
	}
}

/**
 * Õ£÷π“Ù∆µµƒ≤•∑≈
 */
function stopSoundTWO() {
	if (ie4)
		document.all['BGSOUND_IDTWO'].src = '';
	if ((ns4 || ns3) && navigator.javaEnabled()
			&& navigator.mimeTypes['audio/x-midi']) {
		self.document.BachTWO.stop()
	}
}