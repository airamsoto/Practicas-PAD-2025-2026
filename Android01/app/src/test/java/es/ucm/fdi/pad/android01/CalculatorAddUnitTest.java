package es.ucm.fdi.pad.android01;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorAddUnitTest {

    private static final double DELTA = 1e-9; //Se usa porque como estmamos usando doubles, no siempre son exactos

    @Test
    public void checkCalculatorAdd() {
        assertEquals(5, CalculatorAdd.add(2, 3), DELTA);
        assertEquals(-5, CalculatorAdd.add(-10, 5), DELTA);
        assertEquals(5.6, CalculatorAdd.add(2.5, 3.1), DELTA);
        assertEquals(-3, CalculatorAdd.add(-2.7, -0.3), DELTA);
    }
}
