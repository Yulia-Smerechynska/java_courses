package com.company.mainproject;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


@ToString
public class Card {
    @Getter
    private String stepImageName;

    public String createCards() {

        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        // TODO: 29.09.2019 change count of elements depends from level
        int countOfElements = 9;
        // TODO: 29.09.2019 change count of matched elements depends from level
        int shouldBeMatched = 3;

        ImageLoader imageLoader = new ImageLoader();
        ArrayList<String> images = imageLoader
                .getRenderedImages("/src/main/resources/static/image/", countOfElements, shouldBeMatched);

        stepImageName = imageLoader.getCurrentStepImageName();


        for (int i = 0; i < 10; i++) {
            obj.put("id", i);
            obj.put("image", "/image/" + images.get(i));
            obj.put("name", images.get(i));
            obj.put("isMain", images.get(i).equals(stepImageName));
            obj.put("matchCount", shouldBeMatched);
            arr.put(obj);

            obj = new JSONObject();
        }
        return arr.toString();
    }
}
