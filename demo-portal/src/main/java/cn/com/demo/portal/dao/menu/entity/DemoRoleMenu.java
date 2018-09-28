package cn.com.demo.portal.dao.menu.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 菜单角色对应表
 * </p>
 *
 * @author code4fun
 * @since 2018-09-28
 */
@TableName("demo_role_menu")
public class DemoRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("role_menu_id")
    private String roleMenuId;
    @TableField("role_id")
    private String roleId;
    @TableField("role_code")
    private String roleCode;
    @TableField("menu_id")
    private String menuId;
    @TableField("menu_addr")
    private String menuAddr;
    /**
     * 1 有效，0无效
     */
    private Integer valid;


    public String getRoleMenuId() {
        return roleMenuId;
    }

    public void setRoleMenuId(String roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuAddr() {
        return menuAddr;
    }

    public void setMenuAddr(String menuAddr) {
        this.menuAddr = menuAddr;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "DemoRoleMenu{" +
        "roleMenuId=" + roleMenuId +
        ", roleId=" + roleId +
        ", roleCode=" + roleCode +
        ", menuId=" + menuId +
        ", menuAddr=" + menuAddr +
        ", valid=" + valid +
        "}";
    }
}
