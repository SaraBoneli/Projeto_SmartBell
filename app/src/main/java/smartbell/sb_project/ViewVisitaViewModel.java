package smartbell.sb_project;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


    /**
     * ViewModel referente a ViewProductActivity
     */
    public class ViewVisitaViewModel extends AndroidViewModel {

        public ViewVisitaViewModel(@NonNull Application application) {
            super(application);
        }

        /**
         * Método que cria e executa uma requisição ao servidor web para obter os detalhes de um produto
         * na base de dados do servidor
         * @param vid id do produto que se quer obter os detalhes
         * @return um LiveData que vai conter a resposta do servidor quando esta estiver disponível
         */
        public LiveData<Visita> getProductDetailsLD(String vid) {

            // Cria um container do tipo MutableLiveData (um LiveData que pode ter seu conteúdo alterado).
            MutableLiveData<Visita> productDetailLD = new MutableLiveData<>();

            // Cria uma nova linha de execução (thread). O android obriga que chamadas de rede sejam feitas
            // em uma linha de execução separada da principal.
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            // Executa a nova linha de execução. Dentro dessa linha, iremos realizar as requisições ao
            // servidor web.
            executorService.execute(new Runnable() {

                /**
                 * Tudo o que colocármos dentro da função run abaixo será executada dentro da nova linha
                 * de execução.
                 */
                @Override
                public void run() {

                    // Criamos uma instância de ProductsRepository. É dentro dessa classe que estão os
                    // métodos que se comunicam com o servidor web.
                    VisitasRepository visitasRepository = new VisitasRepository(getApplication());

                    // O método loadProductDetail obtem os dados detalhados de um produto junto ao servidor.
                    // Ele retorna um objeto do tipo Product, que contém os dados detalhados do produto.
                    Visita v = visitasRepository.loadLastVisita();

                    // Aqui postamos o resultado da operação dentro do LiveData. Quando fazemos isso,
                    // quem estiver observando o LiveData será avisado de que o resultado está disponível.
                    productDetailLD.postValue(v);
                }
            });

            return productDetailLD;
        }
    }


