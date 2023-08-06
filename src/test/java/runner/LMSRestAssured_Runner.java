package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		plugin = {"pretty", "html:target/LMS_HtmlReport.html",
				"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
				}, 
		monochrome=false,  
//tags = "@1 or @2 or @3 or @4 or @5 or @6 or @7 or @8 or @9 or @10 or @11 or @12 or @13 or @14 or @15 or @16 or @17 or @18",
		//tags="@17",	
		tags="",
		//tags="@PostAssignment_201_positive or @PostAssignment_400_negetive or @Get_Allassignment_200_positive"
				//+ "or @Get_assignmentById_200_positive or @Get_assignmentById_404_negetive or @Get_assignmentForBatch_200_positive"
				//+ "or @Get_assignmentForBatch_404_negetive or @Put_updateAssignmentbyid_200_positive or  @Put_updateAssignmentbyid_404_negetive"
				//+ "or @Put_updateAssignmentbyid_400_negetive or @Delete_Assignmentbyid_200_positive or @Delete_Assignmentbyid_404_negetive",
		features = {"src/test/resources/features",

		},
		glue= "stepdefinitions") 


public class LMSRestAssured_Runner extends AbstractTestNGCucumberTests {

}
