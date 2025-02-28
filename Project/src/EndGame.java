import javax.swing.*;
import java.awt.*;

public class EndGame extends JFrame {
    
 
        private JPanel screen;

        public EndGame(int i) {
            super("Game");
            this.setSize(1080, 960);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            this.print(i);
            setVisible(true);
        }

        public void print(int i) {
            screen = new JPanel();
            screen.setBackground(new Color(119, 118, 179));
            screen.setLayout(null);
            this.add(screen, BorderLayout.CENTER);
            JLabel win = new JLabel();
            JLabel who = new JLabel();

            win.setText("END OF THE GAME !");

            //kto wygral
            if ( i == 1) {
                win.setForeground(Color.white);
                who.setForeground(Color.white);
                who.setText("congrats bunny player");
            } else if (i== 2 ){
                who.setText("congrats deer player");
                win.setForeground(new Color(232, 197, 229));
                who.setForeground(new Color(232, 197, 229));
            } else {
                who.setText("no one won, sorry :(");
            }

            win.setBounds(270, 300, 700,100);
            who.setBounds(200, 400, 700,100);

            win.setFont(new Font("Monospaced", Font.ITALIC, 50));
            who.setFont(new Font("Monospaced", Font.ITALIC, 50));
            

            screen.add(win);
            screen.add(who);

            //obrazki na ekranie koncowym
            ImageIcon bunny = new ImageIcon(new ImageIcon("C:/Users/zofia/IdeaProjects/Project/src/media/player1.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
            JPanel bunBottom = new JPanel();
            JPanel bunTop = new JPanel();
            bunBottom.setOpaque(false);
            bunTop.setOpaque(false);
            bunBottom.setBounds(700,600, 300,300);
            bunTop.setBounds(30,0, 300,300);
            bunBottom.add(new JLabel(bunny));
            bunTop.add(new JLabel(bunny));
            screen.add(bunBottom);
            screen.add(bunTop);

            ImageIcon deery = new ImageIcon(new ImageIcon("C:/Users/zofia/IdeaProjects/Project/src/media/player2.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
            JPanel deerBottom = new JPanel();
            JPanel deerTop = new JPanel();
            deerBottom.setOpaque(false);
            deerTop.setOpaque(false);
            deerBottom.setBounds(30,600, 300,300);
            deerTop.setBounds(750,0, 300,300);
            deerBottom.add(new JLabel(deery));
            deerTop.add(new JLabel(deery));
            screen.add(deerBottom);
            screen.add(deerTop);




    }

}
