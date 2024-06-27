package smartbell.sb_project.adapter;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import smartbell.sb_project.utils.Config;
import smartbell.sb_project.utils.HttpRequest;
import smartbell.sb_project.utils.Util;
import smartbell.sb_project.model.Visita;

public class VisitasRepository {

    Context context;
    public VisitasRepository(Context context) {
        this.context = context;
    }
    public List<Visita> loadVisitas(Integer limit, Integer offSet) {

        // cria a lista de produtos incicialmente vazia, que será retornada como resultado
        List<Visita> visitasList = new ArrayList<>();

        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        //String login = Config.getLogin(context);
        //String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.VISITAS_APP_URL +"smartbell_pega_tudo.php", "GET", "UTF-8");
        httpRequest.addParam("limit", limit.toString());
        httpRequest.addParam("offset", offSet.toString());
        //HttpRequest httpRequest = new HttpRequest(Config.VISITAS_APP_URL +"smartbell_ultima_visita.php", "POST", "UTF-8");
        //httpRequest. alguma coisa que vai servir para puxar a ultima imagem

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        //httpRequest.setBasicAuth(login, password);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1,
            //  "produtos":[
            //          {"id":"7", "nome":"produto 1", "preco":"10.00", "img":"www.imgur.com/img1.jpg"},
            //          {"id":"8", "nome":"produto 2", "preco":"20.00", "img":"www.imgur.com/img2.jpg"}
            //       ]
            // }
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao obter produtos"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP VISITAS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os produtos são obtidos da String JSON e adicionados à lista de
            // produtos a ser retornada como resultado.
            if(success == 1) {

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto
                JSONArray jsonArray = jsonObject.getJSONArray("visitas");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de uma visita
                for(int i = 0; i < jsonArray.length(); i++) {

                    // Obtemos o JSONObject referente a uma visita
                    JSONObject jVisitas = jsonArray.getJSONObject(i);

                    // Obtemos os dados de uma visita via JSONObject
                    String pid = jVisitas.getString("id");
                    String data = jVisitas.getString("data_criacao");
                    String img = jVisitas.getString("img");

                    // Criamo um objeto do tipo Visitas para guardar esses dados
                    Visita visita = new Visita();
                    visita.id = pid;
                    visita.data = data;
                    visita.img = img;

                    // Adicionamos o objeto product na lista de produtos
                    visitasList.add(visita);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return visitasList;
    }

    /**
     * Método que cria uma requisição HTTP para obter os detalhes de um produto junto ao servidor web.
     * @param id id do produto que se deseja obter os detalhes
     * @return objeto do tipo product contendo os detalhes do produto
     */
    public Visita loadLastVisita() {

        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        //String login = Config.getLogin(context);
        //String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.VISITAS_APP_URL +"smartbell_ultima_visita.php", "GET", "UTF-8");
        //httpRequest.addParam("id", id);

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        //httpRequest.setBasicAuth(login, password);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1,"nome":"produto 1","preco":"10.00", "img":"www.imgur.com/img1.jpg", "descricao":"produto 1","criado_em":"2022-10-03 19:43:31.42905","criado_por":"daniel"}
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao obter detalhes do produto"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP DETAILS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os detalhes do produto são obtidos da String JSON e um objeto
            // do tipo Product é criado para guardar esses dados
            if(success == 1) {

                // obtém os dados detalhados do produto. A imagem não vem junto. Ela é obtida
                // separadamente depois, no momento em que precisa ser exibida na app. Isso permite
                // que os dados trafeguem mais rápido.


                String img = jsonObject.getString("img");
                String data = jsonObject.getString("data_criacao");
                String id = jsonObject.getString("id");
                //vai precisar do id aqui também? e pq data está cinza


                // Cria um objeto Product e guarda os detalhes do produto dentro dele.
                Visita v = new Visita();


                v.id = id;
                v.img = img;
                v.data = data;

                return v;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
