class EdjllVertex {
    constructor(id, name, x, y, editable = true, color = {text: 'rgba(255, 255, 255, 0.8)', backgroundColor: 'rgb(82 83 95)'}) {
        this.id = id;
        this.name = name;
        this.position = new EdjllVector(x, y);
        this.radius = 0.8;
        this.parent;
        this.children = [];
        this.selected = false;
        this.color = color;
        this.editable = editable;
    }

    willCycle(vertex, newVertex) {
		if (vertex == newVertex) return true;
		for (let index in vertex.children) {
			const childVertex = vertex.children[index];
			if (this.willCycle(childVertex, newVertex)) return true;
		}
		return false;
	}
    
    changeParent(newParent) {
        if (!this.willCycle(this, newParent)) {
            if (this.parent) {
                this.parent.children.splice(this.parent.children.indexOf(this), 1);
            }
            if (this.children.includes(newParent)) {
                this.children.splice(this.children.indexOf(newParent), 1);
            }
            this.parent = null;
            if (this.parent != newParent) {
                this.parent = newParent;
                this.parent.children.push(this);
            }
        }
    }

    removeParent(vertex) {
        if (this.parent == vertex) {
            this.parent.children.splice(this.parent.children.indexOf(this), 1);
            this.parent = null;
        } else if (vertex.parent == this) {
            vertex.removeParent(this);
        }
    }

	draw(ctx, scale) {

        ctx.beginPath();

        if (this.selected) {
            ctx.fillStyle = '#5545e7';
        } else if (this.editable) {
            ctx.fillStyle = '#55bb9e';
        } else {
            ctx.fillStyle = this.color.backgroundColor;
        }
        
        ctx.arc(
            /* x */             this.position.x * scale,
            /* y */             this.position.y * scale,
            /* radius */        this.radius * scale,
            /* startAngle */    0,
            /* endAngle */      Math.PI * 2
        );
        ctx.fill();
        ctx.closePath();

        ctx.font = 1.7 * scale + 'px system-ui, serif';
        ctx.textAlign = 'center';
        ctx.fillStyle = this.color.text;
        const name = this.name.length > 7 ? this.name.slice(0, 7) + '...' : this.name;
        ctx.fillText(name, this.position.x * scale, this.position.y * scale - this.radius * 2 * scale);

    }

    drawLine(ctx, scale) {
        this.children.forEach(child => {
            ctx.beginPath();
            ctx.moveTo(this.position.x * scale, this.position.y * scale);
            ctx.lineTo(child.position.x * scale, child.position.y * scale);
            ctx.closePath();
            const gradient = ctx.createLinearGradient(this.position.x * scale, this.position.y * scale,
                                                    child.position.x * scale, child.position.y * scale);

            gradient.addColorStop(0.2, "#d16767");
            gradient.addColorStop(0.8, "#4799eb");

            ctx.lineWidth = this.radius * scale / 2;
            ctx.strokeStyle = gradient;
            ctx.stroke();
        });
    }
}