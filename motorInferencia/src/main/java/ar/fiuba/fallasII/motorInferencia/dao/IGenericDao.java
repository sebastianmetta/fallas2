package ar.fiuba.fallasII.motorInferencia.dao;

import java.util.List;

public interface IGenericDao {

	public <T> T save(final T o);

	public void delete(final Object object);

	public <T> T get(final Class<T> type, final Long id);

	public <T> void saveOrUpdate(final T o);

	public <T> List<T> getAll(final Class<T> type);
}
