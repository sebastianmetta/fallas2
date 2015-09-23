package ar.fiuba.fallasII.motorInferencia.model;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Rule {

	@Id
	@GeneratedValue(strategy = AUTO)
	@Column
	private long id;

	@Column(length = 50, nullable = false, unique = true)
	private String name;

	@Column(length = 200, nullable = true)
	private String description;

	Rule() {
		// for hibernate.
	}

	public Rule(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Rule [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

}