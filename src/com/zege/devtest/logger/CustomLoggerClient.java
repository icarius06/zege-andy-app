package com.zege.devtest.logger;

import android.annotation.SuppressLint;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

/**
 * This enables logging from Js console to LogCat
 * 
 * @author Michael
 * 
 */
public class CustomLoggerClient extends WebChromeClient {
	public CustomLoggerClient() {
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
		String msg = new StringBuilder(consoleMessage.messageLevel().name())
				.append('\t').append(consoleMessage.message()).append('\t')
				.append(consoleMessage.sourceId()).append(" (")
				.append(consoleMessage.lineNumber()).append(")\n")
				.toString();

		if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
			Log.e("ERROR>>>", msg.toString());
		} else {
			Log.d("DEBUG>>", msg.toString());
		}
		return true;
	}
}