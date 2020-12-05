package cn.easybuy.dao.product;

import cn.easybuy.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    Integer updateStock(@Param("id") Integer id, @Param("stock") Integer stock) throws Exception;

    public Integer add(Product product) throws Exception;

    public Integer update(Product product) throws Exception;

    public Integer deleteProductById(Integer id) throws Exception;

    public Product getProductById(Integer id)throws Exception;

    public List<Product> getProductList(@Param("currentPageNo") Integer currentPageNo,
                                        @Param("pageSize") Integer pageSize,
                                        @Param("proName") String proName,
                                        @Param("categoryId") Integer categoryId,
                                        @Param("level") Integer level)throws Exception;

    public Integer queryProductCount(@Param("proName") String proName,
                                     @Param("categoryId") Integer categoryId,
                                     @Param("level") Integer level)throws Exception;
}
