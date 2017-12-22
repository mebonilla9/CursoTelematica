package co.appreactor.conocimentos.persistencia.dto.arcgis.request;
/**
 * Awesome Pojo Generator
 * */
public class RequestArcGisDto{
  private Data Data;
  private String Client;
  public void setData(Data Data){
   this.Data=Data;
  }
  public Data getData(){
   return Data;
  }
  public void setClient(String Client){
   this.Client=Client;
  }
  public String getClient(){
   return Client;
  }
}