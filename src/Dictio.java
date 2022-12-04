import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictio extends JFrame{
    private JTextField textField1;
    private JPanel dictioPanel;
    private JLabel description;
    private JButton loadBtn;
    private JButton saveBtn;
    private JButton addBtn;
    private JPanel btnPanel;
    private JList allWordList;
    private JPanel wordPanel;
    private JScrollBar scrollBar;
    private static File[] files;
    private static ArrayList<String> lines = new ArrayList<String>();
    private static ArrayList<String> words = new ArrayList<String>();
    private static ArrayList<String> definitions = new ArrayList<String>();

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
    }

    public static void main(String[] args) throws IOException {
        Dictio d = new Dictio();
        d.setContentPane(d.dictioPanel);
        d.setTitle("Dictio");
        d.setSize(900, 400);
        d.setVisible(true);
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
