package smartbell.sb_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistActivity extends AppCompatActivity {

    static int ADD_PRODUCT_ACTIVITY_RESULT = 1;

    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Aqui configuramos o RecycleView
        RecyclerView rvVisitas = findViewById(R.id.rvVisitas);
        rvVisitas.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvVisitas.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(this, new VisitasComparator());
        rvVisitas.setAdapter(myAdapter);

        // obtemos o ViewModel pois é nele que está o método que se conecta ao servior web.
        HistViewModel histViewModel = new ViewModelProvider(this).get(HistViewModel.class);

        // O ViewModel possui o método getProductsLD, que obtém páginas/blocos de produtos do servidor
        // web. Cada página contém 10 produtos. Quando o usuário rola a tela, novas páginas de produtos
        // são obtidas do servidor.
        //
        // O método de getProductsLD retorna um LiveData, que na prática é um container que avisa
        // quando o resultado do servidor chegou. Ele guarda a página de produtos que o servidor
        // entregou para a app.
        LiveData<PagingData<Visita>> visitasLd = histViewModel.getVisitasLd();

        // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado contendo uma página
        // com 10 produtos será guardado dentro do LiveData. Neste momento o
        // LiveData avisa que uma nova página de produtos chegou chamando o método onChanged abaixo.
        visitasLd.observe(this, new Observer<PagingData<Visita>>() {
            /**
             * Esse método é chamado sempre que uma nova página de produtos é entregue à app pelo
             * servidor web.
             * @param visitasPagingData contém uma página de produtos
             */
            @Override
            public void onChanged(PagingData<Visita> visitasPagingData) {

                // Adiciona a nova página de produtos ao Adapter do RecycleView. Isso faz com que
                // novos produtos apareçam no RecycleView.
                myAdapter.submitData(getLifecycle(),visitasPagingData);
            }
        });


    }
    public void startViewVisitasAcitivity(String img,String data) {
        Intent i = new Intent(this, ViewVisitaActivity.class);
        i.putExtra("img", img);
        i.putExtra("date", data);
        startActivity(i);
    }


}

