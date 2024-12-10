package com.github.jbarus.gradmasterdesktop.models.communication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullResponse<T,V> extends Response<V> {
    private T serverCode;

    public FullResponse(int HTTPStatusCode, T serverCode, V body) {
        super(HTTPStatusCode, body);
        this.serverCode = serverCode;
    }
}
