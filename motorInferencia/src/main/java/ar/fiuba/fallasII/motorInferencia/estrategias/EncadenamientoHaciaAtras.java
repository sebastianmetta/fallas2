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

	private static final Logger log = Logger.getLogger(EncadenamientoHaciaAdelante.class);

	public EncadenamientoHaciaAtras(BaseDeConocimiento baseDeConocimiento) {
		this.baseDeConocimiento = baseDeConocimiento;
	}

	public List<Premisa> evaluar(List<Premisa> hechosIniciales, Premisa hipotesisInicial) {
		boolean continuar = true;
		List<Premisa> conocimientoAdquirido = new ArrayList<Premisa>();
		conocimientoAdquirido.addAll(hechosIniciales);
		LinkedList<Premisa> colaHipotesis = new LinkedList<Premisa>();
		List<Premisa> hipotesisProcesadas = new ArrayList<Premisa>();
		colaHipotesis.addLast(hipotesisInicial);
		int nroIteracion = 0;
		Premisa hipotesisActual = colaHipotesis.getLast();
		while (continuar) {
			if (conocimientoAdquirido.contains(hipotesisInicial)) {
				continuar = false;
			} else {
				log.info("Iteracion " + nroIteracion + ". Base de conocimiento:");
				nroIteracion++;
				this.baseDeConocimiento.imprimir();
				log.info("Conocimiento adquirido hasta el momento: " + Arrays.toString(conocimientoAdquirido.toArray()));
				log.info("Hipotesis actual a verificar: " + hipotesisActual);
				List<Regla> reglasAplicables = baseDeConocimiento.obtenerReglasAplicablesSegunHecho(hipotesisActual);
				if (!reglasAplicables.isEmpty()) {
					// reglasAplicables tiene todas las reglas cuya conclusion coincide con la hipotesis.
					Regla reglaADisparar = this.elegirRegla(reglasAplicables);
					// verificar si las premisas de la regla elegida se encuentran en la base de conocimiento.
					log.info("Regla seleccionada: " + reglaADisparar.toString());
					if (conocimientoAdquirido.containsAll(reglaADisparar.getPremisas())) {
						// La hipotesis es deducible mediante la regla.
						log.info("La hipotesis " + hipotesisActual.toString() + " es deducible mediante la regla " + reglaADisparar.toString());
						// se incluye el hecho de la hipotesis en el conocimiento adquirido.
						log.info("Se incluye " + reglaADisparar.getHecho() + " en el conocimiento adquirido hasta el momento.");
						conocimientoAdquirido.add(reglaADisparar.getHecho());
						reglaADisparar.setEvaluada(true);
						colaHipotesis.remove(hipotesisActual);
						hipotesisProcesadas.add(hipotesisActual);
					} else {
						log.info("La hipotesis " + hipotesisActual.toString() + " no es deducible en forma completa mediante la regla " + reglaADisparar.toString());
						List<Premisa> nuevasHipotesisAVerificar = reglaADisparar.obtenerPremisasNoCoincidentes(conocimientoAdquirido);
						colaHipotesis.addAll(nuevasHipotesisAVerificar);
						for (Premisa each : nuevasHipotesisAVerificar) {
							if (!hipotesisProcesadas.contains(each)) {								
								log.info("Se agrega hipotesis para intentar verificar: " + each.toString());
							} else {
								//Se intento agregar una hipotesis que ya fue verificada y no pudo ser deducida. 
								continuar=false;
							}
						}
						log.info("Cola de hipotesis a verificar: " + Arrays.toString(colaHipotesis.toArray()));
					}
				} else {
					log.info("No existe ninguna regla que permita deducir la hipotesis " + hipotesisActual);
					colaHipotesis.remove(hipotesisActual);
					hipotesisProcesadas.add(hipotesisActual);
				}
				if (colaHipotesis.size() > 0) {
					hipotesisActual = colaHipotesis.getLast();
				} else {
					continuar = false;
				}
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
