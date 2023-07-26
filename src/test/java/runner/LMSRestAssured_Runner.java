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
		features = {"src/test/resources/features",

		},
		glue= "stepdefinitions") 


public class LMSRestAssured_Runner extends AbstractTestNGCucumberTests {

}
