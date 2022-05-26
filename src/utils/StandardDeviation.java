package utils;

import java.util.ArrayList;

public class StandardDeviation {
    public static double calculateSD(ArrayList<Double> workLoads)
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = workLoads.size();
        for(double num : workLoads) {sum += num;}

        double mean = sum/length;

        for(double num: workLoads) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation/length);
    }
}
