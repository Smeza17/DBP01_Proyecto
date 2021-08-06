package com.utec.fmagot.internet;

public class Usuario {
    private String user;
    private String password;
    private boolean es_admin;

    public Usuario(String user, String password, boolean es_admin) {
        this.user = user;
        this.password = password;
        this.es_admin = es_admin;
    }
}
