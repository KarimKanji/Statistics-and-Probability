package fi.arcada.sos21proj2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //Text
    TextView textOut;
    //Stat out
    TextView statString;
    TextView col1StatOut;
    TextView col2StatOut;

    //Prefs
    SharedPreferences prefs;
    SharedPreferences.Editor editor;



    //Under input knapparna
    TextView chi2Resultat;
    TextView signifikansniva;
    TextView pvarde;

    //Cols och rows
    TextView textViewRow1;
    TextView textViewRow2;
    TextView textViewCol1;
    TextView textViewCol2;

    // Deklarera 4 Button-objekt
    Button btn1, btn2, btn3, btn4, btnReset;
    // Deklarera 4 heltalsvariabler för knapparnas värden
    int val1, val2, val3, val4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Stat out koppling
        statString = findViewById(R.id.statString);
        col1StatOut = findViewById(R.id.col1StatOut);
        col2StatOut = findViewById(R.id.col2StatOut);

        //Test
        textOut = findViewById(R.id.textOut);


        //Preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);



        //Koppla samman rows och cols
        textViewRow1 = findViewById(R.id.textViewRow1);
        textViewRow2 = findViewById(R.id.textViewRow2);
        textViewCol1 = findViewById(R.id.textViewCol1);
        textViewCol2 = findViewById(R.id.textViewCol2);

        //Kopplar ihop anda textViews
        chi2Resultat = findViewById(R.id.chi2Resultat);
        signifikansniva = findViewById(R.id.signifikansniva);
        pvarde = findViewById(R.id.pvarde);

        // Koppla samman Button-objekten med knapparna i layouten
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);

        btnReset = findViewById(R.id.reset);

        //setText
        textViewCol1.setText(prefs.getString("textViewCol1", ""));
        textViewCol2.setText(prefs.getString("textViewCol2", ""));
        textViewRow1.setText(prefs.getString("textViewRow1", ""));
        textViewRow2.setText(prefs.getString("textViewRow2", ""));

        //Set signifikansnivå
        String signifikans = prefs.getString("signifikansniva", "");
        double signifikansDouble = Double.parseDouble(signifikans);
        signifikansniva.setText(String.format("%.03f", signifikansDouble));

        //experiment
        editor = prefs.edit();

        val1 = prefs.getInt("val1", 0);
        editor = prefs.edit();
        val2 = prefs.getInt("val2", 0);
        editor = prefs.edit();
        val3 = prefs.getInt("val3", 0);
        editor = prefs.edit();
        val4 = prefs.getInt("val4", 0);
        editor = prefs.edit();

        calculate();

    }

    /**
     *  Klickhanterare för knapparna
     */
    public void buttonClick(View view) {

        // Skapa ett Button-objekt genom att type-casta (byta datatyp)
        // på det View-objekt som kommer med knapptrycket
        Button btn = (Button) view;

        // Kontrollera vilken knapp som klickats, öka värde på rätt vaiabel
        if (view.getId() == R.id.button1) val1++;
        if (view.getId() == R.id.button2) val2++;
        if (view.getId() == R.id.button3) val3++;
        if (view.getId() == R.id.button4) val4++;



        // Slutligen, kör metoden som ska räkna ut allt!
        calculate();
    }

    /**
     * Metod som uppdaterar layouten och räknar ut själva analysen.
     */

    public void calculate() {



        editor.putInt("val1", val1);
        editor.putInt("val2", val2);
        editor.putInt("val3", val3);
        editor.putInt("val4", val4);
        editor.apply();

        // Uppdatera knapparna med de nuvarande värdena

        btn1.setText(String.valueOf(val1));
        btn2.setText(String.valueOf(val2));
        btn3.setText(String.valueOf(val3));
        btn4.setText(String.valueOf(val4));

        //show();

        // Mata in värdena i Chi-2-uträkningen och ta emot resultatet
        // i en Double-variabel
        double chi2 = Significance.chiSquared(val1, val2, val3, val4);

        // Mata in chi2-resultatet i getP() och ta emot p-värdet
        double pValue = Significance.getP(chi2);

        /**
         *  - Visa chi2 och pValue åt användaren på ett bra och tydligt sätt!
         *
         *  - Visa procentuella andelen jakande svar inom de olika grupperna.
         *    T.ex. (val1 / (val1+val3) * 100) och (val2 / (val2+val4) * 100
         *
         *  - Analysera signifikansen genom att jämföra p-värdet
         *    med signifikansnivån, visa reultatet åt användaren
         *
         */

        //- Visa chi2 och pValue åt användaren på ett bra och tydligt sätt!
        chi2Resultat.setText(String.format("%s: %.2f",
                "Chi-2 resultat: ", chi2
                ));
        pvarde.setText(String.format("%s: %.3f",
                "P-värde: " , pValue));

        //- Visa procentuella andelen jakande svar inom de olika grupperna.
        statString.setText(prefs.getString("textViewRow1", ""));
        double val1D = val1;
        double val3D = val3;
        double col1Stat = (val1D / (val1D+val3D)) * 100;


        col1StatOut.setText(String.format("%s: %.0f %s",
                prefs.getString("textViewCol1", ""), col1Stat, "%"
        ));


        double val2D = val2;
        double val4D = val4;
        double col2Stat = (val2D / (val2D+val4D)) * 100;


        col2StatOut.setText(String.format("%s: %.0f %s",
                prefs.getString("textViewCol2", ""), col2Stat, "%"
        ));

       String signifikans = prefs.getString("signifikansniva", "");
       double signifikansDouble = Double.parseDouble(signifikans);

        if (pValue<signifikansDouble) {

            //Bestämmer texten
            double pVardePros = pValue * 100;
            double pVardePros100 = 100 - pVardePros;
            textOut.setText(String.format("%s %.1f %s",
                    "Resultatet är med ",
                    pVardePros100,
                    "% sannolikhet inte oberoende och kan betraktas som signifikant. Ett värde över signifikansnivån betyder att du kan förkasta nollhypotesen eftersom ditt resultat är med stor sannolikhet signifikant"
            ));
        } else if (pValue>signifikansDouble){
            //Bestämmer texten
            double pVardePros = pValue * 100;
            double pVardePros100 = 100 - pVardePros;
            textOut.setText(String.format("%s %.1f %s",
                    "Resultatet är med ",
                    pVardePros100,
                    "% sannolikhet inte oberoende och kan betraktas som  inte signifikant. Ett värde under signifikans nivån betyder att sannolikheten för att nollhypotesen fortfarande är sann"
            ));
        }




    }

    //Här kommer settings metoden
    public void btnSettings (View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //Reset knappen

    public void reset (View view) {
        val1 = 0;
        val2 = 0;
        val3 = 0;
        val4 = 0;
        calculate();
    }


    }