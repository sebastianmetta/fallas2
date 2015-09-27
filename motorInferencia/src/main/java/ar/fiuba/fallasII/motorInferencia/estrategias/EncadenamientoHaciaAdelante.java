package ar.fiuba.fallasII.motorInferencia.estrategias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import ar.fiuba.fallasII.motorInferencia.modelo.BaseDeConocimiento;
import ar.fiuba.fallasII.motorInferencia.modelo.Premisa;
import ar.fiuba.fallasII.motorInferencia.modelo.Regla;

public class EncadenamientoHaciaAdelante {

	private BaseDeConocimiento baseDeConocimiento;

	private static final Logger log = Logger.getLogger(EncadenamientoHaciaAdelante.class);

	public EncadenamientoHaciaAdelante(BaseDeConocimiento baseDeConocimiento) {
		this.baseDeConocimiento = baseDeConocimiento;
	}

	public List<Premisa> evaluar(List<Premisa> hechosIniciales) {
		boolean continuar = true;
		List<Premisa> conocimientoAdquirido = new ArrayList<Premisa>();
		conocimientoAdquirido.addAll(hechosIniciales);
		while (continuar) {
			log.info("Base de conocimiento:");
			this.baseDeConocimiento.imprimir();
			log.info("Conocimiento adquirido hasta el momento: " + Arrays.toString(conocimientoAdquirido.toArray()));
			List<Regla> reglasAplicables = baseDeConocimiento.obtenerReglasAplicablesSegunPremisas(conocimientoAdquirido);
			continuar = this.continuar(reglasAplicables);
			if (continuar) {
				Regla reglaElegida = this.elegirRegla(reglasAplicables);
				log.info("Regla elegida: " + reglaElegida.toString());
				log.info("Añadiendo hecho al conocimiento adquirido: " + reglaElegida.getHecho());
				conocimientoAdquirido.add(reglaElegida.getHecho());
				reglaElegida.setEvaluada(true);
			}
		}
		return conocimientoAdquirido;
	}

	/**
	 * Estrategia de eleccion de reglas.
	 */
	public Regla elegirRegla(List<Regla> reglasElegibles) {
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
