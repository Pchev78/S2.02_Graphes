package representation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrapheMAdjTest {

    @Test
    void testToString() {
        GrapheMAdj matriceVideTest = new GrapheMAdj(3,3);

        String matriceTest = matriceVideTest.toString();
        String matriceDevrait = "-1, -1, -1\n-1, -1, -1\n-1, -1, -1";
        assertEquals(matriceDevrait,matriceTest);
    }
}