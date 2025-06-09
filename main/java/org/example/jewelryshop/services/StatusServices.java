package org.example.jewelryshop.services;

import org.example.jewelryshop.models.statuses;
import org.example.jewelryshop.repositories.StatusDao;

import java.util.List;

public class StatusServices {

    private StatusDao currentDao = new StatusDao();

    public List<statuses> findAll(){return currentDao.findAll();}

    public statuses findOne(final long id){return currentDao.findOne(id);}

    public void save(final statuses entity){
        if (entity == null){
            return;
        }
        currentDao.save(entity);
    }

    public void update(final statuses entity){
        if(entity == null){
            return;
        }
        currentDao.update(entity);
    }

    public void delete(final statuses entity){
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
