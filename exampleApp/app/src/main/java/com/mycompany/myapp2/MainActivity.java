package com.mycompany.myapp2;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private MaterialButton mButton;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                    Snackbar.make(p1,"This is a Sackbar!",Snackbar.LENGTH_LONG).show();
                    
                }
            });
        
        
    }


}
