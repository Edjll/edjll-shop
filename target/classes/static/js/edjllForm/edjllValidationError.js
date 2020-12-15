class EdjllValidationError {
    static REQUIRED_VALIDATION_ERROR() { return 'Поле должно быть заполнено'; }
    static MIN_LENGTH_VALIDATION_ERROR(minLength) { return `Длина должна быть больше ${minLength}`; }
    static MIN_VALIDATION_ERROR(min) { return `Значение должно быть больше ${min}`; }
    static MAX_VALIDATION_ERROR(max) { return `Значение должно быть меньше ${max}`; }
    static LATER_THAN_VALIDATION_ERROR(laterThan) { return `Дата должна быть позже ${new Date(laterThan).toLocaleString()}`; }
    static IDENTITY_VALIDATION_ERROR(identity) { return `Значение должно быть таким же как у ${identity.toLowerCase()}`; }
    static REGEX_VALIDATION_ERROR(message) { return message; }
}