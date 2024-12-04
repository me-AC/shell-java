import java.util.*;

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
