import java.io.FileWriter;
import java.io.IOException;

public class StochasticModel {
    int m, n;
    private double[][] matrix;
    private int state;
    private double[] expectation;
    private double[] variance;

    FileWriter fw;

    public StochasticModel(int m, int n, double[][] matrix, String filename) {
        this.m = m;
        this.n = n;
        this.matrix = matrix;

        try {
            fw = new FileWriter(filename, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.expectation = new double[n];
        for (int i = 0; i <n; i++) {
            double exp = 0.;
            for (int j = 0; j < m; j++) {
                exp += matrix[j][i];
            }
            this.expectation[i] = exp / m;
        }

        this.variance = new double[n];
        for (int i = 0; i < n; i++) {
            double var = 0.;
            for (int j = 0; j < m; j++) {
                var += Math.abs(matrix[j][i] - expectation[j]);
            }
            this.variance[i] = var / m;
        }
    }

    public void next() {
        System.out.println("Текущее состояние: " + state);
        try {
            fw.write(state + "\t");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double stochastic = Math.random();
        System.out.println("Случайное число: " + stochastic);

        double p = 0.;
        for (int i = 0; i < n; i++) {
            p += matrix[state][i];
            if (stochastic < p) {
                state = i;
                System.out.println("Новое состояние: " + state);
                break;
            }
        }

        try {
            String str = String.format("%.3f\t", stochastic);
            fw.write(str + state + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getCurrentStateMark() {
        double[] mark = new double[n];
        try {
            fw.write("\n");
            for (int i = 0; i < n; i++) {
                mark[i] = expectation[i] + Math.random() * variance[i];
                String str = String.format("%.5f\t", mark[i]);
                System.out.print(str);
                fw.write(str);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void playInitialState() {
        this.state = (int) (Math.random() * this.n);
    }

    public int getState() {
        return state;
    }

    public void saveFile() {
        try {
         fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
