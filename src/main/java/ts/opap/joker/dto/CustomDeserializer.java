package ts.opap.joker.dto;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ts.opap.joker.domain.Draw;

public class CustomDeserializer extends JsonDeserializer<Draw> {

	@Override
	public Draw deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

		ObjectCodec oc = jsonParser.getCodec();
		JsonNode rootNode = oc.readTree(jsonParser);
		JsonNode childNode = rootNode.get("draw");

		Draw draw = new Draw();

		Date date = date(childNode.get("drawTime").asText());
		draw.setDrawTime(date);
		int drawNo = childNode.get("drawNo").asInt();
		draw.setDrawNo(drawNo);
		Integer[] value = new ObjectMapper().readValue(childNode.get("results").toString(), Integer[].class);

		for (int c = 0; c < value.length; c++) {

			switch (c) {
			case 0:
				draw.setNumber1(value[c]);
				break;
			case 1:
				draw.setNumber2(value[c]);
				break;
			case 2:
				draw.setNumber3(value[c]);
				break;
			case 3:
				draw.setNumber4(value[c]);
				break;
			case 4:
				draw.setNumber5(value[c]);
				break;
			case 5:
				draw.setJoker(value[c]);
				break;
			default:
				break;
			}

		}

		return draw;
	}

	private Date date(String date) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
