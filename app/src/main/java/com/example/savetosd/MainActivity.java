package com.example.savetosd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    String filename = "yourdata";
    Button writeBtn,readBtn;
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        readBtn = findViewById(R.id.readBtn);
        writeBtn = findViewById(R.id.writeBtn);
        textView = findViewById(R.id.textView);
        textView.setVisibility(View.GONE);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    write();
            }
        });

    }

    private void write(){
        String state;
        state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)){
            File sdCard = Environment.getExternalStorageDirectory();
            File Dir = new File(sdCard.getAbsolutePath()+"/MyAppFile");
            if (!Dir.exists()){
                Dir.mkdir();
            }
            File file = new File(Dir,"MyMessage.txt");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                String Message = editText.getText().toString();
                fileOutputStream.write(Message.getBytes());
                fileOutputStream.close();
                editText.setText(" ");
                Toast.makeText(getApplicationContext(),"Message saved to SDCard: ",Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(getApplicationContext(),"SD card not found ",Toast.LENGTH_SHORT).show();
        }
    }

    public void readExternal(View view){
        File sdCard = Environment.getExternalStorageDirectory();
        File Dir = new File(sdCard.getAbsolutePath()+"/MyAppFile");
        File file = new File(Dir,"MyMessage.txt");
        String Message;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((Message=bufferedReader.readLine())!=null){
                stringBuffer.append(Message+"\n");
            }
            textView.setText(stringBuffer.toString());
            textView.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
