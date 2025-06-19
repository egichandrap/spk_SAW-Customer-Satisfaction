package org.spk.service;

import org.spk.model.CustomerFeedback;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExportService {
    // Menyimpan hasil ranking ke file CSV
    public static void exportToCSV(List<Map.Entry<CustomerFeedback, Double>> rankedList, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Rank,Nama,Skor\n"); // header CSV

            int rank = 1;
            for (Map.Entry<CustomerFeedback, Double> entry : rankedList) {
                CustomerFeedback fb = entry.getKey();
                double score = entry.getValue();
                // Tulis data ke file CSV
                writer.append(rank++ + "," + fb.customerName + "," + String.format("%.4f", score) + "\n");
            }

            writer.flush(); // pastikan data dikirim
        } catch (IOException e) {
            e.printStackTrace(); // tampilkan error jika gagal
        }
    }
}
