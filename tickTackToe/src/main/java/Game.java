
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Game {

    private boolean gameEnded;
    private int stepCounter = 0;
    private final String[][] field = {{"", "", ""}, {"", "", ""}, {"", "", ""}};
    private HashMap<String, WinCombinations> pathMap = new HashMap<>();
    private Player currentPlayer;
    private Player firstPlayer = null;
    private Player secondPlayer = null;
    private final String cell_0 = "0,0";
    private final String cell_1 = "0,1";
    private final String cell_2 = "0,2";
    private final String cell_3 = "1,0";
    private final String cell_4 = "1,1";
    private final String cell_5 = "1,2";
    private final String cell_6 = "2,0";
    private final String cell_7 = "2,1";
    private final String cell_8 = "2,2";


    public Game() {
        pathMap.put(this.cell_0, new WinCombinations(new Integer[][][]{{{0, 1}, {0, 2},}, {{1, 1}, {2, 2},}, {{1, 0}, {2, 0},}}));
        pathMap.put(this.cell_1, new WinCombinations(new Integer[][][]{{{0, 0}, {0, 2},}, {{1, 1}, {2, 1},}}));
        pathMap.put(this.cell_2, new WinCombinations(new Integer[][][]{{{0, 0}, {0, 1},}, {{1, 1}, {2, 0},}, {{1, 2}, {2, 2},}}));
        pathMap.put(this.cell_3, new WinCombinations(new Integer[][][]{{{0, 0}, {2, 0},}, {{1, 1}, {1, 2},}}));
        pathMap.put(this.cell_4, new WinCombinations(new Integer[][][]{{{0, 0}, {2, 2},}, {{0, 1}, {2, 1},}, {{0, 2}, {2, 0},}, {{1, 0}, {1, 2},}}));
        pathMap.put(this.cell_5, new WinCombinations(new Integer[][][]{{{1, 0}, {1, 1},}, {{0, 2}, {2, 2},}}));
        pathMap.put(this.cell_6, new WinCombinations(new Integer[][][]{{{0, 0}, {1, 0},}, {{1, 1}, {0, 2},}, {{2, 1}, {2, 2},}}));
        pathMap.put(this.cell_7, new WinCombinations(new Integer[][][]{{{0, 1}, {1, 1},}, {{2, 0}, {2, 2},}}));
        pathMap.put(this.cell_8, new WinCombinations(new Integer[][][]{{{0, 0}, {1, 1},}, {{0, 2}, {1, 2},}, {{2, 0}, {2, 1},}}));
    }

    public void start() {
        System.out.println("game started");

        this.createPlayerOne();
        this.createPlayerTwo();

        BufferedReader br = null;

        try {

            br = new BufferedReader(new InputStreamReader(System.in));

            while (!gameEnded) {

                setCurrentPlayer();

                System.out.print("Player " + this.currentPlayer.getName() + " next step : ");
                String input = br.readLine();

                if ("q".equals(input)) {
                    gameOver("Game over");
                }
                input = input.replaceAll("[^\\.012]", "");
                char[] inputChars = input.toCharArray();

                if (inputChars.length != 2) {
                    System.out.println("Should be 2 characters in range from 0 till 2");
                } else {
                    try {
                        this.step(Character.getNumericValue(inputChars[0]), Character.getNumericValue(inputChars[1]));
                    } catch (AlreadyFilledFieldException e) {
                        System.out.println("this field is filled out already");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GameAlreadyEndedException e) {
            gameOver("Game over");
        } catch (GameDeadHeatException e) {
            gameOver("Game over. Dead Heat");
        }
    }

    public void step(int x, int y) {

        setCurrentPlayer();

        checkIfFilled(this.field[x][y]);

        setPlayerStepMarker(x, y);

        calculateEndOfTheGame(x, y);

        checkForGameEnd();

        this.displayStep();

        stepCounter++;

        checkForeDeadHeat();
    }

    private void calculateEndOfTheGame(int x, int y) {

        WinCombinations path = pathMap.get(Integer.toString(x) + "," + Integer.toString(y));

        for (int i = 0; i < path.getWinCombinations().length; i++) {
            Integer[][] combinationCells = path.getWinCombinations()[i];
            for (int k = 0; k < combinationCells.length; k++) {
                Integer neighbourOneCoordinatesX = combinationCells[0][0];
                Integer neighbourOneCoordinatesY = combinationCells[0][1];
                Integer neighbourTwoCoordinatesX = combinationCells[1][0];
                Integer neighbourTwoCoordinatesY = combinationCells[1][1];

                String neighbourOne = field[neighbourOneCoordinatesX][neighbourOneCoordinatesY];
                String neighbourTwo = field[neighbourTwoCoordinatesX][neighbourTwoCoordinatesY];
                String currentStep = field[x][y];

                if (currentStep.equals(neighbourOne) && neighbourOne.equals(neighbourTwo)) {
                    gameEnded = true;
                    break;
                }
            }
        }
    }

    private void createPlayerOne() {
        try {
            String firstPlayerName = this.getPlayerName("first");
            String firstPlayerMarker = this.getMarker("first");
            setFirstPlayer(new Player(firstPlayerName, firstPlayerMarker));
        } catch (IOException e) {
            setFirstPlayer(new Player("First", "X"));
        }
    }

    private void createPlayerTwo() {
        try {
            String secondPlayerName = this.getPlayerName("second");
            String secondPlayerMarker = this.getMarker("second");
            setSecondPlayer(new Player(secondPlayerName, secondPlayerMarker));
        } catch (IOException e) {
            setSecondPlayer(new Player("Second", "O"));
        }
    }

    public void setFirstPlayer(Player player) {
        this.firstPlayer = player;
    }

    public void setSecondPlayer(Player player) {
        this.secondPlayer = player;
    }

    private void setCurrentPlayer() {
        this.currentPlayer = stepCounter % 2 == 0 ? this.firstPlayer : this.secondPlayer;
    }

    private String getPlayerName(String player) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(System.in));

        String name = "";
        while (name.length() == 0) {
            System.out.print("Set " + player + " Name: ");
            String input = br.readLine();

            if ("q".equals(input)) {
                gameOver("Game over");
            }

            if (input.length() == 0) {
                System.out.println("Should be 1 symbol at least");
            } else {
                name = input;
            }
        }
        return name;
    }

    private String getMarker(String player) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(System.in));

        String marker = "";
        while (marker.length() == 0) {
            System.out.print("Set " + player + " Marker: ");
            String input = br.readLine();

            if ("q".equals(input)) {
                gameOver("Game over");
            }

            if (input.length() > 1) {
                System.out.println("Should be 1 symbol");
            } else {
                if (this.firstPlayer != null) {
                    if (!this.firstPlayer.getMarker().isEmpty() && !this.firstPlayer.getMarker().equals(input)) {
                        marker = input;
                    } else {
                        System.out.println("current marker is already used by another player");
                    }
                } else {
                    marker = input;
                }
            }
        }

        return marker;
    }

    private void displayStep() {
        for (int i = 0; i < field.length; i++) {
            String[] row = field[i];
            for (int k = 0; k < row.length; k++) {
                System.out.print("|" + (row[k].isEmpty() ? "_" : row[k]) + "|");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void setPlayerStepMarker(int x, int y) {
        this.field[x][y] = this.currentPlayer.getMarker();
    }

    private void checkForeDeadHeat() {
        if (this.stepCounter == 9) {
            this.displayStep();
            throw new GameDeadHeatException();
        }
    }

    private void checkForGameEnd() {
        if (gameEnded) {
            this.displayStep();
            System.out.println("Player " + this.currentPlayer.getName() + " win!");
            throw new GameAlreadyEndedException();
        }
    }

    private void checkIfFilled(String s) {
        if (!s.equals("")) {
            throw new AlreadyFilledFieldException();
        }
    }

    private void gameOver(String text) {
        System.out.println(text);
        System.exit(0);
    }

    public String[][] getField() {
        return field;
    }
}
