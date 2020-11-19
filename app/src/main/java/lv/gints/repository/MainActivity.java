package lv.gints.repository;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String FILE_NAME = "savedtext.txt";
    EditText myEditText;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myEditText = findViewById(R.id.myEditText);
        loadText();

        Spinner ThemeSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> ThemeAdapter = new ArrayAdapter<String>(MainActivity.this,
                R.layout.color_spinner_layout, getResources().getStringArray(R.array.ThemeArray));
        ThemeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        ThemeSpinner.setAdapter(ThemeAdapter);
        ThemeSpinner.setOnItemSelectedListener(this);
    }

    public void loadText() {
        myEditText.getText().clear();
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null ) {
                sb.append(text).append("\n");
            }
            myEditText.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void save(View v) {
        String text = myEditText.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
            //myEditText.getText().clear();
            if (toast != null) toast.cancel();
            toast = Toast.makeText(this, "Saglabāts " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_SHORT);
            toast.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void goToSecond(View v) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                setTheme(R.style.Theme1);
                break;
            case 1:
                setTheme(R.style.Theme3);
                break;
            case 2:
                setTheme(R.style.Theme2);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}