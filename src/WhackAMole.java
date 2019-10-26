import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.util.Random;

/**
 * A simple implementation for Whack a mole game.
 * @author shenx
 *
 */
public class WhackAMole {
    /**
     * This is the time count down.
     */
    private int count = 20;
    /**
     * This is the boolean to see if the start button is reclicked.
     */
    private boolean clicked;
    /**
     * This is the flag for the start button renewal.
     */
    private boolean flag = true;
    /**
     * This is the JButton for startButton.
     */
    private JButton startButton;
    /**
     * This is the time Area where we would show the time.
     */
    private JTextArea timeArea;
    /**
     * This is the scoreArea where we would show the score.
     */
    private JTextArea scoreArea;
    /**
     * This is the score counts.
     */
    private int currentScore;
    /**
     * This is the start button listener.
     */
    private ActionListener startListner = new StartButtonListener();
    /**
     * This is the animal button listner.
     */
    private ActionListener animalListner = new OtherButtonListener();
    /**
     * This is the buttonlist for containing all the animal buttons.
     */
    private ArrayList<AnimalButton> buttonList = new ArrayList<AnimalButton>();
    /**
     * Create this random class.
     */
    private Random random = new Random();
    /**
     * This is the constructor of this game class.
     */
    public WhackAMole() {
        /**
         * Create the main frame of this application.
         */
        JFrame frame = new JFrame("Whack A Mole Game");
        frame.setSize(900, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /**
         * Creat the main pane to contain operations.
         */
        JPanel mainPane = new JPanel();
        //Set the mainPane to be 9 seperated individual sections.
        mainPane.setLayout(new GridLayout(9, 1, 5, 5));
        //Set the top pane.
        TopPartForStart startPane = new TopPartForStart();
        //add top pane to the main pane.
        mainPane.add(startPane.startPane);
        //add the animal pane list for maintanance
        AnimalPanel[] allAnimalPanes = new AnimalPanel[8];
        for (int i = 0; i < 8; i++) {
            //construct the animal panel class so that for each instance we have the animalpane instance,
            //and the animal buttons[] instance
            allAnimalPanes[i] = new AnimalPanel();
            mainPane.add(allAnimalPanes[i].animalPane);
        }
        //Get all the buttons.
        for (int i = 0; i < allAnimalPanes.length; i++) {
            for (int j = 0; j < allAnimalPanes[i].animalButtons.length; j++) {
                buttonList.add(allAnimalPanes[i].animalButtons[j]);
            }
        }
        //Check if we got all the buttons
        System.out.println(buttonList.size());
        //testing
//        allAnimalPanes[0].animalButtons[0].m();
        frame.setContentPane(mainPane);
        frame.setVisible(true);
    }

    private JTextArea setTextArea(int x, int y, int width, int height) {
        JTextArea newArea = new JTextArea();
        newArea.setSize(width, height);
        newArea.setLocation(x, y);
        return newArea;
    }

    private JLabel setLabel(int x, int y, int width, int height, String whatFor) {
        JLabel newLabel = new JLabel(whatFor);
        newLabel.setLocation(x, y);
        newLabel.setSize(width, height);
        return newLabel;
    }

    private JButton setButton(int x, int y, int width, int height, String whatFor) {
        JButton newButton = new JButton(whatFor);
        newButton.setLocation(x, y);
        newButton.setSize(width, height);
        return newButton;
    }

    private class TopPartForStart {
        /**
         * This is the Jpanel for the start pane that this class would yield.
         */
        private JPanel startPane;
        /**
         * This is the constructor of this pane.
         */
        private TopPartForStart() {
            startPane = setTopPane();
        }
        private JPanel setTopPane() {
            startPane = new JPanel();
            startPane.setLayout(null);
            //set up the start button.
            startButton = setButton(150, 50, 100, 20, "Start");
            startButton.addActionListener(startListner);
            startPane.add(startButton);
            //set up the time left label.
            JLabel timeLeft = setLabel(300, 50, 100, 20, "Time Left");
            startPane.add(timeLeft);
            //set up the time left text area.
            timeArea = setTextArea(370, 50, 100, 20);
            startPane.add(timeArea);
            //set up the score label.
            JLabel score = setLabel(490, 50, 100, 20, "Score");
            startPane.add(score);
            //set up the score text area.
            scoreArea = setTextArea(540, 50, 100, 20);
            startPane.add(scoreArea);
            return startPane;
        }
    }

    private class AnimalButton extends JButton {
        /**
         * this is the final long serialID.
         */
        private static final long serialVersionUID = 1L;
        /**
         * This is the status.
         */
        private int status = 0;

        public void popUp() {
            this.setBackground(Color.GREEN);
            this.setText("UP");
            this.status = 1;
        }
        /**
         * This is the member function to make this button to be able to be killed.
         */
        public void killIt() {
            this.setBackground(Color.RED);
            this.setText("HIT");
            this.status = 3;
        }
        /**
         * This is the member function to make this button to be hide down.
         */
        public void hideDown() {
            this.setBackground(Color.DARK_GRAY);
            this.setText("");
            this.status = 0;
        }
    }

    private class AnimalPanel {
        /**
         * This is the animalPane.
         */
        private JPanel animalPane;
        /**
         * Create the animal buttons list to contain all the animal buttons.
         */
        private AnimalButton[] animalButtons;
        private AnimalPanel() {
            animalPane = new JPanel();
            animalPane.setLayout(null);
            animalPane.setLayout(new GridLayout(1, 8, 5, 5));
            animalPane.setBackground(Color.GRAY);
            animalButtons = new AnimalButton[8];
            for (int i = 0; i < 8; i++) {
                animalButtons[i] = new AnimalButton();
                animalButtons[i].setBackground(Color.DARK_GRAY);
                animalButtons[i].setOpaque(true);
                animalButtons[i].addActionListener(animalListner);
                animalPane.add(animalButtons[i]);
            }
        }
    }

    private class StartRunnable implements Runnable {
        /**
         * This is the sleep time for the thread.
         */
        private long mySleepTime;

        private StartRunnable(int sleepTime) {
            mySleepTime = sleepTime * 1000;
        }
        @Override
        public void run() {
            try {
                while (count > 0) {
                    clicked = true;
                    timeArea.append(String.valueOf(count));
                    Thread.sleep(mySleepTime);
                    timeArea.setText("");
                    count--;
                }
                clicked = false;
                //This is for sleeping 5 more seconds before we can start again.
                Thread.sleep(5000);
                scoreArea.setText("");
                flag = true;
                count = 20;
            } catch (Exception e) {
            }
        }
    }

    private class AnimalRunnable implements Runnable {
        /**
         * This is for the sleep time for the pop up.
         */
        private long upTime;
        /**
         * This is the sleep time for the down.
         */
        private long hitTime;
        /**
         * This is the constructor for this animal runnable.
         * @param popTime as the integer
         * @param downTime as the integer
         */
        private AnimalRunnable(float popTime, int downTime) {
            upTime = (long) (1000 * popTime);
            hitTime = 1000 * downTime;
        }
        @Override
        public void run() {
            try {
                while (clicked) {
                    int randomNum = random.nextInt(buttonList.size());
                    AnimalButton thisButton = buttonList.get(randomNum);
                    synchronized (thisButton) {
                        if (thisButton.status == 0) {
                            thisButton.popUp();
                            Thread.sleep(upTime);
                        } else {
                            Thread.sleep(hitTime);
                            thisButton.hideDown();
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == startButton) {
                //This is the sleep time for the count down, meaning that if we let the
                //Thread to sleep for 1000 ms before we decrease the count down,
                //The count down is actually 1 second interval
                int sleepTimeInSecs = 1;
                //Set up the show up time for the pop up
                float popTimeInSecs = (float) 0.5;
                //Set up the down time for the down
                int downTimeInSecs = 2;
                //Only when the flag is True, meaning that the only when the flag is true we can recreate another start thread.
                if (flag) {
                    //Set up the start button thread.
                    Runnable startButtonR = new StartRunnable(sleepTimeInSecs);
                    Thread buttonThread = new Thread(startButtonR);
                    buttonThread.start();
                    //Set up all the other animal buttons thread.
                    Runnable animalRun = new AnimalRunnable(popTimeInSecs, downTimeInSecs);
                    Thread animalThread = new Thread(animalRun);
                    animalThread.start();
                    flag = false;
                }
            }
        }
    }

    private class OtherButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
           AnimalButton thisButton = (AnimalButton) event.getSource();
           //If this button currently is alive then we should add score to the panel.
           //also we need to ensure that the start button is under running.
           if (thisButton.status == 1 && clicked) {
               System.out.println(timeArea.getText());
               thisButton.killIt();
               currentScore++;
               String scoreString = String.valueOf(currentScore);
               scoreArea.setText(scoreString);
           }
        }
    }

    public static void main(String[] args) {
        new WhackAMole();
    }
}
