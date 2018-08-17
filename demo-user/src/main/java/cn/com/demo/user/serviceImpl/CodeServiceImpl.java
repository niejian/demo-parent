package cn.com.demo.user.serviceImpl;

import cn.com.demo.user.dao.entity.Code;
import cn.com.demo.user.dao.mapper.CodeMapper;
import cn.com.demo.user.service.CodeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

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
@Slf4j
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
                .eq("code_middle", middle)
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
//            if (1 == 1)
//                throw new RuntimeException("自爆");
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
    @Async
    @Override
    public ListenableFuture<String> asyncCreateSerialCode(String table, String prefix, String middle, int length) throws Exception {
        String code = "";
        try {
            log.info("---->异步生成订单编码：");

            code = this.createSerialCode(table, prefix, middle, length);
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult<String>(e.getMessage());
        }

        return new AsyncResult<>("success");
    }


    public void addCallBack(ListenableFuture listenableFuture, String successful, String failure) {
        final String successInfo = successful;
        final String failureInfo = failure;

        /**
         * 异步调用成功
         */
        SuccessCallback<String> successCallback = new SuccessCallback<String>() {
            @Override
            public void onSuccess(String s) {
                log.info("{}--->回调成功， 结果：{}", successInfo, s);
            }
        };

        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("{}--->回调失败， 结果：{}", failureInfo, throwable.getMessage());
            }
        };

        listenableFuture.addCallback(successCallback, failureCallback);
    }
}
