package ar.fiuba.fallasII.motorInferencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import ar.fiuba.fallasII.motorInferencia.dao.IGenericDao;
import ar.fiuba.fallasII.motorInferencia.estrategias.EncadenamientoHaciaAdelante;
import ar.fiuba.fallasII.motorInferencia.estrategias.EncadenamientoHaciaAtras;
import ar.fiuba.fallasII.motorInferencia.modelo.BaseDeConocimiento;
import ar.fiuba.fallasII.motorInferencia.modelo.ContenedorMarcos;
import ar.fiuba.fallasII.motorInferencia.modelo.Marco;
import ar.fiuba.fallasII.motorInferencia.modelo.Premisa;
import ar.fiuba.fallasII.motorInferencia.modelo.RedSemantica;
import ar.fiuba.fallasII.motorInferencia.modelo.Regla;
import ar.fiuba.fallasII.motorInferencia.modelo.Relacion;
import ar.fiuba.fallasII.motorInferencia.modelo.TipoRelacion;

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
		Scanner scanner = new Scanner(System.in);

		log.info("Por favor, seleccione la modalidad para la ejecución: ");
		log.info("1. Encadenamiento hacia adelante.");
		log.info("2. Encadenamiento hacia atras.");
		log.info("3. Red Semantica.");
		log.info("4. Marcos.");
		int opcion = scanner.nextInt();
		if (opcion < 3) {

			BaseDeConocimiento baseDeConocimiento = new BaseDeConocimiento();
			baseDeConocimiento.addReglas(this.obtenerReglas());

			log.info("Base de conocimiento:");
			baseDeConocimiento.imprimir();

			if (opcion == 1) {
				this.ejecutarEncadenamientoHaciaAdelante(baseDeConocimiento, scanner);
			} else if (opcion == 2) {
				this.ejecutarEncadenamientoHaciaAtras(baseDeConocimiento, scanner);
			}
		} else if (opcion == 3) {
			this.armarRedSemantica(scanner);
		} else if (opcion == 4) {
			this.armarMarcos(scanner);
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
		List<Premisa> hechosIniciales = this.obtenerPremisasDelUsuario(scanner);
		Premisa hipotesisInicial = this.obtenerHipotesisDelUsuario(scanner);

		EncadenamientoHaciaAtras bc2 = new EncadenamientoHaciaAtras(baseDeConocimiento, hechosIniciales);
		List<Premisa> conocimientoAdquirido = bc2.evaluar(hipotesisInicial);

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
		premisasR4.add(new Premisa("x"));
		premisasR4.add(new Premisa("r"));
		r4.setNombre("R4");
		r4.setHecho(new Premisa("v"));
		r4.setPremisas(premisasR4);
		reglas.add(r4);

		Regla r5 = new Regla();
		List<Premisa> premisasR5 = new ArrayList<Premisa>();
		premisasR5.add(new Premisa("s"));
		premisasR5.add(new Premisa("y"));
		r5.setNombre("R5");
		r5.setHecho(new Premisa("x"));
		r5.setPremisas(premisasR5);
		reglas.add(r5);

		Regla r6 = new Regla();
		List<Premisa> premisasR6 = new ArrayList<Premisa>();
		premisasR6.add(new Premisa("a"));
		r6.setNombre("R6");
		r6.setHecho(new Premisa("x"));
		r6.setPremisas(premisasR6);
		reglas.add(r6);

		return reglas;
	}

	// ------------------------------------------------------------------------------------------------------
	// REDES SEMANTICAS
	// ------------------------------------------------------------------------------------------------------

	private void armarRedSemantica(Scanner scanner) {
		List<Relacion> relaciones = null;

		log.info("Desea usar set de datos de ejemplo precargados? (S/N)");
		String opcion = scanner.next();
		if (opcion.equalsIgnoreCase("S")) {
			relaciones = obtenerRelaciones();

		} else {
			List<TipoRelacion> tiporelacionesIniciales = this.obtenerTipoRelacionesDelUsuario(scanner);
			List<Premisa> premisasIniciales = this.obtenerTipoPremisasDelUsuario(scanner);
			relaciones = armarRelacionesDelUsuario(premisasIniciales, tiporelacionesIniciales, scanner);
		}

		// Arma la red Semantica
		RedSemantica redSemantica = new RedSemantica();
		for (Relacion relacion : relaciones) {
			redSemantica.addRelacion(relacion);
		}

		log.info("");
		log.info("RED SEMANTICA CREADA: ");

		log.info("");
		log.info("Red Semantica agrupada por PREMISA: ");
		redSemantica.imprimirPorPremisa();

		log.info("");
		log.info("Red Semantica agrupada por TIPO DE RELACION: ");
		redSemantica.imprimirPorTipoRelacion();

	}

	private List<Premisa> obtenerTipoPremisasDelUsuario(Scanner scanner) {
		List<Premisa> premisas = new ArrayList<Premisa>();
		log.info("Ingrese el nombre de la nueva PREMISA o 0 para terminar: ");
		String opcion = scanner.next();
		while (!opcion.equals("0")) {
			if (!opcion.equals("0")) {
				premisas.add(new Premisa(opcion));
			}
			log.info("Ingrese el nombre de la nueva PREMISA o 0 para terminar: ");
			opcion = scanner.next();
		}

		return premisas;
	}

	private List<TipoRelacion> obtenerTipoRelacionesDelUsuario(Scanner scanner) {
		List<TipoRelacion> relaciones = new ArrayList<TipoRelacion>();
		log.info("Ingrese el nombre del nuevo TIPO DE RELACION o 0 para terminar: ");
		String opcion = scanner.next();
		while (!opcion.equals("0")) {
			if (!opcion.equals("0")) {
				relaciones.add(new TipoRelacion(opcion));
			}
			log.info("Ingrese el nombre del nuevo TIPO DE RELACION o 0 para terminar: ");
			opcion = scanner.next();
		}

		return relaciones;
	}

	private void mostrarTipoRelacionDelUsuario(List<TipoRelacion> lista) {
		int i = 0;
		for (TipoRelacion object : lista) {
			i++;
			log.info(i + ") " + object.toString());
		}
	}

	private TipoRelacion seleccioneTipoRelacionDelUsuario(List<TipoRelacion> lista, Scanner scanner) {
		TipoRelacion tipoRelacion = null;

		while (tipoRelacion == null) {
			log.info("Elija el tipo de nueva relacion de la red: ");
			mostrarTipoRelacionDelUsuario(lista);
			log.info("Ingrese el numero: ");
			String opcion = scanner.next();

			try {
				int index = Integer.parseInt(opcion);
				tipoRelacion = lista.get(index - 1);
			} catch (Exception e) {
				log.info("Seleccion incorrecta!");
			}
		}

		return tipoRelacion;
	}

	private void mostrarPremisaDelUsuario(List<Premisa> lista) {
		int i = 0;
		for (Premisa object : lista) {
			i++;
			log.info(i + ") " + object.toString());
		}
	}

	private Premisa seleccionePremisaDelUsuario(List<Premisa> lista, TipoRelacion tipoRelacion, Premisa primerPremisa, Scanner scanner) {
		Premisa premisa = null;

		while (premisa == null) {

			if (primerPremisa == null)
				log.info("Seleccione la primer premisa: XX " + tipoRelacion.toString() + " YY");
			else
				log.info("Seleccione la segunda premisa: " + primerPremisa.getValor() + " " + tipoRelacion.toString() + " YY");

			mostrarPremisaDelUsuario(lista);
			log.info("Ingrese el numero: ");
			String opcion = scanner.next();

			try {
				int index = Integer.parseInt(opcion);
				premisa = lista.get(index - 1);
			} catch (Exception e) {
				log.info("Seleccion incorrecta!");
			}
		}

		return premisa;
	}

	private List<Relacion> armarRelacionesDelUsuario(List<Premisa> premisas, List<TipoRelacion> tipoRelaciones, Scanner scanner) {
		List<Relacion> relaciones = new ArrayList<Relacion>();

		log.info("Agrega una nueva relacion? (S/N)");
		String opcion = scanner.next();
		while (!opcion.equalsIgnoreCase("N")) {
			TipoRelacion tipoRelacion = seleccioneTipoRelacionDelUsuario(tipoRelaciones, scanner);
			Premisa primerPremisa = seleccionePremisaDelUsuario(premisas, tipoRelacion, null, scanner);
			Premisa segundaPremisa = seleccionePremisaDelUsuario(premisas, tipoRelacion, primerPremisa, scanner);
			Relacion relacion = new Relacion(tipoRelacion, primerPremisa, segundaPremisa);
			relaciones.add(relacion);

			log.info("Se ha agregado la nueva relacion: " + relacion.toString());
			log.info("Agrega una nueva relacion? (S/N)");
			opcion = scanner.next();
		}

		return relaciones;
	}

	private List<Relacion> obtenerRelaciones() {
		List<Relacion> relaciones = new ArrayList<Relacion>();

		// Tipos de Relacion;
		TipoRelacion trEsUn = new TipoRelacion("es_un");
		TipoRelacion trMayor = new TipoRelacion("mayor_que");
		TipoRelacion trMenor = new TipoRelacion("menor_que");
		List<TipoRelacion> tipoRelaciones = new ArrayList<TipoRelacion>();
		tipoRelaciones.add(trEsUn);
		tipoRelaciones.add(trMayor);
		tipoRelaciones.add(trMenor);
		log.info("");
		log.info("Tipo de Relaciones del set de datos: ");
		mostrarTipoRelacionDelUsuario(tipoRelaciones);

		// Premisas
		Premisa pA = new Premisa("a");
		Premisa pB = new Premisa("b");
		Premisa pC = new Premisa("c");
		Premisa pA1 = new Premisa("a1");
		Premisa pB1 = new Premisa("b1");
		List<Premisa> premisas = new ArrayList<Premisa>();
		premisas.add(pA);
		premisas.add(pB);
		premisas.add(pC);
		premisas.add(pA1);
		premisas.add(pB1);
		log.info("");
		log.info("Premisas del set de datos: ");
		mostrarPremisaDelUsuario(premisas);

		Relacion r1 = new Relacion(trEsUn, pA, pA1);
		relaciones.add(r1);
		Relacion r2 = new Relacion(trMenor, pA, pB);
		relaciones.add(r2);
		Relacion r3 = new Relacion(trMenor, pB, pC);
		relaciones.add(r3);
		Relacion r4 = new Relacion(trMayor, pB, pA);
		relaciones.add(r4);
		Relacion r5 = new Relacion(trMayor, pC, pA1);
		relaciones.add(r5);
		Relacion r6 = new Relacion(trMayor, pB, pA);
		relaciones.add(r6);
		Relacion r7 = new Relacion(trMayor, pC, pB1);
		relaciones.add(r7);
		Relacion r8 = new Relacion(trEsUn, pB1, pB);
		relaciones.add(r8);
		Relacion r9 = new Relacion(trEsUn, pA1, pA);
		relaciones.add(r9);
		Relacion r10 = new Relacion(trMenor, pA1, pC);
		relaciones.add(r10);
		Relacion r11 = new Relacion(trMayor, pB1, pA);
		relaciones.add(r11);
		Relacion r12 = new Relacion(trMayor, pB, pA1);
		relaciones.add(r12);
		Relacion r13 = new Relacion(trEsUn, pB, pB1);
		relaciones.add(r13);
		log.info("");
		log.info("Relaciones del set de datos: ");
		for (Relacion relacion : relaciones) {
			log.info(relacion.toString());
		}

		return relaciones;
	}

	/**
	 * ***************************************************
	 * ******************** MARCOS ***********************
	 * ***************************************************
	 */

	private void armarMarcos(Scanner scanner) {
		log.info("Desea usar set de datos de ejemplo precargados? (S/N)");
		String opcion = scanner.next();
		ContenedorMarcos contenedorMarcos = new ContenedorMarcos();
		if (opcion.equalsIgnoreCase("S")) {
			contenedorMarcos.cargarDatosDePrueba();
		} else {
			// Se piden marcos al usuario.
			String nombreMarco = "";
			while (!nombreMarco.contains("done")) {
				log.info("Ingrese el nombre del marco y presione ENTER. Para finalizar, ingrese 'done' y presione ENTER.");
				nombreMarco = scanner.nextLine();
				if (!nombreMarco.contains("done") && !nombreMarco.isEmpty()) {
					// Ahora se pide al usuario que ingrese las caracteristicas que dispone.
					HashSet<String> caracteristicasMarco = new HashSet<String>();
					log.info("Ingrese las caracteristicas escribiendolas en consola y presionando ENTER. Para finalizar, ingrese 'done' y presione ENTER.");
					String caracteristicaMarco = "";
					while (!caracteristicaMarco.contains("done")) {
						caracteristicaMarco = scanner.nextLine();
						if (!caracteristicaMarco.contains("done") && !caracteristicaMarco.isEmpty()) {
							caracteristicasMarco.add(caracteristicaMarco);
							log.info("Se ha agregado la caracteristica: " + caracteristicaMarco);
						}
					}
					contenedorMarcos.addMarco(new Marco(nombreMarco, caracteristicasMarco));
					log.info("Se agregó un nuevo Marco a la base de conocimientos (" + nombreMarco + ")");
				}
			}
		}

		log.info("Se muestra la base de conocimientos: ");
		contenedorMarcos.imprimirContenido();

		// Ahora se pide al usuario que ingrese las caracteristicas que dispone.
		HashSet<String> caracteristicasUsuario = new HashSet<String>();
		log.info("Ingrese las caracteristicas escribiendolas en consola y presionando ENTER. Para finalizar, ingrese 'done' y presione ENTER.");
		String caracteristica = "";
		while (!caracteristica.contains("done")) {
			caracteristica = scanner.nextLine();
			if (!caracteristica.contains("done") && !caracteristica.isEmpty()) {
				caracteristicasUsuario.add(caracteristica);
				log.info("Se ha agregado la caracteristica: " + caracteristica);
			}
		}
		scanner.close();
		log.info("Carga de características finalizada. Las características ingresadas son: ");
		for (String eachCaracteristicaIngresada : caracteristicasUsuario) {
			log.info(eachCaracteristicaIngresada);
		}

		log.info("A continuación se realizará la búsqueda de Marcos:");
		boolean found = false;
		// Se busca un marco que se corresponda con las caracteristicas ingresadas por el usuario.
		for (Marco eachMarco : contenedorMarcos.getMarcoList()) {
			if (eachMarco.matches(caracteristicasUsuario)) {
				found = true;
				log.info("Coincidencia de Marco:");
				eachMarco.imprimir();
				break;
			}
		}
		if (!found) {
			log.info("No existe ningún Marco que coincida con las características ingresadas.");
		}

	}

}
