package fi.arcada.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextAge;
    TextView textViewCalcOut;

    RecyclerView recyclerViewPersons;
    PersonsAdapter personsAdapter;

    ArrayList<Double> dataset = new ArrayList<>();
    ArrayList<String> dataLabels = new ArrayList<>();
    ArrayList<Person> persons = new ArrayList<>();

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);

        textViewCalcOut = findViewById(R.id.textViewCalcOut);

        recyclerViewPersons = findViewById(R.id.recyclerViewPersons);
        personsAdapter = new PersonsAdapter(this, persons);
        recyclerViewPersons.setAdapter(personsAdapter);
        recyclerViewPersons.setLayoutManager(new LinearLayoutManager(this));


    }

    public void addPerson(View view) {

        if (TextUtils.isEmpty(editTextName.getText()) || TextUtils.isEmpty(editTextAge.getText())) {

            Toast message = Toast.makeText(this, "Fyll i båda fälten!", Toast.LENGTH_SHORT);
            message.setGravity(Gravity.TOP, 0, 100);
            message.show();
        } else {

            String personName = editTextName.getText().toString();
            int personAge = Integer.parseInt(editTextAge.getText().toString());


            editTextName.setText("");
            editTextAge.setText("");

           // Person person = new Person(personName, personAge);
            //persons.add(person);
            persons.add(new Person(personName, personAge));
            personsAdapter.notifyItemInserted(persons.size()-1);
        }
    }

    public void calculate(View view){

        ArrayList<Double> dataset = new ArrayList<>();

        for (Person person : persons) {
            dataset.add((double)person.getAge());
        }


        Double avg = Statistics.calcAverage(dataset);
        textViewCalcOut.setText(String.format("%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f",
                "Medelvärde", Statistics.calcAverage(dataset),
                "Medianvärde", Statistics.calcMedian(dataset),
                "Standardavvikelse", Statistics.calcSD(dataset),
                "Typvärde", Statistics.calcMode(dataset),
                "Min-värde", Statistics.calcMin(dataset),
                "Max-värde", Statistics.calcMax(dataset),
                "Nedre kvartilen", Statistics.calcLQ(dataset),
                "Övre kvartilen", Statistics.calcUQ(dataset),
                "Kvartilavståndet", Statistics.calcQR(dataset)
        ));
    }


}