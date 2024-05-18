import model.BacteriaPopulation;
import model.FoodPattern;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;

public class BacteriaPopulationTest {

    @Test
    public void testBacteriaPopulationCreation() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        BacteriaPopulation population = new BacteriaPopulation("Test Population", date, 1000, FoodPattern.CONSTANT);

        assertEquals("Test Population", population.getName());
        assertEquals(date, population.getStartDate());
        assertEquals(1000, population.getInitialBacteriaCount());
        assertEquals(FoodPattern.CONSTANT, population.getFoodPattern());
    }
}
