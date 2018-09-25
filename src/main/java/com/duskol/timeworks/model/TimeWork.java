package com.duskol.timeworks.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeWork {

    @NonNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfCharge;

    @NonNull
    //@DateTimeFormat(style = "hh:ss:mm")
    private String hoursOnProject;
}
