import com.yatskiv.textprocessor.FileTextProcessor;
import com.yatskiv.textprocessor.TextProcessor;
import com.yatskiv.textprocessor.analyze.LineMatcher;
import com.yatskiv.textprocessor.print.FilePrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class TestTextAnalyzer {

    private static final String TEST_IN_FILENAME = "src/test/resources/big.txt";
    private static final String TEST_OUT_FILENAME = "src/test/resources/output.txt";

    @Test
    public void testConsistency() {
        TextProcessor processor = new FileTextProcessor(
                TEST_IN_FILENAME,
                List.of("James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph", "Thomas",
                        "Christopher", "Daniel", "Paul", "Mark", "Donald", "George", "Kenneth", "Steven", "Edward", "Brian",
                        "Ronald", "Anthony", "Kevin", "Jason", "Matthew", "Gary", "Timothy", "Jose", "Larry", "Jeffrey",
                        "Frank", "Scott", "Eric", "Stephen", "Andrew", "Raymond", "Gregory", "Joshua", "Jerry", "Dennis",
                        "Walter", "Patrick", "Peter", "Harold", "Douglas", "Henry", "Carl", "Arthur", "Ryan", "Roger")
        );

        long fileSize = 0;
        for (int i = 0; i < 10; i++) {
            Instant start = Instant.now();

            try {
                processor.init();
                processor.analyze(new LineMatcher());
                processor.printResults(new FilePrinter(TEST_OUT_FILENAME));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println("Run " + i + " execution time: " + Duration.between(start, Instant.now()).toMillis() / 1000.0 + " s");

            if (fileSize > 0) {
                Assertions.assertEquals(new File(TEST_OUT_FILENAME).length(), fileSize);
            } else {
                fileSize = new File(TEST_OUT_FILENAME).length();
            }
        }
    }

}
