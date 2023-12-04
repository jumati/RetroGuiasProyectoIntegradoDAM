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

        title_textView = (TextView) findViewById(R.id.guia_title);
        platform_textView = (TextView) findViewById(R.id.guia_platform_text);
        company_textView = (TextView) findViewById(R.id.guia_company);
        volver_button = (Button) findViewById(R.id.guides_volver_button);

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