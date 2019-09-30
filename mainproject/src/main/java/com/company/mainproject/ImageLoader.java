package com.company.mainproject;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ImageLoader {
    @Getter
    @Setter
    private String currentStepImageName;

    public ArrayList<String> getRenderedImages(String directoryPath, int countOfElements, int shouldBeMatched) {
        ArrayList<String> result = new ArrayList<String>() {};

        File[] filesFromDirectory = getFilesFromDirectory(directoryPath);
        File[] cutFilesFromDirectory = Arrays.copyOfRange(filesFromDirectory, 0, countOfElements);

        Random rand = new Random();
        File stepFile = filesFromDirectory[rand.nextInt(filesFromDirectory.length)];
        this.setCurrentStepImageName(stepFile.getName());
        int[] randomIndexes = getRandomIndexes(shouldBeMatched, cutFilesFromDirectory);

        for (int i = 0; i < filesFromDirectory.length; i++) {

            if (filesFromDirectory[i].isFile()) {
                if (contains(randomIndexes, i) && !filesFromDirectory[i].equals(stepFile)) {
                    result.add(stepFile.getName());
                } else {
                    result.add(filesFromDirectory[i].getName());
                }
            }
        }
        return result;
    }

    private static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    private File[] getFilesFromDirectory(String directoryPath) {
        File folder = new File(System.getProperty("user.dir") + directoryPath);
        return folder.listFiles();
    }

    private int[] getRandomIndexes(int countOfIndexes, File[] givenList) {
        Random rand = new Random();
        int[] randomIndexes = new int[countOfIndexes];

        for (int i = 0; i < countOfIndexes; i++) {
            int randomIndex = rand.nextInt(givenList.length - 1);
            randomIndexes[i] = randomIndex;
        }
        return randomIndexes;
    }
}
