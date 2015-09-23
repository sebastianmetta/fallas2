package ar.fiuba.fallasII.motorInferencia;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.fiuba.fallasII.motorInferencia.dao.IGenericDao;
import ar.fiuba.fallasII.motorInferencia.model.Rule;

@Component
public class EntryPoint {

	private static final Logger log = Logger.getLogger(EntryPoint.class);

	@Autowired
	private IGenericDao dataAccess;

	public EntryPoint() {
		System.out.println("EntryPoint()");
	}

	public void run() {
		System.out.println("EntryPoint.run()");
		// final User aUser = new User("Sebastian");
		// dataAccess.save(aUser);
		//
		// final User byId = dataAccess.get(User.class, 1L);
		// System.out.println("User: " + byId);

		Rule rule = new Rule("Regla numero 1");
		rule.setDescription("Descripcion de la regla 1");
		dataAccess.save(rule);
		log.info("Rule saved: " + rule.toString());
		Rule ruleFound = dataAccess.get(Rule.class, rule.getId());
		log.info("Rule found: " + ruleFound.toString());
	}
}