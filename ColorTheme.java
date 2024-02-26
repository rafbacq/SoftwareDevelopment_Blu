package Software_Development;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
public class ColorTheme implements ActionListener {
   JFrame frame;
   JPanel contentPane;
   JButton button, button1, button2, button3, button4, button5, button6, button7, backbutton;
   public ColorTheme() {
       frame = new JFrame("Color Theme");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       contentPane = new FallingObjectsPanel();
       contentPane.setLayout(new GridLayout(4, 0, 30, 30));
       contentPane.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
       contentPane.setPreferredSize(new Dimension(600, 800));
      
       ImageIcon icon= new ImageIcon(getClass().getResource("spookyImage.PNG"));
	    button = new JButton("Halloween", icon);
	    button.setHorizontalTextPosition(SwingConstants.CENTER);
		 button.setFont(new Font("Serif", Font.BOLD, 40));
		 button.setForeground(Color.BLACK);
		 button.setActionCommand("Halloween");
		 button.addActionListener(this);
		 contentPane.add(button);
		 button.setPreferredSize(new Dimension(10, 10));
		 ImageIcon icon1= new ImageIcon(getClass().getResource("heartImage.PNG"));
		 button1 = new JButton("Love",icon1);
		 button1.setHorizontalTextPosition(SwingConstants.CENTER);
		 button1.setFont(new Font("Serif", Font.BOLD, 40));
		 button1.setForeground(Color.WHITE);
		 button1.setActionCommand("Love");
		 button1.addActionListener(this);
		 button1.setPreferredSize(new Dimension(20, 20));
		 contentPane.add(button1,BorderLayout.CENTER);
		
		
		
		 /* Create and add button */
		 ImageIcon icon2 = new ImageIcon(getClass().getResource("winterImage.PNG"));
		 button2 = new JButton("Winter", icon2);
		 button2.setHorizontalTextPosition(SwingConstants.CENTER);
		 button2.setFont(new Font("Serif", Font.BOLD, 40));
		 button2.setForeground(Color.BLACK);
		 button2.setActionCommand("Winter");
		 button2.addActionListener(this);
		 contentPane.add(button2);
		
		 ImageIcon icon3 = new ImageIcon(getClass().getResource("pastelImage.PNG"));
		 button4 = new JButton("Pastel", icon3);
		 button4.setHorizontalTextPosition(SwingConstants.CENTER);
		 button4.setFont(new Font("Serif", Font.BOLD, 40));
		 button4.setActionCommand("Pastel");
		 button4.addActionListener(this);
		 contentPane.add(button4, BorderLayout.CENTER);
		
		 ImageIcon icon4 = new ImageIcon(getClass().getResource("blackImage.PNG"));
		 button3 = new JButton("Dark mode", icon4);
		 button3.setHorizontalTextPosition(SwingConstants.CENTER);
		 button3.setFont(new Font("Serif", Font.BOLD, 37));
		 button3.setForeground(Color.WHITE);
		 button3.setActionCommand("Dark mode");
		 button3.addActionListener(this);
		 contentPane.add(button3);
		 button5 = new JButton("Default");
		 button5.setFont(new Font("Serif", Font.BOLD, 40));
		 button5.setActionCommand("Default");
		 button5.addActionListener(this);
		 contentPane.add(button5);
       frame.setContentPane(contentPane);
       frame.pack();
       frame.setVisible(true);
   }
   public void actionPerformed(ActionEvent event) {
       String eventName = event.getActionCommand();
       switch (eventName) {
           case "Love":
               setTheme(new Color(240, 182, 213), new Color(255, 114, 118), FallingObjectType.HEART);
               break;
           case "Halloween":
               setTheme(Color.BLACK, new Color(251, 191, 119), FallingObjectType.SPIDER);
               break;
           case "Winter":
               setTheme(new Color(135, 206, 235), new Color(255,255,255), FallingObjectType.SNOWFLAKE);
               button.setForeground(Color.BLACK);
   	        button2.setForeground(Color.BLACK);
   	        button4.setForeground(Color.BLACK);
   	        button5.setForeground(Color.BLACK);
               break;
           case "Dark mode":
               setTheme(Color.BLACK, new Color(211, 211, 211), FallingObjectType.NONE);
               break;
           case "Pastel":
               setTheme(new Color(212, 231, 197), new Color(253, 206, 223), FallingObjectType.EXCITEMENT);
               break;
           case "Default":
               setDefaultTheme();
               break;
       }
   }
   private void setTheme(Color background, Color foreground, FallingObjectType objectType) {
       ((FallingObjectsPanel) contentPane).setFallingObjectType(objectType);
       contentPane.setBackground(background);
       for (Component component : contentPane.getComponents()) {
           if (component instanceof JButton) {
               JButton button = (JButton) component;
               button.setBackground(foreground);
               button.setForeground(Color.WHITE);
           }
       }
       ((FallingObjectsPanel) contentPane).clearFallingObjects(); // Clear falling objects
   }
   private void setDefaultTheme() {
       ((FallingObjectsPanel) contentPane).setFallingObjectType(FallingObjectType.NONE);
       contentPane.setBackground(null);
       for (Component component : contentPane.getComponents()) {
           if (component instanceof JButton) {
               JButton button = (JButton) component;
               button.setBackground(null);
               button.setForeground(Color.BLACK);
           }
       }
       button3.setForeground(Color.WHITE);
       button1.setForeground(Color.WHITE);
   }
   public static void main(String[] args) {
       SwingUtilities.invokeLater(ColorTheme::new);
   }
   enum FallingObjectType {
       HEART, SPIDER, SNOWFLAKE, EXCITEMENT, NONE
   }
   static class FallingObjectsPanel extends JPanel {
       private final ArrayList<FallingObject> fallingObjects;
       private final Timer timer;
       private FallingObjectType fallingObjectType;
       private static final int DELAY = 50; // Delay in milliseconds
       public FallingObjectsPanel() {
           fallingObjects = new ArrayList<>();
           timer = new Timer(DELAY, new FallingObjectsListener());
           timer.start();
           fallingObjectType = FallingObjectType.NONE;
       }
       public void clearFallingObjects() {
           fallingObjects.clear();
       }
       public void setFallingObjectType(FallingObjectType objectType) {
           fallingObjectType = objectType;
       }
       @Override
       protected void paintComponent(Graphics g) {
           super.paintComponent(g);
           for (FallingObject fallingObject : fallingObjects) {
               fallingObject.draw(g);
           }
       }
       private class FallingObjectsListener implements ActionListener {
           @Override
           public void actionPerformed(ActionEvent e) {
               createFallingObject();
               moveFallingObjects();
               repaint();
           }
       }
       private void createFallingObject() {
           Random random = new Random();
           switch (fallingObjectType) {
               case HEART:
                   fallingObjects.add(new Heart(random.nextInt(getWidth()), -Heart.SIZE, random.nextInt(5) + 1));
                   break;
               case SPIDER:
                   fallingObjects.add(new Spider(random.nextInt(getWidth()), -Spider.SIZE, random.nextInt(5) + 1));
                   break;
               case SNOWFLAKE:
                   fallingObjects.add(new Snowflake(random.nextInt(getWidth()), -Snowflake.SIZE, random.nextInt(5) + 1));
                   break;
               case EXCITEMENT:
                   // Add logic for excitement random.nextInt
                   break;
               case NONE:
                   break;
           }
       }
       private void moveFallingObjects() {
           for (int i = 0; i < fallingObjects.size(); i++) {
               FallingObject fallingObject = fallingObjects.get(i);
               fallingObject.move();
               if (fallingObject.getY() >= getHeight()) {
                   fallingObjects.remove(i);
               }
           }
       }
   }
   static abstract class FallingObject {
       protected int x;
       protected int y;
       protected final int speed;
       public FallingObject(int x, int y, int speed) {
           this.x = x;
           this.y = y;
           this.speed = speed;
       }
       public int getX() {
           return x;
       }
       public int getY() {
           return y;
       }
       public abstract void move();
       public abstract void draw(Graphics g);
   }
   static class Heart extends FallingObject {
       public static final int SIZE = 20;
       public Heart(int x, int y, int speed) {
           super(x, y, speed);
       }
       @Override
       public void move() {
           y += speed;
       }
       @Override
       public void draw(Graphics g) {
           g.setColor(Color.RED);
           g.fillOval(x, y, SIZE, SIZE);
       }
   }
   static class Spider extends FallingObject {
       public static final int SIZE = 30;
       public Spider(int x, int y, int speed) {
           super(x, y, speed);
       }
       @Override
       public void move() {
           y += speed;
       }
       @Override
       public void draw(Graphics g) {
           g.setColor(Color.ORANGE); // Change color to orange
           g.fillOval(x, y, SIZE, SIZE);
       }
   }
   static class Snowflake extends FallingObject {
       public static final int SIZE = 10;
       public Snowflake(int x, int y, int speed) {
           super(x, y, speed);
       }
       @Override
       public void move() {
           y += speed;
       }
       @Override
       public void draw(Graphics g) {
           g.setColor(Color.WHITE);
           g.fillOval(x, y, SIZE, SIZE);
       }
   }
}

