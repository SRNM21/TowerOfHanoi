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

public class Game implements KeyListener
{   
    /* 
    * STATUS
    */
    private int diskCount = 3;
    private Disk currentDisk = null;
    private Peg prevPeg = null;

    /* 
    * FLAGS
    */
    private boolean placeFlag = true;    
    private boolean startFlag = true;
    private boolean finished = false;    

    /* 
    * PROGRESS COMPONENTS
    */
    private int sec = 0;    
    private int min = 0;
    private int hrs = 0;
    private int moves = 0;

    private final JFrame frame = new JFrame("Tower of Hanoi");
    private final JLabel timeLbl = new JLabel("Time: 0s");
    private final JLabel movesLbl = new JLabel("Moves: " + moves);
    private final JLabel noOfDisksLbl = new JLabel("Disk: " + diskCount);
        
    /* 
    * RODS
    */
    private final Peg pegA = new Peg(diskCount);
    private final Peg pegB = new Peg(0);
    private final Peg pegC = new Peg(0);

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
        gamePnl.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

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
        JPanel RODS = new JPanel(new GridLayout(1, 3));
        RODS.setPreferredSize(new Dimension(TowerOfHanoi.FRAME_WIDTH, 400));    
        RODS.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        RODS.setBackground(new Color(222, 226, 230));

        JPanel utilityPnl = new JPanel(new GridLayout(1, 2));        
        utilityPnl.setBackground(Color.red);
        utilityPnl.setMaximumSize(new Dimension(TowerOfHanoi.FRAME_WIDTH, 200));    

        JPanel noOfDiskPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        noOfDiskPnl.setBackground(new Color(222, 226, 230));

        noOfDisksLbl.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(34f));
        
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
         * PLACING COMPONENTS
        */
        timePnl.add(timeLbl);
        movesPnl.add(movesLbl);

        progressPnl.add(timePnl);        
        progressPnl.add(movesPnl);

        RODS.add(pegA, new GridLayout());
        RODS.add(pegB, new GridLayout());
        RODS.add(pegC, new GridLayout());

        noOfDiskPnl.add(noOfDisksLbl);        
        noOfDiskPnl.add(Box.createRigidArea(new Dimension(10, noOfDiskPnl.getHeight())));
        noOfDiskPnl.add(decrementDiskBtn);
        noOfDiskPnl.add(incrementDiskBtn);

        utilityBtns.add(exitBtn);
        utilityBtns.add(resetBtn);

        utilityPnl.add(noOfDiskPnl);
        utilityPnl.add(utilityBtns);

        gamePnl.add(progressPnl);
        gamePnl.add(RODS);
        gamePnl.add(utilityPnl);
        
        frame.add(gamePnl);
        frame.setVisible(true);
    }

    private void incrementDisk()
    {
        if (diskCount < 8)
        {
            ++diskCount;
            reset();
        }

        frame.requestFocus();
    }

    private void decrementDisk()
    {                
        if (diskCount > 3)
        {        
            --diskCount;
            reset();
        }        
        
        frame.requestFocus();
    }

    private void reset()
    {
        timer.stop();
        timeLbl.setText("Time: 0s");
        movesLbl.setText("Moves: 0");
        noOfDisksLbl.setText("Disk: " + diskCount);
        frame.requestFocus();
        currentDisk = null;
        prevPeg = null;
        startFlag = true;
        placeFlag = true;
        finished = false;
        sec = 0;
        moves = 0;

        pegA.clearPeg();
        pegB.clearPeg();
        pegC.clearPeg();

        for (int i = 1; i <= diskCount; i++) pegA.addDisk(i);
    }

    private void getTopDisk(Peg peg)
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

    private void placeDisk(Peg peg)
    {
        currentDisk.highlightDisk(false);
        placeFlag = true;

        /*
         * Prevents:
         *      place disk on the same peg 
         *      place disk on the top of the smaller disk
         */
        if ((peg == prevPeg) || (peg.getDiskCount() > 0 && (currentDisk.getStatus() > peg.peek().getStatus()))) return;

        prevPeg.getTopDisk();
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
                case 'a' -> { getTopDisk(pegA); }
                case 's' -> { getTopDisk(pegB); }
                case 'd' -> { getTopDisk(pegC); }
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
class Peg extends JPanel
{
    /*
    * ROD ATTRIBUTES
    */
    private final int PEG_WIDTH = 20;
    private final int PEG_HEIGHT = 500;

    private Stack<Integer> stack = new Stack<Integer>();
    private Stack<Integer> reference = new Stack<Integer>();

    /*
    * CONTAINER
    */    
    private final Box content = Box.createVerticalBox();

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);

        // Paint rod
        g.setColor(Color.BLACK);
        g.fillRect((this.getWidth() - PEG_WIDTH) / 2, 80, PEG_WIDTH, PEG_HEIGHT);
    }

    public Peg(int disks)
    {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(222, 226, 230));
        add(content, BorderLayout.SOUTH);

        for (int i = 1; i <= disks; i++) this.addDisk(i);
    }

    public void addDisk(int stat)
    {
        reference.add(0, stat);
        stack.add(0, stat);
        content.add(new Disk(stat));
        update();
    }

    public void placeDisk(Disk disk)
    {
        stack.add(disk.getStatus());
        content.add(disk, 0);
        update();
    }
        
    public void clearPeg()
    {
        reference.clear();
        stack.clear();
        content.removeAll();
        update();
    }

    public Disk peek()
    {
        return (Disk) content.getComponent(0);
    }
        
    public void getTopDisk()
    {
        stack.pop();
        content.remove(0);
        update();
    }

    public int getDiskCount()
    {
        return content.getComponentCount();
    }

    private void update()
    {
        content.repaint();
        content.revalidate();
    }

    public Stack<Integer> getStack()
    {
        return stack;
    }

    public Stack<Integer> getReference()
    {
        return reference;
    }
}

/*
 * GAME DISK
 */
class Disk extends JPanel
{
    private int status = 0;

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

    public void highlightDisk(boolean flag)
    {
        this.setBorder(new LineBorder(flag ? Color.RED : Color.BLACK, 5));
    }

    public int getStatus()
    {
        return status;
    }
}