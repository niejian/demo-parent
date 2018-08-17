package cn.com.demo.user.service;

import cn.com.demo.user.dao.entity.Code;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * <p>
 * 编码 服务类
 * </p>
 *
 * @author code4fun
 * @since 2018-08-13
 */
public interface CodeService extends IService<Code> {

    /**
     * 生成编码
     * @param table 表名
     * @param prefix 前缀
     * @param middle 编码中缀
     * @param length 编码长度
     * @return
     */
    String createSerialCode(String table, String prefix,
                            String middle, int length) throws Exception;


    /**
     * 生成编码
     * @param table 表名
     * @param prefix 前缀
     * @param middle 编码中缀
     * @param length 编码长度
     * @return
     */
    ListenableFuture<String> asyncCreateSerialCode(String table, String prefix,
                                                   String middle, int length) throws Exception;
}
