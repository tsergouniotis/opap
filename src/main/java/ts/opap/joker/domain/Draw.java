package ts.opap.joker.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Draw implements Serializable {

	private static final int JOKER = 5;

	@JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss", timezone = "UTC")
	private Calendar drawTime;

	private Integer drawNo;

	private int[] results = new int[6];

	public Calendar getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(Calendar drawTime) {
		this.drawTime = drawTime;
	}

	public Integer getDrawNo() {
		return drawNo;
	}

	public void setDrawNo(Integer drawNo) {
		this.drawNo = drawNo;
	}

	public int[] getResults() {
		return results;
	}

	public void setResults(int[] results) {
		this.results = results;
	}

	public int getJoker() {
		return results[JOKER];
	}

	public void append(Map<Integer, Integer> numbers, Map<Integer, Integer> jokers) {
		for (int i = 0; i < results.length - 1; i++) {
			int number = results[i];
			if (!numbers.containsKey(number)) {
				numbers.put(number, 0);
			}

			Integer freq = numbers.get(Integer.valueOf(number));

			numbers.put(number, ++freq);

		}

		int joker = getJoker();
		if (!jokers.containsKey(joker)) {
			jokers.put(joker, 0);
		}

		Integer freq = jokers.get(joker);
		jokers.put(joker, ++freq);

	}

}
