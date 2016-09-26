package popkovproject.appforyandex;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Андрей on 20.04.2016.
 */

public class SingerAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<Singer> objects;

    SingerAdapter(Context context, ArrayList<Singer> singers) {
        ctx = context;
        objects = singers;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {

        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {

        return objects.get(position);
    }
    // певец по позиции
    private Singer getSinger(int position) {

        return ((Singer) getItem(position));
    }
    // id по позиции
    @Override
    public long getItemId(int position) {

        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.item_main, parent, false);
        }
        Singer p = getSinger(position);

        // заполняем View в пункте списка данными
        setImageSingerSmall(view, p);
        setSingerName(view, p);
        setSingerGenres(view, p);
        setSingerSong(view, p);
        return view;
    }

    //заполняет TextView SingerSong
    private void setSingerSong(View view, Singer p) {
        String albums="";
        if(p.getAlbums()%10==1){
            Integer num = new Integer(p.getAlbums());
            albums+= num.toString()+" альбом,";
        }else if(p.getAlbums()%10>=2 && p.getAlbums()%10<=4){
            Integer num = new Integer(p.getAlbums());
            albums+= num.toString()+" альбома,";
        }else{
            Integer num = new Integer(p.getAlbums());
            albums+= num.toString()+ " альбомов,";
        }
        if(p.getTracks()%10==1){
            Integer num = new Integer(p.getTracks());
            albums+= num.toString()+" песня ";
        }else if(p.getTracks()%10>=2 && p.getTracks()%10<=4){
            Integer num = new Integer(p.getTracks());
            albums+= num.toString()+" песни ";
        }else{
            Integer num = new Integer(p.getTracks());
            albums+= num.toString()+ " песен ";
        }
        ((TextView) view.findViewById(R.id.SingerSong)).setText(albums);
    }

    //заполняет TextView SingerGenres
    private void setSingerGenres(View view, Singer p) {
        ((TextView) view.findViewById(R.id.SingerGenres)).setText(p.getGenres());
    }

    //заполняет TextView SingerName
    private void setSingerName(View view, Singer p) {
        ((TextView) view.findViewById(R.id.SingerName)).setText(p.getName());
    }

    //заполняет ImageView imageSingerSmall
    private void setImageSingerSmall(View view, Singer p) {
        ImageView imagePerson =  ((ImageView) view.findViewById(R.id.imageSingerSmall));
        Callback callBack = new Callback(){
            @Override
            public void onSuccess(){

            }
            @Override
            public void onError(){
                Toast.makeText(ctx, "Картинка не загрузилась", Toast.LENGTH_LONG).show();
            }
        };
        Picasso.with(ctx).load( p.getSmall()).resize(150, 150).error( R.drawable.ic_collections_black_48dp).into(imagePerson,callBack);
    }


}