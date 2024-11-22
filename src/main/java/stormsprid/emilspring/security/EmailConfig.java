package stormsprid.emilspring.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailConfig {

    private String username;
    private String password;
    private int port;
    private String host;

}