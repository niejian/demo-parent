package cn.com.demo.portal.dao.role.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户角色信息表
 * </p>
 *
 * @author code4fun
 * @since 2018-09-28
 */
@TableName("demo_user_role")
public class DemoUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("user_role_id")
    private String userRoleId;
    @TableField("role_id")
    private String roleId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;
    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;
    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;
    /**
     * 开始时间
     */
    @TableField("start_date")
    private Date startDate;
    /**
     * 结束日期
     */
    @TableField("end_date")
    private Date endDate;
    /**
     * 是否有效，1有效，0无效
     */
    private Integer valid;


    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "DemoUserRole{" +
        "userRoleId=" + userRoleId +
        ", roleId=" + roleId +
        ", userId=" + userId +
        ", roleName=" + roleName +
        ", roleCode=" + roleCode +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", valid=" + valid +
        "}";
    }
}
