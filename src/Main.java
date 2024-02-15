import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static double[][] matrix = new double[][] {{0.5, 0.25, 0.25}, {0.5, 0., 0.5}, {0.67, 0.33, 0.}};

    public static void main(String[] args) {
        StochasticModel stochasticModel = new StochasticModel(3, 3, matrix, "stohastic.txt");
        stochasticModel.playInitialState();
        System.out.println("Начальное состояние: " + stochasticModel.getState() + "\n");
        for (int i = 0; i < 20; i++) {
            System.out.println("Шаг №" + i);
            stochasticModel.next();
        }

        System.out.println("Оценка текущего состояния:");
        for (int i = 0; i < 20; i++) {
            System.out.println("Шаг №" + i + "\t");
            stochasticModel.getCurrentStateMark();
            System.out.println();
        }

        stochasticModel.saveFile();

        MChain mChain = new MChain(3, 3, matrix, "chain.txt");
        mChain.playInitialState();
        System.out.print("\nНачальное состояние:");
        mChain.printState();
        System.out.println();
        System.out.println("Моделирование:");
        mChain.model(0.0001, 20);
        mChain.saveFile();
    }
}