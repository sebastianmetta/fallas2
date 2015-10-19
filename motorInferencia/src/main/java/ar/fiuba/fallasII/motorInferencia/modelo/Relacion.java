package ar.fiuba.fallasII.motorInferencia.modelo;

public class Relacion {
	
	private TipoRelacion tipoRelacion;
	private Premisa premisaPrincipal;
	private Premisa premisaRelacionada;

	public Relacion(TipoRelacion tipoRelacion, Premisa premisaPrincipal, Premisa premisaRelacionada) {
		super();
		this.tipoRelacion = tipoRelacion;
		this.premisaPrincipal = premisaPrincipal;
		this.premisaRelacionada = premisaRelacionada;
	}

	public TipoRelacion getTipoRelacion() {
		return tipoRelacion;
	}
	public void setTipoRelacion(TipoRelacion tipoRelacion) {
		this.tipoRelacion = tipoRelacion;
	}
	public Premisa getPremisaPrincipal() {
		return premisaPrincipal;
	}
	public void setPremisaPrincipal(Premisa premisaPrincipal) {
		this.premisaPrincipal = premisaPrincipal;
	}
	public Premisa getPremisaRelacionada() {
		return premisaRelacionada;
	}
	public void setPremisaRelacionada(Premisa premisaRelacionada) {
		this.premisaRelacionada = premisaRelacionada;
	}

	@Override
	public String toString() {
		return premisaPrincipal.getValor() + "----" + tipoRelacion.getValor() + "--->" + premisaRelacionada.getValor();
	}


}
