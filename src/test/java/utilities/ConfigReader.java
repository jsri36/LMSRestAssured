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
	/*  User Module */
	
	public String getBatch_Post_Url() {	
		return property.getProperty("batch_post_url");
	}
	
	public String getBatch_Getall_Url() {	
		return property.getProperty("batch_getall_url");
	}
	public String getUser_post_Url() {
		return property.getProperty("user_post_url");
	}
	public String getUser_getalluser_Url() {
		return property.getProperty("user_getallusers_url");
	}
	public String getUser_getallstaff_Url() {
		return property.getProperty("user_getallstaff_url");
	}
	public String getUser_getuserroles_Url() {
		return property.getProperty("user_getusersroles_url");
	}
	public String putUser_putuserrolestatus_Url() {
		return property.getProperty("user_putrolestatus_url");
	}
	public String putUser_assignuserrolebatch_Url() {
		return property.getProperty("user_assignuserbatch_url");
	}
	
	/* Assignment Module */
	public String getAllAssinmentUrl() {	
		return property.getProperty("Assignment_getall_url");
	}
	
	public String getAssignment_getbyid_url() {	
		return property.getProperty("Assignment_getbyid_url");
	}
	public String getAssignmentforBatch_url() {	
		return property.getProperty("Assignment_getforbatch");
}
	
		public String postAssignmet_post_url() {	
			return property.getProperty("Assignment_post_url");
		}	
			public String putAssignment_put_url() {	
					return property.getProperty("Assignment_put_url");
			}
			public String deleteAssignment_delete_url() {	
				return property.getProperty("Assignment_delete_url");
		}
}
