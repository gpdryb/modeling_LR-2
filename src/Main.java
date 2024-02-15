public class Main {
    private static double[][] matrix = new double[][] {{0.5, 0.25, 0.25}, {0.5, 0., 0.5}, {0.67, 0.33, 0.}};

    public static void main(String[] args) {
        StochasticModel stochasticModel = new StochasticModel(3, 3, matrix);
        stochasticModel.setState(2);
        stochasticModel.getStochasticNextState();
        stochasticModel.getCurrentStateMark();

    }
}