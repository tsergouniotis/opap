package ts.opap.joker.main;

import java.sql.Connection;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jpa.JPAFraction;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import ts.opap.joker.resources.OpapResource;

public class Main {

	public static void main(String[] args) throws Exception {

		Container container = new Container();

		// Configure the Datasources subsystem with a driver
		// and a datasource
		container.fraction(new DatasourcesFraction().jdbcDriver("postgresql", (d) -> {
			d.driverClassName("org.postgresql.Driver");
			d.xaDatasourceClass("org.postgresql.ds.PGSimpleDataSource");
			d.driverModuleName("org.postgresql");
		}).dataSource("OpapDS", (ds) -> {
			ds.driverName("postgresql");
			ds.connectionUrl(DBUtils.url());
			ds.userName(DBUtils.user());
			ds.password(DBUtils.pass());
		}));

		// Prevent JPA Fraction from installing it's default datasource fraction
		container.fraction(new JPAFraction().inhibitDefaultDatasource().defaultDatasource("jboss/datasources/OpapDS"));

		// container.fraction(TransactionsFraction.createDefaultFraction());

		// perform liquibase migration
		migrate();

		// Start the container
		container.start();

		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
		deployment.addPackages(true, "ts.opap");
		deployment.addDependency("org.liquibase:liquibase-core");
		deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()),
				"classes/META-INF/persistence.xml");
		deployment.addResource(OpapResource.class);

		// Deploy your app
		container.deploy(deployment);

	}

	private static void migrate() throws Exception {
		try (Connection c = DBUtils.getConnection()) {
			Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
			Liquibase liquibase = new Liquibase("db/ts/opap/joker/db.changelog-master.xml",
					new ClassLoaderResourceAccessor(), database);
			liquibase.update(new Contexts());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
