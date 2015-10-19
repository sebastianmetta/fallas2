package ar.fiuba.fallasII.motorInferencia.estrategias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

import ar.fiuba.fallasII.motorInferencia.modelo.BaseDeConocimiento;
import ar.fiuba.fallasII.motorInferencia.modelo.Premisa;
import ar.fiuba.fallasII.motorInferencia.modelo.Regla;

public class EncadenamientoHaciaAtras {

	private BaseDeConocimiento baseDeConocimiento;
	private ArrayList<Premisa> conocimientoAdquirido;
	private LinkedList<Premisa> pilaHipotesisProcesar;
	private List<Premisa> hipotesisProcesadas;
	private int nroIteracion;

	private static final Logger log = Logger.getLogger(EncadenamientoHaciaAdelante.class);

	public EncadenamientoHaciaAtras(BaseDeConocimiento baseDeConocimiento, List<Premisa> hechosIniciales) {
		this.baseDeConocimiento = baseDeConocimiento;
		
		this.conocimientoAdquirido = new ArrayList<Premisa>();
		this.conocimientoAdquirido.addAll(hechosIniciales);
		
		this.pilaHipotesisProcesar = new LinkedList<Premisa>();
		
		this.hipotesisProcesadas = new ArrayList<Premisa>();
		
		this.nroIteracion = 0;
	}

	public List<Premisa> evaluar(Premisa hipotesisInicial) {
		int nroIteracionActual = nroIteracion;

		if (pilaHipotesisProcesar.size() == 0 || (pilaHipotesisProcesar.size() > 0 && !pilaHipotesisProcesar.getLast().equals(hipotesisInicial)))
			pilaHipotesisProcesar.addLast(hipotesisInicial);

		Premisa hipotesisActual = pilaHipotesisProcesar.getLast();
		if (conocimientoAdquirido.contains(hipotesisActual)) {
			pilaHipotesisProcesar.remove(hipotesisActual);
			hipotesisProcesadas.add(hipotesisActual);
		} else {

			// Imprime los datos por consola
			log.info("");
			log.info("Iteracion " + nroIteracionActual + ". Base de conocimiento:");
			this.baseDeConocimiento.imprimir();
			log.info("Conocimiento adquirido hasta el momento: " + Arrays.toString(conocimientoAdquirido.toArray()));
			log.info("Cola de Hipotesis actual a verificar: " + Arrays.toString(pilaHipotesisProcesar.toArray()));
			log.info("Hipotesis actual a verificar: " + hipotesisActual);

			// reglasAplicables tiene todas las reglas cuya conclusion coincide con la hipotesis.
			List<Regla> reglasAplicables = baseDeConocimiento.obtenerReglasAplicablesSegunHecho(hipotesisActual);
			if (!reglasAplicables.isEmpty()) {

				// se elige la hipotesis segun una estrategia definida.
				Regla reglaADisparar = this.elegirRegla(reglasAplicables);
				log.info("Regla seleccionada: " + reglaADisparar.toString());

				// verificar si las premisas de la regla elegida se encuentran en la base de conocimiento.
				reglaADisparar.setEvaluada(true);
				List<Premisa> nuevasHipotesisAVerificar = reglaADisparar.obtenerPremisasNoCoincidentes(conocimientoAdquirido);
				
				boolean premisasEnBaseConocimiento = conocimientoAdquirido.containsAll(reglaADisparar.getPremisas());
				if (nuevasHipotesisAVerificar.size() > 0) {
					// nuestra por consola
					log.info("La hipotesis " + hipotesisActual.toString() + " no es deducible en forma completa mediante la regla " + reglaADisparar.toString());
					log.info("Nueva cola de hipotesis a verificar: " + Arrays.toString(nuevasHipotesisAVerificar.toArray()));

					// evalua si pudo deducir todas las hipotesis
					for (Premisa premisa : nuevasHipotesisAVerificar) {
						nroIteracion++;
						evaluar(premisa);
						
						if (!conocimientoAdquirido.contains(premisa)) {
							break;
						}
					}
					
					// evalua nuevamente si pudo incorporar todas las premisas
					premisasEnBaseConocimiento = conocimientoAdquirido.containsAll(reglaADisparar.getPremisas());
					
					// notifica en que iteracion estamos
					log.info("");
					log.info("Continua Iteracion " + nroIteracionActual + ". Base de conocimiento:");
				} else {
					premisasEnBaseConocimiento = true;
				}
				
				if (premisasEnBaseConocimiento) {
					// las premisas se encuentran en la regla, se dispara la
					// regla y se actualiza el banco de conocimiento
					conocimientoAdquirido.add(reglaADisparar.getHecho());
					pilaHipotesisProcesar.remove(hipotesisActual);
					hipotesisProcesadas.add(hipotesisActual);
					
					// se notifica por consola
					log.info("La hipotesis " + hipotesisActual.toString() + " es deducible mediante la regla " + reglaADisparar.toString());
					log.info("Se incluye " + reglaADisparar.getHecho() + " en el conocimiento adquirido hasta el momento.");
				} else {
					log.info("La hipotesis " + hipotesisActual.toString() + " NO es deducible mediante la regla " + reglaADisparar.toString());
					log.info("Se buscara una otra regla que permita deducir la hipotesis " + hipotesisActual);
					
					nroIteracion++;
					evaluar(hipotesisInicial);
				}

			} else {
				pilaHipotesisProcesar.remove(hipotesisActual);
				hipotesisProcesadas.add(hipotesisActual);

				log.info("No existe ninguna regla que permita deducir la hipotesis " + hipotesisActual);
			}
			
		}

		return conocimientoAdquirido;
	}

	/**
	 * Estrategia de eleccion de reglas.
	 */
	private Regla elegirRegla(List<Regla> reglasElegibles) {
		// Estrategia actual: Devuelve siempre la primera.
		return reglasElegibles.get(0);
	}

	public boolean continuar(List<Regla> reglasAplicables) {
		if (!reglasAplicables.isEmpty()) {
			return true;
		} else {
			log.info("No hay mas reglas a disparar.");
			return false;
		}
	}

}
