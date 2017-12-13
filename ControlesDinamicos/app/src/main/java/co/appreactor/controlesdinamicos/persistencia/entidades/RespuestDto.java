package co.appreactor.controlesdinamicos.persistencia.entidades;

/**
 * Awesome Pojo Generator
 */
public class RespuestDto {

    private Integer codigo;
    private String mensaje;

    public RespuestDto() {
    }

    public RespuestDto(Integer codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}