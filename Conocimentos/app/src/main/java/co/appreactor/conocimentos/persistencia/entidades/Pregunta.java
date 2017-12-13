package co.appreactor.conocimentos.persistencia.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lord_nightmare on 5/12/17.
 */

public class Pregunta implements Serializable {

    private Long PreguntaTestId;
    private Long CategoriaTestId;
    private String Pregunta;
    private Boolean Estado;

    // Coleccion que representa a las respuestas de la pregunta

    private List<Respuesta> RespuestaTest;


    public Long getPreguntaTestId() {
        return PreguntaTestId;
    }

    public void setPreguntaTestId(Long preguntaTestId) {
        PreguntaTestId = preguntaTestId;
    }

    public Long getCategoriaTestId() {
        return CategoriaTestId;
    }

    public void setCategoriaTestId(Long categoriaTestId) {
        CategoriaTestId = categoriaTestId;
    }

    public String getPregunta() {
        return Pregunta;
    }

    public void setPregunta(String pregunta) {
        Pregunta = pregunta;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }

    public List<Respuesta> getRespuestaTest() {
        return RespuestaTest;
    }

    public void setRespuestaTest(List<Respuesta> respuestaTest) {
        RespuestaTest = respuestaTest;
    }
}
