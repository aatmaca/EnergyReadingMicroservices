package tr.com.dvm.utils;

import java.util.Date;
import java.util.List;

import tr.com.dvm.model.PowerPlant;

public class CsvUtil {

	public static StringBuilder powerPlantsToCsv(List<PowerPlant> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("PowerPlant,meter\n");
		for (PowerPlant pp : list) {
			sb.append(pp.getName() + "," + pp.getMeter() + "\n");
		}
		return sb;
	}

	public static StringBuilder recentEnergyGenerationReadingsToCsv(List<PowerPlant> list, Long queryTime, boolean isFileExists) {
		StringBuilder sb = new StringBuilder();
		if (isFileExists == false) {
			for (PowerPlant pp : list) {
				sb.append(pp.getName() + ",");
			}
			sb.append("UNIX_Timestamp,Human_Readable_Time\n");
		}
		
		for (PowerPlant pp : list) {
			sb.append(pp.getGen1min() + ",");
		}
		sb.append(queryTime + "," + Constants.FORMATTER_CSV.format(new Date(queryTime)) + "\n");
		
		return sb;
	}
}
