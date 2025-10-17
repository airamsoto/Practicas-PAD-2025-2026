package es.ucm.fdi.pad.googlebooksclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.ConnectivityManager;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int BOOK_LOADER_ID = 1;
    private String queryString = "";
    private String printType = "";
    private EditText editTextTitulo;
    private EditText editTextAutor;
    private TextView resultText;
    private RadioButton selectedButton;
    private BooksResultListAdapter booksResultListAdapter;

    private final BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if (loaderManager.getLoader(BOOK_LOADER_ID) != null) {
            loaderManager.initLoader(BOOK_LOADER_ID, null, bookLoaderCallbacks);
        }

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextAutor = findViewById(R.id.editTextAutor);
        resultText = findViewById(R.id.resultView);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        selectedButton = findViewById(R.id.radioButtonLibro);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            selectedButton = findViewById(checkedId);
            if (selectedButton == findViewById(R.id.radioButtonRevista)) {
                editTextAutor.setVisibility(View.GONE);
                editTextAutor.setText("");
                editTextAutor.setEnabled(false);
            } else {
                editTextAutor.setVisibility(View.VISIBLE);
                editTextAutor.setEnabled(true);
            }
        });

        ImageButton busqueda = findViewById(R.id.imageButton);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        booksResultListAdapter = new BooksResultListAdapter(Collections.emptyList(), this);
        recyclerView.setAdapter(booksResultListAdapter);

        busqueda.setOnClickListener(v -> {
            if (isConnected()) {
                printType = selectedButton.getText().toString();
                Log.i("printType", printType);

                if (selectedButton == findViewById(R.id.radioButtonRevista)) {
                    if (editTextTitulo.getText().toString().isBlank()) {
                        Toast.makeText(MainActivity.this, R.string.error_falta_titulo_revista, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    queryString = "intitle:" + editTextTitulo.getText().toString();
                } else {
                    if (editTextTitulo.getText().toString().isBlank() && editTextAutor.getText().toString().isBlank()) {
                        Log.w(this.getClass().getName(), "Falta de argumentos");
                        Toast.makeText(MainActivity.this, R.string.error_text, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    queryString = "";
                    if (!editTextTitulo.getText().toString().isBlank()) {
                        queryString = "intitle:" + editTextTitulo.getText().toString();
                    }
                    if (!editTextAutor.getText().toString().isBlank()) {
                        if (!queryString.isEmpty()) {
                            queryString += "+inauthor:" + editTextAutor.getText().toString();
                        } else {
                            queryString = "inauthor:" + editTextAutor.getText().toString();
                        }
                    }
                }

                searchBooks(v);
                queryString = "";
            } else {
                Toast.makeText(MainActivity.this, R.string.error_sin_conexion_busqueda, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isConnected() {
        Log.i(this.getClass().getName(), "Se comprueba la conexión a Internet antes de realizar la búsqueda.");
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connMgr.getActiveNetwork();
        NetworkCapabilities networkCap = connMgr.getNetworkCapabilities(network);
        return (networkCap != null);
    }

    public void searchBooks(View view) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        resultText.setText(R.string.cargando_string);
        LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateBooksResultList(List<BookInfo> books) {
        booksResultListAdapter.setBooksData(books);
        if (books == null) {
            resultText.setText(R.string.nullResultado_string);
        } else {
            resultText.setText(R.string.resultado_string);
        }
        booksResultListAdapter.notifyDataSetChanged();
    }
}

