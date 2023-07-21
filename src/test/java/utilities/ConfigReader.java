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
}
