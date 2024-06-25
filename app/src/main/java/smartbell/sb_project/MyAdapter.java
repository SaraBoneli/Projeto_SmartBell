package smartbell.sb_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;


public class MyAdapter extends PagingDataAdapter<Visita, MyViewHolder> {

    HistActivity histActivity;

    public MyAdapter(HistActivity histActivity, @NonNull DiffUtil.ItemCallback<Visita> diffCallback) {
            super(diffCallback);
            this.histActivity = histActivity;
    }

    /**
     * Cria os elementos de UI referente a um item da lista
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    /**
     * Preenche um item da lista
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Visita visita = this.getItem(position);

        // preenche o campo de foto
        int w = (int) histActivity.getResources().getDimension(R.dimen.thumb_width);
        int h = (int) histActivity.getResources().getDimension(R.dimen.thumb_height);
        ImageView imgVisitasThumb = holder.itemView.findViewById(R.id.imgVisitas);

        // preenche o campo de data
        TextView tvdataList = holder.itemView.findViewById(R.id.data_foto);
        tvdataList.setText(visita.data);


        // somente agora o a imagem é obtida do servidor. Caso a imagem já esteja salva no cache da app,
        // não baixamos ela de novo
        ImageCache.loadImageUrlToImageView(histActivity, visita.img, imgVisitasThumb , w, h);

            // ao clicar em um item da lista, navegamos para a tela que mostra os detalhes do produto
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    histActivity.startViewVisitasAcitivity(visita.img, visita.data);
                }
            });
        }
    }



