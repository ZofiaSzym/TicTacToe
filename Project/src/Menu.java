import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Menu extends JFrame {

    private JPanel options;

    public Menu() {
        super("Game");
        this.setSize(1080, 960);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.makeOptions();
        setVisible(true);
    }

    public void makeOptions() {
        options = new JPanel();
        options.setBackground(new Color(245, 218, 210));
        options.setLayout(null);
        this.add(options, BorderLayout.CENTER);
        JButton start = new JButton();
        JButton load = new JButton();

        start.setText("START");
        load.setText("LOAD");

        start.setBounds(240, 100, 600,200);
        load.setBounds(240, 400, 600,200);

        start.setFont(new Font("Monospaced", Font.ITALIC, 50));
        load.setFont(new Font("Monospaced", Font.ITALIC, 50));

        start.setBackground(new Color(186, 205, 146));
        load.setBackground(new Color(252, 255, 224));

        options.add(start);
        options.add(load);

        start.addActionListener((e) -> {
            Game game = new Game();
        });

        load.addActionListener((e) -> {
            Game game = new Game();
            try {
                game.reading();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


    }

}
