package ar.fiuba.fallasII.motorInferencia.modelo;

public class TipoRelacion {
	private String valor;

	public TipoRelacion(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "----" + this.valor + "--->";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Premisa other = (Premisa) obj;
		if (valor == null) {
			if (other.getValor() != null)
				return false;
		} else if (!valor.equals(other.getValor()))
			return false;
		return true;
	}
}
