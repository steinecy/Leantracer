package leantracer.shared;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeekDays {
	
	private Logger logger = LogManager.getLogger();
	private Calendar calendar;
	private Calendar[] weekDays = new Calendar[7];
	
	public WeekDays (Calendar calendar) {
		this.calendar = calendar;
		logger.info("Der WeekDays calendar hat den Wert : " + new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
		calculateWeekdays();
	}
	
	private void calculateWeekdays() {
	
	        int factor = 0;
	        
			switch (calendar.get(Calendar.DAY_OF_WEEK)) {
			
			case Calendar.MONDAY:
				 factor = 0;
				 break;
			case Calendar.TUESDAY:
				 factor = -1;
				 break;
			case Calendar.WEDNESDAY :
				 factor = -2;
				 break;
			case Calendar.THURSDAY:
				 factor = -3;
				 break;
			case Calendar.FRIDAY:
				 factor = -4;
				 break;
			case Calendar.SATURDAY:
				 factor = -5;
				 break;
			case Calendar.SUNDAY:
				 factor = -6;
				 break;
			default:
				 factor = 1;
				 break;
			}
			logger.info("Der Tag ist identifiziert als: \"" + calendar.get(Calendar.DAY_OF_WEEK) + "\", der factor lautet: \"" + factor + "\"");
			setWeekdayArray(factor);
	}
			
	private void setWeekdayArray(int factor) {
		
		for (int i=0; i<7; i++) {
			Calendar weekDay = Calendar.getInstance();
			weekDay.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
			weekDay.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
			weekDay.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
			weekDay.add(Calendar.DAY_OF_MONTH, (i+factor));
//			if (weekDay==null) {
//				System.out.println("Oops, Variable weekDay is null");
//			} else {
				System.out.println("Variable weekDay is: " + calendar.toString());
//			}
			weekDays[i] = weekDay;
			logger.info("weekday[" + i + "] hat den Wert : " + new SimpleDateFormat("yyyy-MM-dd").format(weekDays[i].getTime()));;
		}
	}
	
	public Calendar[] getWeekDayArray() {
		return weekDays;
	}
		

}
