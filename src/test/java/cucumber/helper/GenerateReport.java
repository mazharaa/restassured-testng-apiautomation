package cucumber.helper;

import java.io.File;
import java.util.Collections;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

public class GenerateReport {
    public static void generateReport() {
        File reportOutputDirectory = new File("target/cucumber-reports");
        File jsonFile = new File("target/cucumber.json");

        Configuration config = new Configuration(reportOutputDirectory, "Employee");
        config.addClassifications("Platform", "Windows");

        ReportBuilder reportBuilder = new ReportBuilder(Collections.singletonList(jsonFile.getAbsolutePath()), config);
        reportBuilder.generateReports();
    }
}
