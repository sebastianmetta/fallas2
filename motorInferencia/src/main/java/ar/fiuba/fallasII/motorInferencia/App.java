package ar.fiuba.fallasII.motorInferencia;

import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.OServerMain;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.AbstractApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	// public static void main(String[] args) {
	// final AbstractApplicationContext appContext = new
	// ClassPathXmlApplicationContext(
	// "classpath:applicationContext.xml");
	// start(appContext);
	// appContext.close();
	// }
	//
	// private static void start(final ApplicationContext appContext) {
	// final EntryPoint entryPoint = appContext.getBean(EntryPoint.class);
	// entryPoint.run();
	// }

	public static void main(String[] args) {
		OServer server = null;
		try {
			server = OServerMain.create();
			server.startup("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<orient-server>"
					+ "<network>" + "<protocols>"
					+ "<protocol name=\"binary\" implementation=\"com.orientechnologies.orient.server.network.protocol.binary.ONetworkProtocolBinary\"/>"
					+ "<protocol name=\"http\" implementation=\"com.orientechnologies.orient.server.network.protocol.http.ONetworkProtocolHttpDb\"/>"
					+ "</protocols>" + "<listeners>"
					+ "<listener ip-address=\"0.0.0.0\" port-range=\"2424-2430\" protocol=\"binary\"/>"
					+ "<listener ip-address=\"0.0.0.0\" port-range=\"2480-2490\" protocol=\"http\"/>" + "</listeners>"
					+ "</network>" + "<users>" + "<user name=\"root\" password=\"root\" resources=\"*\"/>"
					+ "</users>" + "<properties>"
					+ "<entry name=\"orientdb.www.path\" value=\"C:/work/dev/orientechnologies/orientdb/releases/1.0rc1-SNAPSHOT/www/\"/>"
					+ "<entry name=\"orientdb.config.file\" value=\"C:/work/dev/orientechnologies/orientdb/releases/1.0rc1-SNAPSHOT/config/orientdb-server-config.xml\"/>"
					+ "<entry name=\"server.cache.staticResources\" value=\"false\"/>"
					+ "<entry name=\"log.console.level\" value=\"info\"/>"
					+ "<entry name=\"log.file.level\" value=\"fine\"/>"
					// The following is required to eliminate an error or
					// warning "Error on resolving property: ORIENTDB_HOME"
					+ "<entry name=\"plugin.dynamic\" value=\"false\"/>" + "</properties>" + "</orient-server>");
			server.activate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final EntryPoint entryPoint = new EntryPoint();
		entryPoint.run();
		
		if (server != null)
			server.shutdown();
	}
}
