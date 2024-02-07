package fi.arcada.android;

import java.lang.reflect.Array;

public class RegressionLine {

    // deklarera k, m, x  och correlationCoefficient som double
    // correlationCoefficient = r
    // minstakvadratmetod = k
    // m och x?
    double k, m,x, correlationCoefficient;

    // Skapa en konstruktor som tar emot data-arrays för x och y
    public RegressionLine (double[] xData, double[] yData) {
        // Uträkningen för k och m kan ske i konstruktorn m.h.a.
        // formeln för minsta kvadratmetoden
        double sumX = 0;
        double sumY = 0;

        double n = 26;
        double sumXY = 0;
        double sumPowX = 0;
        double sumXPow = 0;

        double yAvg;
        double xAvg;


        double sumPowY = 0;
        double sumYPow = 0;

        //Här räknas sumX
        for (int i = 0; i <xData.length ; i++) {
            sumX = sumX + xData[i];
        }
        //Här räknas sumY
        for (int i = 0; i <yData.length ; i++) {
            sumY = sumY + yData[i];
        }

        //Här räknas sumXY
        for (int i = 0; i <xData.length ; i++) {
            sumXY = sumXY + (xData[i]*yData[i]);
        }

        //Här räknas sumPowX
        for (int i = 0; i <xData.length ; i++) {
            sumPowX = sumPowX + Math.pow(xData[i], 2);
        }

        //Här räknas sumPowY
        for (int i = 0; i <yData.length ; i++) {
            sumPowY = sumPowY + Math.pow(yData[i], 2);
        }
        //Här räknas sumYPow
        sumYPow = Math.pow(sumY, 2);

        //Här räknas sumXPow
        sumXPow = Math.pow(sumX, 2);


        //Här räknas k
        k = (n*sumXY-sumX*sumY) / (n*sumPowX-sumXPow);

        //Här räknas m
        yAvg = sumY / yData.length;
        xAvg = sumX / xData.length;

        m = yAvg  - k*xAvg;


        // Del 3: uträkningen för correlationCoefficient kan också ske i konstruktorn
        // (det är förstås också ok att ha en skild metod för uträknigarna om man vill
        // hålla konstruktorn simpel.)
        double sqrt = (Math.sqrt(Math.abs(n*sumPowX-sumXPow) * Math.abs(n*sumPowY - sumYPow)));
        correlationCoefficient = (n*(sumXY) - (sumX * sumY)) / sqrt;


    }
    // skapa en metod getX som tar emot ett y-värde, räknar ut x
    // m.h.a. räta linjens ekvation y=kx+m, och returnerar x
    public double getX (double yValue) {

        x = (yValue-m)/k;
        return x;

    }


    // Del 3:
    // - skapa en getter-metod för correlationCoefficient
    public double getCorrelationCoefficient(double yValue) {
    //correlationCoefficient = (yValue - r) / k;
      return correlationCoefficient;
    }

    // - skapa en String-metod getCorrelationGrade() för uträkning av korrelationsgrad
    public String getCorrelationGrade(double correlationCoefficient){
        String p = "ingen";

        if (correlationCoefficient >= 0.75 && correlationCoefficient < 1) p ="hög" ;
        if (correlationCoefficient >= 1) p = "perfekt";
        if (correlationCoefficient < 0.75 && correlationCoefficient >= 0.25  ) p ="måttlig";
        if (correlationCoefficient < 0.25 && correlationCoefficient > 0.00) p = "låg";
        if (correlationCoefficient <= 0.00) p = "ingen";

        return p;
    }

}
