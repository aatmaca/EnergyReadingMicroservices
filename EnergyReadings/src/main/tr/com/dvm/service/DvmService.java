package tr.com.dvm.service;

import java.util.Date;
import java.util.List;

import tr.com.dvm.model.HistoricData;
import tr.com.dvm.model.PowerPlant;

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
public interface DvmService {

	/**
	 * Write a script in NodeJS that lists the names of the power plants given
	 * in the service, the corresponding meter ID and store the list as a comma
	 * separated value formatted text in “powerplants.csv” file.
	 * 
	 * @param meterId
	 */
	public List<PowerPlant> retrievePowerPlants(String serviceUrl);

	public void retrieveAndStorePowerPlants(String serviceUrl);

	/**
	 * Write a script that polls the most recent minute-based energy generation
	 * readings from all the power plants every minute and stores this to a csv
	 * file, a column for each power plant, a column for the timestamp as UNIX
	 * seconds and a column for human-readable time formatted as string. The
	 * output filename should be “yourname_lastname_minutereadings.csv”
	 * @return 
	 */
	public boolean storeRecentEnergyGenerationReadings(String serviceUrl);

	public List<HistoricData> recentEnergyGenerationReadingsByHistoricDataUrl(Date startDateTime, Date endDateTime);
}