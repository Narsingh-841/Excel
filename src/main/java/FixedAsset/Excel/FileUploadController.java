package FixedAsset.Excel;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    private final FileProcessingService fileProcessingService;
    private final List<File> processedFiles = new ArrayList<>(); // List to store processed files
    private final List<String> originalFileNames = new ArrayList<>(); // Store original file names

    public FileUploadController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("processedFileNames", originalFileNames);
        return "index";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleFileUpload(MultipartFile[] files) {
        Map<String, String> response = new HashMap<>();
        try {
            if (files.length == 0) {
                response.put("error", "Please select valid XLSX files to process.");
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(response);
            }

            for (MultipartFile file : files) {
                if (file.isEmpty() || file.getOriginalFilename() == null || !file.getOriginalFilename().endsWith(".xlsx")) {
                    response.put("error", "Only XLSX files are supported.");
                    return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(response);
                }

                String originalFileName = file.getOriginalFilename().replace(".xlsx", "");
                originalFileNames.add(originalFileName);

                List<XLSXRecord> records = fileProcessingService.readXLSXFile(file);

                File processedFile = File.createTempFile(originalFileName + "_processed", ".csv");
                fileProcessingService.writeToCSV(records, processedFile.getAbsolutePath());

                if (!processedFile.exists()) {
                    response.put("error", "Error creating processed file for " + originalFileName);
                    return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(response);
                }

                processedFiles.add(processedFile);
            }

            response.put("message", "All files processed successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error processing files: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFiles() throws IOException {
        if (processedFiles.isEmpty()) {
            throw new FileNotFoundException("No processed files available for download.");
        }

        // Clear the processed files and original file names after download
        List<File> filesToDownload = new ArrayList<>(processedFiles); // Save the files to be downloaded
        List<String> namesToDownload = new ArrayList<>(originalFileNames); // Save the names to be downloaded
        processedFiles.clear(); // Clear processed files list
        originalFileNames.clear(); // Clear original file names list

        if (filesToDownload.size() == 1) {
            // Single file: Return the file directly
            File processedFile = filesToDownload.get(0);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(processedFile));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + namesToDownload.get(0) + "_processed.csv")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(processedFile.length())
                    .body(resource);
        } else {
            // Multiple files: Create a ZIP file
            File zipFile = File.createTempFile("processed_files", ".zip");
            try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
                for (int i = 0; i < filesToDownload.size(); i++) {
                    File processedFile = filesToDownload.get(i);
                    String zipEntryName = namesToDownload.get(i) + "_processed_" + i + ".csv"; // Unique name

                    try (FileInputStream fis = new FileInputStream(processedFile)) {
                        ZipEntry zipEntry = new ZipEntry(zipEntryName);
                        zipOut.putNextEntry(zipEntry);

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            zipOut.write(buffer, 0, length);
                        }
                        zipOut.closeEntry();
                    }
                }
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=processed_files.zip")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(zipFile.length())
                    .body(resource);
        }
    }


}
