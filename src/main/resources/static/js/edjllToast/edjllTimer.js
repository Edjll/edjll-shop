class EdjllTimer {
    constructor() {
        this.value = new Date().getTime();
    }

    parseToString() {
        const difference = new Date().getTime() - this.value;
        let string = '';

        const days = Math.floor(difference / (1000 * 60 * 60 * 24));
        const hours = Math.floor((difference / (1000 * 60 * 60)) % 24);
        const minutes = Math.floor((difference / (1000 * 60)) % 60);
        const seconds = Math.floor((difference / 1000) % 60);

        if (days > 0) string += days + 'д ';
        if (hours > 0) string += hours + 'ч ';
        if (minutes > 0) string += minutes + 'м ';
        if (seconds > 0) string += seconds + 'с ';

        if (string.length) string += 'назад';
        else string = '0с назад';

        return string;
    }
}