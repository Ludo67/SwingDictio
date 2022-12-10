import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ConstructorUnitTest {

    private Word word1;

    /**
     * set up word before each test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        word1 = new Word();
    }

    /**
     * test set and get word
     */
    @Test
    public void testSetWordAndGetWord() {
        word1.setWord("word");
        assertEquals("word", word1.getWord());
    }

    /**
     * test set definition and get definition
     */
    @Test
    public void testSetDefinitionAndGetDefinition() {
        word1.setDefinition("definition");
        assertEquals("definition", word1.getDefinition());
    }

    /**
     * test empty constructor
     */
    @Test
    public void testEmptyConstructor(){
        assertNull(word1.getWord());
        assertNull(word1.getDefinition());
    }

    /**
     * test constructor with params
     */
    @Test
    public void testConstructorWithParameters(){
        word1 = new Word("word", "definition");
        assertEquals("word", word1.getWord());
        assertEquals("definition", word1.getDefinition());
    }
}
