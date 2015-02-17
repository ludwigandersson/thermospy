/*
 * Copyright (C) 2015 ludde
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.luan.thermospy.server.db.dao;

import com.luan.thermospy.server.db.Cut;
import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 *
 * @author ludde
 */
public class CutDAO extends AbstractDAO<Cut> {

    public CutDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
        
    public Cut findById(int id) {
        return get(id);
    }

    public Cut create(Cut cut) {
        return persist(cut);
    }

    public boolean delete(Cut cut)
    {
       Query q = currentSession().createQuery("delete Cut where id = "+cut.getId());
       return q.executeUpdate() >= 1;
    }
    
    public List<Cut> findAll() {
        return list(namedQuery("Cut.findAll"));
    }
    
}