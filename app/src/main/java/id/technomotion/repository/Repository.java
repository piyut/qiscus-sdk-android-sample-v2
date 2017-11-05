package id.technomotion.repository;

import java.util.List;

import id.technomotion.model.Person;

/**
 * Created by omayib on 22/09/17.
 */

public interface Repository {
    public void loadAll(RepositoryCallback<List<Person>> callback);
    public void save(List<Person> persons);
    public void save(Person person);
}
