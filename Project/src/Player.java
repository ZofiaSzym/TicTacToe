public class Player {
    int[][][][] Points;

    public Player() {
        Points = new int[4][4][3][3];
    }

    public void setPoint(int i, int j, int k, int l) {
        Points[i][j][k][l] = 1;
    }

    public boolean isPoint(int i, int j, int k, int l) {
        return Points[i][j][k][l] == 1;
    }
}
