import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Demo extends JFrame{
    private JPanel TestPanel;
    private JTextField txtName;
    private JButton btnClick;

    public Demo() {
        btnClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(btnClick,txtName.getText()+ ", Hello");
            }
        });
    }

    public static void main(String[] args) {
        Demo d = new Demo();
        d.setContentPane(d.TestPanel);
        d.setTitle("This is a Swing test");
        d.setSize(300, 400);
        d.setVisible(true);
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
