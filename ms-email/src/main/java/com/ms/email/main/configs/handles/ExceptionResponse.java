package com.ms.email.main.configs.handles;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, int code, LocalDateTime timestamp) {}
