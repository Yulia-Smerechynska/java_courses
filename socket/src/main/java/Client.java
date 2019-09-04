import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] ar) {
        int serverPort = 6666;
        String address = "127.0.0.1";

        try {
            Socket socket = new Socket(address, serverPort);

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Type something");

            String line = null;
            while (true) {
                line = keyboard.readLine();
                out.writeUTF(line);
                out.flush();
                line = in.readUTF();
                System.out.println("It comes back from server: " + line);
                System.out.println("Tape more");
                System.out.println();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
