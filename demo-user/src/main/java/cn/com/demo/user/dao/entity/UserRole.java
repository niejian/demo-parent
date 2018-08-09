package cn.com.demo.user.dao.entity;

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
 * @since 2018-08-08
 */
@TableName("demo_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("role_id")
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
    @TableField("role_coe")
    private String roleCoe;
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

    public String getRoleCoe() {
        return roleCoe;
    }

    public void setRoleCoe(String roleCoe) {
        this.roleCoe = roleCoe;
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

    @Override
    public String toString() {
        return "UserRole{" +
        "roleId=" + roleId +
        ", userId=" + userId +
        ", roleName=" + roleName +
        ", roleCoe=" + roleCoe +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        "}";
    }
}
