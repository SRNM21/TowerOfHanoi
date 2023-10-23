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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class TowerOfHanoi 
{
    protected static final int FRAME_HEIGHT = 800;
    protected static final int FRAME_WIDTH = 1300;
    protected static final Font CENTURY_GOTHIC = new Font("Century Gothic", Font.BOLD, 12);        
    private final JFrame frame = new JFrame("Tower of Hanoi");

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TowerOfHanoi()); }

    TowerOfHanoi()
    {
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Images\\Icon.png"));
        frame.getContentPane().setBackground(new Color(222, 226, 230));
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(instructionPanel());
        frame.setVisible(true);
    }

    protected JPanel instructionPanel()
    {
        JPanel InsPanel = new JPanel();
        InsPanel.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        InsPanel.setLayout(new BoxLayout(InsPanel, BoxLayout.Y_AXIS));
        InsPanel.setBackground(new Color(222, 226, 230));

        JLabel title = new JLabel("Instructions");
        title.setFont(CENTURY_GOTHIC.deriveFont(42f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        Box imgInstuctions = Box.createHorizontalBox();
        imgInstuctions.setSize(FRAME_WIDTH, 400);
        imgInstuctions.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        /* Select Disk Instruction Box */
        Box slctDskImgBx = Box.createVerticalBox();
        slctDskImgBx.setSize(500, imgInstuctions.getHeight());   
        slctDskImgBx.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel slctDiskLbl = new JLabel("Select Disk");
        slctDiskLbl.setFont(CENTURY_GOTHIC.deriveFont(28f));
        slctDiskLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon slctDskImgIco = new ImageIcon("Images\\SelectDiskInstruction.png");
        Image sltctDskImg = slctDskImgIco.getImage().getScaledInstance(580, 380, Image.SCALE_SMOOTH);

        JLabel slctDskImgIns = new JLabel(new ImageIcon(sltctDskImg));
        slctDskImgIns.setBorder(new LineBorder(Color.BLACK, 2));        
        slctDskImgIns.setAlignmentX(Component.CENTER_ALIGNMENT);
        slctDskImgBx.add(slctDiskLbl);
        slctDskImgBx.add(slctDskImgIns);

        /* Place Disk Instruction Box */
        Box plcDskImgBx = Box.createVerticalBox();    
        plcDskImgBx.setSize(500, imgInstuctions.getHeight());
        plcDskImgBx.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel plcDskLbl = new JLabel("Place Disk");
        plcDskLbl.setFont(CENTURY_GOTHIC.deriveFont(28f));
        plcDskLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon plcDskImgIco = new ImageIcon("Images\\PlaceDiskInstruction.png");
        Image plcDskImg = plcDskImgIco.getImage().getScaledInstance(580, 380, Image.SCALE_SMOOTH);

        JLabel plcDskImgIns = new JLabel(new ImageIcon(plcDskImg));
        plcDskImgIns.setBorder(new LineBorder(Color.BLACK, 2));
        plcDskImgIns.setAlignmentX(Component.CENTER_ALIGNMENT);
        plcDskImgBx.add(plcDskLbl);
        plcDskImgBx.add(plcDskImgIns);

        imgInstuctions.add(slctDskImgBx);
        imgInstuctions.add(Box.createRigidArea(new Dimension(20, imgInstuctions.getHeight())));
        imgInstuctions.add(plcDskImgBx);

        /* Rules Box */
        JPanel rulesLayout = new JPanel(new BorderLayout());
        rulesLayout.setBackground(new Color(222, 226, 230));
        
        Box rules = Box.createVerticalBox();
        rules.setAlignmentY(Component.RIGHT_ALIGNMENT);
        
        JLabel ruleTitle = new JLabel("Rules");
        ruleTitle.setFont(CENTURY_GOTHIC.deriveFont(32f));
        ruleTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel rule1 = new JLabel("1.  Only one disk may be moved at a time");
        rule1.setFont(CENTURY_GOTHIC.deriveFont(24f));

        JLabel rule2 = new JLabel("2.  A larger disk may never be placed on a smaller disk");
        rule2.setFont(CENTURY_GOTHIC.deriveFont(24f));

        JLabel rule3 = new JLabel("3.  Only the topmost disk from a stack may be moved");
        rule3.setFont(CENTURY_GOTHIC.deriveFont(24f));

        rules.add(ruleTitle);
        rules.add(rule1);
        rules.add(rule2);
        rules.add(rule3);

        JButton playBtn = new JButton("Play");
        playBtn.setPreferredSize(new Dimension(150, 50));        
        playBtn.setBackground(Color.WHITE);
        playBtn.setBorder(new LineBorder(Color.BLACK, 5));
        playBtn.setFont(CENTURY_GOTHIC.deriveFont(28f));
        playBtn.setFocusPainted(false);
        playBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playBtn.addActionListener((e) -> {  
            frame.dispose();
            new Game();
        });

        JPanel btnLayoutPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLayoutPnl.setBackground(new Color(222, 226, 230));
        btnLayoutPnl.add(playBtn);

        JPanel playBtnPnl = new JPanel(new BorderLayout());
        playBtnPnl.setBackground(new Color(222, 226, 230));
        playBtnPnl.add(btnLayoutPnl, BorderLayout.SOUTH);

        Box rulesPanel = Box.createHorizontalBox();
        rulesPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 30, 40));
        
        rulesLayout.add(rules, BorderLayout.SOUTH);

        rulesPanel.add(rulesLayout);
        rulesPanel.add(playBtnPnl);

        InsPanel.add(title);
        InsPanel.add(imgInstuctions);
        InsPanel.add(rulesPanel);

        return InsPanel;
    }
}