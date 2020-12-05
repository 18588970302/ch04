package cn.easybuy.service.user;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import cn.easybuy.dao.order.UserAddressDao;
import cn.easybuy.dao.order.UserAddressDaoImpl;
import cn.easybuy.dao.user.UserDao;
import cn.easybuy.dao.user.UserDaoImpl;
import cn.easybuy.dao.user.UserMapper;
import cn.easybuy.entity.UserAddress;
import cn.easybuy.utils.DataSourceUtil;
import cn.easybuy.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import cn.easybuy.entity.User;

public class UserServiceImpl implements UserService {
	
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Override
	public boolean add(User user){
		SqlSession ss = MyBatisUtil.createSqlSession();
		int num = -1;
		try {
			num = ss.getMapper(UserMapper.class).add(user);
			ss.commit();
			logger.debug("新增成功!");
		} catch (Exception e) {
			ss.rollback();
			logger.debug("新增失败!");
			e.printStackTrace();
		}finally {
			MyBatisUtil.closeSqlSession(ss);
		}
		return num == 1 ? true:false;
	}

	@Override
	public boolean update(User user) {
		SqlSession ss = MyBatisUtil.createSqlSession();
		int num = -1;
		try {
			num = ss.getMapper(UserMapper.class).update(user);
			ss.commit();
			logger.debug("修改成功!");
		} catch (Exception e) {
			ss.rollback();
			logger.debug("修改失败!");
			e.printStackTrace();
		}finally {
			MyBatisUtil.closeSqlSession(ss);
		}
		return num == 1 ? true:false;
	}

	@Override
	public boolean deleteUserById(Integer userId) {
		SqlSession ss = MyBatisUtil.createSqlSession();
		int num = -1;
		try {
			num = ss.getMapper(UserMapper.class).deleteUserById(userId);
			ss.commit();
			logger.debug("删除成功!");
		} catch (Exception e) {
			ss.rollback();
			logger.debug("删除失败!");
			e.printStackTrace();
		}finally {
			MyBatisUtil.closeSqlSession(ss);
		}
		return num == 1 ? true:false;
	}

	@Override
	public User getUser(Integer userId, String loginName) {
		SqlSession ss = MyBatisUtil.createSqlSession();
		User user = null;
		try {
			user = ss.getMapper(UserMapper.class).getUser(userId,loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MyBatisUtil.closeSqlSession(ss);
		}
		return user;
	}

	@Override
	public List<User> getUserList(Integer currentPageNo, Integer pageSize) {
		SqlSession ss = MyBatisUtil.createSqlSession();
		List<User> list = new ArrayList<User>();
		try {
			list = ss.getMapper(UserMapper.class).getUserList((currentPageNo - 1) * pageSize,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MyBatisUtil.closeSqlSession(ss);
		}
		return list;
	}
	@Override
	public int count() {
		SqlSession ss = MyBatisUtil.createSqlSession();
		int num = 0;
		try {
			num = ss.getMapper(UserMapper.class).count();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MyBatisUtil.closeSqlSession(ss);
		}
		return num;
	}
}
