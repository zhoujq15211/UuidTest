package lib.zhoujq.com.uuidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText text = findViewById(R.id.text);
        text.setKeyListener(null);
        text.setText(AppTool.getPhoneName() + "\n" + AppTool.getPhoneModel() + "\n" + AppTool.createDeviceUUID(this));
    }
}
