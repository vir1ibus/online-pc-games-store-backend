package org.vir1ibus.onlinestore.exception;

import org.json.JSONObject;

public class AuthorizationException extends Exception{
    public AuthorizationException(String errorMessage) {
        super(errorMessage);
    }

}
