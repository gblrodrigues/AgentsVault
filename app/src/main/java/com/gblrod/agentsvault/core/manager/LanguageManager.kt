package com.gblrod.agentsvault.core.manager

import android.content.Context
import android.content.res.Configuration
import com.gblrod.agentsvault.core.util.orDeviceDefault
import com.gblrod.agentsvault.language.LanguageOptions
import java.util.Locale

object LanguageManager {
    fun resolveLocale(savedLanguage: LanguageOptions?): Locale {
        return savedLanguage
            .orDeviceDefault()
            .toLocale()
    }

    fun applyLocale(
        base: Context,
        locale: Locale
    ): Context {
        val config = Configuration(base.resources.configuration)
        config.setLocale(locale)
        return base.createConfigurationContext(config)
    }

    fun LanguageOptions.toLocale(): Locale {
        return when (this) {
            LanguageOptions.PT_BR -> Locale.forLanguageTag("pt-BR")
            LanguageOptions.EN_US -> Locale.forLanguageTag("en")
            LanguageOptions.ES -> Locale.forLanguageTag("es")
        }
    }
}