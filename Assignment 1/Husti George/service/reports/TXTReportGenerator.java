package service.reports;

import repository.ActionRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class TXTReportGenerator implements IReportGenerator {

    private ActionRepository actionRepository;

    @Override
    public void generateReport(LocalDateTime startDate, LocalDateTime endDate, int userID) {

        StringBuilder reportContent = new StringBuilder("Report from " + startDate + " to " + endDate + " for " + userID + "\n");

        actionRepository.getAllBetweenDates(startDate, endDate, userID).forEach(action ->
                reportContent
                        .append(action.getUser())
                        .append(" -> ")
                        .append(action.getName())
                        .append(" at ")
                        .append(action.getDate())
                        .append("\n"));

        createFile("C:\\Users\\husti\\OneDrive\\Desktop\\Anul 3\\Sem2\\New folder", "report.txt", reportContent.toString());
    }

    @Override
    public IReportGenerator setActionRepository(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
        return this;
    }

    private void createFile(String fileDirectoryPath, String fileName, String fileContent) {

        String fullFilePath = fileDirectoryPath + File.separator + fileName;

        try {
            new File(fullFilePath).createNewFile();
        } catch (IOException e) {
            System.out.println(fullFilePath + " could not have been created");
            e.printStackTrace();
        }

        writeInFile(fullFilePath, fileContent);
    }

    private void writeInFile(String filePath, String fileContent) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(fileContent);
            myWriter.close();
        } catch (IOException e) {
            System.out.println(filePath + " could not write in it");
            e.printStackTrace();
        }
    }
}
