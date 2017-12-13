package co.appreactor.conocimentos.persistencia.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lord_nightmare on 5/12/17.
 */

public class Categoria implements Serializable {

    private Long CategoriaTestId;
    private String Nombre;
    private Boolean Estado;

    // lista de preguntas pertenecientes a la categoria

    private List<Pregunta> PreguntaTest;


    public Long getCategoriaTestId() {
        return CategoriaTestId;
    }

    public void setCategoriaTestId(Long categoriaTestId) {
        CategoriaTestId = categoriaTestId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }

    public List<Pregunta> getPreguntaTest() {
        return PreguntaTest;
    }

    public void setPreguntaTest(List<Pregunta> preguntaTest) {
        PreguntaTest = preguntaTest;
    }
}
