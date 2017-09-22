package id.technomotion.repository;

import java.util.ArrayList;
import java.util.List;

import id.technomotion.Person;

/**
 * Created by omayib on 22/09/17.
 */

public interface RepositoryTransactionListener {
    public void onLoadAlumnusSucceeded(List<Person> alumnus);
}
