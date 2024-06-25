package smartbell.sb_project;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import kotlinx.coroutines.CoroutineScope;

/**
 * ViewModel referente a HistActivity
 */
public class HistViewModel extends AndroidViewModel {

    LiveData<PagingData<Visita>> visitasLd;

    public HistViewModel(@NonNull Application application) {
        super(application);

        // Abaixo configuramos o uso da biblioteca de paginação Paging 3, assim como foi feito na
        // atividade Galeria Pública
        VisitasRepository visitasRepository = new VisitasRepository(getApplication());
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, Visita> pager = new Pager(new PagingConfig(10), () -> new VisitasPagingSource(visitasRepository));
        visitasLd = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }

    public LiveData<PagingData<Visita>> getVisitasLd() {
        return visitasLd;
    }

}

