/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class Requests {
    private Requests() {}

    public static HttpServletRequest getCurrentRequest() {
        var request = RequestContextHolder.currentRequestAttributes().resolveReference(RequestAttributes.REFERENCE_REQUEST);
        if (request == null) {
            throw new IllegalStateException("No current request");
        }
        return (HttpServletRequest) request;
    }
}
