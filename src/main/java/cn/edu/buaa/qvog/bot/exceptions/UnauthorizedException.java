/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Unauthorized Request")
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        this("Unauthorized Request");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
