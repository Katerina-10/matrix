import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class testMain {

    @Test
    public void testReadFile() {
        assertEquals(main.readFile("src/main/inputF.txt"),0);
        assertEquals(main.readFile("src/main/inputF_test.txt"),1);
    }

}
