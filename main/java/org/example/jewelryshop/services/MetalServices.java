package org.example.jewelryshop.services;

import org.example.jewelryshop.models.metals;
import org.example.jewelryshop.repositories.MetalsDao;

import java.util.List;

public class MetalServices {

private MetalsDao currentDao = new MetalsDao();

public List<metals> findAll(){return currentDao.findAll();}

    public metals findOne(final long metalId){return currentDao.findOne(metalId);}

    public void save(final metals entity){
    if (entity == null){
        return;
    }
        currentDao.save(entity);
    }

    public void update(final metals entity){
    if(entity == null){
        return;
    }
        currentDao.update(entity);
    }

    public void delete(final metals entity){
    if(entity == null){
        return;
    }
        currentDao.delete(entity);
    }

    public void deleteById(final Long metalId){
    if (metalId == null){
        return;
    }
        currentDao.deleteById(metalId);
    }
}
