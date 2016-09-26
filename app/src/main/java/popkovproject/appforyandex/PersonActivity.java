package popkovproject.appforyandex;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import popkovproject.appforyandex.database.SingerDataBaseAdapter;


public class PersonActivity extends AppCompatActivity  {

    private Toolbar singertoolbar;
    private ArrayList<Singer> singers;
    private SingerAdapter myAdapter;//адаптер списка
    private ListView lvMain;
    private SingerDataBaseAdapter dataBase;//класс для работы с бд
    public static final String SINGERNAMEEXTRAS="SingerNameExtras";
    public static final String SINGERGENRESEXTRAS="SingerGenresExtras";
    public static final String SINGERSONGEXTRAS="SingerSongExtras";
    public static final String DESCRIPTIONEXTRAS="DescriptionExtras";
    public static final String IMAGEPERSONBIGEXTRAS="ImagePersonBigExtras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singertoolbar = (Toolbar) findViewById(R.id.singerToolbar);
        setSupportActionBar(singertoolbar);
        new JsonParsing().execute("http://cache-default01d.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json");
        lvMain = (ListView) findViewById(R.id.lvMain);
        dataBase=new SingerDataBaseAdapter(this);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PersonActivity.this, DescriptionActivity.class);
                intent.putExtra(SINGERNAMEEXTRAS, ((TextView) view.findViewById(R.id.SingerName)).getText().toString());
                intent.putExtra(SINGERGENRESEXTRAS, ((TextView) view.findViewById(R.id.SingerGenres)).getText().toString());
                intent.putExtra(SINGERSONGEXTRAS, ((TextView) view.findViewById(R.id.SingerSong)).getText().toString());
                String descriptionText = "Биография \n";
                descriptionText += singers.get(position).getDescription();
                intent.putExtra(DESCRIPTIONEXTRAS, descriptionText);
                intent.putExtra(IMAGEPERSONBIGEXTRAS, singers.get(position).getBig());
                dataBase.insertRow( singers.get(position));
                startActivity(intent);
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключения
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {//вызываем список просмотренных записей
            Intent intent = new Intent(PersonActivity.this, Activity_history.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public class JsonParsing extends AsyncTask<String, Void, String> {//класс для асинхронного считывания данных с сервера

        @Override
        protected String doInBackground(String... params) { // получаем данные с внешнего ресурса в фоновом потоке
            String urlstr="";
            String resultJson = "";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            if(params.length>0){

                urlstr=params[0];
            }
            try {

                URL url = new URL(urlstr);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                resultJson = buffer.toString();
            }
            catch (MalformedURLException e) {//неправильный URL
                e.printStackTrace();
                Toast.makeText(PersonActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            catch (IOException e) {//исключение ввода-вывода
                e.printStackTrace();
                Toast.makeText(PersonActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String result) {//парсинг json  в главном потоке
            super.onPostExecute(result);
            JSONArray singerJSONArray = null;
            Singer obj;
            singers=new ArrayList<>();

            try {
                singerJSONArray = new JSONArray(result);
                for (int i = 0; i < singerJSONArray.length(); i++) {
                    obj = new Singer();
                    if(singerJSONArray.getJSONObject(i).has("id")) {
                        obj.setId(singerJSONArray.getJSONObject(i).getInt("id"));
                    }
                    if(singerJSONArray.getJSONObject(i).has("name")) {
                        obj.setName(singerJSONArray.getJSONObject(i).getString("name"));
                    }
                    if(singerJSONArray.getJSONObject(i).has("genres")) {
                        JSONArray genresJSONArray = singerJSONArray.getJSONObject(i).getJSONArray("genres");
                        String genres = "";
                        for (int j = 0; j < genresJSONArray.length(); j++) {
                            if(j>0) {
                                genres += ","+ genresJSONArray.getString(j) + " ";
                            }
                            else{
                                genres += genresJSONArray.getString(j) + " ";
                            }
                        }
                        obj.setGenres(genres);
                    }
                    if(singerJSONArray.getJSONObject(i).has("tracks")) {
                        obj.setTracks(singerJSONArray.getJSONObject(i).getInt("tracks"));
                    }
                    if(singerJSONArray.getJSONObject(i).has("albums")) {
                        obj.setAlbums(singerJSONArray.getJSONObject(i).getInt("albums"));
                    }
                    if(singerJSONArray.getJSONObject(i).has("link")){
                        obj.setLink(singerJSONArray.getJSONObject(i).getString("link"));
                    }
                    if(singerJSONArray.getJSONObject(i).has("description")) {
                        obj.setDescription(singerJSONArray.getJSONObject(i).getString("description"));
                    }
                    if(singerJSONArray.getJSONObject(i).has("cover")) {
                        if(singerJSONArray.getJSONObject(i).getJSONObject("cover").has("small")) {
                            obj.setSmall(singerJSONArray.getJSONObject(i).getJSONObject("cover").getString("small"));
                        }
                        if(singerJSONArray.getJSONObject(i).getJSONObject("cover").has("big")) {
                            obj.setBig(singerJSONArray.getJSONObject(i).getJSONObject("cover").getString("big"));
                        }
                    }
                    singers.add(i, obj);
                }
            }
            catch (JSONException e) {//ошибка парсинга json
                e.printStackTrace();
                Toast.makeText(PersonActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            setListAdapter();
        }
    }
    private void setListAdapter(){ // создаем адаптер и привязываем список к item_main
        myAdapter = new SingerAdapter(this,singers );
        lvMain.setAdapter(myAdapter);

    }

}

