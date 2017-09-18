package id.technomotion;

/**
 * Created by omayib on 18/09/17.
 */

public class Alumni {
    private final String id;
    private final String name;
    private final String email;
    private final String job;


    public Alumni(String id, String name, String email, String job) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
