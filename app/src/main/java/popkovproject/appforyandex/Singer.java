package popkovproject.appforyandex;

import java.util.ArrayList;


/**
 * Created by Андрей on 19.04.2016.
 */
public class Singer {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    private int id; // название
    private String name; // название
    private String genres;  // стили
    private int tracks; // количество песен
    private int albums; // количество альбомов
    private String link; // ссылка на сайт
    private String description; // описание
    private String small; // ссылка на фото (маленькое)
    private String big; // ссылка на на фото (большое)


    public Singer(int id, String name, String genres, int tracks, int albums, String link, String description, String small, String big){
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;
        this.small = small;
        this.big = big;
        }
    public Singer(){


    }
    public Singer(Singer obj ){
        this.id = obj.id;
        this.name = obj.name;
        this.genres = obj.genres;
        this.tracks = obj.tracks;
        this.albums = obj.albums;
        this.link = obj.link;
        this.description = obj.description;
        this.small = obj.small;
        this.big = obj.big;
    }


}
