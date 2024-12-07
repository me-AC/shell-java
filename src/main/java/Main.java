import java.util.*;
import java.nio.file.*;

public class Main {

    static private String input = "";
    static private String builtin_list[] = { "echo", "exit", "type" };

    static private String nextToken() {
        String token = "";
        int i = 0;
        for (; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                input = input.substring(i + 1);
                return token;
            }
            token += input.charAt(i);
        }
        input = input.substring(i);
        return token;
    }

    static private boolean isBuiltin(String s) {
        for (int i = 0; i < builtin_list.length; i++)
            if (s.equals(builtin_list[i]))
                return true;
        return false;
    }

    static private String handleExec(String in) {
        String PATH = System.getenv("PATH");
        StringTokenizer path_tokens = new StringTokenizer(PATH, ":");
        String dir = "";
        while (path_tokens.hasMoreTokens()) {
            dir = path_tokens.nextToken();
            if (isExec(dir, in))
                return dir;
        }
        return "";
    }

    static private boolean isExec(String dir, String file) {
        Path path = Paths.get(dir, file);
        if (Files.exists(path))
            return true;
        return false;
    }

    public static void main(String[] args) throws Exception {
        System.out.print("$ ");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            input = scanner.nextLine();
            String command = nextToken();
            switch (command) {
                case "exit":
                    System.exit((input != "") ? Integer.parseInt(input) : 0);
                    scanner.close();
                    break;

                case "echo":
                    System.out.println(input);
                    break;

                case "type":
                    if (isBuiltin(input))
                        System.out.println(input + " is a shell builtin");
                    else if (handleExec(input) != "")
                        System.out.println(input + "is" + handleExec(input));
                    else
                        System.out.println(input + ": not found");
                    break;

                default:
                    System.out.println(command + ": not found");
                    break;
            }

            System.out.print("$ ");
        }
    }
}
