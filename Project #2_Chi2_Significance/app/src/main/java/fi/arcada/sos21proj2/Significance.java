package fi.arcada.sos21proj2;

public class Significance {

    /**
     * Metod som räknar ut Chi-två på basis av fyra observerade värden (o1 - o4).
     */
    public static double chiSquared(int o1, int o2, int o3, int o4) {

        // heltalsvariabler tänkta att få de förväntade värdena OBS! Skall här inte vara en double eftersom förväntade värden kan ha decimaler i sig? Visserligen kan man
        // avrunda men förvränger det inte isåfall resultatet? ändrade alltså int -> double
        double e1, e2, e3, e4, eTotal;

        /**
         *  Implementera din egen Chi-två-uträkning här!
         *
         *  1.  Räkna de förväntade värdena, spara resultaten i e1 - e4 DONE
         *
         *  2.  Använd de observerade värdena (o1 - o4) och de förväntade
         *      värdena (e1 - e4) för att räkna ut Chi-två enligt formeln.
         *
         *  3.  returnera resultatet
         *      (använd det sedan för att få p-värdet via getP()
         *
         * */

        //1.  Räkna de förväntade värdena, spara resultaten i e1 - e4
        eTotal = o1 + o2 + o3 + o4;

        e1 = ((o1 + o2) * (o1 + o3))/eTotal;

        e2 = ((o1 + o2) * (o2 + o4))/eTotal;

        e3 = ((o3 + o4) * (o1 + o3))/eTotal;

        e4 = ((o3 + o4) * (o2 + o4))/eTotal;

        //2.  Använd de observerade värdena (o1 - o4) och de förväntade

        double chi1, chi2, chi3, chi4, chiResult;

        chi1 = Math.pow(o1-e1, 2)/e1;
        chi2 = Math.pow(o2-e2, 2)/e2;
        chi3 = Math.pow(o3-e3, 2)/e3;
        chi4 = Math.pow(o4-e4, 2)/e4;

        chiResult = chi1 + chi2 + chi3 + chi4;

        return chiResult;
    }


    /**
     * Metod som tar emot resultatet från Chi-två-uträkningen
     * och returnerar p-värde enligt tabellen (en frihetsgrad)
     * (De mest extrema värdena har lämnats bort, det är ok för våra syften)
     *
     * exempel: getP(2.82) returnerar ett p-värde på 0.1
     *
     */
    public static double getP(double chiResult) {

        double p = 0.99;

        if (chiResult >= 1.642) p = 0.2;
        if (chiResult >= 2.706) p = 0.1;
        if (chiResult >= 3.841) p = 0.05;
        if (chiResult >= 5.024) p = 0.025;
        if (chiResult >= 5.412) p = 0.02;
        if (chiResult >= 6.635) p = 0.01;
        if (chiResult >= 7.879) p = 0.005;
        if (chiResult >= 9.550) p = 0.002;

        return p;

    }

}
