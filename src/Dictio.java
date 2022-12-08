import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that extends JFrame containing all the elements in the Dictio.form.
 * Contains also actions that can be performed by the user.
 */
public class Dictio extends JFrame implements DictioInterface{
    /**
     * Boite de recherche
     */
    private JTextField searchBox;
    private JPanel dictioPanel;
    /**
     * Boutton pour charger les donnees
     */
    private JButton loadBtn;
    /**
     * Boutton pour sauvegarder
     */
    private JButton saveBtn;
    /**
     * Boutton pour ajouter les donnees
     */
    private JButton addBtn;
    private JPanel btnPanel;
    /**
     * List de tous les mots
     */
    private JList allWordList;
    private JPanel wordPanel;
    /**
     * Liste des mots trouvés
     */
    private JList foundWords;
    /**
     * Boite de texte pour definition
     */
    private JTextArea description;
    private JLabel nothing;
    private static File[] files;
    /**
     * Array List pour les données ajoutées
     */
    private static ArrayList<String> lines = new ArrayList<String>();
    /**
     * Array List pour les mots trouvés après la recherche
     */
    private ArrayList<String> foundWordList = new ArrayList<>();

    /**
     * Class that contains all the actions for the buttons
     */

    private ArrayList<Word> wordsUpdated = new ArrayList<>();

    public Dictio() {
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenDataInFileAndSaveAsList();
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = searchBox.getText();
                String definition = description.getText();
                AddWord(word, definition);
            }
        });

        searchBox.addKeyListener(new KeyListener() {
            @Override
            /**
             * Fonction qui prend le KeyEvent quand une lettre est ajouté dans la bar de recherche
             * @param e
             */
            public void keyTyped(KeyEvent e) {
//                SearchWord();
            }

            @Override
            /**
             * Fonction qui prend le KeyEvent quand la touche du clavier est cliqué dans la bar de recherche
             * @param e
             */
            public void keyPressed(KeyEvent e) {
//                SearchWord();
            }
            /**
             * Fonction qui prend le KeyEvent quand la touche du clavier est remonté dans la bar de recherche
             * @param e
             */
            @Override
            public void keyReleased(KeyEvent e) {
                String searchWord = searchBox.getText();
                SearchWord(searchWord);
            }

        });

        foundWords.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                DisplayWordDefinitionWhenWordSelected(foundWords);
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveAsDocumemnt();

            }
        });

        allWordList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                DisplayWordDefinitionWhenWordSelected(allWordList);
            }
        });
    }

    public static void main(String[] args) throws IOException {
        Dictio d = new Dictio();
        d.setContentPane(d.dictioPanel);
        d.setTitle("Dictio");
        d.setSize(900, 400);
        d.setVisible(true);
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Fonction qui cherche le mot dans le searchBox
     * @param searchWord Le mot recherché
     */
    public void SearchWord(String searchWord){
        boolean hasMatch = false;
        DefaultListModel model = new DefaultListModel();
        ArrayList<String> searchList = new ArrayList<>();
        for (int i = 0; i < wordsUpdated.size(); i++ ){
            if(wordsUpdated.get(i).getWord().toLowerCase().trim().equals(searchWord.toLowerCase().trim()) && !hasMatch){
                description.setText(wordsUpdated.get(i).getDefinition());
                hasMatch = true;
            }
            else if (!hasMatch){
                description.setText(" ");

            }
            if (wordsUpdated.get(i).getWord().toLowerCase().trim().contains(searchWord.toLowerCase().trim())){
                searchList.add(wordsUpdated.get(i).getWord());

            }
        }
        model.addAll(searchList);
        foundWords.setModel(model);
        foundWords.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Fonction qui prend un "word" et une "definition", puis regarde s'il existe deja. Si oui, il sera mis a jour. Sinon, il sera ajoute
     * @param word String
     * @param definition String
     * @throws Exception invalid word
     */
    @Override
    public void AddWord(String word, String definition) {
        char[] chars = word.toCharArray();
        boolean wordFound = false;
        int indexOfWord = 0;
        int count = 0;

        for(char c : chars){
            if(Character.isDigit(c) || !Character.isAlphabetic(c) || Character.isWhitespace(c)){
                try {
                    throw new Exception("Invalid word. Only letters are allowed");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                count++;
            }
        }

        if(count==0) {
            for (int i = 0; i < wordsUpdated.size(); i++){
                if (word.trim().equals(wordsUpdated.get(i).getWord().trim()) && !wordFound){
                    wordFound = true;
                    indexOfWord = i;
                }
            }

            if (wordFound){
                wordsUpdated.get(indexOfWord).setDefinition(definition);
            }
            else{
                Word newWord = new Word(word.trim(), definition);
                wordsUpdated.add(newWord);
                DefaultListModel modelUpdated = (DefaultListModel) allWordList.getModel();
                modelUpdated.addElement(newWord.getWord());
                allWordList.setModel(modelUpdated);
            }
        }
        else{
            JOptionPane.showMessageDialog(dictioPanel,"Invalid word. Only letters are allowed");
        }
    }
    /**
     * Fonction qui sauvegarde le document
     * @throws FileNotFoundException when file is not found
     */
    @Override
    public void SaveAsDocumemnt() {
        try {
            PrintWriter writer= new PrintWriter(new File("D:\\dictio.txt"));
            String[] wordsArrayUpdated = new String[wordsUpdated.size()];

            for(int i=0; i<wordsUpdated.size(); i++){
                wordsArrayUpdated[i] = wordsUpdated.get(i).getWord() + " & " + wordsUpdated.get(i).getDefinition() + "\n";
            }

            writer.flush();
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Fonction qui montre la definition quand le mot dans la liste de recherche est selectionee
     */
    @Override
    public void DisplayWordDefinitionWhenWordSelected(JList toLook) {
        int indexInListOfWords = 0;
        for (int i = 0; i < wordsUpdated.size(); i++){
            if(wordsUpdated.get(i).getWord().toLowerCase().equals(toLook.getSelectedValue())){
                indexInListOfWords = i;
            }

        }
        description.setText(wordsUpdated.get(indexInListOfWords).getDefinition());
    }

    /**
     * Ouvre le fichier selectionné et ajoute les definitions et mots au liste respective
     */
    @Override
    public void OpenDataInFileAndSaveAsList() {
        FileDialog fd = new FileDialog(new JFrame());
        fd.setVisible(true);
        files = fd.getFiles();

        Scanner reader = null;
        try {
            reader = new Scanner(files[0]);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        while (reader.hasNextLine()){
            String line = reader.nextLine();
            lines.add(line);
        }

        ArrayList<String> wordList = new ArrayList<>();
        for(String elem: lines) {
            String[] result = elem.split("&", 2);
            Word word = new Word(result[0].toLowerCase(), result[1]);
            wordsUpdated.add(word);

            wordList.add(result[0].toLowerCase());
        }

        DefaultListModel model = new DefaultListModel();
        model.addAll(wordList);
        allWordList.setModel(model);
        reader.close();
    }

}
