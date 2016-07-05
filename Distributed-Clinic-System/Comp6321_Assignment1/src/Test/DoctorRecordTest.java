/**
 * 
 */
package Test;

import static org.junit.Assert.*;
import java.rmi.RemoteException;
import org.junit.Test;
import com.dds.sms.server.ClinicServer;

public class DoctorRecordTest {

	@Test
	public void testCreateDRecord() {

		ClinicServer testobjMTL;
		ClinicServer testobjLVL;
		ClinicServer testobjDDO;

		try {
			testobjMTL = new ClinicServer("MTL", 5002, 2525);
			testobjLVL = new ClinicServer("LVL", 5002, 3030);
			testobjDDO = new ClinicServer("DDO", 5002, 3535);

			boolean result = testobjMTL.createDRecord("Shubhangi","Sheel", "St mathiew canada", "5895484", "childspecialist", "MTL");
			boolean result1 = testobjMTL.createDRecord("Shubham","Singh", "St mathiew canada", "5895484", "childspecialist", "MTL");
			
			boolean result2 = testobjLVL.createDRecord("Shubhangi","Sheel", "St mathiew canada", "5895484", "childspecialist", "LVL");
			boolean result3 = testobjLVL.createDRecord("Shubham","Singh", "St mathiew canada", "5895484", "childspecialist", "LVL");
			
			boolean result4= testobjDDO.createDRecord("Shubhangi","Sheel", "St mathiew canada", "5895484", "childspecialist", "DDO");
			boolean result5 = testobjDDO.createDRecord("Shubham","Singh", "St mathiew canada","5895484", "childspecialist", "DDO");

			assertEquals(true, result);
			assertEquals(true, result1);
			
			assertEquals(true, result2);
			assertEquals(true, result3);
			
			assertEquals(true, result4);
			assertEquals(true, result5);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
