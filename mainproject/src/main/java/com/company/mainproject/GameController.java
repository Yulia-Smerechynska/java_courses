package com.company.mainproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GameController {

    @GetMapping("/")
    public String greeting(
    ) {
        return "main";
    }

    @PostMapping(path = "/setUser")
    public String setUser(
            @RequestParam(name = "name", required = true, defaultValue = "undefined") String name,
            Map<String, Object> model
    ) {
        Game game = new Game();
        game.createNewPlayer(name);
        // TODO: 01.10.2019  check and save user
        // maybe set user id in cache
        int[] yourList = new int[]{0, 1, 2, 3, 4, 5};
        model.put("name", game.getCurrentPlayer().getName());
        model.put("yourList", yourList); // not need for now

        return "game";
    }

}

