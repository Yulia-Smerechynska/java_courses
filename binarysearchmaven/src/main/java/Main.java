import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        SystemCheck checkingResult = new SystemCheck();
        Gson gson = new Gson();
        String json = gson.toJson(checkingResult);

        try(FileWriter writer = new FileWriter("json-file.txt", true))
        {
            writer.write(json);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        System.out.println(json);
    }
}
