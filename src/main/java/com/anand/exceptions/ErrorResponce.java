package com.anand.exceptions;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponce {

	private String message;
    private String error;
    private int status;
    private LocalDateTime timestamp;


}
