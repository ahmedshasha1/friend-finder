package com.project.friend.dto.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionDto {
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "YYYY - MM - DD  hh:mm:ss")
    private LocalDateTime localDateTime;

    private BundleMessage bundleMessage;

    public ExceptionDto(HttpStatus status, BundleMessage bundleMessage) {
        localDateTime = LocalDateTime.now();
        this.status = status;
        this.bundleMessage = bundleMessage;
    }

}