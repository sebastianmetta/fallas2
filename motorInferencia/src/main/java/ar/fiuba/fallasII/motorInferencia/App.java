package ar.fiuba.fallasII.motorInferencia;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		final AbstractApplicationContext appContext = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");

		start(appContext);

		appContext.close();
	}

	private static void start(final ApplicationContext appContext) {
		final EntryPoint entryPoint = appContext.getBean(EntryPoint.class);
		entryPoint.run();
	}
}
