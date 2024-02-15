import java.util.ArrayList;
import java.util.List;

public class MChain {
    // m - число строк, n - столбцов.
    int m, n;
    private double[][] matrix;
    private List<Double> current;

    public MChain(double[][] matrix, int m, int n) {
        this.matrix = matrix;
        this.n = n;
        this.m = m;
    }

    private List<Double> multiplyWithMatrix(List<Double> vector) {
        List<Double> result = new ArrayList<>(n);

        for (int i = 0; i < m; i++) {
            double s = 0.;
            for (int j = 0; j < n; j++) {
                s += matrix[i][j] * vector.get(j);
            }
            result.set(i, s);
        }

        return result;
    }

    private List<List<Double>> model(double eps) {
        List<List<Double>> calculated = new ArrayList<>();

        double diff;
        do {
            List<Double> next = multiplyWithMatrix(current);
            diff = 0.;
            for (int i = 0; i < n; i++) {
                diff += Math.abs(next.get(i) - current.get(i));
            }
            current = next;
            for (Double item : next) {
                System.out.println(item);
            }

            calculated.add(next);
        } while (diff > eps);

        return calculated;
    }
}
