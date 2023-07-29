package org.example.apitests.entities.responseentities;

public class CourierResponse {
    private Integer id;
    private boolean ok;
    private String message;

    public CourierResponse() {
    }

    public CourierResponse(Integer id, boolean ok, String message) {
        this.id = id;
        this.ok = ok;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
