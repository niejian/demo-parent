package cn.com.demo.user.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 编码
 * </p>
 *
 * @author code4fun
 * @since 2018-08-13
 */
@TableName("demo_code")
public class Code implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("code_type")
    private String codeType;
    /**
     * 编码后缀
     */
    @TableField("code_suffix")
    private String codeSuffix;
    /**
     * 编码
     */
    @TableField("num_int")
    private Integer numInt;
    /**
     * 属于哪个表
     */
    @TableField("table_ref")
    private String tableRef;
    /**
     * 编码前缀
     */
    @TableField("code_prefix")
    private String codePrefix;
    /**
     * 编码中段
     */
    @TableField("code_middle")
    private String codeMiddle;
    @TableField("create_date")
    private Date createDate;
    @TableField("create_by")
    private String createBy;
    @TableField("last_update_date")
    private Date lastUpdateDate;
    @TableField("last_update_by")
    private String lastUpdateBy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeSuffix() {
        return codeSuffix;
    }

    public void setCodeSuffix(String codeSuffix) {
        this.codeSuffix = codeSuffix;
    }

    public Integer getNumInt() {
        return numInt;
    }

    public void setNumInt(Integer numInt) {
        this.numInt = numInt;
    }

    public String getTableRef() {
        return tableRef;
    }

    public void setTableRef(String tableRef) {
        this.tableRef = tableRef;
    }

    public String getCodePrefix() {
        return codePrefix;
    }

    public void setCodePrefix(String codePrefix) {
        this.codePrefix = codePrefix;
    }

    public String getCodeMiddle() {
        return codeMiddle;
    }

    public void setCodeMiddle(String codeMiddle) {
        this.codeMiddle = codeMiddle;
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

    @Override
    public String toString() {
        return "Code{" +
        "id=" + id +
        ", codeType=" + codeType +
        ", codeSuffix=" + codeSuffix +
        ", numInt=" + numInt +
        ", tableRef=" + tableRef +
        ", codePrefix=" + codePrefix +
        ", codeMiddle=" + codeMiddle +
        ", createDate=" + createDate +
        ", createBy=" + createBy +
        ", lastUpdateDate=" + lastUpdateDate +
        ", lastUpdateBy=" + lastUpdateBy +
        "}";
    }
}
