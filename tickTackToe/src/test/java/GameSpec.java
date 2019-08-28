import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GameSpec {

    private Game game;

    @Before
    public void init() {
        game = new Game();
        game.setFirstPlayer(new Player("first", "X"));
        game.setSecondPlayer(new Player("second", "O"));
    }

    @Test
    public void mustGenerateTicTacToeField() {
        int expectedSize = 3;

        String[][] field = game.getField();

        assertEquals(field.length, expectedSize);
        for (int i = 0; i < field.length; i++) {
            int actual = field[i].length;
            assertEquals(expectedSize, actual);
        }
    }

    @Test
    public void mustGenerateAllFieldsEmptyStrings() {
        String[][] field = game.getField();

        for (int i = 0; i < field.length; i++) {
            String[] row = field[i];
            for (int j = 0; j < row.length; j++) {
                assertEquals("", row[j]);
            }
        }
    }

    @Test
    public void mustSetSymbolByCoords() {
        String expected = "X";

        game.step(1, 1);

        String[][] field = game.getField();
        String actual = field[1][1];
        assertEquals(expected, actual);
    }

    @Test
    public void mustChangePlayerAfterMove() {
        game.step(1, 1);
        game.step(1, 2);

        String[][] field = game.getField();
        assertNotEquals(field[1][1], field[1][2]);
    }

    @Test(expected = AlreadyFilledFieldException.class)
    public void mustNotOverrideExistingSteps() {
        game.step(1, 1);
        game.step(1, 1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void mustNotSetOutOfBoundsOfField() {
        game.step(5, 5);
    }


    @Test(expected = GameAlreadyEndedException.class)
    public void mustNotAbleToDoMoveAfterExdOfTheGameByRow() {
        game.step(0, 0);
        game.step(1, 0);
        game.step(0, 1);
        game.step(1, 1);
        game.step(0, 2);
        game.step(2, 2);
    }

    @Test(expected = GameAlreadyEndedException.class)
    public void mustNotAbleToDoMoveAfterExdOfTheGameByColumn() {
        game.step(0, 0);
        game.step(0, 1);
        game.step(1, 0);
        game.step(1, 1);
        game.step(2, 0);
        game.step(2, 2);
    }

    @Test(expected = GameAlreadyEndedException.class)
    public void mustWinIfAllInRowFilledByOnePlayer() {
        game.step(0, 0);
        game.step(0, 1);
        game.step(1, 1);
        game.step(1, 2);
        game.step(2, 2);
        game.step(2, 1);
    }

    @Test(expected = GameDeadHeatException.class)
    public void mustNobodyWinIfDeadHeat() {
        game.step(1, 1);
        game.step(0, 0);
        game.step(0, 1);
        game.step(2, 1);
        game.step(2, 0);
        game.step(0, 2);
        game.step(2, 2);
        game.step(1, 0);
        game.step(1, 2);
    }

}