package com.github.jbarus.gradmasterdesktop.models.communication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerResponse<T, V> {
    private T status;
    private V responseBody;

    public ServerResponse(T status) {
        this.status = status;
    }

    public ServerResponse(T status, V responseBody) {
        this.status = status;
        this.responseBody = responseBody;
    }

    public ServerResponse() {
    }
}
