package com.musdon.thejavaacadmeybank.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.musdon.thejavaacadmeybank.dto.EmailDetails;
import com.musdon.thejavaacadmeybank.entity.Transaction;
import com.musdon.thejavaacadmeybank.entity.User;
import com.musdon.thejavaacadmeybank.repository.TransactionRepository;
import com.musdon.thejavaacadmeybank.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    private static final String FILE = "C:\\Users\\gopuv\\Documents\\MyStatement.pdf";

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws DocumentException, FileNotFoundException {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        List<Transaction> transactionList = transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> !transaction.getCreatedAt().isBefore(start) && !transaction.getCreatedAt().isAfter(end))
                .toList();

        User user = userRepository.findByAccountNumber(accountNumber);
        if (user == null) {
            throw new IllegalArgumentException("User not found for account number: " + accountNumber);
        }

        String customerName = user.getFirstname() + " " + user.getLastname();
        Document document = new Document(PageSize.A4);
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLUE);
        Paragraph title = new Paragraph("BANK STATEMENT", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Bank Info Table
        PdfPTable bankInfoTable = new PdfPTable(1);
        bankInfoTable.setWidthPercentage(100);

        PdfPCell bankName = new PdfPCell(new Phrase("State Bank of 3rd Year", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE)));
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(10f);
        bankName.setHorizontalAlignment(Element.ALIGN_CENTER);
        bankName.setBorder(Rectangle.NO_BORDER);

        PdfPCell bankAddress = new PdfPCell(new Phrase("32, Crown City, Kharar, Punjab"));
        bankAddress.setHorizontalAlignment(Element.ALIGN_CENTER);
        bankAddress.setBorder(Rectangle.NO_BORDER);

        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);
        document.add(bankInfoTable);

        // Space before statement info
        document.add(new Paragraph("\n"));

        // Statement Info Table
        PdfPTable statementInfo = new PdfPTable(2);
        statementInfo.setWidthPercentage(100);
        statementInfo.setSpacingBefore(10f);

        statementInfo.addCell(createCell("Start Date:", Element.ALIGN_LEFT, Font.BOLD));
        statementInfo.addCell(createCell(startDate, Element.ALIGN_RIGHT, Font.NORMAL));
        statementInfo.addCell(createCell("End Date:", Element.ALIGN_LEFT, Font.BOLD));
        statementInfo.addCell(createCell(endDate, Element.ALIGN_RIGHT, Font.NORMAL));
        statementInfo.addCell(createCell("Customer Name:", Element.ALIGN_LEFT, Font.BOLD));
        statementInfo.addCell(createCell(customerName, Element.ALIGN_RIGHT, Font.NORMAL));
        statementInfo.addCell(createCell("Customer Address:", Element.ALIGN_LEFT, Font.BOLD));
        statementInfo.addCell(createCell(user.getAddress(), Element.ALIGN_RIGHT, Font.NORMAL));

        document.add(statementInfo);

        // Space before transactions table
        document.add(new Paragraph("\n"));

        // Transactions Table
        PdfPTable transactionTable = new PdfPTable(4);
        transactionTable.setWidthPercentage(100);
        transactionTable.setSpacingBefore(10f);
        transactionTable.setWidths(new float[]{3, 4, 3, 3});

        // Table Headers
        transactionTable.addCell(createHeaderCell("DATE"));
        transactionTable.addCell(createHeaderCell("TRANSACTION TYPE"));
        transactionTable.addCell(createHeaderCell("AMOUNT"));
        transactionTable.addCell(createHeaderCell("STATUS"));

        // Adding transactions
        boolean alternateColor = false;
        for (Transaction transaction : transactionList) {
            BaseColor rowColor = alternateColor ? new BaseColor(235, 235, 235) : BaseColor.WHITE;
            transactionTable.addCell(createRowCell(transaction.getCreatedAt().toString(), rowColor));
            transactionTable.addCell(createRowCell(transaction.getTransactionType(), rowColor));
            transactionTable.addCell(createRowCell(transaction.getAmount().toString(), rowColor));
            transactionTable.addCell(createRowCell(transaction.getStatus(), rowColor));
            alternateColor = !alternateColor;
        }

        document.add(transactionTable);
        document.close();

        log.info("Bank statement PDF generated successfully.");

        // Send Email
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("STATEMENT OF ACCOUNT")
                .messageBody("Kindly find your requested account statement attached.")
                .attachment(FILE)
                .build();
        emailService.sendEmailAlert(emailDetails);

        log.info("Email sent successfully.");
        return transactionList;
    }

    private PdfPCell createCell(String text, int alignment, int style) {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, style);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

    private PdfPCell createHeaderCell(String text) {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(BaseColor.BLUE);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell createRowCell(String text, BaseColor backgroundColor) {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setBackgroundColor(backgroundColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
}
