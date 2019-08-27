import javax.swing.*;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FlashCardFileSave extends JFrame {
    FlashCardFileSave() {
        initiate();
    }

    private JPanel panel;
    private JMenuBar menuBar;
    private JTextArea questions, answars;
    private Font font;
    private JButton next;
    private ArrayList<FlashCard> cards;


    private void initiate() {
        setTitle("Save File");
        setBounds(150, 150, 800, 800);
        panel = new JPanel();
        panel.setBackground(Color.BLUE);
        setResizable(false);

        menuBar = new JMenuBar();
        JMenuItem save = new JMenuItem("Save");
        JMenu file = new JMenu("File");
        file.add(save);
        menuBar.add(file);
        save.addActionListener(new saveMenu());


        JLabel q = new JLabel("Questions");
        q.setBounds(10, 50, 100, 30);
        q.setForeground(Color.WHITE);

        font = new Font("Arial", Font.BOLD, 22);
        questions = new JTextArea();
        questions.setFont(font);
        questions.setWrapStyleWord(true);
        questions.setLineWrap(true);
        JScrollPane scrollPane1 = new JScrollPane(questions);
        scrollPane1.setBounds(100, 50, 400, 300);
        scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel a = new JLabel("Answer");
        a.setForeground(Color.WHITE);
        a.setBounds(10, 400, 100, 30);

        answars = new JTextArea();
        answars.setFont(font);
        answars.setWrapStyleWord(true);
        answars.setLineWrap(true);
        JScrollPane scrollPane2 = new JScrollPane(answars);
        scrollPane2.setBounds(100, 400, 400, 300);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        cards = new ArrayList<>();
        next = new JButton("Next");
        next.setBounds(600, 350, 100, 50);
        next.addActionListener(new nextButton());


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(next);
        add(a);
        add(scrollPane1);
        add(scrollPane2);
        add(q);
        add(menuBar, BorderLayout.NORTH);
        add(panel);
    }

    private class nextButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            FlashCard flashCard = new FlashCard(questions.getText(), answars.getText());
            cards.add(flashCard);
            questions.setText("");
            answars.setText("");
        }
    }

    private class saveMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser=new JFileChooser();
            int dialog=chooser.showSaveDialog(panel);

            FlashCard flashCard=new FlashCard(questions.getText(),answars.getText());
            cards.add(flashCard);

            if(dialog==JFileChooser.APPROVE_OPTION){

                FileWriter writer= null;
                try {
                    writer = new FileWriter(new File(chooser.getSelectedFile().getPath()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Iterator iter= cards.iterator();
                while (iter.hasNext()){
                    FlashCard flashCard1= (FlashCard) iter.next();
                    try {
                        writer.write(flashCard1.getQuestion()+"/");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        writer.write(flashCard1.getAnswar()+"\n");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
