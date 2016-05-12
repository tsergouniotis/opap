package ts.opap.joker.resources;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ts.opap.joker.domain.Draw;
import ts.opap.joker.dto.DrawWrapper;

@Path("/opap")
public class OpapResource {

	@GET
	@Path("/sync/{draw}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response sync(@PathParam("draw") int drawNumber) {

		String url = "http://applications.opap.gr/DrawsRestServices/joker/{0}.json";

		Map<Integer, Integer> numbers = new HashMap<>();
		Map<Integer, Integer> jokers = new HashMap<>();

		int d = 1;
		for (int i = 1; i <= drawNumber; i++) {
			Draw draw = extract(url, d);
			draw.append(numbers, jokers);
		}

		numbers = sortHashMapByValues(numbers);
		jokers = sortHashMapByValues(jokers);

		int[] nums = new int[6];

		nums[5] = jokers.entrySet().iterator().next().getKey();

		int count = 0;
		Set<Entry<Integer, Integer>> entrySet = numbers.entrySet();
		for (Entry<Integer, Integer> entry : entrySet) {
			nums[count++] = entry.getKey();
			if (count == 4) {
				break;
			}
		}

		Draw newDraw = new Draw();
		newDraw.setResults(nums);
		newDraw.setDrawNo(drawNumber + 1);
		newDraw.setDrawTime(Calendar.getInstance());

		DrawWrapper wrapper = new DrawWrapper();
		wrapper.setDraw(newDraw);
		return Response.ok(wrapper).build();
	}

	private Draw extract(String url, int d) {
		url = MessageFormat.format(url, Integer.valueOf(d).toString());
		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(url);
		Builder builder = target.request();
		Response response = builder.get();

		DrawWrapper entity = response.readEntity(DrawWrapper.class);

		Draw result = entity.getDraw();
		return result;
	}

	public LinkedHashMap<Integer, Integer> sortHashMapByValues(Map<Integer, Integer> passedMap) {
		List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
		List<Integer> mapValues = new ArrayList<>(passedMap.values());
		Collections.sort(mapValues);
//		Collections.reverse(mapValues);
		Collections.sort(mapKeys);
//		Collections.reverse(mapKeys);

		LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();

		Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Integer val = valueIt.next();
			Iterator<Integer> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Integer key = keyIt.next();
				Integer comp1 = passedMap.get(key);
				Integer comp2 = val;

				if (comp1.equals(comp2)) {
					keyIt.remove();
					sortedMap.put(key, val);
					break;
				}
			}
		}
		return sortedMap;
	}

}
