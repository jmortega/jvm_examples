package files;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * User: starblood
 * Date: 2014. 4. 15.
 * Time: 오전 9:26
 */
public class ListSelectFiles {
    public static void main(String[] args) throws IOException, InterruptedException {
        Files.list(Paths.get(".")).forEach(System.out::println);
        System.out.println("");
        Files.list(Paths.get(".")).filter(Files::isDirectory).forEach(System.out::println);

        final String [] files = new File("book_src/compare/fpij").list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });
        System.out.println(files);

        Files.newDirectoryStream(Paths.get("book_src/compare/fpij"), path -> path.toString().endsWith(".java")).forEach(System.out::println);

        final File[] hiddenFiles = new File(".").listFiles(file -> file.isHidden());
        for (File file : hiddenFiles) {
            System.out.println(file);
        }

        for (File file : new File(".").listFiles(File::isHidden)) {
            System.out.println(file);
        }

        listTheHardWay();
        betterWay();

        final Path path = Paths.get(".");
        final WatchService watchService = path.getFileSystem().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        System.out.println("Report any file changed within 1 minute...");


        final WatchKey watchKey = watchService.poll(1, TimeUnit.MINUTES);
        if (watchKey != null) {
            watchKey.pollEvents().stream().forEach(event -> System.out.println(event.context()));
        }
    }

    public static void listTheHardWay() {
        List<File> files = new ArrayList<File>();

        File[] filesInCurrentDir = new File(".").listFiles();
        for (File file : filesInCurrentDir) {
            File[] filesInSubDir = file.listFiles();
            if (filesInSubDir != null) {
                files.addAll(Arrays.asList(filesInSubDir));
            } else {
                files.add(file);
            }
        }
        System.out.println("file size: " + files.size());
        for (File file : files) {
            System.out.println(file);
        }
    }
    public static void betterWay() {
        List<File> files = Stream.of(new File(".").listFiles())
                .flatMap(file -> file.listFiles() != null ? Stream.of(file.listFiles()) : Stream.of(file)).collect(toList());
        System.out.println("file size: " + files.size());
        for (File file : files) {
            System.out.println(file);
        }
    }
}
