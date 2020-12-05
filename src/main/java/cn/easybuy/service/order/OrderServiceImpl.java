package cn.easybuy.service.order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import cn.easybuy.dao.order.*;
import cn.easybuy.dao.product.ProductDao;
import cn.easybuy.dao.product.ProductDaoImpl;
import cn.easybuy.dao.product.ProductMapper;
import cn.easybuy.utils.*;
import cn.easybuy.entity.Order;
import cn.easybuy.entity.OrderDetail;
import cn.easybuy.entity.User;
import org.apache.ibatis.session.SqlSession;

public class OrderServiceImpl implements OrderService {

    /**
     * 结算购物车物品包含以下步骤：
     * 1.生成订单
     * 2.生成订单明细
     * 3.更新商品表，减库存
     * 注意加入事物的控制
     */

    @Override
    public Order payShoppingCart(ShoppingCart cart, User user, String address) {
        SqlSession ss = null;
        Order order = new Order();
        try{
            ss = MyBatisUtil.createSqlSession();
            order.setUserId(user.getId());
            order.setLoginName(user.getLoginName());
            order.setUserAddress(address);
            order.setCreateTime(new Date());
            order.setCost(cart.getTotalCost());
            order.setSerialNumber(StringUtils.randomUUID());
            ss.getMapper(OrderMapper.class).add(order);
            for (ShoppingCartItem item : cart.getItems()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(order.getId());
                orderDetail.setCost(item.getCost());
                orderDetail.setProductId(item.getProduct().getId());
                orderDetail.setQuantity(item.getQuantity());
                ss.getMapper(OrderDetailMapper.class).add(orderDetail);
                ss.getMapper(ProductMapper.class).updateStock(item.getProduct().getId(), item.getQuantity());
                ss.commit();
            }
        } catch (Exception e) {
            ss.rollback();
            e.printStackTrace();
        }finally {
            MyBatisUtil.closeSqlSession(ss);
        }
        return order;
    }
    @Override
    public List<Order> getOrderList(Integer userId, Integer currentPageNo, Integer pageSize) {
        SqlSession ss = MyBatisUtil.createSqlSession();
        List<Order> list = new ArrayList<Order>();
        try {
            list = ss.getMapper(OrderMapper.class).getOrderList(userId,(currentPageNo - 1) * pageSize,pageSize);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MyBatisUtil.closeSqlSession(ss);
        }
        return list;
    }

    @Override
    public int count(Integer userId) {
        SqlSession ss = MyBatisUtil.createSqlSession();
        int num = 0;
        try {
            num = ss.getMapper(OrderMapper.class).count(userId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MyBatisUtil.closeSqlSession(ss);
        }
        return num;
    }

    /**
     * 调用dao接口：OrderDetailMapper的方法实现
     */
    @Override
    public List<OrderDetail> getOrderDetailList(Integer orderId) {
        SqlSession ss = MyBatisUtil.createSqlSession();
        List<OrderDetail> list = new ArrayList<OrderDetail>();
        try {
            list = ss.getMapper(OrderDetailMapper.class).getOrderDetailList(orderId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MyBatisUtil.closeSqlSession(ss);
        }
        return list;
    }
}
