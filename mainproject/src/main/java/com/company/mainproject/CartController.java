package com.company.mainproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class CartController {
    private Card card;
    @GetMapping(value = "/getStartCards", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getStartCards() {
        card = new Card();
        return card.createCards();
    }

    @PostMapping(value = "/makeStep")
    public boolean makeStep(@RequestBody String jsonString) throws IOException {
        // TODO: 30.09.2019 set Game class as spring been
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Iterator x = jsonObject.keys();
            JSONArray jsonArray = new JSONArray();

            while (x.hasNext()) {
                for(int i=0;i<jsonObject.length();i++) {
                    String key = (String) x.next();
                    jsonArray.put(jsonObject.get(key));
                }
            }
            String currentStepImage = card.getStepImageName();

           for(int i=0; i<jsonArray.length(); i++) {
               if(!jsonArray.get(i).equals(currentStepImage)) {
                   return false;
               }
           }
        }catch (JSONException err){}
        return true;
    }
}
