package uk.georgeansell.tests.accessibility;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AccessibilityHelper {

    private static WebDriver unwrapDriver(WebDriver driver) {
        // Unwrap Serenity's WebDriverFacade to get the actual RemoteWebDriver
        if (driver instanceof WebDriverFacade) {
            return ((WebDriverFacade) driver).getProxiedDriver();
        }
        return driver;
    }

    public static Results runAccessibilityScan(WebDriver driver) {
        WebDriver unwrappedDriver = unwrapDriver(driver);
        AxeBuilder axeBuilder = new AxeBuilder();
        return axeBuilder.analyze(unwrappedDriver);
    }

    public static void assertNoAccessibilityViolations(WebDriver driver) {
        Results results = runAccessibilityScan(driver);
        List<Rule> violations = results.getViolations();
        
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("\n=== Accessibility Violations Found ===\n\n");
            
            for (Rule violation : violations) {
                errorMessage.append(String.format("Rule: %s\n", violation.getId()));
                errorMessage.append(String.format("Impact: %s\n", violation.getImpact()));
                errorMessage.append(String.format("Description: %s\n", violation.getDescription()));
                errorMessage.append(String.format("Help: %s\n", violation.getHelp()));
                errorMessage.append(String.format("Help URL: %s\n", violation.getHelpUrl()));
                errorMessage.append(String.format("Nodes affected: %d\n", violation.getNodes().size()));
                errorMessage.append("---\n");
            }
            
            assertThat(violations)
                .withFailMessage(errorMessage.toString())
                .isEmpty();
        }
    }

    public static void assertNoSeriousAccessibilityViolations(WebDriver driver) {
        Results results = runAccessibilityScan(driver);
        List<Rule> violations = results.getViolations();
        
        List<Rule> seriousViolations = violations.stream()
            .filter(v -> "critical".equals(v.getImpact()) || "serious".equals(v.getImpact()))
            .toList();
        
        if (!seriousViolations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("\n=== Critical/Serious Accessibility Violations ===\n\n");
            
            for (Rule violation : seriousViolations) {
                errorMessage.append(String.format("Rule: %s\n", violation.getId()));
                errorMessage.append(String.format("Impact: %s\n", violation.getImpact()));
                errorMessage.append(String.format("Description: %s\n", violation.getDescription()));
                errorMessage.append(String.format("Help URL: %s\n", violation.getHelpUrl()));
                errorMessage.append("---\n");
            }
            
            assertThat(seriousViolations)
                .withFailMessage(errorMessage.toString())
                .isEmpty();
        }
    }

    public static int getViolationCount(WebDriver driver) {
        Results results = runAccessibilityScan(driver);
        return results.getViolations().size();
    }

    public static void logAccessibilityResults(WebDriver driver) {
        Results results = runAccessibilityScan(driver);
        
        System.out.println("\n=== Accessibility Test Results ===");
        System.out.println("Violations: " + results.getViolations().size());
        System.out.println("Passes: " + results.getPasses().size());
        System.out.println("Incomplete: " + results.getIncomplete().size());
        System.out.println("Inapplicable: " + results.getInapplicable().size());
        
        if (!results.getViolations().isEmpty()) {
            System.out.println("\nViolations by Impact:");
            long critical = results.getViolations().stream().filter(v -> "critical".equals(v.getImpact())).count();
            long serious = results.getViolations().stream().filter(v -> "serious".equals(v.getImpact())).count();
            long moderate = results.getViolations().stream().filter(v -> "moderate".equals(v.getImpact())).count();
            long minor = results.getViolations().stream().filter(v -> "minor".equals(v.getImpact())).count();
            
            System.out.println("  Critical: " + critical);
            System.out.println("  Serious: " + serious);
            System.out.println("  Moderate: " + moderate);
            System.out.println("  Minor: " + minor);
        }
    }
}
