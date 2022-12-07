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
public class Dictio extends JFrame{
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
     * List de tout les mots
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
     * Array List pour les mots
     */
    private static ArrayList<String> words = new ArrayList<String>();
    /**
     * Array List pour les definitions
     */
    private static ArrayList<String> definitions = new ArrayList<String>();
    /**
     * Array List pour les mots trouvés après la recherche
     */
    private ArrayList<String> foundWordList = new ArrayList<>();

    /**
     * Class that contains all the actions for the buttons
     */
    public Dictio() {
        loadBtn.addActionListener(new ActionListener() {
            @Override
            /**
             * Fonction qui prend le ActionEvent quand le boutton Ajouter est actioné.
             * Elle ouvrira une fenetre qui demande à l'utilisateur de selectionner un fichier txt.
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
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

                for(String elem: lines) {
                    String[] result = elem.split("&", 2);
                    words.add(result[0].toLowerCase());
                    definitions.add(result[1]);
                }

                ArrayList<String> wordList = new ArrayList<>();
                wordList.addAll(words);

                DefaultListModel model = new DefaultListModel();
                model.addAll(words);
                allWordList.setModel(model);
                reader.close();
            }
        });
        /**
         * Fonction qui prend un ActionEvent puis ajoute un mot après que l'utilisateur clique sur le bouton ajouter/modifier
         * Le mot dans la boite à recherche sera ajouté à la liste
         * @param e
         */
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = searchBox.getText();
                String definition = description.getText();
                char[] chars = word.toCharArray();
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
                    words.add(word.trim());
                    definitions.add(definition);
                    DefaultListModel model = (DefaultListModel) allWordList.getModel();
                    model.addElement(word);
                    allWordList.setModel(model);
                }
                else{
                    JOptionPane.showMessageDialog(dictioPanel,"Invalid word. Only letters are allowed");
                }
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
            /**
             * Fonction qui prend le ListSelectionEvent quand un élément de la liste a été selectionné
             * @param e
             */
            public void valueChanged(ListSelectionEvent e) {
//
//                System.out.println(foundWords.getSelectedValue());
                int indexInListOfWords = 0;
                for (int i = 0; i < words.size(); i++){
                    if (words.get(i).toLowerCase().equals(foundWords.getSelectedValue())){
                        indexInListOfWords = i;
                    }

                }
                description.setText(definitions.get(indexInListOfWords));
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            /**
             * Fonction qui prend le ActionListener qunad le boutton "charger" est cliqué
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PrintWriter writer= new PrintWriter(new File("D:\\dictio.txt"));

                    String[] wordsArrays = words.toArray(new String[0]);

                    for(int i=0; i<wordsArrays.length; i++){
                        writer.write(words.get(i) + " & " + definitions.get(i) + "\n");
                    }

                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

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
    private void SearchWord(String searchWord){
        foundWordList.clear();
        boolean hasMatch = false;
        for (int i = 0; i < words.size(); i++ ){
            if(words.get(i).toLowerCase().trim().equals(searchWord.toLowerCase().trim()) && !hasMatch){
                description.setText(definitions.get(i));
                hasMatch = true;
            }
            else if (!hasMatch){
                description.setText(" ");

            }
            if (words.get(i).toLowerCase().trim().contains(searchWord.toLowerCase().trim())){
                foundWordList.add(words.get(i));

            }
        }

        DefaultListModel model = new DefaultListModel();
        model.addAll(foundWordList);
        foundWords.setModel(model);
        foundWords.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

}
