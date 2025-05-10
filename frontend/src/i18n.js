import i18n from "i18next";
import { initReactI18next } from "react-i18next";

// Import translation files
import translationEN from "./i18n/locales/en/translation.json";
import translationFR from "./i18n/locales/fr/translation.json";

const resources = {
  en: { translation: translationEN },
  fr: { translation: translationFR },
};

i18n
  .use(initReactI18next)
  .init({
    resources,
    lng: "en", // default language
    fallbackLng: "fr",
    interpolation: { escapeValue: false },
  });

export default i18n;
