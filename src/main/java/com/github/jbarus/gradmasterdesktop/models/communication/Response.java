package com.github.jbarus.gradmasterdesktop.models.communication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T>{
    private int HTTPStatusCode;
    private T body;

}
