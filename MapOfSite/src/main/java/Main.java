import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String start = "https://skillbox.ru/";
        String attribute = "skillbox.ru";
        PageLinksFinder task = new PageLinksFinder(attribute, start);
        Set<String> result = new ForkJoinPool().invoke(task);
        if (result.getClass() != TreeSet.class) {
             result = new TreeSet<>(result);
        }
        writeFile(result);
    }

    private static void writeFile(Set<String> result) {
        try {
            File file = new File("D:/IdeaProjects/java_basics/Multithreading" +
                    "/MapOfSite/src/main/resources/links.txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            String[] examp = new String[20];
            for (int i = 0; i < 20; i++) {
                examp[i] = "xxx111YYY222zzz";
            }
            for (String s : result) {
                int i = 0;
                while (s.contains(examp[i]) && (s.length() > examp[i].length()) && (i < 19)) {
                    i++;
                }
                examp[i] = s;
                bw.write("\t".repeat(i) + s + System.lineSeparator());
            }
            bw.write(result.size() + System.lineSeparator());
            bw.flush();
            bw.close();
            fileWriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
