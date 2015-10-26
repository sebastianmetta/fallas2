package ar.fiuba.fallasII.motorInferencia.modelo;

import java.util.HashSet;

import org.apache.log4j.Logger;

public class Marco {

	private String nombreMarco;

	private HashSet<String> caracteristicas = new HashSet<String>();

	private static final Logger log = Logger.getLogger(Marco.class);

	public Marco(String nombreMarco, HashSet<String> caracteristicas) {
		super();
		this.nombreMarco = nombreMarco;
		this.caracteristicas = caracteristicas;
	}

	public Marco() {
		super();
	}

	public HashSet<String> getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(HashSet<String> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getNombreMarco() {
		return nombreMarco;
	}

	public void setNombreMarco(String nombreMarco) {
		this.nombreMarco = nombreMarco;
	}

	public void addCaracteristica(String caracteristica) {
		this.getCaracteristicas().add(caracteristica);
	}

	public boolean matches(HashSet<String> caracteristicas) {
		return this.getCaracteristicas().equals(caracteristicas);

	}

	public void imprimir() {
		log.info("Nombre Marco: " + this.getNombreMarco());
		log.info("Características: ");
		for (String eachCaracteristica : this.caracteristicas) {
			log.info(eachCaracteristica);
		}
	}

}
