package es.ucm.fdi.pad.android01;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button suma;
    EditText editTextNumberDecimal1;
    EditText editTextNumberDecimal2;

/*

Me faltan añadir logs de:
    - w que son para advertencias
    - e que son para errores
    - wtf que son para situaciones nunca deberían ocurrir
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumberDecimal1 = (EditText) findViewById(R.id.editTextNumberDecimal);
        editTextNumberDecimal2 = (EditText) findViewById(R.id.editTextNumberDecimal2);
        suma = (Button) findViewById(R.id.button);
        Log.d(TAG, "Referencias asignadas a los elementos de la interfaz"); //para mensajes dedepuraciós
        suma.setOnClickListener(view -> {
            Log.v(TAG, "Botón suma pulsado"); //para mensajes internos, cosas detalladas
            Intent intent = new Intent(this, CalculatorAddResultActivity.class);
            intent.putExtra("decimal1", editTextNumberDecimal1.getText().toString());
            intent.putExtra("decimal2", editTextNumberDecimal2.getText().toString());
            startActivity(intent);
        });

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }






}