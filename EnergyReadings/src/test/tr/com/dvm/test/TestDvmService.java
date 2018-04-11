package tr.com.dvm.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import junit.framework.TestCase;
import tr.com.dvm.model.HistoricData;
import tr.com.dvm.model.PowerPlant;
import tr.com.dvm.service.DvmService;
import tr.com.dvm.service.impl.DvmServiceImpl;
import tr.com.dvm.utils.Constants;

public class TestDvmService extends TestCase {
	protected int value1, value2;
	protected DvmService service;

	// assigning the values
	protected void setUp() {
		service = new DvmServiceImpl();
	}

	public void testRetrievePowerPlants() {
		List<PowerPlant> powerPlants = service.retrievePowerPlants(Constants.GAMA_NETWORK_DATA_URL);

		assertNotNull("Error in retrieving power plant list.", powerPlants);
		assertEquals(7, powerPlants.size());

		// Check whether retrieved list contains PowerPlant0006_GamaEnerji.
		PowerPlant pp = new PowerPlant("PowerPlant0006_GamaEnerji", "PowerTrack2006", "0");
		assertTrue(powerPlants.contains(pp));
	}

	public void testRecentEnergyGenerationReadings() {
		
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		c.set(2018, Calendar.MARCH, 21, 11, 44);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date endDateTime = new Date(c.getTimeInMillis()-1);
		
		c.add(Calendar.MINUTE, -1);
		Date startDateTime = new Date(c.getTimeInMillis());
		
		List<HistoricData> list = service.recentEnergyGenerationReadingsByHistoricDataUrl(startDateTime, endDateTime);

		assertNotNull("Error in retrieving power plant list.", list);
		assertEquals(5, list.size());

		// TODO
		// Check whether retrieved data were correct or not.
	}
}