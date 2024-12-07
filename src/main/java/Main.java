import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Main {

	static private String input = "";
	static private String PATH = System.getenv("PATH");
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

	static private String handleTypeExec(String in) {
		String[] paths = PATH.split(":");
		for (String dir : paths) {
			if (isExec(dir, in))
				return dir;
		}
		return "";
	}

	static private boolean isExec(String dir, String file) {
		if (Files.exists(Paths.get(dir, file)))
			return true;
		return false;
	}

	static private void handleExecution(String[] program) {
		try {
			Process process = Runtime.getRuntime().exec(program);
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(process.getInputStream()));
			BufferedReader reader = process.inputReader();
			String output = "";

			while ((output = reader.readLine()) != null) {
				System.out.print(output);
			}
			process.waitFor();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
					else if (handleTypeExec(input) != "")
						System.out.println(input + " is " + handleTypeExec(input) + "/" + input);
					else
						System.out.println(input + ": not found");
					break;

				default:
					if (handleTypeExec(command) != null) {
						command = command + " " + input;
						handleExecution(command.split(" "));
					} else
						System.out.println(command + ": not found");
					break;
			}
			System.out.print("$ ");
		}
	}
}
