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
    private int currentDisks = 3;

    private Disk heldDisk = null;
    private Rod prevRod = null;

    private boolean placeFlag = true;    
    private boolean startFlag = true;
    private boolean finished = false;    

    private int sec = 0;    
    private int min = 0;
    private int hrs = 0;
    private int moves = 0;

    private final JFrame frame = new JFrame("Tower of Hanoi");
    private final JLabel timeLbl = new JLabel("Time: 0s");
    private final JLabel movesLbl = new JLabel("Moves: " + moves);
    private final JLabel disks = new JLabel("Disk: " + currentDisks);

    private final Rod rod1 = new Rod(currentDisks);
    private final Rod rod2 = new Rod(0);
    private final Rod rod3 = new Rod(0);

    Game()
    {   
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Images\\Icon.png"));
        frame.getContentPane().setBackground(new Color(222, 226, 230));
        frame.setSize(TowerOfHanoi.FRAME_WIDTH, TowerOfHanoi.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(gamePanel());
        frame.setVisible(true);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.requestFocus();
    }

    private JPanel gamePanel()
    {
        JPanel gamePnl = new JPanel();
        gamePnl.setSize(TowerOfHanoi.FRAME_WIDTH, TowerOfHanoi.FRAME_HEIGHT);
        gamePnl.setLayout(new BoxLayout(gamePnl, BoxLayout.Y_AXIS));
        gamePnl.setBackground(new Color(222, 226, 230));
        gamePnl.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        /* Progress Info */
        JPanel progressBx = new JPanel(new GridLayout(1, 2));      
        progressBx.setMaximumSize(new Dimension(TowerOfHanoi.FRAME_WIDTH, 100));    
        progressBx.setBackground(new Color(222, 226, 230));

        JPanel timePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePnl.setBackground(new Color(222, 226, 230));

        timeLbl.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(34f));
        timePnl.add(timeLbl);

        JPanel movesPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        movesPnl.setBackground(new Color(222, 226, 230));
        
        movesLbl.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(34f));
        movesPnl.add(movesLbl);

        progressBx.add(timePnl);        
        progressBx.add(movesPnl);
        
        JPanel rods = new JPanel(new GridLayout(1, 3));
        rods.setPreferredSize(new Dimension(TowerOfHanoi.FRAME_WIDTH, 400));    
        rods.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        rods.setBackground(new Color(222, 226, 230));

        rods.add(rod1, new GridLayout());
        rods.add(rod2, new GridLayout());
        rods.add(rod3, new GridLayout());

        /* Utilities */
        JPanel utils = new JPanel(new GridLayout(1, 2));        
        utils.setBackground(Color.red);
        utils.setMaximumSize(new Dimension(TowerOfHanoi.FRAME_WIDTH, 200));    

        JPanel disksMan = new JPanel(new FlowLayout(FlowLayout.LEFT));
        disksMan.setBackground(new Color(222, 226, 230));

        disks.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(34f));
        
        JButton diskRemBtn = new JButton("-");
        diskRemBtn.setPreferredSize(new Dimension(50, 50));        
        diskRemBtn.setBackground(Color.WHITE);
        diskRemBtn.setBorder(new LineBorder(Color.BLACK, 5));
        diskRemBtn.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(28f));
        diskRemBtn.setFocusPainted(false);
        diskRemBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        diskRemBtn.addActionListener((e) -> { decrementDisk(); });
        
        JButton diskAddBtn = new JButton("+");
        diskAddBtn.setPreferredSize(new Dimension(50, 50));        
        diskAddBtn.setBackground(Color.WHITE);
        diskAddBtn.setBorder(new LineBorder(Color.BLACK, 5));
        diskAddBtn.setFont(TowerOfHanoi.CENTURY_GOTHIC.deriveFont(28f));
        diskAddBtn.setFocusPainted(false);
        diskAddBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        diskAddBtn.addActionListener((e) -> { incrementDisk(); });
        
        disksMan.add(disks);        
        disksMan.add(Box.createRigidArea(new Dimension(10, disksMan.getHeight())));
        disksMan.add(diskRemBtn);
        disksMan.add(diskAddBtn);

        JPanel utilBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        utilBtns.setPreferredSize(new Dimension(TowerOfHanoi.FRAME_WIDTH / 2, 50));  
        utilBtns.setBackground(new Color(222, 226, 230));

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
        
        utilBtns.add(exitBtn);
        utilBtns.add(resetBtn);

        utils.add(disksMan);
        utils.add(utilBtns);

        gamePnl.add(progressBx);
        gamePnl.add(rods);
        gamePnl.add(utils);

        return gamePnl;
    }

    private void incrementDisk()
    {
        if (currentDisks < 8)
        {
            ++currentDisks;
            reset();
        }

        frame.requestFocus();
    }

    private void decrementDisk()
    {                
        if (currentDisks > 3)
        {        
            --currentDisks;
            reset();
        }        
        
        frame.requestFocus();
    }

    private void reset()
    {
        timer.stop();
        timeLbl.setText("Time: 0s");
        movesLbl.setText("Moves: 0");
        disks.setText("Disk: " + currentDisks);
        frame.requestFocus();
        heldDisk = null;
        prevRod = null;
        startFlag = true;
        placeFlag = true;
        finished = false;
        sec = 0;
        moves = 0;

        rod1.clearRod();
        rod2.clearRod();
        rod3.clearRod();

        for (int i = 1; i <= currentDisks; i++) rod1.addDisk(i);
    }

    private void getTopDisk(Rod rod)
    {
        System.out.println("Get Disk");

        if (rod.getDiskCount() > 0)
        {
            Disk currDisk = (Disk) rod.peekTopDisk();
            currDisk.highlightDisk();

            heldDisk = currDisk;
            prevRod = rod;
            placeFlag = false;
        }
    }

    private void placeDisk(Rod rod)
    {
        System.out.println("Place Disk");

        if (heldDisk == null) return;
        
        heldDisk.unhighlightDisk();
        placeFlag = true;

        if (rod.getDiskCount() > 0 && (heldDisk.getStatus() > rod.peekTopDisk().getStatus())) return;
        if (rod == prevRod) return;

        heldDisk.unhighlightDisk();
        prevRod.getTopDisk();
        rod.addDisk(heldDisk);
        movesLbl.setText("Moves: " + ++moves);

        heldDisk = null;
        prevRod = null;

        if (startFlag) 
        {
            timer.start();
            startFlag = false;
        }

        if (isFinished())
        {
            timer.stop();
            finished = true;
            JOptionPane.showMessageDialog(null, "You successfully completed the tower! \nPress reset to play again", "Tower of Hanoi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean isFinished()
    {
        if (rod1.getRef().size() != rod3.getStack().size()) return false;
        return rod1.getRef().toString().equals(rod3.getStack().toString());
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        if (placeFlag && !finished)
        {
            switch (e.getKeyChar())
            {
                case 'a' -> { getTopDisk(rod1); }
                case 's' -> { getTopDisk(rod2); }
                case 'd' -> { getTopDisk(rod3); }
            }
        }
        else
        {
            switch (e.getKeyChar())
            {
                case 'a' -> { placeDisk(rod1); }
                case 's' -> { placeDisk(rod2); }
                case 'd' -> { placeDisk(rod3); }
            }
        }

        System.out.println("Rod1 : " + rod1.getDiskCount() + " - Stack : " + rod1.getStack().toString());
        System.out.println("Rod2 : " + rod2.getDiskCount() + " - Stack : " + rod2.getStack().toString());
        System.out.println("Rod3 : " + rod3.getDiskCount() + " - Stack : " + rod3.getStack().toString());
        System.out.println("REF(Rod1) : " + rod1.getRef().toString() + " - Rod3 : " + rod3.getStack().toString());
        System.out.println("Finished : " + finished);
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
        int confirm = JOptionPane.showConfirmDialog(null, "Do you really want to exit the game?", "Tower of Hanoi", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.OK_OPTION) frame.dispose();
    }
}

class Rod extends JPanel
{
    private final int ROD_WIDTH = 20;
    private final int ROD_HEIGHT = 500;
    private final Box box = Box.createVerticalBox();

    private Stack<Integer> stack = new Stack<Integer>();
    private Stack<Integer> ref = new Stack<Integer>();;

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect((this.getWidth() / 2) - (ROD_WIDTH / 2), 80, ROD_WIDTH, ROD_HEIGHT);
    }

    public Rod(int disks)
    {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(222, 226, 230));
        add(box, BorderLayout.SOUTH);

        for (int i = 1; i <= disks; i++) this.addDisk(i);
    }

    public void addDisk(int stat)
    {
        ref.add(0, stat);
        stack.add(0, stat);
        box.add(new Disk(stat));
        box.repaint();
        box.revalidate();
    }

    public void addDisk(Disk disk)
    {
        stack.add(disk.getStatus());
        box.add(disk, 0);
        box.repaint();
        box.revalidate();
    }
        
    public void clearRod()
    {
        ref.clear();
        stack.clear();
        box.removeAll();
        box.repaint();
        box.revalidate();
    }

    public Disk peekTopDisk()
    {
        return (Disk) box.getComponent(0);
    }
        
    public void getTopDisk()
    {
        stack.pop();
        box.remove(0);
        box.repaint();
        box.revalidate();
    }

    public int getDiskCount()
    {
        return box.getComponentCount();
    }

    public Stack<Integer> getStack()
    {
        return stack;
    }

    public Stack<Integer> getRef()
    {
        return ref;
    }
}

class Disk extends JPanel
{
    private int status = 0;

    public Disk(int stat) 
    {
        this.status = stat;
        this.setBorder(new LineBorder(Color.BLACK, 5));
        this.setBackground(Color.BLUE);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        /* Default size */
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

    public void highlightDisk()
    {
        this.setBorder(new LineBorder(Color.RED, 5));
    }

    public void unhighlightDisk()
    {
        this.setBorder(new LineBorder(Color.BLACK, 5));
    }

    public int getStatus()
    {
        return status;
    }
}