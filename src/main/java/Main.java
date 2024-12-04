import java.util.*;

public class Main {

    static private String input="";

    static private String nextToken() {
        String token="";
        for(int i=0;i<input.length();i++) {
            if(input.charAt(i)==' ') {
                input=input.substring(i+1);
                return token;
            }
            token+=input.charAt(i);
        }
        return input;
    }


    public static void main(String[] args) throws Exception {
        System.out.print("$ ");
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            input = scanner.nextLine();

            switch (nextToken()) {
                case "exit":
                    System.exit((input!="") ? Integer.parseInt(input):0);
                    scanner.close();
                    break;

                case "echo":
                    System.out.println(input);
                    break;

                default:
                    System.out.println(input+": not found");
                    break;
            }

            System.out.print("$ ");
        }
    }
}
