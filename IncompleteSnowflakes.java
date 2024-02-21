	package Software_Development;
	import javax.swing.*;
import java.awt.*;
import java.util.Random;
import javax.swing.ImageIcon;

public class Snowflakes extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int WINDOW_WIDTH = 1300;
    private static final int WINDOW_HEIGHT = 800;
    private static final int SQUARE_WIDTH = 25;
    private static final int SQUARE_HEIGHT = 25;
    private static final int NUM_SNOWFLAKES = 10; // Set the number of snowflakes

    int[] snowflakeXLocations;
    int[] snowflakeYLocations;
    Image raindropImage; // Image for raindrop

    public Snowflakes() {
        snowflakeXLocations = new int[NUM_SNOWFLAKES];
        snowflakeYLocations = new int[NUM_SNOWFLAKES];

        // Load the snowflake image
        ImageIcon imgIcon = new ImageIcon(getClass().getResource("Snowflake.png.png"));
        raindropImage = imgIcon.getImage();

        initializeSnowflakes();
    }

    public void initializeSnowflakes() {
        Random rand = new Random();
        int delay = 0; // Delay before initializing each snowflake
        for (int i = 0; i < NUM_SNOWFLAKES; i++) {
            snowflakeXLocations[i] = rand.nextInt(WINDOW_WIDTH - SQUARE_WIDTH);
            snowflakeYLocations[i] = -SQUARE_HEIGHT;
            delay += rand.nextInt(200); // Random delay between 0 and 200 milliseconds
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        for (int i = 0; i < NUM_SNOWFLAKES; i++) {
            g.drawImage(raindropImage, snowflakeXLocations[i], snowflakeYLocations[i], SQUARE_WIDTH, SQUARE_HEIGHT, this);
        }
    }

    public void update() {
        for (int i = 0; i < NUM_SNOWFLAKES; i++) {
            if (snowflakeYLocations[i] <= WINDOW_HEIGHT) {
                snowflakeYLocations[i]++;
            } else {
                snowflakeYLocations[i] = -SQUARE_HEIGHT;
            }
        }
    }

    public void start() {
        // Add any initialization code here
    }

    public static void main(String[] args) throws InterruptedException {
        Snowflakes game = new Snowflakes();
        JFrame frame = new JFrame();
        frame.add(game);

        frame.setVisible(true);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Snowfall");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        while (true) {
            game.update();
            game.repaint();
            Thread.sleep(10); // Adjust sleep time to control the falling speed
        }
    }
}
