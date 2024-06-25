package smartbell.sb_project;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;


public class VisitasComparator extends DiffUtil.ItemCallback<Visita> {
    @Override
    public boolean areItemsTheSame(@NonNull Visita oldItem, @NonNull Visita newItem) {
        return oldItem.id.equals(newItem.id);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Visita oldItem, @NonNull Visita newItem) {
        return oldItem.id.equals(newItem.id) &&
                oldItem.img.equals(newItem.img);
    }
}

