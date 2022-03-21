package service.generate_report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dtos.UserDTO;
import model.UserAction;
import org.modelmapper.ModelMapper;
import dtos.UserActionDTO;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import repository.user_action.UserActionRepository;
import service.crud.UserCRUD;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PDFReportGenerator implements ReportGenerator {

    private UserActionRepository<UserActionDTO> userActionRepository;
    private ModelMapper userActionMapper;

    private UserRepository<UserDTO> userRepository;
    private ModelMapper userMapper;

    private String userName;
    private Date periodStart;
    private Date periodEnd;

    public PDFReportGenerator(UserActionRepository<UserActionDTO> userActionRepository, ModelMapper userActionMapper) {
        this.userActionRepository = userActionRepository;
        this.userActionMapper = userActionMapper;
    }

    @Override
    public void generate(String downloadPath) {

        List<UserAction> userAction = new ArrayList<>();
        UserCRUD userCRUD = (UserCRUD) new UserCRUD()
                .setRepository(userRepository)
                .setMapper(userMapper);

        long userID = userCRUD.getByName(userName).get().getId();

        this.userActionRepository
                .getByUserID(userID)
                .forEach(userActionDTO -> userAction.add(userActionMapper.map(userActionDTO, UserAction.class)));

        ArrayList<Chunk> pdfRows = getChunks(userAction);
        downloadPDF(pdfRows, downloadPath);
    }

    private ArrayList<Chunk> getChunks(List<UserAction> userAction) {

        ArrayList<Chunk> pdfRows = new ArrayList<>();
        userAction.forEach(currentUserAction -> {

            String pdfRow =
                    currentUserAction.getUser().getName() + " -> " +
                            currentUserAction.getAction() + " : " +
                            currentUserAction.getActionDetails();

            pdfRows.add(new Chunk(pdfRow));
        });
        return pdfRows;
    }

    private void downloadPDF(ArrayList<Chunk> chunks, String downloadPath) {

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(downloadPath + getPDFName()));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();
        addChunksToDocument(chunks, document);
        document.close();
    }

    private String getPDFName() {
        return "/UserActionsReport" + LocalDateTime.now().toString() + ".pdf";
    }

    private void addChunksToDocument(ArrayList<Chunk> chunks, Document document) {
        chunks.forEach(chunk -> {
            try {
                document.add(new Paragraph(chunk));
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
    }

    public PDFReportGenerator setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public PDFReportGenerator setPeriod(Date periodStart, Date periodEnd) {
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        return this;
    }

    public PDFReportGenerator setUserRepository(UserRepository<UserDTO> userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    public PDFReportGenerator setUserMapper(ModelMapper userMapper) {
        this.userMapper = userMapper;
        return this;
    }
}
