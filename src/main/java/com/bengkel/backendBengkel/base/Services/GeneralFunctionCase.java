package com.bengkel.backendBengkel.base.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GeneralFunctionCase {

    public static String getDateTransform(LocalDateTime dateRequest) {
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("EEEE, dd-MMMM-yyyy", Locale.ENGLISH);
        return dateRequest.format(formatDate);
    }
}
