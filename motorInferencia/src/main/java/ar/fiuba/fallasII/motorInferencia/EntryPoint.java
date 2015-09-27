package ar.fiuba.fallasII.motorInferencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import ar.fiuba.fallasII.motorInferencia.dao.IGenericDao;
import ar.fiuba.fallasII.motorInferencia.estrategias.EncadenamientoHaciaAdelante;
import ar.fiuba.fallasII.motorInferencia.estrategias.EncadenamientoHaciaAtras;
import ar.fiuba.fallasII.motorInferencia.modelo.BaseDeConocimiento;
import ar.fiuba.fallasII.motorInferencia.modelo.Premisa;
import ar.fiuba.fallasII.motorInferencia.modelo.Regla;

@Component
public class EntryPoint {

	private static final Logger log = Logger.getLogger(EntryPoint.class);

	// @Autowired
	// private IGenericDao dataAccess;

	public EntryPoint() {
		System.out.println("EntryPoint()");
	}

	public void run() {
		System.out.println("EntryPoint.run()");
		// Regla rule = new Regla("Regla numero 1");
		// rule.setDescription("Descripcion de la regla 1");
		// dataAccess.save(rule);
		// log.info("Rule saved: " + rule.toString());
		// Regla ruleFound = dataAccess.get(Regla.class, rule.getId());
		// log.info("Rule found: " + ruleFound.toString());
		Scanner scanner = new Scanner(System.in);
		// scanner.useDelimiter("\\n");

		BaseDeConocimiento baseDeConocimiento = new BaseDeConocimiento();
		baseDeConocimiento.addReglas(this.obtenerReglas());

		log.info("Base de conocimiento:");
		baseDeConocimiento.imprimir();

		log.info("Por favor, seleccione la modalidad para la ejecución: ");
		log.info("1. Encadenamiento hacia adelante.");
		log.info("2. Encadenamiento hacia atras.");
		int opcion = scanner.nextInt();
		if (opcion == 1) {
			this.ejecutarEncadenamientoHaciaAdelante(baseDeConocimiento, scanner);
		}
		if (opcion == 2) {
			this.ejecutarEncadenamientoHaciaAtras(baseDeConocimiento, scanner);
		}

		scanner.close();
	}

	private List<Premisa> obtenerPremisasDelUsuario(Scanner scanner) {
		List<Premisa> premisas = new ArrayList<Premisa>();
		log.info("Ingrese una premisa como conocimiento inicial o 0 para terminar: ");
		String opcion = scanner.next();
		while (!opcion.equals("0")) {
			if (!opcion.equals("0")) {
				premisas.add(new Premisa(opcion));
			}
			log.info("Ingrese una premisa como conocimiento inicial o 0 para terminar: ");
			opcion = scanner.next();
		}

		return premisas;
	}

	private Premisa obtenerHipotesisDelUsuario(Scanner scanner) {
		log.info("Ingrese una hipotesis a verificar: ");
		String opcion = scanner.next();
		return (new Premisa(opcion));
	}

	private void ejecutarEncadenamientoHaciaAdelante(BaseDeConocimiento baseDeConocimiento, Scanner scanner) {
		EncadenamientoHaciaAdelante fc = new EncadenamientoHaciaAdelante(baseDeConocimiento);
		List<Premisa> hechosIniciales = this.obtenerPremisasDelUsuario(scanner);
		List<Premisa> conocimientoAdquirido = fc.evaluar(hechosIniciales);
		log.info("Conocimiento adquirido: " + Arrays.toString(conocimientoAdquirido.toArray()));
	}

	private void ejecutarEncadenamientoHaciaAtras(BaseDeConocimiento baseDeConocimiento, Scanner scanner) {
		EncadenamientoHaciaAtras bc = new EncadenamientoHaciaAtras(baseDeConocimiento);
		List<Premisa> hechosIniciales = this.obtenerPremisasDelUsuario(scanner);
		Premisa hipotesisInicial = this.obtenerHipotesisDelUsuario(scanner);
		List<Premisa> conocimientoAdquirido = bc.evaluar(hechosIniciales, hipotesisInicial);
		
		log.info("Conocimiento adquirido: " + Arrays.toString(conocimientoAdquirido.toArray()));
		if (conocimientoAdquirido.contains(hipotesisInicial)) {
			log.info("La hipótesis " + hipotesisInicial + " fue deducida.");
		} else {
			log.info("La hipótesis " + hipotesisInicial + " no fue deducida.");
		}
	}

	private List<Regla> obtenerReglas() {
		List<Regla> reglas = new ArrayList<Regla>();

		Regla r1 = new Regla();
		List<Premisa> premisasR1 = new ArrayList<Premisa>();
		premisasR1.add(new Premisa("p"));
		premisasR1.add(new Premisa("q"));
		r1.setNombre("R1");
		r1.setHecho(new Premisa("s"));
		r1.setPremisas(premisasR1);
		reglas.add(r1);

		Regla r2 = new Regla();
		List<Premisa> premisasR2 = new ArrayList<Premisa>();
		premisasR2.add(new Premisa("r"));
		r2.setNombre("R2");
		r2.setHecho(new Premisa("t"));
		r2.setPremisas(premisasR2);
		reglas.add(r2);

		Regla r3 = new Regla();
		List<Premisa> premisasR3 = new ArrayList<Premisa>();
		premisasR3.add(new Premisa("s"));
		premisasR3.add(new Premisa("t"));
		r3.setNombre("R3");
		r3.setHecho(new Premisa("u"));
		r3.setPremisas(premisasR3);
		reglas.add(r3);

		Regla r4 = new Regla();
		List<Premisa> premisasR4 = new ArrayList<Premisa>();
		premisasR4.add(new Premisa("s"));
		premisasR4.add(new Premisa("r"));
		r4.setNombre("R4");
		r4.setHecho(new Premisa("v"));
		r4.setPremisas(premisasR4);
		reglas.add(r4);

		return reglas;
	}

}