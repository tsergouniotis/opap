package ts.opap.joker.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public final class DBUtils {

	private DBUtils() {

	}

	protected static Connection getConnection() throws Exception {
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection(JDBC_URL, jdbcProps());
	}

	private static Properties jdbcProps() {
		Properties props = new Properties();
		props.setProperty("user", DB_USER);
		props.setProperty("password", DB_PASSWORD);
		return props;
	}

	private static String JDBC_URL = null;

	public static String url() {
		if (null == JDBC_URL) {
			StringBuilder b = new StringBuilder("jdbc:postgresql://").append(System.getenv("OPAP_DB_HOST")).append(":")
					.append(System.getenv("OPAP_DB_PORT")).append("/").append(DB_USER);
			JDBC_URL = b.toString();
		}
		return JDBC_URL;
	}

	private static final String DB_USER = "opap";

	public static String user() {
		return DB_USER;
	}

	private static final String DB_PASSWORD = "opap";

	public static String pass() {
		return DB_PASSWORD;
	}

}
