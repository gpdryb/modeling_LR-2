import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StochasticModel {
    private final int n;
    private final double[][] matrix;
    private int state;
    private final List<Integer> states = new ArrayList<>();

    private List<Double> calculatedStates = new ArrayList<>();
    private int step = 0;
    private double diff;

    public StochasticModel(int n, double[][] matrix) {
        this.n = n;
        this.matrix = matrix;

        for (int i = 0; i < n; i++) {
            states.add(0);
            calculatedStates.add(0.);
        }

    }

    public void doStep() {
        double stochastic = Math.random();
        double p = 0.;
        for (int i = 0; i < n; i++) {
            p += matrix[state][i];
            if (stochastic < p) {
                state = i;
                break;
            }
        }

        states.set(state, states.get(state) + 1);
        this.step += 1;
    }

    public List<Double> getCurrentStochasticMark() {
        List<Double> mark = new ArrayList<>(n);
        for (Integer s : states) {
            mark.add((double)s / step);
        }
        return mark;
    }

    public void playInitialState() {
        this.state = (int) (Math.random() * this.n);
        this.states.set(state, 1);
        this.calculatedStates.set(state, 1.);
        this.step++;
    }

    private List<Double> multiplyWithMatrix(List<Double> vector) {
        List<Double> result = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            double s = 0.;
            for (int j = 0; j < n; j++) {
                s += matrix[i][j] * vector.get(j);
            }
            result.add(s);
        }

        return result;
    }

    public void calcKolmogorovChapman() {
        List<Double> next = multiplyWithMatrix(calculatedStates);
        diff = 0.;
        for (int i = 0; i < n; i++) {
            diff += Math.abs(next.get(i) - calculatedStates.get(i));
        }
        calculatedStates = next;
    }

    public int getState() {
        return state;
    }

    public List<Double> getCalculatedStates() {
        return calculatedStates;
    }

    public double getDiff() {
        return diff;
    }
}
