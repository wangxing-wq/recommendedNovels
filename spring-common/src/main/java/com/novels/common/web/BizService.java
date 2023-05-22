package com.novels.common.web;


import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class BizService<K, B> {
    private BaseDao<K, B> baseDao;

    public BizService() {
    }

    public boolean isExist(K k) {
        if (Objects.isNull(k)) {
            return false;
        } else {
            return this.baseDao.findById(k) != null;
        }
    }

    public boolean isExistByBean(B bean, boolean containYourself) {
        if (Objects.isNull(bean)) {
            return false;
        } else {
            List<B> byBeanSelective = this.baseDao.findByBeanSelective(bean);
            Iterator<B> iterator = byBeanSelective.iterator();

            Object b;
            do {
                if (!iterator.hasNext()) {
                    return byBeanSelective.size() > 0;
                }

                b = iterator.next();
            } while (containYourself || !Objects.equals(bean, b));

            return true;
        }
    }
}
