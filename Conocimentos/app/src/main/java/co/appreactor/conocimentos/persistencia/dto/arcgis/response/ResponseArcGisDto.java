package co.appreactor.conocimentos.persistencia.dto.arcgis.response;

/**
 * Awesome Pojo Generator
 */
public class ResponseArcGisDto {
    
    private Object Messsage;
    private Boolean IsError;
    private Data Data;

    public void setMesssage(Object Messsage) {
        this.Messsage = Messsage;
    }

    public Object getMesssage() {
        return Messsage;
    }

    public void setIsError(Boolean IsError) {
        this.IsError = IsError;
    }

    public Boolean getIsError() {
        return IsError;
    }

    public void setData(Data Data) {
        this.Data = Data;
    }

    public Data getData() {
        return Data;
    }
}