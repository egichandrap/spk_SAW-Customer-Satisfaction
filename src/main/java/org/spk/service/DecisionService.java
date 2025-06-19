package org.spk.service;

import org.spk.model.CustomerFeedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecisionService {

    // Bobot untuk masing-masing kriteria sesuai perbandingan kepentingan
    private static final double WEIGHT_QUALITY = 0.3;
    private static final double WEIGHT_SPEED = 0.25;
    private static final double WEIGHT_PRICE = 0.2;
    private static final double WEIGHT_CUSTOMER_SERVICE = 0.25;

    // Hitung skor semua pelanggan berdasarkan metode SAW
    public static Map<CustomerFeedback, Double> calculateSAWScores(List<CustomerFeedback> feedbackList) {
        // Mencari nilai maksimum untuk tiap kriteria (karena semua benefit)
        double maxQuality = feedbackList.stream().mapToInt(f -> f.productQuality).max().orElse(1);
        double maxSpeed = feedbackList.stream().mapToInt(f -> f.serviceSpeed).max().orElse(1);
        double maxPrice = feedbackList.stream().mapToInt(f -> f.price).max().orElse(1);
        double maxCS = feedbackList.stream().mapToInt(f -> f.customerService).max().orElse(1);

        Map<CustomerFeedback, Double> scoreMap = new HashMap<>();

        // Normalisasi dan kalkulasi skor total
        for (CustomerFeedback f : feedbackList) {
            double score = (f.productQuality / (double) maxQuality) * WEIGHT_QUALITY
                    + (f.serviceSpeed / (double) maxSpeed) * WEIGHT_SPEED
                    + (f.price / (double) maxPrice) * WEIGHT_PRICE
                    + (f.customerService / (double) maxCS) * WEIGHT_CUSTOMER_SERVICE;

            scoreMap.put(f, score);
        }

        return scoreMap;
    }

    // Meranking pelanggan berdasarkan skor SAW
    public static List<Map.Entry<CustomerFeedback, Double>> rank(List<CustomerFeedback> list) {
        Map<CustomerFeedback, Double> scoreMap = calculateSAWScores(list);
        List<Map.Entry<CustomerFeedback, Double>> entries = new ArrayList<>(scoreMap.entrySet());
        entries.sort((a, b) -> Double.compare(b.getValue(), a.getValue())); // descending
        return entries;
    }
}
