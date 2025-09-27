package es.ucm.fdi.pad.android01;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorAddResultActivity extends AppCompatActivity {

    EditText resultadoText;
    Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator_add_result);

        volver = (Button) findViewById(R.id.button2);
        volver.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        Intent intent = getIntent();
        String decimal1 = intent.getStringExtra("decimal1");
        String decimal2 = intent.getStringExtra("decimal2");

        TextView var1 = (EditText) findViewById(R.id.editTextNumberDecimal3);
        TextView var2 = (EditText) findViewById(R.id.editTextNumberDecimal4);

        var1.setText(decimal1);
        var2.setText(decimal2);

        double resultado = CalculatorAdd.add(Double.parseDouble(decimal1), Double.parseDouble(decimal2));
        Log.i(TAG, "Suma realizada"); //para eventos importantes como esta suma

        resultadoText = (EditText) findViewById(R.id.editTextNumberDecimal5);

        resultadoText.setText(String.valueOf(resultado));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
    }

}