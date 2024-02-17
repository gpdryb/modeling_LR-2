import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    private static double[][] matrix = new double[][] {{0.5, 0.25, 0.25}, {0.5, 0., 0.5}, {0.67, 0.33, 0.}};

    public static void main(String[] args) {
        FileWriter stochasticFw = null;
        try {
            stochasticFw = new FileWriter("stochastic.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StochasticModel stochasticModel = new StochasticModel(3, matrix);
        stochasticModel.playInitialState();
        try {
            stochasticFw.write("Начальное состояние: " + stochasticModel.getState() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            stochasticFw.write("Моделирование:\n");
            int counter = 0;
            do {
                stochasticFw.write(counter + 1 + "\t\t");
                Integer state = stochasticModel.getState();
                stochasticFw.write(state + "\t\t");
                List<Double> mark = stochasticModel.getCurrentStochasticMark();
                for (Double m : mark) {
                    String str = String.format("%.5f\t", m);
                    stochasticFw.write(str);
                }
                stochasticFw.write("\t");

                stochasticModel.calcKolmogorovChapman();
                List<Double> states = stochasticModel.getCalculatedStates();

                for (Double m : states) {
                    String str = String.format("%.7f\t", m);
                    stochasticFw.write(str);
                }
                stochasticFw.write("\n");
                stochasticModel.doStep();
                counter++;
            } while (stochasticModel.getDiff() > 0.00001 && counter < 30);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            stochasticFw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}