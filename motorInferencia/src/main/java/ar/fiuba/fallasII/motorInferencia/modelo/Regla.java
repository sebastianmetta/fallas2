package ar.fiuba.fallasII.motorInferencia.modelo;

import java.util.ArrayList;
import java.util.List;

public class Regla {

	private String nombre;

	private boolean evaluada;

	private List<Premisa> premisas;

	private Premisa hecho;

	public Regla() {
		this.premisas = new ArrayList<Premisa>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Premisa> getPremisas() {
		return premisas;
	}

	public void setPremisas(List<Premisa> premisas) {
		this.premisas = premisas;
	}

	public Premisa getHecho() {
		return hecho;
	}

	public void setHecho(Premisa hecho) {
		this.hecho = hecho;
	}

	public boolean isEvaluada() {
		return evaluada;
	}

	public void setEvaluada(boolean evaluada) {
		this.evaluada = evaluada;
	}

	/**
	 * Comprueba si un conjunto de premisas coinciden con las premisas de esta
	 * regla.
	 * 
	 * @param premisas
	 *            conjunto de premisas a comprobar
	 * @return {@link CoincidenciaPremisa}
	 */
	public CoincidenciaPremisa comprobarPremisas(List<Premisa> premisas) {
		CoincidenciaPremisa coincidencia = new CoincidenciaPremisa();
		if (premisas.containsAll(this.getPremisas())) {
			coincidencia.setCantidadCoincidencias(this.getPremisas().size());
			coincidencia.setTipoCoincidencia(TipoCoincidenciaPremisa.TOTAL);
		} else {
			// comprobamos si hay coincidencia parcial.
			for (Premisa each : premisas) {
				if (this.getPremisas().contains(each)) {
					coincidencia.incrementarCantidadCoincidencias();
				}
			}
			if (coincidencia.getCantidadCoincidencias() == 0) {
				coincidencia.setTipoCoincidencia(TipoCoincidenciaPremisa.NULA);
			} else {
				coincidencia.setTipoCoincidencia(TipoCoincidenciaPremisa.PARCIAL);
			}
		}
		return coincidencia;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Regla [nombre=");
		builder.append(nombre);
		builder.append(", eval=");
		builder.append(evaluada);
		builder.append(", premisas=");
		builder.append(premisas);
		builder.append(", hecho=");
		builder.append(hecho);
		builder.append("]");
		return builder.toString();
	}

}