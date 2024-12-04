import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Uncomment this block to pass the first stage
        System.out.print("$ ");

        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            String input = scanner.nextLine();
            StringTokenizer st=new StringTokenizer(input," ");
            switch (st.nextToken()) {
                case "exit":
                    System.exit(st.hasMoreTokens() ? Integer.parseInt(st.nextToken()) : 0);
                    break;

                case "echo":
                    while(st.hasMoreTokens())
                        System.out.print(st.nextToken()+ " ");
                    System.out.println();
                    break;

                default:
                    System.out.println(input+": not found");
                    break;
            }

            System.out.print("$ ");
        }
    }
}
