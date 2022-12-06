import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictio extends JFrame{
    private JTextField searchBox;
    private JPanel dictioPanel;
    private JLabel description;
    private JButton loadBtn;
    private JButton saveBtn;
    private JButton addBtn;
    private JPanel btnPanel;
    private JList allWordList;
    private JPanel wordPanel;
    private JScrollBar scrollBar;
    private JList foundWords;
    private static File[] files;
    private static ArrayList<String> lines = new ArrayList<String>();
    private static ArrayList<String> words = new ArrayList<String>();
    private static ArrayList<String> definitions = new ArrayList<String>();
    private ArrayList<String> foundWordList = new ArrayList<>();

    public Dictio() {
        loadBtn.addActionListener(new ActionListener() {
            @Override
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
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var word = JOptionPane.showInputDialog("Entrer un nouveau nom");
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
                    words.add(word);
                    DefaultListModel model = (DefaultListModel) allWordList.getModel();
                    model.addElement(word);
                    allWordList.setModel(model);
                }
                else{
                    JOptionPane.showMessageDialog(dictioPanel,"Invalid word. Only letters are allowed");
                }
            }
        });
        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                System.out.println(scrollBar.getValue());
            }
        });

        searchBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                String searchWord = searchBox.getText();

                for (int i = 0; i < words.size(); i++ ){
                    if (words.get(i).toLowerCase().contains(searchWord.toLowerCase())){
                        foundWordList.add(words.get(i));

                    }
                }

                DefaultListModel model = new DefaultListModel();
                model.addAll(foundWordList);
                foundWords.setModel(model);
                foundWords.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                foundWordList.clear();
                String searchWord = searchBox.getText();
                for (int i = 0; i < words.size(); i++ ){
                    if (words.get(i).toLowerCase().contains(searchWord.toLowerCase())){
                        foundWordList.add(words.get(i));

                    }
                }

                DefaultListModel model = new DefaultListModel();
                model.addAll(foundWordList);
                foundWords.setModel(model);
                foundWords.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }

        });

        foundWords.addListSelectionListener(new ListSelectionListener() {
            @Override
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
}
