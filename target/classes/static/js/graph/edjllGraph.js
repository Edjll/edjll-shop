class EdjllGraph {
	constructor(
			node,
			edjllForm,
			attributes = null,
			request = null
	) {
		this.canvas 		= document.createElement('canvas');
		this.canvas.width 	= node ? node.offsetWidth : window.innerWidth;
		this.canvas.height 	= node ? node.offsetHeight - 70 : window.innerHeight - 70;
		this.canvas.onclick = (e) => this.click(e);
		this.canvas.onmousemove = (e) => this.mousemove(e);
		this.canvas.onwheel = (e) => this.wheel(e);
		this.canvas.onmouseleave = (e) => this.mouseleave(e);
		this.canvas.oncontextmenu = (e) => e.preventDefault();

		this.node = node;

		this.edjllForm = edjllForm;

		this.attributes = {
			name: attributes && attributes.name ? attributes.name : 'edjll-graph',
			id: attributes && attributes.id ? attributes.id : null
		}

		this.request = {
			url: request && request.url ? request.url : null
		}

		if (this.request.url) {
			this.sendRequest();
		}

		this.moveable;

		this.resizeObserver = new ResizeObserver(
			entries => {
				for (let entry of entries) {
					this.canvas.width 	= entry.contentBoxSize && entry.contentBoxSize.inlineSize ? entry.contentBoxSize.inlineSize : entry.contentRect.width;
					this.canvas.height 	= (entry.contentBoxSize && entry.contentBoxSize.height ? entry.contentBoxSize.height : entry.contentRect.height) - 69;
					this.loop();
				}
			}
		);

		this.resizeObserver.observe(this.node);

		this.scale = {
			value: 10,
			max: 30,
			min: 5
		};

		this.camera = {
			position: new EdjllVector(0, 0),
			limit: {
				x0: 0,
				y0: 0,
				x1: 0,
				y1: 0
			}
		};

		this.parents = [];
		this.vertexes = [];
		this.vertex = null;
		this.selectedVertex;
		this.vertexType = EdjllVertexType.PARENT;
		this.actionType = EdjllActionType.ADD;

		this.controls = {
			node: this.createControlsWrapper()
		}

		this.node.append(this.controls.node);

		this.controls.switchVertexType = new EdjllSwitch(this.changeVertexType.bind(this), this.controls.node, 'Тип вершины', '/static/image/icons/parent.png', '/static/image/icons/child.png');
		this.controls.switchActionType = new EdjllSwitch(this.changeActionType.bind(this), this.controls.node, 'Действие', 'ion-plus-round', 'ion-close-round', true);

		this.ctx 			= this.canvas.getContext('2d');


		this.node.append(this.canvas);
		window.requestAnimationFrame(this.loop.bind(this));
	}

	static create(node, edjllForm) {
		const settings = node.getElementsByClassName('edjll-graph__settings')[0];
		if (!settings) return new EdjllGraph(node, edjllForm);
		else {
			let attributes = null, request = null;
			try {
				attributes = JSON.parse(settings.getElementsByClassName('edjll-graph__settings__attributes')[0].textContent);
			} catch (e) { }
			try {
				request = JSON.parse(settings.getElementsByClassName('edjll-graph__settings__request')[0].textContent);
			} catch (e) { }
			settings.remove();
			return new EdjllGraph(
				node,
				edjllForm,
				attributes,
				request
			);
		}
	}

	createControlsWrapper() {
		const wrapper = document.createElement('div');
		wrapper.classList.add('edjll-graph__controls');

		return wrapper;
	}

	changeNodeName(name) {
		this.vertex.name = name;
	}

	convertToGlobalPosition(position) {
		return new EdjllVector(
			position.x - this.canvas.offsetLeft - (this.edjllForm ? this.edjllForm.node.offsetLeft : 0) - this.camera.position.x,
			position.y - this.canvas.offsetTop - (this.edjllForm ? this.edjllForm.node.offsetTop : 0) - this.camera.position.y
		);
	}

	convertToLocalPosition(position) {
		return new EdjllVector(
			position.x - this.canvas.offsetLeft - (this.edjllForm ? this.edjllForm.node.offsetLeft : 0),
			position.y - this.canvas.offsetTop - (this.edjllForm ? this.edjllForm.node.offsetTop : 0)
		);
	}

	mouseleave(e) {
		this.moveable = null;
	}

	click(e) {
		if (!this.moveable) {
			const globalMousePosition = this.convertToGlobalPosition(new EdjllVector(e.clientX, e.clientY));


			if (this.isDesiredVertex(this.vertex, globalMousePosition, this.clickAction)) return;

			for (let index in this.vertexes) {
				const vertex = this.vertexes[index];
				if (this.isDesiredVertex(vertex, globalMousePosition, this.clickAction)) return;
			}
		}
	}

	clickAction(data) {
		if (data.vertex !== this.selectedVertex) {
			if (this.selectedVertex) {
				if (this.vertexType === EdjllVertexType.PARENT) {
					if (this.actionType === EdjllActionType.ADD) {
						if (this.selectedVertex.editable) {
							data.vertex.changeParent(this.selectedVertex);
						} else {
							new EdjllToast(EdjllToastEvent.ERROR, 'Ошибка', `Категория "${this.selectedVertex.name}" не может быть родителем`)
						}
					} else if (this.actionType === EdjllActionType.REMOVE) {
						data.vertex.removeParent(this.selectedVertex);
					}
				} else {
					if (this.actionType === EdjllActionType.ADD) {
						if (data.vertex.editable) {
							this.selectedVertex.changeParent(data.vertex);
						} else {
							new EdjllToast(EdjllToastEvent.ERROR, 'Ошибка', `Категория "${data.vertex.name}" не может быть родителем`)
						}
					} else if (this.actionType === EdjllActionType.REMOVE) {
						this.selectedVertex.removeParent(data.vertex);
					}
				}
				this.selectedVertex.selected = false;
				this.selectedVertex = null;
			} else {
				this.selectedVertex = data.vertex;
				this.selectedVertex.selected = true;
			}
		}
	}

	mousemove(e) {
		if (e.buttons == 0) {
			if (this.moveable) this.moveable = null;
			const mousePosition = this.convertToGlobalPosition(new EdjllVector(e.clientX, e.clientY));

			if (this.isDesiredVertex(this.vertex, mousePosition, this.mousemoveAction)) return;

			for (let index in this.vertexes) {
				const vertex = this.vertexes[index];
				if (this.isDesiredVertex(vertex, mousePosition, this.mousemoveAction)) return;
			}
			this.canvas.style.cursor = 'default';
		} else if (e.buttons == 1) {
			const globalMousePosition = this.convertToGlobalPosition(new EdjllVector(e.clientX, e.clientY));

			if (this.moveable) {
				this.changeVertexPosition({constants: {globalMousePosition: globalMousePosition}});
			} else {
				if (this.isDesiredVertex(this.vertex, globalMousePosition, this.changeVertexPosition, {globalMousePosition: globalMousePosition})) return;

				for (let index in this.vertexes) {
					const vertex = this.vertexes[index];
					if (this.isDesiredVertex(vertex, globalMousePosition, this.changeVertexPosition, {globalMousePosition: globalMousePosition})) return;
				}
			}
		} else if (e.buttons == 2) {
			if (this.camera.position.x + e.movementX < this.camera.limit.x0) {
				this.camera.position.x = this.camera.limit.x0;
			} else if (this.camera.position.x + e.movementX > this.camera.limit.x1) {
				this.camera.position.x = this.camera.limit.x1;
			} else {
				this.camera.position.x += e.movementX;
			}
			if (this.camera.position.y + e.movementY < this.camera.limit.y0) {
				this.camera.position.y = this.camera.limit.y0;
			} else if (this.camera.position.y + e.movementY > this.camera.limit.y1) {
				this.camera.position.y = this.camera.limit.y1;
			} else {
				this.camera.position.y += e.movementY;
			}
		}
	}

	changeVertexPosition(data) {
		if (!this.moveable) this.moveable = data.vertex;
		this.moveable.position.x = Math.round(data.constants.globalMousePosition.x / this.scale.value);
		this.moveable.position.y = Math.round(data.constants.globalMousePosition.y / this.scale.value);
	}

	mousemoveAction() {
		this.canvas.style.cursor = 'pointer';
	}

	wheel(e) {
		if (e.ctrlKey) {
			e.preventDefault();
			const localMousePosition = this.convertToLocalPosition(new EdjllVector(e.clientX, e.clientY));

			if (this.scale.value - e.deltaY / 100 >= this.scale.min && this.scale.value - e.deltaY / 100 <= this.scale.max) {
				this.scale.value -= e.deltaY / 100;
				if (this.camera.position.x + this.canvas.width / 2 - localMousePosition.x < this.camera.limit.x0) {
					this.camera.position.x = this.camera.limit.x0;
				} else if (this.camera.position.x + this.canvas.width / 2 - localMousePosition.x > this.camera.limit.x1) {
					this.camera.position.x = this.camera.limit.x1;
				} else {
					this.camera.position.x += this.canvas.width / 2 - localMousePosition.x;
				}

				if (this.camera.position.y + this.canvas.height / 2 - localMousePosition.y < this.camera.limit.y0) {
					this.camera.position.y = this.camera.limit.y0;
				} else if (this.camera.position.y + this.canvas.height / 2 - localMousePosition.y > this.camera.limit.y1) {
					this.camera.position.y = this.camera.limit.y1;
				} else {
					this.camera.position.y += this.canvas.height / 2 - localMousePosition.y;
				}
			}

		} else {
			if (this.camera.position.x - e.deltaX / 5 < this.camera.limit.x0) {
				this.camera.position.x = this.camera.limit.x0;
			} else if (this.camera.position.x - e.deltaX / 5 > this.camera.limit.x1) {
				this.camera.position.x = this.camera.limit.x1;
			} else {
				this.camera.position.x -= e.deltaX / 5;
			}
			if (this.camera.position.y - e.deltaY / 5 < this.camera.limit.y0) {
				this.camera.position.y = this.camera.limit.y0;
			} else if (this.camera.position.y - e.deltaY / 5 > this.camera.limit.y1) {
				this.camera.position.y = this.camera.limit.y1;
			} else {
				this.camera.position.y -= e.deltaY / 5;
			}
		}
	}

	changeVertexType() {
		if (this.vertexType == EdjllVertexType.PARENT) {
			this.vertexType = EdjllVertexType.CHILD;
		} else {
			this.vertexType = EdjllVertexType.PARENT;
		}
	}

	changeActionType() {
		if (this.actionType == EdjllActionType.ADD) {
			this.actionType = EdjllActionType.REMOVE;
		} else {
			this.actionType = EdjllActionType.ADD;
		}
	}

	isDesiredVertex(vertex, mousePosition, action = null, constants) {
		if (!vertex) return false;
		if (
			(vertex.position.x - vertex.radius) * this.scale.value < mousePosition.x &&
			(vertex.position.x + vertex.radius) * this.scale.value > mousePosition.x &&
			(vertex.position.y - vertex.radius) * this.scale.value < mousePosition.y &&
			(vertex.position.y + vertex.radius) * this.scale.value > mousePosition.y
		) {
			if (action) {
				action.bind(this)(
					{
						vertex: vertex,
						constants: constants
					}
				);
			}
			return true;
		}
		return false;
	}

	sendRequest() {
		const xhr = new XMLHttpRequest();
		xhr.open('get', this.request.url, true);
		xhr.onload = () => {
			this.input(JSON.parse(xhr.response));
		}
		xhr.send();
	}

	input(data = null) {
		let position = new EdjllVector(10, 10);
		data.forEach((node) => {
			const vertexData = this.convertToVertex(node, new EdjllVector(position.x, position.y));
			this.parents.push(vertexData.vertex);
			position = new EdjllVector(position.x, vertexData.position.y + 10);
		});

		if (!this.attributes.id) {
			this.vertex = new EdjllVertex(-1, "Текущая", Math.round(this.canvas.width / 2 / this.scale.value), 5, true, {text: '#6252ef', backgroundColor: 'rgb(82 83 95)'});
		} else {
			this.vertex = this.vertexes.find(vertex => vertex.id == this.attributes.id);
			this.vertex.color = {text: '#6252ef', backgroundColor: 'rgb(82 83 95)'};
			this.vertexes.splice(this.vertexes.indexOf(this.vertex), 1);
		}

	}

	convertToVertex(data, position) {
		const vertex = new EdjllVertex(data.id, data.name, position.x, position.y, data.editable);
		this.vertexes.push(vertex);
		data.children.forEach((child, index) => {
			if (index > 0) position.y += 10;
			const vertexData = this.convertToVertex(child, new EdjllVector(position.x + 10, position.y));
			vertex.children.push(vertexData.vertex);
			vertexData.vertex.parent = vertex;
			position = new EdjllVector(position.x, vertexData.position.y);
		});
		if (this.camera.limit.x0 > -(position.x + 10) * this.scale.value + this.canvas.width) {
			this.camera.limit.x0 = -(position.x + 10) * this.scale.value + this.canvas.width;
		}
		if (this.camera.limit.y0 > -(position.y + 10) * this.scale.value + this.canvas.height) {
			this.camera.limit.y0 = -(position.y + 10) * this.scale.value + this.canvas.height;
		}
		return {
			vertex: vertex,
			position: position
		};
	}

	getValue() {
		return [this.vertex, ...this.vertexes].map(function(vertex) {
			return {
				id: vertex.id,
				name: vertex.name,
				parent: vertex.parent ? vertex.parent.id : null
			}
		});
	}

	validate() {
		return true;
	}

	loop() {
		this.ctx.save();

		this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
		this.ctx.translate(this.camera.position.x, this.camera.position.y);

		this.vertexes.forEach(vertex => {
			vertex.drawLine(this.ctx, this.scale.value);
		});

		if (this.vertex) {
			this.vertex.drawLine(this.ctx, this.scale.value);
		}

		this.vertexes.forEach(vertex => {
			vertex.draw(this.ctx, this.scale.value);
		});

		if (this.vertex) {
			this.vertex.draw(this.ctx, this.scale.value);
		}

		this.ctx.restore();

		window.requestAnimationFrame(this.loop.bind(this));
	}
}