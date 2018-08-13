package cn.com.demo.user.serviceImpl;

import cn.com.demo.user.dao.entity.Code;
import cn.com.demo.user.dao.mapper.CodeMapper;
import cn.com.demo.user.service.CodeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 编码 服务实现类
 * </p>
 *
 * @author code4fun
 * @since 2018-08-13
 */
@Service(value = "codeService")
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements CodeService {
    /**
     * 生成编码
     *
     * @param table  表名
     * @param prefix 前缀
     * @param middle 编码中缀
     * @param length 编码长度
     * @return
     */
    @Transactional
    @Override
    public String createSerialCode(String table, String prefix, String middle, int length) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();

        EntityWrapper ew = new EntityWrapper();
        ew.setEntity(new Code());
        ew.eq("table_ref", table)
                .eq("code_prefix", prefix)
                .orderBy(true, "id", false);

        List<Code> codeList = this.selectList(ew);
        int maxNum = 1;

        if (!CollectionUtils.isEmpty(codeList)) {
            maxNum = codeList.get(0).getNumInt() + 1;
        }
        synchronized (this) {

            //后缀长度
            int suffixLength = length - middle.length() - prefix.length();
            if (suffixLength < 0) {
                suffixLength = 0;
            }
            String suffix = this.getSuffixCode(maxNum, suffixLength);
            stringBuffer.append(prefix).append(middle).append(suffix);
            //添加记录
            Code newCode = new Code();
            newCode.setCodeMiddle(middle);
            newCode.setTableRef(table);
            newCode.setNumInt(maxNum);
            newCode.setLastUpdateDate(new Date());
            newCode.setLastUpdateBy("sys");
            newCode.setCreateDate(new Date());
            newCode.setCreateBy("sys");
            newCode.setCodeType(table);
            newCode.setCodeSuffix(suffix);
            newCode.setCodePrefix(prefix);
            insert(newCode);
            if (1 == 1)
                throw new RuntimeException("自爆");
        }

        return stringBuffer.toString();
    }

    /**
     *
     * @param num
     * @param codeLength
     * @return
     */
    private String getSuffixCode(int num, int codeLength) {
        String numStr = num + "";
        StringBuffer sb = new StringBuffer();

        while ((sb.length() + numStr.length()) < codeLength) {
            sb.append(0);
        }

        return sb.append(numStr).toString();

    }


}
