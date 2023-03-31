package com.patronus.network.model;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class ImageUploadRaisecomplainResponse implements Serializable {




    private String message = "";
    private String success = "";

    public String getSuccess() {
        return this.success;
    }

    public void setSuccess(String success2) {
        this.success = success2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }




}
