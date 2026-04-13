package com.gblrod.agentsvault.core.locale

import android.content.Context
import android.content.res.Configuration
import com.gblrod.agentsvault.language.LanguageOptions
import java.util.Locale

object LanguageManager {
    fun resolveLocale(savedLanguage: LanguageOptions?): Locale {
        val systemLocale = Locale.getDefault()

        return when (savedLanguage) {
            LanguageOptions.PT_BR -> Locale("pt", "BR")
            LanguageOptions.EN_US -> Locale("en")
            LanguageOptions.ES -> Locale("es")
            null -> {
                when (systemLocale.language) {
                    "pt" -> Locale("pt", "BR")
                    "es" -> Locale("es")
                    else -> Locale("en")
                }
            }
        }
    }

    fun applyLocale(
        base: Context,
        locale: Locale
    ): Context {
        val config = Configuration(base.resources.configuration)
        config.setLocale(locale)
        return base.createConfigurationContext(config)
    }
}