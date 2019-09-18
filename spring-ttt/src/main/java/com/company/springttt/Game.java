package com.company.springttt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Game {

    private boolean gameEnded;

    private final String[][] field = new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}};
    private HashMap<String, WinCombinations> pathMap = new HashMap<>();

    private final String cell_0 = "00";
    private final String cell_1 = "01";
    private final String cell_2 = "02";
    private final String cell_3 = "10";
    private final String cell_4 = "11";
    private final String cell_5 = "12";
    private final String cell_6 = "20";
    private final String cell_7 = "21";
    private final String cell_8 = "22";

    private int stepCounter = 0;
    private int maxStepsCount = 0;
    private Player currentPlayer;
    private Player firstPlayer = null;
    private Player secondPlayer = null;

    private int currentX;
    private int currentY;

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
        getMaxStepsCount();
    }

    public String step(String stepValue) {

        getStepCoordinates(stepValue);

        setCurrentPlayer();

        checkIfFilled();

        setPlayerStepMarker();

        calculateEndOfTheGame();

        checkForGameEnd();

        checkForeDeadHeat();

        stepCounter++;

        return displayStep();

    }

    private void getStepCoordinates(String stepValue) {
        stepValue = stepValue.replaceAll("[^\\.012]", "");
        char[] inputChars = stepValue.toCharArray();
        if (inputChars.length < 2) {
            throw new ArrayIndexOutOfBoundsException();
        }

        currentX = Character.getNumericValue(inputChars[0]);
        currentY = Character.getNumericValue(inputChars[1]);
    }

    private void checkIfFilled() {
        if (!field[currentX][currentY].equals("")) {
            throw new AlreadyFilledFieldException();
        }
    }

    private void checkForeDeadHeat() {
        if (this.stepCounter == maxStepsCount) {
            throw new GameDeadHeatException();
        }
    }

    private void getMaxStepsCount() {
        int count = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                count++;
            }
        }
        maxStepsCount = count;
    }

    private void setCurrentPlayer() {
        currentPlayer = stepCounter % 2 == 0 ? firstPlayer : secondPlayer;
    }

    private void setPlayerStepMarker() {
        field[currentX][currentY] = currentPlayer.getUserMarker();
    }

    public Player setPlayerOne(String playerName) {
        Player playerOne = new Player();
        playerOne.setUserName(playerName);
        playerOne.setUserMarker("X");
        firstPlayer = playerOne;
        return playerOne;
    }

    public Player setPlayerTwo(String playerName) {
        Player playerTwo = new Player();
        playerTwo.setUserName(playerName);
        playerTwo.setUserMarker("O");
        secondPlayer = playerTwo;
        return playerTwo;
    }

    private void calculateEndOfTheGame() {

        WinCombinations path = pathMap.get(Integer.toString(currentX) + Integer.toString(currentY));
        System.out.println(path.getWinCombinations().length);
        for (int i = 0; i < path.getWinCombinations().length; i++) {
            Integer[][] combinationCells = path.getWinCombinations()[i];
            for (int k = 0; k < combinationCells.length; k++) {
                Integer neighbourOneCoordinatesX = combinationCells[0][0];
                Integer neighbourOneCoordinatesY = combinationCells[0][1];
                Integer neighbourTwoCoordinatesX = combinationCells[1][0];
                Integer neighbourTwoCoordinatesY = combinationCells[1][1];

                String neighbourOne = field[neighbourOneCoordinatesX][neighbourOneCoordinatesY];
                String neighbourTwo = field[neighbourTwoCoordinatesX][neighbourTwoCoordinatesY];
                String currentStep = field[currentX][currentY];

                if (currentStep.equals(neighbourOne) && neighbourOne.equals(neighbourTwo)) {
                    gameEnded = true;
                    break;
                }
            }
        }
    }

    private void checkForGameEnd() {
        if (gameEnded) {
            throw new GameAlreadyEndedException();
        }
    }

    private String displayStep() {
        String rez = "";
        for (int i = 0; i < field.length; i++) {
            String[] row = field[i];
            for (int k = 0; k < row.length; k++) {
                rez = rez + "|" + (row[k].isEmpty() ? "_" : row[k]) + "|";
                System.out.print(rez);
            }
            rez = rez + '\n';
        }
        return rez + '\n';
    }
}
