import javax.swing.*;
import java.util.ArrayList;

public interface DictioInterface {

    public ArrayList<String> SearchWord(String searchWord);

    public void AddWord(String word, String definition) throws InvalidWordException;

    public void SaveAsDocumemnt();

    public int GetWordIndex(JList toLook);

    public void OpenDataInFileAndSaveAsList();
}
