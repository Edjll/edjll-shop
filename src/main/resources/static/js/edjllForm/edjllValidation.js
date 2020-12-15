class EdjllValidation {
    constructor(
        edjllForm,
        settings // { required: false, minLength: null, maxLength: null, fileTypes: [], min: null, max: null }
    ) {
        this.edjllForm = edjllForm;

        if (settings) {
            this.settings = {
                required: settings.required !== undefined ? settings.required : false,
                minLength: settings.minLength !== undefined ? settings.minLength : null,
                maxLength: settings.maxLength !== undefined ? settings.maxLength : null,
                min: settings.min !== undefined ? settings.min : null,
                max: settings.max !== undefined ? settings.max : null,
                laterThan: settings.laterThan !== undefined ? settings.laterThan : null,
                identity: settings.identity !== undefined ? settings.identity : null,
                regex: {
                    pattern: settings.regex !== undefined && settings.regex.pattern !== undefined ? settings.regex.pattern : null,
                    message: settings.regex !== undefined && settings.regex.message !== undefined? settings.regex.message : '',
                }
            }
        } else {
            this.settings = {
                required: false,
                minLength: null,
                maxLength: null,
                min: null,
                max: null,
                regex: {
                    pattern: null,
                    message: ''
                }
            }
        }
    }

    static createError(edjllValidationError) {
        const errorNode = document.createElement('div');
        errorNode.classList.add('edjll-validation-error');

        const errorIcon = document.createElement('i');
        errorIcon.classList.add('ion-close-round', 'edjll-validation-error__icon');

        const errorText = document.createElement('div');
        errorText.classList.add('edjll-validation-error__text');
        errorText.textContent = edjllValidationError;

        errorNode.append(errorIcon, errorText);

        return {
            node: errorNode,
            text: errorText
        }
    }

    validate(value) {
        if (this.settings.required && !EdjllValidation.validateRequired(value)) {
            return EdjllValidationError.REQUIRED_VALIDATION_ERROR();
        }
        if (this.settings.minLength !== null && !EdjllValidation.validateMinLength(value, this.settings.minLength)) {
            return EdjllValidationError.MIN_LENGTH_VALIDATION_ERROR(this.settings.minLength);
        }
        if (this.settings.min !== null && !EdjllValidation.validateMin(value, this.settings.min)) {
            return EdjllValidationError.MIN_VALIDATION_ERROR(this.settings.min);
        }
        if (this.settings.max !== null && !EdjllValidation.validateMax(value, this.settings.max)) {
            return EdjllValidationError.MAX_VALIDATION_ERROR(this.settings.max);
        }
        if (this.settings.laterThan !== null) {
            const laterThan = this.edjllForm.searchFormItemByName(this.settings.laterThan);
            if (laterThan !== null && laterThan.getValue() !== null && !EdjllValidation.validateLaterThan(value, laterThan.getValue())) {
                return EdjllValidationError.LATER_THAN_VALIDATION_ERROR(laterThan.getValue());
            }
        }
        if (this.settings.identity !== null) {
            const identityItem = this.edjllForm.searchFormItemByName(this.settings.identity);
            if (identityItem !== null && identityItem.getValue() !== null && !EdjllValidation.validateIdentity(value, identityItem.getValue())) {
                return EdjllValidationError.IDENTITY_VALIDATION_ERROR(identityItem.attributes.label);
            }
        }
        if (this.settings.regex.pattern !== null && !EdjllValidation.validateRegex(value, this.settings.regex.pattern)) {
            return EdjllValidationError.REGEX_VALIDATION_ERROR(this.settings.regex.message);
        }
        return null;
    }

    static validateRequired(value) {
        if (value) {
            if (value instanceof Array) {
                return value.length;
            }
            return value !== '';
        }
        return false;
    }

    static validateMinLength(value, minLength) {
        return (value && minLength) || value.length >= minLength;
    }

    static validateMin(value, min) {
        return value >= min;
    }

    static validateMax(value, max) {
        return value <= max;
    }

    static validateLaterThan(value, laterThan) {
        return new Date(value) - new Date(laterThan) > 0;
    }

    static validateIdentity(value, identityValue) {
        return value === identityValue;
    }

    static validateRegex(value, regex) {
        return value.match(new RegExp(regex, 'g')) != null;
    }
}