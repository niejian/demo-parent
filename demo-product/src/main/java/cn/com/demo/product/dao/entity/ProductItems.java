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
@TableName("demo_product_items")
public class ProductItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("item_id")
    private String itemId;
    @TableField("item_code")
    private String itemCode;
    /**
     * demo_product主键
     */
    @TableField("product_id")
    private String productId;
    /**
     * 金额，分
     */
    @TableField("item_price")
    private Integer itemPrice;
    /**
     * 单品描述
     */
    @TableField("item_desc")
    private String itemDesc;
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
    @TableField("item_status")
    private Integer itemStatus;


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
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

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }

    @Override
    public String toString() {
        return "ProductItems{" +
        "itemId=" + itemId +
        ", itemCode=" + itemCode +
        ", productId=" + productId +
        ", itemPrice=" + itemPrice +
        ", itemDesc=" + itemDesc +
        ", createDate=" + createDate +
        ", createBy=" + createBy +
        ", lastUpdateDate=" + lastUpdateDate +
        ", lastUpdateBy=" + lastUpdateBy +
        ", itemStatus=" + itemStatus +
        "}";
    }
}
