package tr.com.dvm.runner;

import tr.com.dvm.service.DvmService;
import tr.com.dvm.service.impl.DvmServiceImpl;
import tr.com.dvm.utils.Constants;

public class Runner {

	public static void main(String[] args) throws Exception {
		DvmService service = new DvmServiceImpl();

		System.out.println("STEP 1. Retrieve power plants and their corresponding meter ID.");

		System.out.println("\tReading data from " + Constants.GAMA_NETWORK_DATA_URL + ".");
		service.retrieveAndStorePowerPlants(Constants.GAMA_NETWORK_DATA_URL);
		System.out.println("\t" + Constants.POWER_PLANTS_CSV + " file created.\n\n");

		System.out.println(
				"STEP 2. Retrieve most recent minute-based energy generation readings from all the power plants.");
		while (true) {
			boolean isFileExists = service.storeRecentEnergyGenerationReadings(Constants.GAMA_NETWORK_DATA_URL);
			if (isFileExists) {
				System.out.println("\t" + Constants.MINUTE_READINGS_CSV + " file appended with recent data.");
			} else {
				System.out.println("\t" + Constants.MINUTE_READINGS_CSV + " file created.");
			}

			System.out.println("\tWaiting 60 seconds to retrieve more recent data.\n\tPress Ctrl C to exit.");
			int i = 0;
			while (i++ < 60) {
				Thread.sleep(1000);
				System.out.print(".");
			}
			System.out.println();
		}

	}
}