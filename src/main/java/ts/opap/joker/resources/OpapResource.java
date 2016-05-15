package ts.opap.joker.resources;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
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
import ts.opap.joker.domain.JokerFrequency;
import ts.opap.joker.domain.NumberFrequency;
import ts.opap.joker.persistence.repositories.DrawRepository;
import ts.opap.joker.persistence.repositories.JokerFrequencyRepository;
import ts.opap.joker.persistence.repositories.NumberFrequencyRepository;

@Path("/opap")
public class OpapResource {

	private static final String URL = "http://applications.opap.gr/DrawsRestServices/joker/{0}.json";

	@Inject
	private DrawRepository drawRepository;

	@Inject
	private NumberFrequencyRepository numberFrequencyRepository;

	@Inject
	private JokerFrequencyRepository jokerFrequencyRepository;

	@GET
	@Path("/sync/{draw}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response sync(@PathParam("draw") int latest) {

		int last = 0;
		Integer drawNumber = drawRepository.findLastDrawNumber();
		if (null != drawNumber) {
			last = drawNumber;
		}

		for (int i = last; i < latest; i++) {
			drawRepository.save(draw(i + 1));
		}

		Draw entity = drawRepository.find(latest);

		return Response.ok(entity).build();
	}

	@GET
	@Path("/analyse")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response analyze() {

		numberFrequencyRepository.deleteAll();
		jokerFrequencyRepository.deleteAll();

		Map<Integer, NumberFrequency> numberFreqs = new HashMap<>();
		Map<Integer, JokerFrequency> jokerFreqs = new HashMap<>();
		List<Draw> all = drawRepository.findAll();
		for (Draw draw : all) {

			int number = draw.getNumber1();
			process(number, numberFreqs);

			number = draw.getNumber2();
			process(number, numberFreqs);

			number = draw.getNumber3();
			process(number, numberFreqs);

			number = draw.getNumber4();
			process(number, numberFreqs);

			number = draw.getNumber5();
			process(number, numberFreqs);

			number = draw.getJoker();
			processJoker(number, jokerFreqs);

		}

		numberFrequencyRepository.saveAll(numberFreqs.values());

		jokerFrequencyRepository.saveAll(jokerFreqs.values());

		Map<String, Object> res = new HashMap<>();

		res.put("numbers", numberFrequencyRepository.findNumberFrequenciesOrdered());
		res.put("joker", jokerFrequencyRepository.findNumberFrequenciesOrdered());

		return Response.ok(res).build();

	}

	private static void process(int number, Map<Integer, NumberFrequency> numberFreqs) {
		NumberFrequency freq = null;
		if (!numberFreqs.containsKey(number)) {
			freq = new NumberFrequency(number);
			numberFreqs.put(number, freq);
		}
		freq = numberFreqs.get(number);
		freq.increaseFreq();

	}

	private static void processJoker(int number, Map<Integer, JokerFrequency> jokerFreqs) {
		JokerFrequency freq = null;
		if (!jokerFreqs.containsKey(number)) {
			freq = new JokerFrequency(number);
			jokerFreqs.put(number, freq);
		}
		freq = jokerFreqs.get(number);
		freq.increaseFreq();
	}

	private Draw draw(int drawNumber) {
		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(MessageFormat.format(URL, Integer.toString(drawNumber)));
		Builder builder = target.request();
		Response response = builder.get();

		return response.readEntity(Draw.class);
	}

}
