package co.appreactor.conocimentos.persistencia.dto;

/**
 * Awesome Pojo Generator
 */
public class TokenDto {
    private String access_token;
    private String token_type;
    private String userName;
    private Integer expires_in;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public Integer getExpires_in() {
        return expires_in;
    }


}