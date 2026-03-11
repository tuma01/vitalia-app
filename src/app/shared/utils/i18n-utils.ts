/**
 * Returns a list of all IANA timezones supported by the browser.
 * Uses Intl.supportedValuesOf('timeZone') if available.
 */
export function getTimezones(): { label: string; value: string }[] {
    try {
        const timezones = (Intl as any).supportedValuesOf('timeZone');
        return timezones.map((tz: string) => ({ label: tz, value: tz }));
    } catch (e) {
        console.error('Error fetching timezones:', e);
        return [{ label: 'UTC', value: 'UTC' }];
    }
}

/**
 * Returns the official list of locales supported by the application.
 */
export function getAppLocales(): { label: string; value: string }[] {
    return [
        // Idiomas con soporte de traducción (.json disponible)
        { label: 'Español (España)', value: 'es-ES' },
        { label: 'English (US)', value: 'en-US' },
        { label: 'Français (France)', value: 'fr-FR' },

        // Idiomas Regionales / Originarios (Para futura traducción)
        { label: 'Aymara (ay-BO)', value: 'ay-BO' },
        { label: 'Quechua (qu-BO)', value: 'qu-BO' }
    ];
}
