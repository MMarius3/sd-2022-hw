package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SQLSchemaUtil {

    private static String WORKING_DIR = System.getProperty("user.dir") + "/src/main/java";

    public static ArrayList<String> getCreateTablesQueries() throws Exception {

        String tablesDirectory = WORKING_DIR + "/sql/schema/tabel";
        return getAllFileContentsFromDirectory(tablesDirectory);
    }

    public static ArrayList<String> getConstraintsQueries() throws Exception {
        String tablesDirectory = WORKING_DIR + "/sql/schema/constraint";
        return getAllFileContentsFromDirectory(tablesDirectory);
    }

    public static ArrayList<String> getMockDataQueries() throws Exception {
        String tablesDirectory = WORKING_DIR + "/sql/schema/mock_data_insert";
        return getAllFileContentsFromDirectory(tablesDirectory);
    }

    private static ArrayList<String> getAllFileContentsFromDirectory(String tablesDirectory) throws Exception {

        ArrayList<String> tablesQueries = new ArrayList<>();

        File directory = new File(tablesDirectory);

        String[] filesInDirectory = directory.list();

        if (filesInDirectory == null) {
            throw new Exception("There are no files in directory " + tablesDirectory);
        }

        List<String> filesNames = Arrays.asList(filesInDirectory);
        filesNames = filesNames
                .stream()
                .map(currentFileName -> tablesDirectory + "/" + currentFileName)
                .collect(Collectors.toList());

        ArrayList<String> filesContents = new ArrayList<>();

        filesNames.forEach(s -> {
            try {
                filesContents.add(getFilesContents(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return filesContents;
    }

    private static String getFilesContents(String fileName) throws IOException {
        System.out.println(fileName);
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static String getRefreshDBQuery() throws IOException {
        String filePath = WORKING_DIR + "/sql/schema/RefreshDB.sql";
        return getFilesContents(filePath);
    }
}
