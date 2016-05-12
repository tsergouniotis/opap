package ts.opap.joker.dto;

import java.io.Serializable;

import ts.opap.joker.domain.Draw;

public class DrawWrapper implements Serializable {

	private Draw draw;

	public Draw getDraw() {
		return draw;
	}

	public void setDraw(Draw draw) {
		this.draw = draw;
	}

}
