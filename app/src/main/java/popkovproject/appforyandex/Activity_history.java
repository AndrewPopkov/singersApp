package popkovproject.appforyandex;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import popkovproject.appforyandex.database.DBHelper;
import popkovproject.appforyandex.database.SingerDataBaseAdapter;

public class Activity_history extends AppCompatActivity {

    private Toolbar historytoolbar;
    private ArrayList<Singer> singers;
    private SingerAdapter myAdapter;
    private ListView lvHistory;
    private TextView titleHistory;
    private ImageView ImageButton_description;
    private SingerDataBaseAdapter dataBase;//класс для работы с бд


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dataBase=new SingerDataBaseAdapter(this);
        historytoolbar = (Toolbar) findViewById(R.id.descriptionToolbar);
        setSupportActionBar(historytoolbar);
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        titleHistory=(TextView) findViewById(R.id.toolbar_title_description);
        titleHistory.setText("Ранее посещенные");
        ImageButton_description=(ImageView) findViewById(R.id.imageButton_description);
        ImageButton_description.setOnClickListener(new View.OnClickListener() {//переход назад
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_history.this, PersonActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        singers = dataBase.getAllRow();
        setListAdapter();
        dataBase.closeСonnection();
    }
    private void setListAdapter(){ // создаем адаптер и привязываем список к item_main
        myAdapter = new SingerAdapter(this,singers );
        lvHistory.setAdapter(myAdapter);

    }
    @Override
    public void onDestroy(){
        super.onDestroy();

    }

}
