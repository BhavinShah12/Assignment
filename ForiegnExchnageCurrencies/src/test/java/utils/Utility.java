package utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.sun.xml.xsom.impl.scd.ParseException;

public class Utility 
{
	// Method to get current date
	public static LocalDate getTodaysDate() {
		LocalDate date = null;
		try {
			ZoneId zoneId = ZoneId.of("India/Pune");
			date = LocalDate.from(ZonedDateTime.now(zoneId));
			//date = date.minusDays(1);
			System.out.println("Today's date is" + date.toString());
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Failed to fetch today's date" + e.getMessage());
		}
		
		return date;
	}
	
	public static String getWorkingDate(LocalDate date) throws ParseException 
	{
		LocalDate result = date;
		// If day is saturday then make it (-1) i.e Friday.
		if (date.getDayOfWeek() == DayOfWeek.SATURDAY) 
		{
			System.out.println("Input date falls on a Saturday" + date.toString());
			result = date.minusDays(1);
		}

		// If day is Sunday then make it (-2) i.e Friday.
		else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) 
		{
			System.out.println("Input date falls on a Sunday" + date.toString());
			result = date.minusDays(2);
		}

		System.out.println("Previous working date fetched as '" + result.toString() + "'");
		return result.toString();
	}
}
