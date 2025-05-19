package com.NamVu.ReviewSpring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {

    private static ResourceBundleMessageSource msgSource;

    private Translator(@Autowired ResourceBundleMessageSource messageSource) {
        Translator.msgSource = messageSource;
    }

    public static String toLocale(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return msgSource.getMessage(msgCode, null, locale);
    }
}
