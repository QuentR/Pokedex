package fr.quentinroussat.pokedex.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Quentin on 16/02/2017.
 */

public class Pokemon {

    private int number;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String dataUrl;


    public Pokemon(int number, String name, String dataUrl) {
        this.number = number;
        this.name = name;
        this.dataUrl = dataUrl;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", dataUrl='" + dataUrl + '\'' +
                '}';
    }
}
