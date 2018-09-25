package com.duskol.timeworks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Developer {

    @NonNull
    private String username;
    private List<TimeWork> timeWorks;
}
