package cn.com.demo.portal.dao.role.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author code4fun
 * @since 2018-09-28
 */
@TableName("demo_role")
public class DemoRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("role_id")
    private String roleId;
    @TableField("role_code")
    private String roleCode;
    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
        return "DemoRole{" +
        "roleId=" + roleId +
        ", roleCode=" + roleCode +
        ", roleName=" + roleName +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", valid=" + valid +
        "}";
    }
}
