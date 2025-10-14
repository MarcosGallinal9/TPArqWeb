package repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;


@NoRepositoryBean
public interface BaseJPARepository<T,ID extends Serializable> extends org.springframework.data.repository.Repository<T,ID> {


}
