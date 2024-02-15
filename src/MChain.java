import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MChain {
    // m - число строк, n - столбцов.
    int m, n;
    private double[][] matrix;
    private double[] current;

    FileWriter fw;

    public MChain(int m, int n, double[][] matrix, String filename) {
        this.matrix = matrix;
        this.n = n;
        this.m = m;
        this.current = new double[n];
        try {
            fw = new FileWriter(filename, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private double[] multiplyWithMatrix(double[] vector) {
        double[] result = new double[n];

        for (int i = 0; i < m; i++) {
            double s = 0.;
            for (int j = 0; j < n; j++) {
                s += matrix[i][j] * vector[j];
            }
            result[i] = s;
        }

        return result;
    }

    public void model(double eps, int maxIter) {
        double diff;
        int iter = 0;
        try {
            do {
                double[] next = multiplyWithMatrix(current);
                diff = 0.;
                for (int i = 0; i < n; i++) {
                    diff += Math.abs(next[i] - current[i]);
                }
                current = next;
                for (Double item : next) {
                    System.out.format("%.5f\t",item);
                    String str = String.format("%.5f\t",item);
                    fw.write(str);
                }
                System.out.println();
                fw.write("\n");
                iter++;
            } while (diff > eps && iter < maxIter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void playInitialState() {
        int state = (int) (Math.random() * this.n);
        for (int i = 0; i < n; i++) {
            if (i == state) {
                this.current[i] = 1;
            } else {
                this.current[i] = 0;
            }
        }
    }

    public void printState() {
        try {
            for (int i = 0; i < n; i++) {
                System.out.format("%.5f\t", this.current[i]);
                String str = String.format("%.5f\t",this.current[i]);
                fw.write(str);
            }
            fw.write("\n");
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveFile() {
        try {
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
