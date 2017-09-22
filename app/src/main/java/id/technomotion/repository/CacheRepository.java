package id.technomotion.repository;

import java.util.List;

import id.technomotion.Person;

/**
 * Created by omayib on 22/09/17.
 */

public class CacheRepository implements Repository, CachedData {


    @Override
    public void loadAll(RepositoryCallback<List<Person>> callback) {
        callback.onSucceed(alumnus);
    }

    @Override
    public void save(List<Person> persons) {
        alumnus.addAll(persons);
    }

    @Override
    public void save(Person person) {

    }
}
