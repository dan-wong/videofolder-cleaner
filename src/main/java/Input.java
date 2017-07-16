import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {
    private BufferedReader _input;

    public Input() {
        _input = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getInput() {
        String line = "";

        try {
            line = _input.readLine();
            while (!validTaskInput(line)) {
                System.out.println("Invalid task option. Please try again.");
                line = getInput();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    public boolean getYesNo() {
        String line = "";

        try {
            line = _input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!line.equals("")) {
            line = line.toUpperCase();
            if (line.equals("Y")) {
                return true;
            }
        }
        return false;
    }

    public boolean validTaskInput(String input) {
        int choice = Integer.valueOf(input);
        return choice > 0 && choice < 4;
    }
}
