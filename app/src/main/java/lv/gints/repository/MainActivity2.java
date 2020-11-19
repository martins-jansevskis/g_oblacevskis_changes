package lv.gints.repository;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity2 extends AppCompatActivity {
    private static final String FILE_NAME = "savedtext.txt";
    EditText myEditText;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myEditText = findViewById(R.id.myEditText);
    }

    public void load(View v) {
        myEditText.getText().clear();
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            int TextLenght= 0;
            while ((text = br.readLine()) != null ) {
                sb.append(text).append("\n");
            }
            TextLenght = sb.toString().length();
            if  (TextLenght > 0) myEditText.setText(sb.toString());
                else {
                    if (toast != null) toast.cancel();
                    toast = Toast.makeText(this, "Nothing found", Toast.LENGTH_SHORT);
                    toast.show();
            }
            //deleteFile(FILE_NAME);
        } catch (FileNotFoundException e) {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(this, "Nothing found", Toast.LENGTH_SHORT);
            toast.show();
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
    public void goback(View v) {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        intent.putExtra("activity_2_msg", "I clicked the button");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}