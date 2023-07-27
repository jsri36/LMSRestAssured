package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		plugin = {"pretty", "html:target/LMS_HtmlReport.html",
				"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
				}, 
		monochrome=false,  
		tags = " ", 
		features = {//"src/test/resources/features",
				"src/test/resources/features/1_program.feature",
				"src/test/resources/features/2_Program_Batch.feature",
				"src/test/resources/features/3_User.feature",
				"src/test/resources/features/4_Assignment.feature",
				"src/test/resources/features/5_Submission.feature",
				"src/test/resources/features/6_Delete.feature"
		},
		glue= "stepdefinitions") 


public class LMSRestAssured_Runner extends AbstractTestNGCucumberTests {

}
