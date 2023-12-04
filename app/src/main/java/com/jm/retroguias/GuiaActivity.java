package com.jm.retroguias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuiaActivity extends AppCompatActivity {

    TextView title_textView,platform_textView, company_textView;

    private Button volver_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia);
        Intent intent = getIntent();

        title_textView = (TextView) findViewById(R.id.guia_title);
        platform_textView = (TextView) findViewById(R.id.guia_platform);
        company_textView = (TextView) findViewById(R.id.guia_company);
        volver_button = (Button) findViewById(R.id.guides_volver_button);

        title_textView.setText(intent.getStringExtra("guideName"));
        platform_textView.setText(intent.getStringExtra("platformName"));
        company_textView.setText(intent.getStringExtra("companyName"));
        
        // Ir a LoginActivity
        volver();
    } // Fin onCreate


    /**
     * Vuelve a GuidesActivity
     */
    private void volver()
    {
        volver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuiaActivity.this, GuidesActivity.class));
            }
        });
    }


}