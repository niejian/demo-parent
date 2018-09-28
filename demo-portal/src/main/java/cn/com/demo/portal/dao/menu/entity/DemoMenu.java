package cn.com.demo.portal.dao.menu.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 菜单信息表
 * </p>
 *
 * @author code4fun
 * @since 2018-09-28
 */
@TableName("demo_menu")
public class DemoMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("menu_id")
    private String menuId;
    /**
     * 菜单编码
     */
    @TableField("menu_code")
    private String menuCode;
    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;
    /**
     * 父菜单Id
     */
    @TableField("parent_menu_id")
    private String parentMenuId;
    /**
     * 菜单地址
     */
    @TableField("menu_addr")
    private String menuAddr;
    @TableField("create_date")
    private Date createDate;
    @TableField("create_code")
    private String createCode;
    /**
     * 是否有效，1有效，0无效
     */
    private Integer valid;


    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getMenuAddr() {
        return menuAddr;
    }

    public void setMenuAddr(String menuAddr) {
        this.menuAddr = menuAddr;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateCode() {
        return createCode;
    }

    public void setCreateCode(String createCode) {
        this.createCode = createCode;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "DemoMenu{" +
        "menuId=" + menuId +
        ", menuCode=" + menuCode +
        ", menuName=" + menuName +
        ", parentMenuId=" + parentMenuId +
        ", menuAddr=" + menuAddr +
        ", createDate=" + createDate +
        ", createCode=" + createCode +
        ", valid=" + valid +
        "}";
    }
}
