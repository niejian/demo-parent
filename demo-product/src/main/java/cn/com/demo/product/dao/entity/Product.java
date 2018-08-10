package cn.com.demo.product.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code4fun
 * @since 2018-08-10
 */
@TableName("demo_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("product_id")
    private String productId;
    /**
     * 产品编号
     */
    @TableField("product_code")
    private String productCode;
    /**
     * 描述
     */
    @TableField("product_desc")
    private String productDesc;
    @TableField("create_date")
    private Date createDate;
    @TableField("create_by")
    private String createBy;
    @TableField("last_update_date")
    private Date lastUpdateDate;
    @TableField("last_update_by")
    private String lastUpdateBy;
    /**
     * 是否有效；1有效；0无效
     */
    @TableField("product_status")
    private Integer productStatus;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return "Product{" +
        "productId=" + productId +
        ", productCode=" + productCode +
        ", productDesc=" + productDesc +
        ", createDate=" + createDate +
        ", createBy=" + createBy +
        ", lastUpdateDate=" + lastUpdateDate +
        ", lastUpdateBy=" + lastUpdateBy +
        ", productStatus=" + productStatus +
        "}";
    }
}
