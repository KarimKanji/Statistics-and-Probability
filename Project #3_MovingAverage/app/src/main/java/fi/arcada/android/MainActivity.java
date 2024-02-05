package fi.arcada.android;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    GraphView graph;

    /* en arrayList i vilken växelkursvärdena sparas */
    ArrayList<Double> currencyValues;

    EditText from;
    EditText to;
    EditText window;
    String from1 = "2015-01-01";
    String to1 = "2020-03-01";
    String currency = "SEK";
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.valuta, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        window = findViewById(R.id.window);
        String window1 = window.getText().toString();
        int windowInt = Integer.parseInt(window1);

        /* Använd currencyValues för att bygga upp grafen   */
        currencyValues = getCurrencyValues();

        graph = findViewById(R.id.graph);
        buildGraph(graph, Statistics.movingAvg(currencyValues, windowInt));
        buildGraph2(graph, currencyValues);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /* Färdig metod som hämtar växelkurserna från CurrencyApi klassen */
    public ArrayList<Double> getCurrencyValues() {


        CurrencyApi api = new CurrencyApi();
        ArrayList<Double> currencyData = null;

        try {
            String jsonData = api.execute(String.format("https://api.exchangeratesapi.io/history?start_at=%s&end_at=%s&symbols=%s",
                    from1.trim(),
                    to1.trim(),
                    currency.trim()
            )).get();

            if (jsonData != null) {
                currencyData = api.getCurrencyData(jsonData, currency.trim());
                Toast.makeText(getApplicationContext(), String.format("Hämtade %s valutakursvärden från servern", currencyData.size()), Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Kunde inte hämta växelkursdata från servern: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return currencyData;
    }
    


    public void buildGraph(GraphView graph, ArrayList<Double> dataset) {

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        series.setColor(Color.RED);

        window = findViewById(R.id.window);
        String window1 = window.getText().toString();
        int windowInt = Integer.parseInt(window1);

        for (int i = 0; i < dataset.size(); i++) {
            series.appendData(new DataPoint(i+windowInt, dataset.get(i)),true, dataset.size());
        }
        graph.removeAllSeries();
        graph.addSeries(series);
    }

    public void buildGraph2(GraphView graph, ArrayList<Double> dataset) {
        //graph.getViewport().setYAxisBoundsManual(true);
        //graph.getViewport().setMinY(9.2);
       // graph.getViewport().setMaxY(10.8);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(dataset.size());

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        series.setColor(Color.BLUE);

        for (int i = 0; i < dataset.size(); i++) {
            series.appendData(new DataPoint(i, dataset.get(i)),true, dataset.size());
        }


        graph.addSeries(series);
    }

    public void btnClick (View view){
        refresh();
    }
    public void refresh(){
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);

        window = findViewById(R.id.window);
        String window1 = window.getText().toString();
        int windowInt = Integer.parseInt(window1);

        currency = spinner.getSelectedItem().toString();
        from1 = from.getText().toString();
        to1 = to.getText().toString();
        currencyValues = getCurrencyValues();
        buildGraph(graph, Statistics.movingAvg(currencyValues, windowInt));
        buildGraph2(graph, currencyValues);
    }
}