package ts.opap.joker.utils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class PCIDateTime {

	private PCIDateTime() {

	}

	public static LocalDateTime now() {
		return LocalDateTime.now(Clock.systemUTC());
	}

	public static LocalDate firstDayOfMonth(LocalDateTime date) {
		return LocalDate.of(date.getYear(), date.getMonth(), 1);
	}

}
