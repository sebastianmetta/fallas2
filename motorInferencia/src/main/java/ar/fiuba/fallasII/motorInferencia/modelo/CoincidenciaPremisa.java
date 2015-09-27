package ar.fiuba.fallasII.motorInferencia.modelo;

/**
 * Modela el resultado de comprarar un conjunto de premisas contra otro. </br>
 * Indica la cantidad de coincidencias y el {@link TipoCoincidenciaPremisa}
 */
public class CoincidenciaPremisa {

	private int cantidadCoincidencias=0;

	private TipoCoincidenciaPremisa tipoCoincidencia;

	public CoincidenciaPremisa() {
		super();
	}

	public int getCantidadCoincidencias() {
		return cantidadCoincidencias;
	}

	public void setCantidadCoincidencias(int cantidadCoincidencias) {
		this.cantidadCoincidencias = cantidadCoincidencias;
	}

	public TipoCoincidenciaPremisa getTipoCoincidencia() {
		return tipoCoincidencia;
	}

	/**
	 * Incrementa en 1 unidad la cantidad de coincidencias
	 */
	public void incrementarCantidadCoincidencias() {
		this.cantidadCoincidencias++;
	}

	public void setTipoCoincidencia(TipoCoincidenciaPremisa tipoCoincidencia) {
		this.tipoCoincidencia = tipoCoincidencia;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CoincidenciaPremisa [cant=");
		builder.append(cantidadCoincidencias);
		builder.append(", tipo=");
		builder.append(tipoCoincidencia);
		builder.append("]");
		return builder.toString();
	}

}
