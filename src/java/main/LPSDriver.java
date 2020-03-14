import org.shoupu.string.*;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

class LPSDriver {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }
        int choice = Integer.valueOf(args[0]);
        String inputFile = args[1];
        String outputFile = args.length > 2 ? args[2] : null;
        LongestPalindrome solver = getSolver(choice);
        // System.out.printf("Input: %s, output: %s, algorithm: %s\n", inputFile,
        //     outputFile, solver.getClass().getSimpleName());
        String ans = solver.longestPalindrome(readInput(inputFile));
        if (outputFile != null)
            writeOutput(outputFile, ans);
        System.out.println(ans.length());
    }

    static String readInput(String inputFile) {
        try {
            return new String(Files.readAllBytes(Paths.get(inputFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    static void writeOutput(String outputFile, String output) {
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write(output+"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static LongestPalindrome getSolver(int choice) {
        switch (choice) {
            case 0:
            return new LongestPalindromeAugment();
            case 1:
            return new LongestPalindromeIndexMapping();
            case 2:
            return new LongestPalindromeModular();
        }
        throw new IllegalArgumentException(String.format("Invalid choice %d", choice));
    }
}
