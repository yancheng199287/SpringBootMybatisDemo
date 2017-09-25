package net.ftzcode.component.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;

/**
 * Created by WangZiHe on 2017/9/24 QQ/WeChat:648830605 QQ-Group:368512253
 * Blog:www.520code.net Github:https://github.com/yancheng199287
 */
public class CuteCacheSessionDao extends EnterpriseCacheSessionDAO {

	private Logger logger = LoggerFactory.getLogger(CuteCacheSessionDao.class);

	@Override
	protected Serializable doCreate(Session session) {
		logger.info("开始创建session.." + session.getId());
		return super.create(session);
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		logger.info("开始读取session.." + sessionId);
		return super.doReadSession(sessionId);
	}

	@Override
	protected void doUpdate(Session session) {
		logger.info("开始更新session.." + session.getId());
		super.update(session);
	}

	@Override
	protected void doDelete(Session session) {
		logger.info("开始删除session.." + session.getId());
		super.delete(session);
	}
}
