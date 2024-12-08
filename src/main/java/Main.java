import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Main {

	static private String input = "";
	static private String PATH = System.getenv("PATH");
	static private String PWD = System.getProperty("user.dir");
	static private String builtin_list[] = { "echo", "exit", "type", "pwd" };

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
			if (Files.exists(Paths.get(dir, in)))
				return dir;
		}
		return "";
	}

	static private void handleExecution(String[] program) {
		try {
			Process process = Runtime.getRuntime().exec(program);
			BufferedReader reader = process.inputReader();
			String output = "";

			while ((output = reader.readLine()) != null) {
				System.out.println(output);
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
					else if (input.isEmpty())
						System.out.println(input + ": not found");
					else if (handleTypeExec(input) != "")
						System.out.printf("%s is %s/%s%n", input, handleTypeExec(input), input);
					else
						System.out.println(input + ": not found");
					break;

				case "pwd":
					System.out.println(PWD);
					break;

				case "cd":
					if (input.isEmpty())
						break;
					else if (input.equals("~")) {
						String HOME = System.getenv("HOME");
						PWD = HOME;
						System.setProperty("user.dir", PWD);
					} else if (input.startsWith("./")) {
						String abs_path = PWD.concat(input.substring(1));
						if (Files.isDirectory(Paths.get(abs_path))) {
							PWD = abs_path;
							System.setProperty("user.dir", PWD);
						} else
							System.out.printf("cd: %s: No such file or directory%n", input);
					} else if (input.startsWith("/") && Files.isDirectory(Paths.get(input))) {
						PWD = input;
						System.setProperty("user.dir", input);
					} else if (input.startsWith("../")) {
						while (input.startsWith("../")) {
							input = input.substring(3);
							PWD = PWD.substring(0, PWD.lastIndexOf("/"));
							System.setProperty("user.dir", PWD);
						}
					} else
						System.out.printf("cd: %s: No such file or directory%n", input);
					break;

				default:
					if (command.isEmpty()) {
						System.out.println(command + ": not found");
						break;
					}
					if (!handleTypeExec(command).isEmpty()) {
						// command = String.join(" ", command, input);
						command = command.concat(" ").concat(input);
						handleExecution(command.split(" "));
					} else
						System.out.println(command + ": not found");
					break;
			}

			System.out.print("$ ");
		}
	}
}
