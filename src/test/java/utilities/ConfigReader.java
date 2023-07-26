package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class ConfigReader {

	Properties property;
	
	/**
	 * call loadConfig while instantiate 
	 * 
	 */
	public ConfigReader() {
		try {
			loadConfig();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Load properties 
	 * @throws Exception
	 */
	public void loadConfig() throws Exception {
		
		try {
			property = new Properties();
			
			String filePath = System.getProperty("user.dir") + "./src/test/resources/config.properties";
			FileInputStream FP = new FileInputStream(filePath);
			property.load(FP);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
		
	}
	
	/**
	 * get url from properties
	 * @return
	 */
	public String getBaseUrl() {	
		return property.getProperty("base_url");
	}
	
	public String getBatch_Post_Url() {	
		return property.getProperty("batch_post_url");
	}
	
	public String getBatch_Getall_Url() {	
		return property.getProperty("batch_getall_url");
	}
	
	public String getBatch_ById_Url() {	
		return property.getProperty("batch_getbyid_url");
	}
	
	public String getBatch_ByName_Url() {	
		return property.getProperty("batch_getbyname_url");
	}
	
	public String getBatch_ByProgramId_Url() {	
		return property.getProperty("batch_getbyprogramid_url");
	}
	
	public String getBatch_Put_Url() {	
		return property.getProperty("batch_put_url");
	}
	
	public String getBatch_Deletebyid_Url() {	
		return property.getProperty("batch_deletebyid_url");
	}
	
	//Submission
	public String getApiUrl(String prop) {
		return getBaseUrl() + property.getProperty(prop);
	}
	
	public String get_grade_assignment_url() {
		return getBaseUrl() + property.getProperty("grade_assignment");
	}
}
