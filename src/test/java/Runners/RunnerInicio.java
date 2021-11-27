package Runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions (
                    features = "D://AUTOMATIZACIONInteliJ//RetoBCP2021//Feature//Inicio.feature",
                    glue = "stepDefinitions"
)
public class RunnerInicio {
}
