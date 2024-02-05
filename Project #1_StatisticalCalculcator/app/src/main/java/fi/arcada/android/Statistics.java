package fi.arcada.android;

import androidx.core.content.res.TypedArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Statistics {

    public static Double calcAverage(ArrayList<Double> dataValues){

        Double sum = 0.0;

        for (int i = 0; i< dataValues.size();i++){
            sum = sum + dataValues.get(i);
        }
        return sum / dataValues.size();
    }
    public static ArrayList<Double> getSorted(ArrayList<Double> dataValues){
      ArrayList<Double> sorted = new ArrayList<>(dataValues);
        Collections.sort(sorted);
        return sorted;
    }
    public static Double calcMedian(ArrayList<Double> dataValues) {
        ArrayList<Double> sortedSet = getSorted(dataValues);
        Double median;
        int mid = sortedSet.size()/2;

        if (sortedSet.size() % 2 == 0) {
            median = (sortedSet.get(mid -1) + sortedSet.get(mid)) / 2;
        } else {
           median = sortedSet.get(mid);
        }

        return median;
    }

    //Standardavvikelse
    public static Double calcSD(ArrayList<Double> dataValues){
        double sumDiff = 0;
        double avg = calcAverage(dataValues);
        for (Double dataVal: dataValues) {
            sumDiff += Math.pow(dataVal - avg, 2);
        }
        return Math.sqrt(sumDiff / dataValues.size());
    }



    public static double calcMode (ArrayList<Double> dataValues) {
        HashMap<Double,Integer> valueCount = new HashMap<>();

        for (Double dataValue : dataValues) {
            Integer count = valueCount.get(dataValue);
            valueCount.put(dataValue, (count == null ? 0 : count) +1);
        }
        int maxCount= 0;
        Double modeValue = 0.0;

        for (Double dataValue : valueCount.keySet()) {
            Integer curCount = valueCount.get(dataValue); //T.ex. valueCount.get(1.0)
            if (curCount != null && curCount > maxCount){
                maxCount = curCount;
                modeValue = dataValue;
            }
        }
        return modeValue;
    }

    public static double calcMin (ArrayList<Double> dataValues) {
        ArrayList<Double> sortedSet = getSorted(dataValues);

        double minValue = sortedSet.get(0);
        return minValue;
    }

    public static double calcMax (ArrayList<Double> dataValues) {
        ArrayList<Double> sortedSet = getSorted(dataValues);

        double maxValue = sortedSet.get(sortedSet.size()-1);
        return maxValue;
    }

    //HÄR STARTAR KVARTIL RÄKNINGAR

    public static double calcLQ (ArrayList<Double> dataValues) {
        ArrayList<Double> sortedSet = getSorted(dataValues);
        double LQ;

        int mid = sortedSet.size()/2;
        int q1 = mid /2;

        if (sortedSet.size()/2  % 2 == 0) {
            LQ = ((sortedSet.get(q1 -1) + sortedSet.get(q1)) / 2);

        } else {
            LQ = sortedSet.get(q1);
        }

        return LQ;
    }
    public static double calcUQ (ArrayList<Double> dataValues) {
        ArrayList<Double> sortedSet = getSorted(dataValues);
        Collections.reverse(sortedSet);
        double UQ;

        int mid = sortedSet.size()/2;
        int q3 = mid /2;

        if (sortedSet.size()/2 % 2 == 0) {
            UQ = ((sortedSet.get(q3 -1) + sortedSet.get(q3)) / 2);

        } else {
            UQ = sortedSet.get(q3);
        }

        return UQ;
    }

    public static double calcQR (ArrayList<Double> dataValues) {

        ArrayList<Double> sortedSet = getSorted(dataValues);

            double LQ;
            int mid1 = sortedSet.size() / 2;
            int q1 = mid1 / 2;
            if (sortedSet.size() / 2 % 2 == 0) {
                LQ = ((sortedSet.get(mid1 - 1) + sortedSet.get(mid1)) / 2);
                LQ = Math.round(LQ);
            } else {
                LQ = sortedSet.get(q1);
            }

            Collections.reverse(sortedSet);
            double UQ;

            int mid2 = sortedSet.size() / 2;
            int q3 = mid2 / 2;

            if (sortedSet.size() / 2 % 2 == 0) {
                UQ = ((sortedSet.get(mid2 - 1) + sortedSet.get(mid2)) / 2);
                UQ = Math.round(UQ);
            } else {
                UQ = sortedSet.get(q3);
            }

            double QR = UQ - LQ;

            return QR;


    }

}
