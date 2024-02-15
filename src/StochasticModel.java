public class StochasticModel {
    int m, n;
    private double[][] matrix;
    private int state;
    private double[] expectation;

    public StochasticModel(int m, int n, double[][] matrix) {
        this.m = m;
        this.n = n;
        this.matrix = matrix;
        this.expectation = new double[n];
        for (int i = 0; i <n; i++) {
            double exp = 0.;
            for (int j = 0; j < m; j++) {
                exp += matrix[j][i];
            }
            this.expectation[i] = exp / m;
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getStochasticNextState() {
        double stochastic = Math.random();
        double p = 0.;
        for (int i = 0; i < n; i++) {
            p += matrix[state][i];
            if (stochastic < p) return i;
        }
        return -1;
    }

    public double[] getCurrentStateMark() {
        double[] mark = new double[n];
        for (int i = 0; i < n; i++) {
            mark[i] = expectation[i];
        }

        return mark;
    }
}
