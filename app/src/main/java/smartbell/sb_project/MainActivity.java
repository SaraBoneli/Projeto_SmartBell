package smartbell.sb_project;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        //ImageButton btn_ok = findViewById(R.id.btn_ok);
        //btn_ok.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //EditText link_imgur = (EditText) findViewById(R.id.link_imgur);
                //String linkimgur = link_imgur.getText().toString();

        Toolbar toolbar = findViewById(R.id.tb_main);

        ImageView imgfoto = findViewById(R.id.img_foto);
        int imgHeight = (int) MainActivity.this.getResources().getDimension(R.dimen.img_height);
        ImageCache.loadImageUrlToImageView(MainActivity.this, imgfoto,-1, imgHeight);//tamanho da iamgem da primeira tela

        //Button provavelmente vai precisar de um intent
        //TextView
    }
        //});
}
