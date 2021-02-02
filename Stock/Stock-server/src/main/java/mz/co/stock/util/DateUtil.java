package mz.co.stock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <code>DateUtil</code> will format the date for type that fit 
 * for is needed.
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 * */
public class DateUtil {

	/**
	 * Get the present date change the format of the date and 
	 * return date.
	 * @return 
	 * */
	public static Date parse() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		format.setLenient(false);
		try {
			String date = format.format(new Date());
			return format.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * this method have parameter date then change the format the
	 * return date.
	 * 
	 * @param date
	 * @return  
	 * */
	public static Date parse(Date date) {
		try {
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
			/*SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yy");
			SimpleDateFormat format3 = new SimpleDateFormat("d/MM/yy");
			SimpleDateFormat format4 = new SimpleDateFormat("dd-MMM-yy");
			SimpleDateFormat format5 = new SimpleDateFormat("dd-MMM-yyyy");*/
			format1.setLenient(false);
			String data = format1.format(date);
			return format1.parse(data);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date parse(String data) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			date = sdf.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date; 
	} 
	
	/**
	 * this method receive parameter number of days
	 * and sum with the present date and return the result.
	 * 
	 *  @param days
	 *  @return
	 * */
	public static Date calendaryConvert(int days) {
		Date date = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DAY_OF_MONTH, days);
			String data = format.format(c.getTime());
			date = format.parse(data);
			return date;
		} catch (ParseException e1) {
			e1.printStackTrace();
			return date;
		}
	}
	
	/**
	 * This will get the current date and return only the year.
	 * @return
	 * */
	public static int getYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}
	
	
	/**
	 * This will get the current date and return only the time.
	 * @return
	 * */
	public static Date getTime() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}
		
}
