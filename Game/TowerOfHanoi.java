package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class TowerOfHanoi 
{
    /* 
    * FRAME ATTRIBUTES
    */
    protected static final int FRAME_HEIGHT = 800;
    protected static final int FRAME_WIDTH = 1300;
    protected static final Font CENTURY_GOTHIC = new Font("Century Gothic", Font.BOLD, 12);        

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TowerOfHanoi()); }

    TowerOfHanoi()
    {   
        JFrame frame = new JFrame("Tower of Hanoi");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Images\\Icon.png"));
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);        
        frame.getContentPane().setBackground(new Color(222, 226, 230));
        frame.addWindowListener(new WindowAdapter() 
        { 
            public void windowClosing(WindowEvent we) 
            { 
                close(frame); 
            } 
        });
        
        JPanel InstructionPnl = new JPanel();
        InstructionPnl.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        InstructionPnl.setLayout(new BoxLayout(InstructionPnl, BoxLayout.Y_AXIS));
        InstructionPnl.setBackground(new Color(222, 226, 230));

        JLabel titleLbl = new JLabel("Instructions");
        titleLbl.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        titleLbl.setFont(CENTURY_GOTHIC.deriveFont(42f));
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        /* 
         * INSTRUCTION CONTENT COMPONENTS
        */
        Box instructionImgBx = Box.createHorizontalBox();
        instructionImgBx.setSize(FRAME_WIDTH, 400);
        instructionImgBx.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        Box selectDiskImgBx = Box.createVerticalBox();
        selectDiskImgBx.setSize(500, instructionImgBx.getHeight());   
        selectDiskImgBx.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel selectDiskLbl = new JLabel("Select Disk");
        selectDiskLbl.setFont(CENTURY_GOTHIC.deriveFont(28f));
        selectDiskLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel selectDiskImg = new JLabel(new ImageIcon(
            new ImageIcon("Images\\SelectDiskInstruction.png").getImage()
            .getScaledInstance(580, 380, Image.SCALE_SMOOTH)));

        selectDiskImg.setBorder(new LineBorder(Color.BLACK, 2));        
        selectDiskImg.setAlignmentX(Component.CENTER_ALIGNMENT);

        Box placeDiskImgBx = Box.createVerticalBox();    
        placeDiskImgBx.setSize(500, instructionImgBx.getHeight());
        placeDiskImgBx.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel placeDiskLbl = new JLabel("Place Disk");
        placeDiskLbl.setFont(CENTURY_GOTHIC.deriveFont(28f));
        placeDiskLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel plcDskImg = new JLabel(new ImageIcon(
            new ImageIcon("Images\\PlaceDiskInstruction.png").getImage()
            .getScaledInstance(580, 380, Image.SCALE_SMOOTH)));

        plcDskImg.setBorder(new LineBorder(Color.BLACK, 2));
        plcDskImg.setAlignmentX(Component.CENTER_ALIGNMENT);

        /* 
         * RULES COMPONENTS
        */
        JPanel rulesLytPnl = new JPanel(new BorderLayout());
        rulesLytPnl.setBackground(new Color(222, 226, 230));
        
        Box rulesBx = Box.createVerticalBox();
        rulesBx.setAlignmentY(Component.RIGHT_ALIGNMENT);
        
        JLabel ruleTitle = new JLabel("Rules");
        ruleTitle.setFont(CENTURY_GOTHIC.deriveFont(32f));
        ruleTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel rule1 = new JLabel("1.  Only one disk may be moved at a time");
        rule1.setFont(CENTURY_GOTHIC.deriveFont(24f));

        JLabel rule2 = new JLabel("2.  A larger disk may never be placed on a smaller disk");
        rule2.setFont(CENTURY_GOTHIC.deriveFont(24f));

        JLabel rule3 = new JLabel("3.  Only the topmost disk from a stack may be moved");
        rule3.setFont(CENTURY_GOTHIC.deriveFont(24f));

        JButton playBtn = new JButton("Play");
        playBtn.setPreferredSize(new Dimension(150, 50));          
        playBtn.setBorder(new LineBorder(Color.BLACK, 5));
        playBtn.setBackground(Color.WHITE);
        playBtn.setFont(CENTURY_GOTHIC.deriveFont(28f));
        playBtn.setFocusPainted(false);
        playBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playBtn.addActionListener((e) -> 
        {  
            frame.dispose();
            new Game();
        });

        JPanel playBtnLytPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        playBtnLytPnl.setBackground(new Color(222, 226, 230));
        playBtnLytPnl.add(playBtn);

        JPanel playBtnPnl = new JPanel(new BorderLayout());
        playBtnPnl.setBackground(new Color(222, 226, 230));
        playBtnPnl.add(playBtnLytPnl, BorderLayout.SOUTH);

        Box instructionContBx = Box.createHorizontalBox();
        instructionContBx.setBorder(BorderFactory.createEmptyBorder(0, 40, 30, 40));
                        
        /* 
         * PLACING COMPONENTS
        */
        selectDiskImgBx.add(selectDiskLbl);
        selectDiskImgBx.add(selectDiskImg);

        placeDiskImgBx.add(placeDiskLbl);
        placeDiskImgBx.add(plcDskImg);

        instructionImgBx.add(selectDiskImgBx);
        instructionImgBx.add(Box.createRigidArea(new Dimension(20, instructionImgBx.getHeight())));
        instructionImgBx.add(placeDiskImgBx);

        rulesBx.add(ruleTitle);
        rulesBx.add(rule1);
        rulesBx.add(rule2);
        rulesBx.add(rule3);
        rulesLytPnl.add(rulesBx, BorderLayout.SOUTH);

        instructionContBx.add(rulesLytPnl);
        instructionContBx.add(playBtnPnl);

        InstructionPnl.add(titleLbl);
        InstructionPnl.add(instructionImgBx);
        InstructionPnl.add(instructionContBx);
        
        frame.add(InstructionPnl);
        frame.setVisible(true);
    }
    
    private void close(JFrame frame)
    {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Do you really want to exit the game?", 
            "Tower of Hanoi", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.OK_OPTION) frame.dispose();
        else if (confirm == JOptionPane.NO_OPTION) frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}