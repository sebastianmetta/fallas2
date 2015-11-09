package ar.fiuba.fallasII.motorInferencia.modelo;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.log4j.Logger;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public class RedSemanticaDB {

	private OrientGraph graph;
	private TreeMap<String, ArrayList<Relacion>> redSemanticaxRelacion;
	private TreeMap<String, ArrayList<Relacion>> redSemanticaxPremisa;
	
	private static final Logger log = Logger.getLogger(RedSemanticaDB.class);

	public RedSemanticaDB() {
		graph = new OrientGraph("plocal:/temp/databases/fallas");
	}
	
	public void cerrar() {
		graph.shutdown();
	}
	
	public void addRelacion(Relacion relacion) {
		Vertex premisaPrincipal = null;
		Vertex premisaRelacion = null;
				
		//Busca la premisa principal, si no esta la ingresa en la bd
		try {
			Iterable<Vertex> premisas = graph.getVertices("name", relacion.getPremisaPrincipal().getValor());
			premisaPrincipal = premisas.iterator().next();
		} catch (Exception e) {
			premisaPrincipal = graph.addVertex(null); // 1st OPERATION: IMPLICITLY BEGIN A TRANSACTION
			premisaPrincipal.setProperty("name",  relacion.getPremisaPrincipal().getValor());
		}
		
		//Busca la premisa relacionada, si no esta la ingresa en la bd
		try {
			Iterable<Vertex> premisas = graph.getVertices("name", relacion.getPremisaRelacionada().getValor());
			premisaRelacion = premisas.iterator().next();
		} catch (Exception e) {
			premisaRelacion = graph.addVertex(null); // 1st OPERATION: IMPLICITLY BEGIN A TRANSACTION
			premisaRelacion.setProperty("name",  relacion.getPremisaRelacionada().getValor());
		}
        
		Edge relacionTipo = graph.addEdge(null, premisaPrincipal, premisaRelacion, relacion.getTipoRelacion().getValor());
		graph.commit();
	}

	/*
	public ArrayList<Relacion> getRelacionesPorPremisa(Premisa premisa) {
		ArrayList<Relacion> relaciones = this.redSemanticaxPremisa.get(premisa.getValor());
		return relaciones;
	}

	public ArrayList<Relacion> getRelacionesPorRelacion(Relacion relacion) {
		ArrayList<Relacion> relaciones = new ArrayList<Relacion>();
		for (Edge e : graph.getEdges()) {
			TipoRelacion tipoRelacion = new TipoRelacion(e.getLabel());
			Premisa premisaPrincipal = new Premisa(e.getVertex(Direction.IN).getProperty("name").toString());		
			Premisa premisaRelacion = new Premisa(e.getVertex(Direction.OUT).getProperty("name").toString());		
			relaciones.add(new Relacion(tipoRelacion, premisaPrincipal, premisaRelacion));
		}
		ArrayList<Relacion> relaciones = this.redSemanticaxRelacion.get(relacion.getTipoRelacion().getValor());
		return relaciones;
	}
	*/

	public void imprimirPorPremisa() {
		ArrayList<Relacion> relaciones = new ArrayList<Relacion>();
		for (Vertex v : graph.getVertices()) {
			Premisa premisaPrincipal = new Premisa(v.getProperty("name").toString());	
			
			log.info("Relaciones de la Premisa '" + premisaPrincipal.getValor() + "'");
			for (Edge e : v.getEdges(Direction.OUT)) {
				TipoRelacion tipoRelacion = new TipoRelacion(e.getLabel());
				Premisa premisaRelacion = new Premisa(e.getVertex(Direction.IN).getProperty("name").toString());
				Relacion relacion = new Relacion(tipoRelacion, premisaPrincipal, premisaRelacion);
				relaciones.add(relacion);
				
				log.info(relacion.toString());
			}
			log.info("");
		}
	}
	
	public void imprimirPorTipoRelacion() {
		ArrayList<Relacion> relaciones = new ArrayList<Relacion>();
		for (Edge e : graph.getEdges()) {
			TipoRelacion tipoRelacion = new TipoRelacion(e.getLabel());
			Premisa premisaPrincipal = new Premisa(e.getVertex(Direction.OUT).getProperty("name").toString());		
			Premisa premisaRelacion = new Premisa(e.getVertex(Direction.IN).getProperty("name").toString());	
			Relacion relacion = new Relacion(tipoRelacion, premisaPrincipal, premisaRelacion);
			relaciones.add(relacion);
			
			log.info(relacion.toString());
		}
	}
}
