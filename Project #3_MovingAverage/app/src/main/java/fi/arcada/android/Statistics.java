package fi.arcada.android;

import android.widget.TextView;

import java.util.ArrayList;



public class Statistics {


    public static ArrayList<Double> movingAvg(ArrayList<Double> dataset, int window) {

        ArrayList<Double> betterMa = new ArrayList<>();

        for (int i = window-1; i < dataset.size(); i++) {
            double sum = 0;

            for (int j = 0; j < window; j++) {
                sum += dataset.get(i-j);
            }

            betterMa.add(sum / window);
        }

        return betterMa;
    }

}
