package tr.com.dvm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tr.com.dvm.model.HistoricData;
import tr.com.dvm.model.PowerPlant;
import tr.com.dvm.service.DvmService;
import tr.com.dvm.utils.Constants;
import tr.com.dvm.utils.CsvUtil;
import tr.com.dvm.utils.FileUtils;
import tr.com.dvm.utils.HttpUtil;

/**
 * 
 * DVM PowerTrack Data Integration Test
 * 
 * Use the attached “DVM PowerTrack WebService Documentation Data Integration
 * Services” as your integration guideline.
 * 
 * Package your project to a rar archive and send it to us. Make sure you
 * include all necessary modules so it can be run on our side without problems.
 * 
 * @author abdullahatmaca
 *
 */
public class DvmServiceImpl implements DvmService {

	/**
	 * Write a script in NodeJS that lists the names of the power plants given
	 * in the service, the corresponding meter ID and store the list as a comma
	 * separated value formatted text in “powerplants.csv” file.
	 * 
	 * @param meterId
	 */
	@Override
	public List<PowerPlant> retrievePowerPlants(String serviceUrl) {

		String jsonString = HttpUtil.getJsonString(serviceUrl, "POST");

		JsonParser jsonParser = new JsonParser();
		JsonElement root = jsonParser.parse(jsonString);
		JsonObject jsonObj = root.getAsJsonObject();
		JsonElement rows = jsonObj.get("rows");
		JsonArray jsonArray = rows.getAsJsonArray();

		List<PowerPlant> powerPlants = new ArrayList<>();
		for (JsonElement el : jsonArray) {
			JsonObject titleObj = el.getAsJsonObject();
			String pp = titleObj.get("PowerPlant").getAsString();
			String meter = titleObj.get("meter").getAsString();
			String gen1min = titleObj.get("gen1min").getAsString();
			powerPlants.add(new PowerPlant(pp, meter, gen1min));
		}

		return powerPlants;
	}

	@Override
	public void retrieveAndStorePowerPlants(String serviceUrl) {
		List<PowerPlant> list = retrievePowerPlants(serviceUrl);

		try {
			FileUtils.writeToFile(CsvUtil.powerPlantsToCsv(list), Constants.POWER_PLANTS_CSV);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write a script that polls the most recent minute-based energy generation
	 * readings from all the power plants every minute and stores this to a csv
	 * file, a column for each power plant, a column for the timestamp as UNIX
	 * seconds and a column for human-readable time formatted as string. The
	 * output filename should be “yourname_lastname_minutereadings.csv”
	 */

	@Override
	public boolean storeRecentEnergyGenerationReadings(String serviceUrl) {
		Long now = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTimeInMillis();
		List<PowerPlant> list = retrievePowerPlants(serviceUrl);

		boolean isFileExists = FileUtils.isFileExists(Constants.MINUTE_READINGS_CSV);
		try {
			FileUtils.appendToFile(CsvUtil.recentEnergyGenerationReadingsToCsv(list, now, isFileExists),
					Constants.MINUTE_READINGS_CSV);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isFileExists;
	}

	@Override
	public List<HistoricData> recentEnergyGenerationReadingsByHistoricDataUrl(Date startDateTime, Date endDateTime) {
		List<PowerPlant> powerPlants = retrievePowerPlants(Constants.GAMA_NETWORK_DATA_URL);

		List<HistoricData> historicalData = new ArrayList<HistoricData>();
		for (PowerPlant pp : powerPlants) {
			historicalData.addAll(retrieve1MinEnergyDataByHistoricDataUrl(pp, Constants.GAMA_HISTORIC_DATA_URL,
					startDateTime, endDateTime));
		}
		return historicalData;
	}

	private List<HistoricData> retrieve1MinEnergyDataByHistoricDataUrl(PowerPlant pp, String serviceUrl,
			Date startDateTime, Date endDateTime) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("meter", pp.getMeter());
		// "2018-03-20T09:05:00+03:00"
		map.put("startDateTime", Constants.FORMATTER.format(startDateTime));
		map.put("endDateTime", Constants.FORMATTER.format(endDateTime));

		String jsonString = HttpUtil.getJsonString(serviceUrl, map, "POST");

		JsonParser jsonParser = new JsonParser();
		JsonElement root = jsonParser.parse(jsonString);
		JsonObject jsonObj = root.getAsJsonObject();
		JsonElement rows = jsonObj.get("rows");
		JsonArray jsonArray = rows.getAsJsonArray();

		List<HistoricData> list = new ArrayList<>();
		for (JsonElement el : jsonArray) {
			JsonObject titleObj = el.getAsJsonObject();
			Long receivedAt = titleObj.get("receivedAt").getAsLong();
			String energy = titleObj.get("energy").getAsString();
			list.add(new HistoricData(pp, energy, receivedAt));
		}

		return list;
	}
}