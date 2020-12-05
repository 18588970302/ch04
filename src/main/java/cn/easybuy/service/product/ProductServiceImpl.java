package cn.easybuy.service.product;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import cn.easybuy.dao.product.ProductDao;
import cn.easybuy.dao.product.ProductDaoImpl;
import cn.easybuy.dao.product.ProductMapper;
import cn.easybuy.dao.user.UserMapper;
import cn.easybuy.entity.User;
import cn.easybuy.utils.DataSourceUtil;
import cn.easybuy.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import cn.easybuy.entity.Product;
/**
 * 商品的业务类
 */
public class ProductServiceImpl implements ProductService {
	
	private Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	@Override
	public boolean add(Product product) {
		SqlSession ss = MyBatisUtil.createSqlSession();
		int num = -1;
		try {
			num = ss.getMapper(ProductMapper.class).add(product);
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
	public boolean update(Product product) {
		SqlSession ss = MyBatisUtil.createSqlSession();
		int num = -1;
		try {
			num = ss.getMapper(ProductMapper.class).update(product);
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
	public boolean deleteProductById(Integer productId) {
		SqlSession ss = MyBatisUtil.createSqlSession();
		int num = -1;
		try {
			num = ss.getMapper(ProductMapper.class).deleteProductById(productId);
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
	public Product getProductById(Integer productId) {
		SqlSession ss = MyBatisUtil.createSqlSession();
		Product product = null;
		try {
			product = ss.getMapper(ProductMapper.class).getProductById(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MyBatisUtil.closeSqlSession(ss);
		}
		return product;
	}

	@Override
	public List<Product> getProductList(Integer currentPageNo,Integer pageSize,String proName, Integer categoryId, Integer level) {
		SqlSession ss = MyBatisUtil.createSqlSession();
		List<Product> list = new ArrayList<Product>();
		try {
			list = ss.getMapper(ProductMapper.class).getProductList((currentPageNo - 1) * pageSize,pageSize,proName,categoryId,level);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MyBatisUtil.closeSqlSession(ss);
		}
		return list;
	}

	@Override
	public int count(String proName,Integer categoryId, Integer level) {
		SqlSession ss = MyBatisUtil.createSqlSession();
		int num = 0;
		try {
			num = ss.getMapper(ProductMapper.class).queryProductCount(proName,categoryId,level);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MyBatisUtil.closeSqlSession(ss);
		}
		return num;
	}

	@Override
	public boolean updateStock(Integer productId, Integer stock) {
		SqlSession ss = MyBatisUtil.createSqlSession();
		int num = -1;
		try {
			num = ss.getMapper(ProductMapper.class).updateStock(productId,stock);
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
}
