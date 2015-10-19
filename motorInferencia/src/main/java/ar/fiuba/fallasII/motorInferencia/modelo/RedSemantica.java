package ar.fiuba.fallasII.motorInferencia.modelo;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class RedSemantica {

	private TreeMap<String, ArrayList<Relacion>> redSemanticaxRelacion;
	private TreeMap<String, ArrayList<Relacion>> redSemanticaxPremisa;
	
	private static final Logger log = Logger.getLogger(RedSemantica.class);

	public void addRelacion(Relacion relacion) {
		// verifica que exista la red semantica, si no existe la crea
		if (this.redSemanticaxRelacion == null) {
			this.redSemanticaxRelacion = new TreeMap<String, ArrayList<Relacion>>();
			this.redSemanticaxPremisa = new TreeMap<String, ArrayList<Relacion>>();
		}

		// busca la lista de relaciones existente de ese tipo de relacion
		ArrayList<Relacion> relacionesxRelacion = this.redSemanticaxRelacion.get(relacion.getTipoRelacion().getValor());
		if (relacionesxRelacion == null) {
			// si no existe crea una lista de ese tipo de relaciones, la crea y
			// la agrega a la red semantica x relacion
			relacionesxRelacion = new ArrayList<Relacion>();
			this.redSemanticaxRelacion.put(relacion.getTipoRelacion().getValor(), relacionesxRelacion);
		}

		// busca la lista de relaciones existente de ese tipo de premisa
		ArrayList<Relacion> relacionesxPremisa = this.redSemanticaxPremisa
				.get(relacion.getPremisaPrincipal().getValor());
		if (relacionesxPremisa == null) {
			// si no existe crea una lista de ese tipo de premisas, la crea y
			// la agrega a la red semantica x premisas
			relacionesxPremisa = new ArrayList<Relacion>();
			this.redSemanticaxPremisa.put(relacion.getPremisaPrincipal().getValor(), relacionesxPremisa);
		}

		// agrega la relacion a la lista de relaciones de ese tipo
		relacionesxRelacion.add(relacion);
		relacionesxPremisa.add(relacion);
	}

	public ArrayList<Relacion> getRelacionesPorPremisa(Premisa premisa) {
		ArrayList<Relacion> relaciones = this.redSemanticaxPremisa.get(premisa.getValor());
		return relaciones;
	}

	public ArrayList<Relacion> getRelacionesPorRelacion(Relacion relacion) {
		ArrayList<Relacion> relaciones = this.redSemanticaxRelacion.get(relacion.getTipoRelacion().getValor());
		return relaciones;
	}

	public void imprimirPorPremisa() {
		for (Entry<String, ArrayList<Relacion>> entry : redSemanticaxPremisa.entrySet()) {
			String premisa = entry.getKey();
			ArrayList<Relacion> relaciones = entry.getValue();
			
			log.info("Relaciones de la Premisa '" + premisa + "'");
			for (Relacion relacion : relaciones) {
				log.info(relacion.toString());
			}
			log.info("");
			
		}
	}
	
	public void imprimirPorTipoRelacion() {
		for (Entry<String, ArrayList<Relacion>> entry : redSemanticaxRelacion.entrySet()) {
			String tiporelacion = entry.getKey();
			ArrayList<Relacion> relaciones = entry.getValue();
			
			log.info("Relacion de tipo '" + tiporelacion + "'");
			for (Relacion relacion : relaciones) {
				log.info(relacion.toString());
			}
			log.info("");
			
		}
	}
}
