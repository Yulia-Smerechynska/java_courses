package com.company.springttt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "tictacktoe/")
@RestController
public class GameController {
    private Game game;

    @GetMapping(value = "startgame")
    public String runGame() {
        game = new Game();
        return "game have been started";
    }

    @GetMapping(value = "setPlayerOne")
    public String setPlayerOne(@RequestParam(value = "playerName") String playerName) {
        try {
            game.setPlayerOne(playerName);
            return "First player name: " + game.getFirstPlayer().getUserName();
        } catch (NullPointerException e) {
            return "Please run game first";
        }
    }

    @GetMapping(value = "setPlayerTwo")
    public String setPlayerTwo(@RequestParam(value = "playerName") String playerName) {
        try {
            game.setPlayerTwo(playerName);
            return "Second player name: " + game.getSecondPlayer().getUserName();
        } catch (NullPointerException e) {
            return "Please run game first";
        }
    }

    @GetMapping(value = "step")
    public String makeStep(@RequestParam(value = "stepValue") String stepValue) {
        try {
            return game.step(stepValue);
        } catch (AlreadyFilledFieldException e) {
            return "this field is filled out already";
        } catch (GameDeadHeatException e) {
            return "nobody win";
        } catch (GameAlreadyEndedException e) {
            return "Player " + game.getCurrentPlayer().getUserName() + " win!";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "set step values between 0 and 2";
        } catch (NullPointerException e) {
            return "Please run game and set players first";
        }
    }

}
