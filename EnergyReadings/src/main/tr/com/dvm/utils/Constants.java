package tr.com.dvm.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Constants {
	
	public static final String TEST_FOLDER = System.getProperty("user.dir") + File.separator;
	
	public static final String POWER_PLANTS_CSV = "powerplants.csv";
	public static final String MINUTE_READINGS_CSV = "abdullah_atmaca_minutereadings.csv";

	public static final String ACCEPT_TYPE_JSON = "application/JSON";
	public static final String APP_KEY = "9a3ab6d8-9ffe-49a5-8194-bc7d61123f4a";

	public static final String GAMA_PREFIX = "https://power.ivyiot.com/Thingworx/Things/GamaNetworkServices/Services/";
	public static final String GAMA_NETWORK_DATA_URL = GAMA_PREFIX + "GetNetworkData";
	public static final String GAMA_HISTORIC_DATA_URL = GAMA_PREFIX + "GetHistoricDataByMinute";

	public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	public static final SimpleDateFormat FORMATTER_CSV = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	static{
		FORMATTER.setTimeZone(TimeZone.getTimeZone("GMT"));
		FORMATTER_CSV.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
	}
}