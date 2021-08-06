package com.utec.fmagot.internet;

public class FlaskResponse {
    private String message;
    private String category;
    private boolean error;

    public FlaskResponse(String message, String category, boolean error) {
        this.message = message;
        this.category = category;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public String getCategory() {
        return category;
    }

    public boolean isError() {
        return error;
    }

}
