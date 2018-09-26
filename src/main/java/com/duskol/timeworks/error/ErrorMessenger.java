package com.duskol.timeworks.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessenger {

    private long code;
    private String message;
}
