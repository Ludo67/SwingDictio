import javax.swing.*;
import java.util.ArrayList;

public interface DictioInterface {

    public void SearchWord(String searchWord);

    public void AddWord(String word, String definition);

    public void SaveAsDocumemnt();

    public void DisplayWordDefinitionWhenWordSelected(JList toLook);

    public void OpenDataInFileAndSaveAsList();
}
