import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.Runner.Builder;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestRunner {

    @Test
    void testTags() {

//        return Karate.run("classpath:com/finspire/features/test/customerManager/smokeAndRegression/Smoke.feature").tags("@jenkinsSmoke").relativeTo(getClass());
        String repoName = System.getenv("REPOSITORY_NAME");
        System.out.println("ENVIRONMENT REPOSITORY NAME : " + repoName);
        String testPath = null;
        boolean runAll = false;
        if (repoName.equals("be-em/address-lookup-manager")) {
            testPath = "classpath:com/finspire/features/test/addressLookupManager";
        } else if (repoName.equals("be-em/current-account-manager")) {
            testPath = "classpath:com/finspire/features/test/currentAccountManager";
        } else if (repoName.equals("be-em/current-ledger-manager")) {
            testPath = "classpath:com/finspire/features/test/currentLedgerManager";
        } else if (repoName.equals("be-em/customer-manager")) {
            testPath = "classpath:com/finspire/features/test/customerManager";
        } else if (repoName.equals("be-em/debit-card-account-manager")) {
            testPath = "classpath:com/finspire/features/test/debitCardManager";
        } else if (repoName.equals("be-em/document-manager")) {
            testPath = "classpath:com/finspire/features/test/documentManager";
        } else if (repoName.equals("be-em/domestic-payment-gateway")) {
            testPath = "classpath:com/finspire/features/test/domesticPaymentManager";
        } else if (repoName.equals("be-em/notification-manager")) {
            testPath = "classpath:com/finspire/features/test/notificationManager";
        } else if (repoName.equals("be-em/onus-payment-manager")) {
            testPath = "classpath:com/finspire/features/test/onusPaymentManager";
        } else if (repoName.equals("be-em/security-manager")) {
            testPath = "classpath:com/finspire/features/test/securityManager";
        } else {
            runAll = true;
            testPath = "classpath:com/finspire/features/test";
        }

        Builder testBuilder = Runner.path(testPath)
            .outputHtmlReport(true)
            .outputCucumberJson(true);
        if (runAll) {
            testBuilder.tags("~@sample");
        }

        Results results = testBuilder.parallel(1);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
        generateReport(results.getReportDir(), repoName);
    }

    private static void generateReport(String karateOutputPath, String repoName) {
        System.out.println("output path : " + karateOutputPath + ", repoName : " + repoName);
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] {"json"}, true);
        List<String> jsonPaths = new ArrayList<String>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), repoName);
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }

//    Run on Terminal: mvn test -Dtest=SampleTest#testSample

//    If you want to run below by using feature name, it should be in the same package.
//    @Karate.Test
//    Karate featureName() {
//
//        return Karate.run("testRunner").relativeTo(getClass());
//    }
//
//    @Karate.Test
//    Karate classPath() {
//
//        return Karate.run("classpath:com/finspire/features/test/testRunner.feature");
//    }
//
//    @Karate.Test
//    Karate testFullPath() {
//
//        return Karate.run("src/test/java/com/finspire/features/test/testRunner.feature");
//    }

}
