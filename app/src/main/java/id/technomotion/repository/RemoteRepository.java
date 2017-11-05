package id.technomotion.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import id.technomotion.model.Person;
import okhttp3.OkHttpClient;

/**
 * Created by omayib on 22/09/17.
 */

public class RemoteRepository  implements Repository {
    private static final String TAG = "RemoteRepository";
    private OkHttpClient okHttpClient;
    public RemoteRepository() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void loadAll(final RepositoryCallback<List<Person>> callback) {
        final ArrayList<Person> alumnus = new ArrayList<>();
        alumnus.add(new Person(UUID.randomUUID().toString(),"Haris","Haris@email.com","Android Programmer"));
        alumnus.add(new Person(UUID.randomUUID().toString(),"Anang","Anang@email.com","Web Programmer"));
        alumnus.add(new Person(UUID.randomUUID().toString(),"Satya","Satya@email.com","Backend Programmer"));
        alumnus.add(new Person(UUID.randomUUID().toString(),"Henri","Henri@email.com","Backend Programmer"));
        alumnus.add(new Person(UUID.randomUUID().toString(),"Desi","Desi@email.com","Pengacara"));
        alumnus.add(new Person(UUID.randomUUID().toString(),"Sutris","Sutris@email.com","Desktop Programmer"));
        alumnus.add(new Person(UUID.randomUUID().toString(),"Dina","Dina@email.com","Android Programmer"));
        alumnus.add(new Person(UUID.randomUUID().toString(),"Winda","Winda@email.com","Android Programmer"));
        callback.onSucceed(alumnus);
    }

    @Override
    public void save(List<Person> persons) {

    }

    @Override
    public void save(Person person) {

    }
}
