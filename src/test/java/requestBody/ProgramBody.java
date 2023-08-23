package requestBody;

import java.io.IOException;
import java.util.ResourceBundle;
import org.apache.commons.lang3.RandomStringUtils;
import payload.ProgramPayload;
import utilities.XLUtils;

public class ProgramBody {

	public static ResourceBundle Endpoints = ResourceBundle.getBundle("endpoints");
	public static ProgramPayload ProgramPayload = new ProgramPayload();
	public static ResourceBundle path = ResourceBundle.getBundle("path");
	public static XLUtils xlutils=new XLUtils(path.getString("Xlpath"));
	
	//post body
	public static ProgramPayload PostBody() throws IOException {
		
		ProgramPayload.setProgramName(xlutils.getCellData("programpostdata", 1, 0)+RandomStringUtils.randomNumeric(3));	
		ProgramPayload.setProgramDescription(xlutils.getCellData("programpostdata", 1, 1));
		ProgramPayload.setProgramStatus(xlutils.getCellData("programpostdata", 1, 2));
		System.out.println("Post-ProgramPayload is :"+ProgramPayload);

		return ProgramPayload;
	}
	
	//put body for valid ID
	public static ProgramPayload PutBodyById() throws IOException {
		
		ProgramPayload.setProgramName(xlutils.getCellData("programputdata", 1, 0)+RandomStringUtils.randomNumeric(3));
		ProgramPayload.setProgramDescription(xlutils.getCellData("programputdata", 1, 1));
		ProgramPayload.setProgramStatus(xlutils.getCellData("programputdata", 1, 2));

		System.out.println("PutById-ProgramPayload is :"+ProgramPayload);

		return ProgramPayload;
	}
	
	//put body for valid Name
		public static ProgramPayload PutBodyByName() throws IOException {
			
			ProgramPayload.setProgramName(ProgramPayload.getProgramName());
			ProgramPayload.setProgramDescription(xlutils.getCellData("programputdata", 2, 1));
			ProgramPayload.setProgramStatus(xlutils.getCellData("programputdata", 2, 2));

			System.out.println("PutByName-ProgramPayload is :"+ProgramPayload);

			return ProgramPayload;
		}
}
