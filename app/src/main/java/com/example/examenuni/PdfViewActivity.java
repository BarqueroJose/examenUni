package com.example.examenuni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

public class PdfViewActivity extends AppCompatActivity {

    PDFView pdfView;
    TextView setText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        pdfView = findViewById(R.id.pdfView);
        setText = findViewById(R.id.set);

        int pos = getIntent().getIntExtra("position",0);

        if (pos==0){
            pdfView.fromAsset("¿Cómo vives.pdf").load();
            setText.setText("¿Cómo vives?");
        }
        else if(pos ==1){

            pdfView.fromAsset("El-Arte-de-Resolver-Problemas-Ackoff.pdf").load();
            setText.setText("El Arte de Resolver Problemas");
        }

    }
}