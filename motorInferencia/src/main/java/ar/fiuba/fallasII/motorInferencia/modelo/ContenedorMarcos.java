package ar.fiuba.fallasII.motorInferencia.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ContenedorMarcos {

	private List<Marco> marcoList = new ArrayList<Marco>();

	public ContenedorMarcos() {
		super();
	}

	public void addMarco(Marco marco) {
		this.marcoList.add(marco);
	}

	public void cargarDatosDePrueba() {
		this.marcoList.add(new Marco("Marco1", ContenedorMarcos.buildHashSet("c1", "c2", "c3", "c4")));
		this.marcoList.add(new Marco("Marco2", ContenedorMarcos.buildHashSet("c5", "c6", "c7")));
		this.marcoList.add(new Marco("Marco3", ContenedorMarcos.buildHashSet("c8", "c9", "c10", "c11", "c12", "c13", "c14")));
		this.marcoList.add(new Marco("Marco4", ContenedorMarcos.buildHashSet("c15")));
		this.marcoList.add(new Marco("Marco5", ContenedorMarcos.buildHashSet("c16", "c17", "c18", "c19")));
		this.marcoList.add(new Marco("Marco6", ContenedorMarcos.buildHashSet("c20", "c21")));
		this.marcoList.add(new Marco("Marco7", ContenedorMarcos.buildHashSet("c22", "c23", "c24", "c25","c26")));
	}

	private static HashSet<String> buildHashSet(String... caracteristicas) {
		HashSet<String> hs = new HashSet<String>();
		for (String eachCaracteristica : caracteristicas) {
			hs.add(eachCaracteristica);
		}
		return hs;
	}

	public void imprimirContenido() {
		for (Marco eachMarco : this.marcoList) {
			eachMarco.imprimir();
		}
	}

	public List<Marco> getMarcoList() {
		return marcoList;
	}

	public void setMarcoList(List<Marco> marcoList) {
		this.marcoList = marcoList;
	}

}
