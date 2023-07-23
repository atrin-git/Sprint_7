package org.example.CourierTests;

import org.hamcrest.number.IsNaN;

public class CourierLoginResponse {
    private Integer id;

    public CourierLoginResponse() {
    }

    public CourierLoginResponse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
