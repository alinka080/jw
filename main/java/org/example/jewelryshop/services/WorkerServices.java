package org.example.jewelryshop.services;

import org.example.jewelryshop.models.workers;
import org.example.jewelryshop.repositories.WorkersDao;

import java.util.List;

public class WorkerServices {

private WorkersDao currentDao = new WorkersDao();

public List<workers> findAll(){return currentDao.findAll();}

    public workers findOne(final long id){return currentDao.findOne(id);}

    public void save(final workers entity){
    if (entity == null){
        return;
    }
        currentDao.save(entity);
    }

    public void update(final workers entity){
    if(entity == null){
        return;
    }
        currentDao.update(entity);
    }

    public void delete(final workers entity){
    if(entity == null){
        return;
    }
        currentDao.delete(entity);
    }

    public void deleteById(final Long id){
    if (id == null){
        return;
    }
        currentDao.deleteById(id);
    }
}
