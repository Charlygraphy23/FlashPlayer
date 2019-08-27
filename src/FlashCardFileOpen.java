import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class FlashCardFileOpen extends JFrame {

    FlashCardFileOpen(){
        initiate();
    }

    //INITALIZATION
    JPanel panel;
    private JMenuBar menuBar;
    private Font font;
    private JLabel qLabel,aLabel,questionLabel,answerLabel;
    private JButton buttonNext;
    private ArrayList<FlashCard> cards;
    private JFileChooser chooser;
    private Iterator iterate;
    private FlashCard currentCard;
    private boolean hasAnswer;



    private void initiate() {
        setTitle("Open File");
        setBounds(160,160,900,800);
        setResizable(false);
        panel=new JPanel();
        panel.setBackground(Color.DARK_GRAY);

        menuBar = new JMenuBar();
        JMenuItem open = new JMenuItem("Open");
        JMenu file = new JMenu("File");
        file.add(open);
        menuBar.add(file);
        open.addActionListener(new openMenu());

        // Question Part
        qLabel=new JLabel("Question");
        qLabel.setBounds(20,50,100,30);
        qLabel.setForeground(Color.WHITE);
        qLabel.setBorder(new LineBorder(Color.WHITE,1, true));

        questionLabel=new JLabel();
        questionLabel.setBounds(150,50,600,300);
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setBorder(new LineBorder(Color.BLACK,1, true));


        //Answer Part
        aLabel=new JLabel("Answer");
        aLabel.setBounds(20,380,100,30);
        aLabel.setForeground(Color.WHITE);
        aLabel.setBorder(new LineBorder(Color.WHITE,1, true));

        answerLabel=new JLabel();
        answerLabel.setBounds(150,380,600,300);
        answerLabel.setForeground(Color.WHITE);
        answerLabel.setBorder(new LineBorder(Color.BLACK,1, true));

        buttonNext=new JButton("Get Card");
        buttonNext.setBounds(780,350,100,30);
        buttonNext.setBackground(Color.LIGHT_GRAY);
        buttonNext.addActionListener(new ButtonListner());




        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(buttonNext);
        add(answerLabel);
        add(aLabel);
        add(questionLabel);
        add(qLabel);
        add(menuBar,BorderLayout.NORTH);
        add(panel);
    }


    private class openMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooser =new JFileChooser();
            cards=new ArrayList<>();
            int dialog=chooser.showOpenDialog(panel);

            if(dialog==JFileChooser.APPROVE_OPTION){

                try {
                    saveDialog();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    private void saveDialog() throws IOException {
        BufferedReader reader =new BufferedReader(new FileReader(chooser.getSelectedFile().getPath()));
        String line=null;
        while ((line=reader.readLine())!=null){
            splitLines(line);
        }
        iterate=cards.iterator();
        nextCard();



    }

    private void nextCard() {
        currentCard= (FlashCard) iterate.next();
        questionLabel.setText(currentCard.getQuestion());
        buttonNext.setText("Show Answer");
        hasAnswer=true;
    }



    private void splitLines(String l) {
        StringTokenizer tokenizer=new StringTokenizer(l,"/");
        while (tokenizer.hasMoreTokens()){
            FlashCard card=new FlashCard(tokenizer.nextToken(),tokenizer.nextToken());
            cards.add(card);
        }
    }


    private class ButtonListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(hasAnswer){
                answerLabel.setText(currentCard.getAnswar());
                buttonNext.setText("Next Card");
                hasAnswer=false;
            }
            else if(questionLabel.getText()==("")){
                JOptionPane.showMessageDialog(panel,"Please Open a Valid File","Alert",JOptionPane.WARNING_MESSAGE);
            }
            else if(iterate.hasNext()){
                nextCard();
                answerLabel.setText("");
            }

            else {
                questionLabel.setText("Dont Have any Questions");
                buttonNext.setEnabled(false);
            }

        }
    }


    //MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardFileOpen().setVisible(true);
            }
        });
    }



}

