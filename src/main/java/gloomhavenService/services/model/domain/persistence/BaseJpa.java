package gloomhavenService.services.model.domain.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseJpa {

    @Id
    @GeneratedValue(generator = "uuid2")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
