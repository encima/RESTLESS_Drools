package com.encima.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class TimeTools {
	
	public static String checkTimePeriod(Date time) {
//		Properties prop = FileTools.getProperties();
		
//		int morning = Integer.parseInt(prop.getProperty("morning", "6"));
		Calendar mornStart = Calendar.getInstance();
		mornStart.setTime(time);
		mornStart.set(Calendar.HOUR_OF_DAY, 6);
		mornStart.set(Calendar.SECOND, 0);
		Calendar aftStart = Calendar.getInstance();
		aftStart.setTime(time);
		aftStart.set(Calendar.HOUR_OF_DAY, 12);
		aftStart.set(Calendar.SECOND, 0);
		Calendar eveStart = Calendar.getInstance();
		eveStart.setTime(time);
		eveStart.set(Calendar.HOUR_OF_DAY, 17);
		eveStart.set(Calendar.SECOND, 0);
		Calendar nightStart = Calendar.getInstance();
		nightStart.setTime(time);
		nightStart.set(Calendar.HOUR_OF_DAY, 21);
		nightStart.set(Calendar.SECOND, 0);
		Calendar nextMornStart = Calendar.getInstance();
		nextMornStart.setTime(mornStart.getTime());
		nextMornStart.add(Calendar.DATE, 1);
		Calendar timeCal = Calendar.getInstance();
		timeCal.setTime(time);
		
		if(timeCal.before(eveStart) && timeCal.after(aftStart)) {
			return "Afternoon";
		}
		else if(timeCal.after(nightStart) && timeCal.before(nextMornStart)) {
			return "Night";
		}
		return null;
	}
}
