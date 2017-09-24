package net.ftzcode.component.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;


/**
 * Created by WangZiHe on 2017/9/24
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 * Github:https://github.com/yancheng199287
 */
public class CuteCacheSessionDao extends EnterpriseCacheSessionDAO {

    @Override
    protected Serializable doCreate(Session session) {
        return super.doCreate(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return super.readSession(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
        super.update(session);
    }

    @Override
    protected void doDelete(Session session) {
        super.delete(session);
    }
}
