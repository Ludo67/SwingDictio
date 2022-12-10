import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class DictionUnitTest {

    private Word word1;
    private Word word2;
    private Dictio dictio1;

    @Before
    public void setUp() throws Exception {

        dictio1 = new Dictio();
        dictio1.AddWord("Word", "Definition");
        dictio1.AddWord("Wordledydumb", "Definition is long as well");
    }

    @Test
    public void testAddWord(){
        ArrayList<String> List = new ArrayList<>();
        List.add("Word");
        JList foundWord = new JList(dictio1.SearchWord("Word").toArray());
        int index = dictio1.GetWordIndex(foundWord);
        assertEquals("Definition", dictio1.wordsUpdated.get(index).getDefinition());
    }

    @Test
    public void testUpdateDefinitionOfExistingWord(){
        ArrayList<String> List = new ArrayList<>();
        List.add("Word");
        dictio1.AddWord("Word", "New Definition");
        JList foundWord = new JList(dictio1.SearchWord("Word").toArray());
        int index = dictio1.GetWordIndex(foundWord);
        assertEquals("New Definition", dictio1.wordsUpdated.get(index).getDefinition());
    }

    @Test
    public void testSearchWord(){
        ArrayList<String> List = new ArrayList<>();
        List.add("Word");
        List.add("Wordledydumb");
        assertEquals(List, dictio1.SearchWord("Wor"));


    }
}