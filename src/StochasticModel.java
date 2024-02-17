import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StochasticModel {
    int m, n, currentM;
    private double[][] matrix;
    private List<List<Double>> currentMatrix;
    private int state;
    FileWriter fw;
    List<Double> expectation;
    List<Double> variance;

    public StochasticModel(int m, int n, double[][] matrix, String filename) {
        this.m = m;
        this.currentM = m;
        this.n = n;
        this.matrix = matrix;
        this.currentMatrix = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(matrix[i][j]);
            }
            this.currentMatrix.add(row);
        }

        try {
            fw = new FileWriter(filename, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    private void calcExpectation() {
        List<Double> tmp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double exp = 0.;
            for (int j = 0; j < this.currentMatrix.size(); j++) {
                exp += this.currentMatrix.get(j).get(i);
            }
            tmp.add(exp / this.currentMatrix.size());
        }

        this.expectation = tmp;
    }

    private void calcVariance() {
        List<Double> tmp = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            double var = 0.;
            for (int j = 0; j < m; j++) {
                var += Math.abs(matrix[j][i] - this.expectation.get(j)) / 2;
            }
            tmp.add(var / this.currentMatrix.size());
        }

        this.variance = tmp;
    }

    public void getCurrentStateMark() {
        List<Double> row = new ArrayList<>();
        this.calcExpectation();
        this.calcVariance();

        try {
            fw.write("\n");
            for (int i = 0; i < n; i++) {
                double next = expectation.get(i) + Math.random() * variance.get(i);
                row.add(next);
                String str = String.format("%.5f\t", next);
                System.out.print(str);
                fw.write(str);
            }

            this.currentMatrix.add(row);

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
