import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class Game extends JFrame {
    JPanel game;
    JPanel memory;
    int memoryLength = 0;
    JPanel board[][];
    JButton smallBoards[][][][];
    Player playerOne;
    Player playerTwo;
    int tura = 0;
    int first = 0;
    int second = 0;
    int bigPoints[][];
    boolean p1 =true;

    public Game() {
        super("Game");
        this.setSize(1080, 900);
        this.setBackground(new Color(119, 118, 179));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);

        //tablice
        this.board = new JPanel[4][4];
        this.smallBoards = new JButton[4][4][3][3];
        this.bigPoints = new int[4][4];

        this.players();
        this.gameBoard();
        setVisible(true);
    }

    public void gameBoard() {
        game = new JPanel();
        game.setBackground(new Color(119, 118, 179));
        game.setLayout(null);
        game.setBounds(0,0, 780, 900);

        memory = new JPanel();
        memory.setBackground(new Color(119, 118, 179));
        memory.setLayout(new BoxLayout(memory, BoxLayout.Y_AXIS));
        memory.setBounds(780,0, 300,900);


        this.add(game, BorderLayout.CENTER);
        this.add(memory, BorderLayout.EAST);

        //podstawa lokalizacji
        JPanel letters[] = new JPanel[9];
        JPanel numbers[] = new JPanel[9];

        for (int i = 0; i <board.length; i++) {
            for (int j = 0; j < board.length; j++) {

                //rysowanie planszy
                board[i][j] = new JPanel();
                board[i][j].setBackground(Color.pink);
                board[i][j].setBounds(10+190*i,10+190*j,180,180);
                board[i][j].setLayout(null);
                game.add(board[i][j]);

                if ( i != 0 && j != 0 ) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {

                        //tworzenie guzikow
                        smallBoards[i][j][k][l] = new JButton();
                       smallBoards[i][j][k][l].setBackground(new Color(249, 251, 231));
                       smallBoards[i][j][k][l].setBounds(61 * k, 61 * l, 58, 58);


                  final int i2 = i;
                  final int j2 = j;
                  final int k2 = k;
                  final int l2 = l;

                  //ustalanie lokalizji
                        int kF = (i-1)*3+k;
                        int lF = (j-1)*3+l;
                  JLabel loc = new JLabel();
                        loc.setFont(new Font("Monospaced", Font.ITALIC, 30));
                        loc.setText("Lokalizacja :" + Integer.toString(kF) + (char) (lF + 'a'));


                        board[i][j].add(smallBoards[i][j][k][l]);

                        smallBoards[i][j][k][l].addActionListener((e) -> {

                            if(e.getSource() instanceof JButton) {
                                if ((i2 == first && j2 == second) || bigPoints[first][second] != 0 || tura ==0 || allIn(first,second)) {

                                    //przewijanie lokalizacji
                                    if (memoryLength <640 ) {
                                        memory.add(loc);
                                    } else {
                                        memory.remove(0);
                                        memory.add(loc);

                                    }
                                    memoryLength +=35;
                                    tura++;

                                    // ustawianie guzika na klikniete + pkt gracza + lokalizacja
                                    if (p1) {
                                        this.pointP1((JButton) e.getSource());
                                        this.playerOne.setPoint(i2, j2, k2, l2);
                                        loc.setForeground(Color.white);
                                    } else {
                                        this.pointP2((JButton) e.getSource());
                                        this.playerTwo.setPoint(i2, j2, k2, l2);
                                        loc.setForeground(new Color(232, 197, 229));
                                    }

                                    for (int kk = 0; kk < 3; kk++) {
                                        for (int ll = 0; ll < 3; ll++) {
                                            smallBoards[i2][j2][kk][ll].setBackground(new Color(249, 251, 231));

                                        }
                                    }

                                    //duze punkty i zaznaczenie pola do klikania
                                    if (this.pointchecker(playerOne, i2, j2)) {
                                       this.drawP1( i2, j2);
                                    } else if (bigPoints[k2+1][l2+1] ==0 && !allIn(k2+1,l2+1)) {
                                        for (int kk = 0; kk < 3; kk++) {
                                            for (int ll = 0; ll < 3; ll++) {

                                                smallBoards[k2+1][l2+1][kk][ll].setBackground(Color.ORANGE);

                                            }
                                        }
                                    }
                                    if (this.pointchecker(playerTwo, i2, j2)) {
                                       this.drawP2(i2,j2);

                                    } else if (bigPoints[k2+1][l2+1] ==0 && !allIn(k2+1,l2+1)){
                                        for (int kk = 0; kk < 3; kk++) {
                                            for (int ll = 0; ll < 3; ll++) {
                                                    smallBoards[k2+1][l2+1][kk][ll].setBackground(Color.ORANGE);

                                                }
                                            }
                                    }
                                    first = k2+1;
                                    second = l2+1;
                                }
                            }

                            //sprawdza czy to nie koniec gry
                           this.winner(1);
                            this.winner(2);
                            if(wholeBoard()) {
                               EndGame endSad = new EndGame(0);
                            }
                            //zapis
                            try {
                                this.saving();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });


                    };
                }
                }
            }
        }

        int j = 1;
        int k = 0;

        //litery do lokalizacji
        for (int i = 0; i< letters.length; i++) {
            letters[i] = new JPanel();
            letters[i].setBackground(new Color(249, 251, 231));

            numbers[i] = new JPanel();
            numbers[i].setBackground(new Color(249, 251, 231));

            letters[i].setBounds(0, 60*k, 180, 58);
            numbers[i].setBounds(60*k, 0, 58, 180);

            board[0][j].add(letters[i]);
            board[j][0].add(numbers[i]);
            k++;

            //numery do lokalizacji
            JLabel l = new JLabel(" " +Integer.toString(i));
            l.setFont(new Font("Monospaced", Font.BOLD, 40));
            numbers[i].setLayout(new BorderLayout());
            numbers[i].add(l, BorderLayout.CENTER);

            //adding letters
            JLabel l2 = new JLabel("     " + (char)(i+'a'));
            l2.setFont(new Font("Monospaced", Font.BOLD, 40));
            letters[i].setLayout(new BorderLayout());
            letters[i].add(l2, BorderLayout.CENTER);

            if (i %3==2) {
                j++;
                k=0;
            }
        }

    }

    //male ikonki male pkt
    private void pointP1(JButton e) {
        ImageIcon p1i = new ImageIcon(new ImageIcon("src/media/player1.png").getImage().getScaledInstance(58, 58, Image.SCALE_DEFAULT));
        e.setIcon(p1i);
        p1 = false;
        for (ActionListener e1 :  e.getActionListeners()) {
            e.removeActionListener(e1);

        }
    }
    private void pointP2(JButton e) {
        ImageIcon p2i = new ImageIcon(new ImageIcon("src/media/player2.png").getImage().getScaledInstance(58, 58, Image.SCALE_DEFAULT));
         e.setIcon(p2i);
         p1 = true;
        for (ActionListener e1 :  e.getActionListeners()) {
            e.removeActionListener(e1);

        }

    }
    //duze ikonki dla duzych pkt
    private void drawP1(int i2, int j2) {
        ImageIcon point1 = new ImageIcon(new ImageIcon("C:/Users/zofia/IdeaProjects/Project/src/media/player1.png").getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT));
        bigPoints[i2][j2] = 1;
        board[i2][j2].removeAll();
        JPanel top = new JPanel();
        top.setBounds(0,0, 180,180);
        top.add(new JLabel(point1));
        board[i2][j2].add(top);
    }

    private void drawP2(int i2, int j2) {
        bigPoints[i2][j2] = 2;
        ImageIcon point2 = new ImageIcon(new ImageIcon("C:/Users/zofia/IdeaProjects/Project/src/media/player2.png").getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT));
        board[i2][j2].removeAll();
        JPanel top = new JPanel();
        top.setBounds(0,0, 180,180);
        top.add(new JLabel(point2));
        board[i2][j2].add(top);
    }

    //tworzy graczy
    private void players() {
        this.playerOne = new Player();
        this.playerTwo = new Player();
    }

    //sprawdza duze punkty
    public boolean pointchecker (Player p, int i, int j) {
                for (int k = 0 ; k< 3; k++) {
                    if ((p.isPoint(i,j,k,0) && p.isPoint(i,j,k,1) && p.isPoint(i,j,k,2) ) ||
                            (p.isPoint(i,j,0,k) && p.isPoint(i,j,1,k) && p.isPoint(i,j,2,k)) ||
                            (p.isPoint(i,j, 0,0) && (p.isPoint(i,j,1,1)) && p.isPoint(i,j,2,2)) ||
                            (p.isPoint(i,j,0,2) && p.isPoint(i,j,1,1) && p.isPoint(i,j,2,0)))
                            {
                                first = i;
                                second = j;
                              return true;
        }
    }
        return false;

}

//sprawdza czy jest wygrany
public boolean winner (int i) {
    for (int k = 1 ; k< 4; k++) {
        if (( bigPoints[k][1]==i && bigPoints[k][2]==i  && bigPoints[k][3]==i  ) ||
                ( bigPoints[1][k]==i && bigPoints[2][k]==i  && bigPoints[3][k]==i  )  ||
                ( bigPoints[3][3]==i && bigPoints[1][1]==i  && bigPoints[2][2]==i  )  ||
                ( bigPoints[3][1]==i && bigPoints[2][2]==i  && bigPoints[1][3]==i  ) )
        {

            EndGame end = new EndGame(i);
            return true;
        }
    }
    return false;

}

//w razie zapelnienia malej planszy bez wygranej
public boolean allIn(int i, int j) {
        for (int k=0;k<3;k++) {
            for (int l=0;l<3;l++) {
                if (!playerOne.isPoint(i,j,k,l) && !playerTwo.isPoint(i,j,k,l)) {
                    return false;
                }
            }
        }
        return true;
}

//sprawdzanie czy nie wystapil remis
public boolean wholeBoard () {
    for (int i=1;i<4;i++) {
        for (int j=1;j<4;j++) {
           if( bigPoints[i][j] ==0 && !allIn(i,j) ) {
               return false;
           }
            }
        }
    return true;
}

public void saving () throws IOException {

    FileOutputStream save = new FileOutputStream("C:/Users/zofia/IdeaProjects/Project/src/media/save");
  //zapis gracza
   save.write(p1?1:0);

   //zapis duzych pkt
   for(int i=1;i<4;i++){
       for (int j=1;j<4;j++) {
           save.write(i);
           save.write(j);
           save.write(bigPoints[i][j]);
       }
    }

   save.write('a');

   //zapis malych pkt
    for(int i=1;i<4;i++){
        for (int j=1;j<4;j++) {
            if ( bigPoints[i][j] == 0 ) {
                save.write(i);
                save.write(j);
                for(int k=0;k<3;k++){
                        for (int l=0;l<3;l++) {
                            if (playerOne.isPoint(i,j,k,l) ) {
                                save.write(1);
                            } else if (playerTwo.isPoint(i,j,k,l)) {
                                save.write(2);
                            } else  {
                                save.write(0);
                            }
                        }
                    }
             }
         }
    }

    save.write('b');

    save.write(tura);

    save.write(first);

    save.write(second);


save.close();
}

public void reading() throws IOException {
        FileReader save = new FileReader("C:/Users/zofia/IdeaProjects/Project/src/media/save");

        //wybor gracz
        int LastPlayer=save.read();
        if (LastPlayer != 1) {
            p1 = true;
        } else {
            p1=false;
        }
        int readerI = 0;
        int readerJ = 0;
        int readerPoint = 0;

        //wczytywanie duzych pkt
       while ((readerI = save.read()) != 'a' ) {
          readerJ = save.read();
          readerPoint = save.read();
           bigPoints[readerI][readerJ] = readerPoint;

           if ( readerPoint == 1) {
               this.drawP1(readerI, readerJ);
           } else if ( readerPoint == 2) {
               this.drawP2(readerI, readerJ);
           }
       }


//wczytywanie malych pkt
       while ((readerI = save.read() )!= 'b') {
           readerJ = save.read();

           for ( int k=0; k<3;k++) {
               for (int l = 0; l<3;l++) {
                   readerPoint = save.read();

                       if (readerPoint == 1) {

                           this.pointP1(smallBoards[readerI][readerJ][k][l]);
                           this.playerOne.setPoint(readerI, readerJ, k, l);
                       }

                       if (readerPoint == 2) {

                           this.pointP2(smallBoards[readerI][readerJ][k][l]);
                           this.playerTwo.setPoint(readerI, readerJ, k, l);
                       }
                   }
               }
           }

       this.tura = save.read();
       this.first = save.read();
       this.second = save.read();

       //ustawia pomarancz
    for (int k = 0; k < 3; k++) {
        for (int l = 0; l < 3; l++) {
            smallBoards[first][second][k][l].setBackground(Color.ORANGE);

        }
    }

       }
}


