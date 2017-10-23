package co.appreactor.pokedex.modelo.entidades;

/**
 * Created by lord_nightmare on 19/10/17.
 */

public class Pokemon {

    private int number;
    private String name;
    private String url;


    public int getNumber() {
        String[] partes = this.url.split("/");
        return Integer.parseInt(partes[partes.length-1]);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
