package co.appreactor.conocimentos.persistencia.entidades;

import java.io.Serializable;

/**
 * Created by lord_nightmare on 5/12/17.
 */

public class Respuesta implements Serializable {

    private Long RespuestaTestId;
    private Long PreguntaTestId;
    private String Respuesta;
    private Boolean Correcta;
    private Boolean Estado;


    public Long getRespuestaTestId() {
        return RespuestaTestId;
    }

    public void setRespuestaTestId(Long respuestaTestId) {
        RespuestaTestId = respuestaTestId;
    }

    public Long getPreguntaTestId() {
        return PreguntaTestId;
    }

    public void setPreguntaTestId(Long preguntaTestId) {
        PreguntaTestId = preguntaTestId;
    }

    public String getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(String respuesta) {
        Respuesta = respuesta;
    }

    public Boolean getCorrecta() {
        return Correcta;
    }

    public void setCorrecta(Boolean correcta) {
        Correcta = correcta;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }
}
