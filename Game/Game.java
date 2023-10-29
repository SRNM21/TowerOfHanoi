package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.Timer;

/**
 * <p>Simple GUI program of Tower of Hanoi.</p>
 * 
 * <p>This Class/Frame contains the main game of the program.</p>
 * 
 * @author GreggyBoii (Gengar)
 * @since 1.0
 */
public class Game implements KeyListener
{   
    /* 
    * STATUS
    */

    /**
     * Current number of {@code disks} on the game.
     */
    private int diskCount = 3;
    
    /**
     * Holds the {@code disk} object to be placed on a specified {@code peg}.
     */
    private Disk currentDisk = null;

    /**
     * Holds the address of the picked {@code disk's} {@code peg}.
     */
    private Peg prevPeg = null;

    /* 
    * FLAGS
    */
    
    /**
     * Flag to identify whether you place or select a {@code disk}.
     */
    private boolean placeFlag = true;    
    
    /**
     * Sets the timer only when the game is started.
     */
    private boolean startFlag = true;
    
    /**
     * Freeze the {@code pegs} and timer once the game is completed.
     */
    private boolean finished = false;    

    /* 
    * PROGRESS COMPONENTS
    */  

    /**
     * Indicates the seconds in to the game.
     */
    private int sec = 0;   

    /**
     * Indicates the minutes in to the game.
     */ 
    private int min = 0;
    
    /**
     * Indicates the hours in to the game.
     */ 
    private int hrs = 0;
    
    /**
     * Indicates the number of moves made.
     */ 
    private int moves = 0;

    /**
     * JFrame component for the main frame.
     */ 
    private final JFrame frame = new JFrame("Tower of Hanoi");

    /**
     * JLabel component that display the current time.
     */ 
    private final JLabel timeLbl = new JLabel("Time: 0s");

    /**
     * JLabel component that display the moves made.
     */ 
    private final JLabel movesLbl = new JLabel("Moves: " + moves);

    /**
     * JLabel component that display the current number of {@code disks}.
     */ 
    private final JLabel noOfDisksLbl = new JLabel("Disk: " + diskCount);

    /**
     * JLabel component that display the best possible number of moves.
     */ 
    private final JLabel bestMoveLbl = new JLabel("Best Moves: 7");
    
    /* 
    * PEGS
    */

    /**
     * {@code Peg A} Object with already has three {@code disk} to start.
     */ 
    private final Peg pegA = new Peg(diskCount);

    /**
     * {@code Peg B} Object that can be place {@code disk} with.
     */ 
    private final Peg pegB = new Peg(0);

    /**
     * {@code Peg C} Object that can be place {@code disk} with.
     */ 
    private final Peg pegC = new Peg(0);

    /**
     * Constructor of the main game frame.
     */
    Game()
    {   
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Images\\Icon.png"));
        frame.getContentPane().setBackground(new Color(222, 226, 230));
        frame.setSize(TowerOfHanoi.FRAME_WIDTH, TowerOfHanoi.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);        
        frame.setFocusable(true);
        frame.addKeyListener(this);
        frame.requestFocus();        
        frame.addWindowListener(new WindowAdapter() 
        { 
            public void windowClosing(WindowEvent we) 
            {
                close(); 
            } 
        });

        JPanel gamePnl = new JPanel();
        gamePnl.setSize(TowerOfHanoi.FRAME_WIDTH, TowerOfHanoi.FRAME_HEIGHT);
        gamePnl.setLayout(new BoxLayout(gamePnl, BoxLayout.Y_AXIS));
        gamePnl.setBackground(new Color(222, 226, 230));
        gamePnl.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        /* 
         * PROGRESS COMPONENTS
        */
        JPanel progressPnl = new JPanel(new GridLayout(1, 2));      
        progressPnl.setMaximumSize(new Dimension(TowerOfHanoi.FRAME_WIDTH, 100));    
        progressPnl.setBackground(new Color(222, 226, 230));

        JPanel timePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePnl.setBackground(new Color(222, 226, 230));

        JPanel movesPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        movesPnl.setBackground(new Color(222, 226, 230));
        
        timeLbl.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(34f));
        movesLbl.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(34f));
        
        /* 
         * GAME COMPONENTS
        */
        JPanel pegsPnl = new JPanel(new GridLayout(1, 3));
        pegsPnl.setPreferredSize(new Dimension(TowerOfHanoi.FRAME_WIDTH, 400));    
        pegsPnl.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
        pegsPnl.setBackground(new Color(222, 226, 230));

        JPanel utilityPnl = new JPanel(new GridLayout(1, 2));        
        utilityPnl.setMaximumSize(new Dimension(TowerOfHanoi.FRAME_WIDTH, 200));    

        JPanel noOfDiskPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        noOfDiskPnl.setBackground(new Color(222, 226, 230));

        noOfDisksLbl.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(34f));
        bestMoveLbl.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(34f));
        
        JButton decrementDiskBtn = new JButton("-");
        decrementDiskBtn.setPreferredSize(new Dimension(50, 50));        
        decrementDiskBtn.setBackground(Color.WHITE);
        decrementDiskBtn.setBorder(new LineBorder(Color.BLACK, 5));
        decrementDiskBtn.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(28f));
        decrementDiskBtn.setFocusPainted(false);
        decrementDiskBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        decrementDiskBtn.addActionListener((e) -> { decrementDisk(); });
        
        JButton incrementDiskBtn = new JButton("+");
        incrementDiskBtn.setPreferredSize(new Dimension(50, 50));        
        incrementDiskBtn.setBackground(Color.WHITE);
        incrementDiskBtn.setBorder(new LineBorder(Color.BLACK, 5));
        incrementDiskBtn.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(28f));
        incrementDiskBtn.setFocusPainted(false);
        incrementDiskBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        incrementDiskBtn.addActionListener((e) -> { incrementDisk(); });
        
        JPanel utilityBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        utilityBtns.setPreferredSize(new Dimension(TowerOfHanoi.FRAME_WIDTH / 2, 50));  
        utilityBtns.setBackground(new Color(222, 226, 230));

        JButton exitBtn = new JButton("Exit");
        exitBtn.setPreferredSize(new Dimension(120, 50));        
        exitBtn.setBackground(Color.WHITE);
        exitBtn.setBorder(new LineBorder(Color.BLACK, 5));
        exitBtn.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(28f));
        exitBtn.setFocusPainted(false);
        exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitBtn.addActionListener((e) -> { close(); });

        JButton resetBtn = new JButton("Reset");
        resetBtn.setPreferredSize(new Dimension(120, 50));        
        resetBtn.setBackground(Color.WHITE);
        resetBtn.setBorder(new LineBorder(Color.BLACK, 5));
        resetBtn.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(28f));
        resetBtn.setFocusPainted(false);
        resetBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetBtn.addActionListener((e) -> { reset(); });

        /* 
         * ADDING COMPONENTS
        */
        timePnl.add(timeLbl);
        movesPnl.add(movesLbl);

        progressPnl.add(timePnl);        
        progressPnl.add(movesPnl);

        pegsPnl.add(pegA, new GridLayout());
        pegsPnl.add(pegB, new GridLayout());
        pegsPnl.add(pegC, new GridLayout());

        noOfDiskPnl.add(noOfDisksLbl);        
        noOfDiskPnl.add(Box.createRigidArea(new Dimension(10, noOfDiskPnl.getHeight())));
        noOfDiskPnl.add(decrementDiskBtn);
        noOfDiskPnl.add(incrementDiskBtn);
        noOfDiskPnl.add(Box.createRigidArea(new Dimension(30, noOfDiskPnl.getHeight())));
        noOfDiskPnl.add(bestMoveLbl);

        utilityBtns.add(exitBtn);
        utilityBtns.add(resetBtn);

        utilityPnl.add(noOfDiskPnl);
        utilityPnl.add(utilityBtns);

        gamePnl.add(progressPnl);
        gamePnl.add(pegsPnl);
        gamePnl.add(utilityPnl);
        
        frame.add(gamePnl);
        frame.setVisible(true);
    }

    /**
     * Increments the number of {@code disk}.
     */
    private void incrementDisk()
    {
        if (diskCount < 8)
        {
            ++diskCount;
            reset();
        }

        frame.requestFocus();
    }

    /**
     * Decrements the number of {@code disk}.
     */
    private void decrementDisk()
    {                
        if (diskCount > 3)
        {        
            --diskCount;
            reset();
        }        
        
        frame.requestFocus();
    }

    /**
     * Reset the all the progress made including the {@code pegs} and {@code disks}.
     */
    private void reset()
    {
        // Reset all the label's texts
        timer.stop();
        timeLbl.setText("Time: 0s");
        movesLbl.setText("Moves: 0");
        noOfDisksLbl.setText("Disk: " + diskCount);
        bestMoveLbl.setText("Best Moves: " + (int) (Math.pow(2, diskCount) - 1));
        frame.requestFocus();

        // Reset all the flags
        currentDisk = null;
        prevPeg = null;
        startFlag = true;
        placeFlag = true;
        finished = false;
        sec = 0;
        moves = 0;

        // Clear all pegs
        pegA.clearPeg();
        pegB.clearPeg();
        pegC.clearPeg();

        // Reput the disk on Peg A
        for (int i = 1; i <= diskCount; i++) pegA.addDisk(i);
    }

    /**
     * Select the top most {@code disk} on the specific {@code peg}.
     * 
     * @param peg the {@code peg} where the {@code disk} is selected.
     */
    private void removeTopDisk(Peg peg)
    {
        if (peg.getDiskCount() > 0)
        {
            Disk currDisk = (Disk) peg.peek();
            currDisk.highlightDisk(true);

            currentDisk = currDisk;
            prevPeg = peg;
            placeFlag = false;
        }
    }

    /**
     * Place the selected {@code disk} on the specified {@code peg}.
     * 
     * @param peg the {@code peg} where the {@code disk} is going to be placed.
     */
    private void placeDisk(Peg peg)
    {
        currentDisk.highlightDisk(false);
        placeFlag = true;

        /*
         * Prevents placing disk on the :
         *      - same peg 
         *      - top of the smaller disk
         */
        if ((peg == prevPeg) || (peg.getDiskCount() > 0 && (currentDisk.getStatus() > peg.peek().getStatus()))) return;

        prevPeg.removeTopDisk();
        peg.placeDisk(currentDisk);
        movesLbl.setText("Moves: " + ++moves);

        currentDisk = null;
        prevPeg = null;

        if (startFlag) 
        {
            timer.start();
            startFlag = false;
        }

        if (isFinished())
        {
            timer.stop();
            finished = true;
            JOptionPane.showMessageDialog(null, 
                "You successfully completed the tower! \nPress reset to play again.", 
                "Tower of Hanoi", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Compares the reference stack from {@code Peg A} to the current stack of {@code Peg C} which determines if the game is finished.
     * 
     * @return {@code true} if {@code Peg A} reference stack is equals to the current stack of {@code Peg C}, {@code false} otherwise.
     */
    public boolean isFinished()
    {
        if (pegA.getReference().size() != pegC.getStack().size()) return false;
        return pegA.getReference().toString().equals(pegC.getStack().toString());
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        if (finished) return;

        if (placeFlag)
        {
            switch (e.getKeyChar())
            {
                case 'a' -> { removeTopDisk(pegA); }
                case 's' -> { removeTopDisk(pegB); }
                case 'd' -> { removeTopDisk(pegC); }
            }
        }
        else
        {
            switch (e.getKeyChar())
            {
                case 'a' -> { placeDisk(pegA); }
                case 's' -> { placeDisk(pegB); }
                case 'd' -> { placeDisk(pegC); }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
    /**
     * <p>Timer that indicates the current time in to the game.</p>
     * 
     * <p>Update the {@code timeLbl's} text every seconds in to the game.</p>
     */
    private Timer timer = new Timer(1000, new ActionListener() 
    {   
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            sec++;
            
            if (sec >= 60) 
            {
                min++;
                sec = 0;
            }

            if (min >= 60)
            {
                hrs++;
                min = 0;
            }

            if (hrs > 0) timeLbl.setText("Time: " + String.valueOf(hrs + "h "+ min + "m "+ sec + "s"));
            else if (min > 0) timeLbl.setText("Time: " + String.valueOf(min + "m "+ sec + "s"));
            else timeLbl.setText("Time: " + String.valueOf(sec + "s"));
        }
    });

    /**
     * Asks user for confirmation on exit.
     */
    private void close()
    {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Do you really want to exit the game?", 
            "Tower of Hanoi", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.OK_OPTION) frame.dispose();
        else if (confirm == JOptionPane.NO_OPTION) frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}

/*
 * GAME PEG
 */

/**
 * {@code Peg} class that inherits JPanel as a visual representation of the actual {@code peg}.
 * 
 * @author GreggyBoii (Gengar)
 * @since 1.0
 */
class Peg extends JPanel
{
    /*
    * PEG ATTRIBUTES
    */
    
    /**
     * Preffered width for {@code peg}.
     */
    private final int PEG_WIDTH = 20;
    
    /**
     * Preffered height for {@code peg}.
     */
    private final int PEG_HEIGHT = 400;

    /**
     * Main or current stack of the {@code peg} which updates eveytime the {@code disk} leave.
     */
    private Stack<Integer> stack = new Stack<Integer>();

    /**
     * <p>reference stack that can not be update through ingame.</p>
     * 
     * <p>The elements of this stack is equals to the original elements of the main stack.</p>
     */
    private Stack<Integer> reference = new Stack<Integer>();

    /*
    * CONTAINER
    */

    /**
     * The container of {@code peg} where the disk is visually placed.
     */
    private final Box content = Box.createVerticalBox();

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);

        // Paint peg
        g.setColor(Color.BLACK);
        g.fillRect((this.getWidth() - PEG_WIDTH) / 2, 120, PEG_WIDTH, PEG_HEIGHT);
    }

    /**
     * Constructor of {@code peg} class that add {@code disk} based on the passed integral value in the parameter.
     * 
     * @param disks indicate the number of {@code disks} on the {@code peg}.
     */
    public Peg(int disks)
    {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(222, 226, 230));
        add(content, BorderLayout.SOUTH);

        for (int i = 1; i <= disks; i++) this.addDisk(i);
    }

    /**
     * Add or increment {@code disk} object on this {@code peg}.
     * 
     * @param stat the hierarchy status of the {@code disk}.
     */
    public void addDisk(int stat)
    {
        reference.add(0, stat);
        stack.add(0, stat);
        content.add(new Disk(stat));
        update();
    }

    /**
     * Place passed {@code disk} object on this {@code peg}.
     * 
     * @param disk {@code disk} object to be placed.
     */
    public void placeDisk(Disk disk)
    {
        stack.add(disk.getStatus());
        content.add(disk, 0);
        update();
    }
    
    /**
     * Clear all the {@code disk} objects on this {@code peg}.
     */
    public void clearPeg()
    {
        reference.clear();
        stack.clear();
        content.removeAll();
        update();
    }

    /**
     * Look at the top most {@code disk} object on this {@code peg} without removing it.
     * 
     * @return the top most {@code disk}.
     */
    public Disk peek()
    {
        return (Disk) content.getComponent(0);
    }
        
    /**
     * Remove the top most {@code disk} object on this {@code peg}.
     */
    public void removeTopDisk()
    {
        stack.pop();
        content.remove(0);
        update();
    }

    /**
     * Count the total number of {@code disk} object on this {@code peg}.
     * 
     * @return the total number of {@code disk}.
     */
    public int getDiskCount()
    {
        return content.getComponentCount();
    }

    /**
     * Update or repaint the {@code pegs} user interfacee.
     */
    private void update()
    {
        content.repaint();
        content.revalidate();
    }

    /**
     * Returns the current stack of this {@code peg}.
     * 
     * @return the current stack of this peg.
     */
    public Stack<Integer> getStack()
    {
        return stack;
    }

    /**
     * Returns the reference stack of this {@code peg}.
     * 
     * @return the reference stack of this peg.
     */
    public Stack<Integer> getReference()
    {
        return reference;
    }
}

/*
 * GAME DISK
 */
 
/**
 * {@code Disk} class that inherits JPanel as a visual representation of the actual {@code disk}.
 * 
 * @author GreggyBoii (Gengar)
 * @since 1.0
 */
class Disk extends JPanel
{
    /**
     * Hierarchy status of the {@code disk}.
     */
    private int status = 0;

    /**
     * Constructor of the {@code disk} class that initialize the status and size of the {@code disk}.
     */
    public Disk(int stat) 
    {
        this.status = stat;
        this.setBackground(Color.BLUE);
        this.setBorder(new LineBorder(Color.BLACK, 5));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Default size
        Dimension size = new Dimension(80, 40);

        switch (stat) 
        {
            case 2 -> size = new Dimension(120, 40);
            case 3 -> size = new Dimension(160, 40);
            case 4 -> size = new Dimension(200, 40);
            case 5 -> size = new Dimension(240, 40);
            case 6 -> size = new Dimension(280, 40);
            case 7 -> size = new Dimension(320, 40);
            case 8 -> size = new Dimension(360, 40);
        }
        
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
    }

    /**
     * Change the disk's border color to red if {@code true} which identifies as highlighted, change the disk's border color to black otherwise.
     * 
     * @param flag if {@code true}, highlights (Change the border color to {@code red}) the border of disk, unhighlight otherwise.
     */
    public void highlightDisk(boolean flag)
    {
        this.setBorder(new LineBorder(flag ? Color.RED : Color.BLACK, 5));
    }

    /**
     * Returns the hierarchy status of the disk.
     */
    public int getStatus()
    {
        return status;
    }
}