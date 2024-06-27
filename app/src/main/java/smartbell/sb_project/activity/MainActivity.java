package smartbell.sb_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import smartbell.sb_project.model.MainActivityViewModel;
import smartbell.sb_project.utils.ImageCache;
import smartbell.sb_project.R;
import smartbell.sb_project.model.Visita;

public class MainActivity extends AppCompatActivity {

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

        Button btnhv = findViewById(R.id.btn_hv);
        btnhv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,HistActivity.class);
                startActivity(i);
            }
        });

        //ImageButton btn_ok = findViewById(R.id.btn_ok);
        //btn_ok.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //EditText link_imgur = (EditText) findViewById(R.id.link_imgur);
                //String linkimgur = link_imgur.getText().toString();



        //Button provavelmente vai precisar de um intent
        //TextView data

        // obtemos o ViewModel pois é nele que está o método que se conecta ao servior web.
        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // O ViewModel possui o método getProductsLD, que obtém páginas/blocos de produtos do servidor
        // web. Cada página contém 10 produtos. Quando o usuário rola a tela, novas páginas de produtos
        // são obtidas do servidor.
        //
        // O método de getProductsLD retorna um LiveData, que na prática é um container que avisa
        // quando o resultado do servidor chegou. Ele guarda a página de produtos que o servidor
        // entregou para a app.
        LiveData<Visita> visitaLd = mainActivityViewModel.loadLastVisita();

        // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado contendo uma página
        // com 10 produtos será guardado dentro do LiveData. Neste momento o
        // LiveData avisa que uma nova página de produtos chegou chamando o método onChanged abaixo.
        visitaLd.observe(this, new Observer<Visita>() {

            @Override
            public void onChanged(Visita visita) {

                ImageView imgfoto = findViewById(R.id.img_foto);
                int imgHeight = (int) MainActivity.this.getResources().getDimension(R.dimen.img_height);
                ImageCache.loadImageUrlToImageView(MainActivity.this, visita.img, imgfoto,-1, imgHeight);//tamanho da iamgem da primeira tela

                TextView data = findViewById(R.id.tv_date);
                data.setText(visita.data);
            }
        });
    }
        //});
}
