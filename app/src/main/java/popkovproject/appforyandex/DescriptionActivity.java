package popkovproject.appforyandex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class DescriptionActivity extends AppCompatActivity {

    private TextView SingerName;
    private TextView SingerGenres;
    private TextView SingerSong;
    private TextView SingerDescription;
    private ImageView ImagePersonBig;
    private ImageView ImageButton_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.descriptionToolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setViewDescription(extras);
        }
        ImageButton_description=(ImageView) findViewById(R.id.imageButton_description);
        ImageButton_description.setOnClickListener(new View.OnClickListener() {//переход назад
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, PersonActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    private void setViewDescription(Bundle extras) {//заполняем View
        SingerName=(TextView) findViewById(R.id.toolbar_title_description);
        SingerGenres=(TextView) findViewById(R.id.singerDescriptionGenres);
        SingerSong=(TextView) findViewById(R.id.singerDescriptionSong);
        SingerDescription=(TextView) findViewById(R.id.singerDescriptionText);
        ImagePersonBig=(ImageView) findViewById(R.id.singerImagePersonBig);
        SingerName.setText(extras.getString(PersonActivity.SINGERNAMEEXTRAS));
        SingerGenres.setText(extras.getString(PersonActivity.SINGERGENRESEXTRAS));
        SingerSong.setText(extras.getString(PersonActivity.SINGERSONGEXTRAS).replace(",", " · "));
        SingerDescription.setText(extras.getString(PersonActivity.DESCRIPTIONEXTRAS));
        setImagePerson(extras);
    }

    private void setImagePerson(Bundle extras) {//загрузка  изображения

        Callback callBack = new Callback(){
            @Override
            public void onSuccess(){

            }
            @Override
            public void onError(){
                Toast.makeText(DescriptionActivity.this, "Картинка не загрузилась", Toast.LENGTH_LONG).show();
            }
        };
       // Glide.with(this).load("http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000").into(ImagePersonBig);
        Picasso.with(this).load(extras.getString(PersonActivity.IMAGEPERSONBIGEXTRAS)).error(R.drawable.ic_collections_black_48dp).into(ImagePersonBig,callBack);
       //Picasso.with(this).load("http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000").into(ImagePersonBig,callBack);
    }

}
