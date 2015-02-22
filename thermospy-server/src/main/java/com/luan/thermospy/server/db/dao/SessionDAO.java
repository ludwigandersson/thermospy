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

import com.luan.thermospy.server.db.Session;
import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 *
 * @author ludde
 */
public class SessionDAO  extends AbstractDAO<Session> {

    public SessionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    public Session findById(int id) {
        return get(id);
    }

    public Session create(Session session) {
        return persist(session);
    }

    public List<Session> findAll() {
        return list(namedQuery("Session.findAll"));
    }
    
    public boolean delete(Session session)
    {
       Query q = currentSession().createQuery("delete Session where id = "+session.getId());
       return q.executeUpdate() >= 1;
    }
}
