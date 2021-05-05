package weather.api;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.core.backend.CucumberBackendException;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Hello world!
 *
 */

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/resources/weather_api.feature", glue="stepDefinition",
plugin= {"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class App 
{

}
