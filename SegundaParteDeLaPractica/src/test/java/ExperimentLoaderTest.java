import model.Experiment;
import org.junit.jupiter.api.Test;
import storage.ExperimentLoader;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ExperimentLoaderTest {

    @Test
    public void testLoadExperiment() throws IOException {
        ExperimentLoader loader = new ExperimentLoader();
        Experiment experiment = loader.loadExperiment("src/test/resources/test_data/sample_experiment_data.txt");

        assertEquals("Sample Experiment", experiment.getName());
        assertEquals(3, experiment.getPopulations().size());
    }
}
