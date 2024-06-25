package smartbell.sb_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ViewVisitaActivity extends AppCompatActivity {

    Visita visita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visita);



        // Para obter os detalhes do produto, a app envia o id do produto ao servidor web. Este
        // último responde com os detalhes do produto referente ao pid.

        // O pid do produto é enviado para esta tela quando o produto é clicado na tela de Home.
        // Aqui nós obtemos o pid.
        Intent i = getIntent();
        String img = i.getStringExtra("img");
        String date = i.getStringExtra("date");


        // aqui nós obtemos a imagem do produto. A imagem não vem logo de cara. Primeiro
        // obtemos os detalhes do produto. Uma vez recebidos os campos de id, nome, preço,
        // descrição, criado por, usamos o id para obter a imagem do produto em separado.
        // A classe ImageCache obtém a imagem de um produto específico, guarda em um cache
        // o seta em um ImageView fornecido.
        ImageView imvVisitaPhoto = findViewById(R.id.img_nova);
        int imgHeight = (int) ViewVisitaActivity.this.getResources().getDimension(R.dimen.img_height);
        ImageCache.loadImageUrlToImageView(ViewVisitaActivity.this, img, imvVisitaPhoto, -1, imgHeight);

        TextView dateVisita = findViewById(R.id.data_img);
        dateVisita.setText(visita.data);



    }
}
