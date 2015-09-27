package ar.fiuba.fallasII.motorInferencia.modelo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class BaseDeConocimiento {

	private List<Regla> reglas;

	private static final Logger log = Logger.getLogger(BaseDeConocimiento.class);

	public BaseDeConocimiento() {
		this.reglas = new ArrayList<Regla>();
	}

	public List<Regla> getReglas() {
		return reglas;
	}

	public void addRegla(Regla regla) {
		this.reglas.add(regla);
	}

	public void removeRegla(Regla regla) {
		this.reglas.remove(regla);
	}

	public void addReglas(List<Regla> reglas) {
		this.reglas.addAll(reglas);
	}

	public List<Regla> obtenerReglasAplicablesSegunPremisas(List<Premisa> premisas) {
		List<Regla> reglasAplicables = new ArrayList<Regla>();
		for (Regla each : this.reglas) {
			if (!each.isEvaluada()) {
				CoincidenciaPremisa conicidencia = each.comprobarPremisas(premisas);
				log.info("La regla " + each.toString() + " es aplicable en forma " + conicidencia.getTipoCoincidencia());
				if (conicidencia.getTipoCoincidencia().equals(TipoCoincidenciaPremisa.TOTAL)) {
					reglasAplicables.add(each);
				} else {
					// TODO: ver que hacer
				}
			}
		}
		return reglasAplicables;
	}

	public void imprimir() {
		for (Regla each : this.getReglas()) {
			log.info(each.toString());
		}
	}
}
