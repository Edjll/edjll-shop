class EdjllVector {
	constructor(x, y) {
		this.x = x;
		this.y = y;
	}

	plus(element) {
		return new EdjllVector(this.x + element.x, this.y + element.y);
	}
}