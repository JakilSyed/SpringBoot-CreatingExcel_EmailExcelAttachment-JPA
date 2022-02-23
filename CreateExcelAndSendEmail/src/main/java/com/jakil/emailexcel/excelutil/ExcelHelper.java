package com.jakil.emailexcel.excelutil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jakil.emailexcel.emailutil.EmailAttachmentModel;
import com.jakil.emailexcel.emailutil.EmailSender;
import com.jakil.emailexcel.model.Tutorial;

@Component
public class ExcelHelper {

	@Autowired
	EmailSender emailSender;

	private static String FILE_NAME = "SAMPLE_EXCEL";
	private static String WORKSHEET_NAME = "Worksheet";
	private static String MIME_TYPE = "application/vnd.ms-excel";
	private static String header[] = { "Id", "Title", "Description", "Published" };

	public void generateExcelFile(List<Tutorial> tutorials) {

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream fileOut = new ByteArrayOutputStream()) {
			Sheet sheet = workbook.createSheet(WORKSHEET_NAME);

			// Header
			int rowNumber = 0;
			Row headerRow = sheet.createRow(rowNumber);
			for (int col = 0; col < header.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(header[col]);
			}

			rowNumber = 1;
			for (Tutorial tutorial : tutorials) {
				Row row = sheet.createRow(rowNumber++);
				row.createCell(0).setCellValue(tutorial.getId());
				row.createCell(1).setCellValue(tutorial.getTitle());
				row.createCell(2).setCellValue(tutorial.getDescription());
				row.createCell(3).setCellValue(tutorial.isPublished());
			}

			workbook.write(fileOut);

			List<EmailAttachmentModel> emailAttachments = prepareEmailAttachent(MIME_TYPE, FILE_NAME, fileOut);
			
			//Calling mail send function
			emailSender.sendMail(emailAttachments);

		} catch (Exception ex) {
			System.out.println("Exception Occured while creating excel. In generateExcelFile() of ExcelHelper class."
					+ ex.getMessage());
		}

	}

	private List<EmailAttachmentModel> prepareEmailAttachent(String mimeType, String fileName,
			ByteArrayOutputStream fileOut) {

		List<EmailAttachmentModel> emailAttachments = new ArrayList<>();
		EmailAttachmentModel emailAttachment = new EmailAttachmentModel(mimeType, fileName, fileOut.toByteArray());
		emailAttachments.add(emailAttachment);

		return emailAttachments;

	}

}
