package org.example.jewelryshop.services;

import org.example.jewelryshop.models.products;
import org.example.jewelryshop.repositories.ProductsDao;

import java.util.List;

public class ProductServices {

private ProductsDao currentDao = new ProductsDao();

public List<products> findAll(){return currentDao.findAll();}

    public products findOne(final long id){return currentDao.findOne(id);}

    public void save(final products entity){
    if (entity == null){
        return;
    }
        currentDao.save(entity);
    }

    public void update(final products entity){
    if(entity == null){
        return;
    }
        currentDao.update(entity);
    }

    public void delete(final products entity){
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
