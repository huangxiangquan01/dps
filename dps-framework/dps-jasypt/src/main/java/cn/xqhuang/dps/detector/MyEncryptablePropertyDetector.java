package cn.xqhuang.dps.detector;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;

public class MyEncryptablePropertyDetector implements EncryptablePropertyDetector {

    /**
     * 是否为可以解密的字符串
     *
     * @param value 全部的字符串
     * @return 是否是解密的字符串，true，是，false，否
     */
    @Override
    public boolean isEncrypted(String value) {
        if (value != null) {
            return value.startsWith("ENC@");    // 自定义规则为 ENC@开头
        }
        return false;
    }

    /**
     * 截取到除了标识之后的值
     *
     * @param value 带前缀
     * @return string 去掉标识符的字符串
     */
    @Override
    public String unwrapEncryptedValue(String value) {
        return value.substring("ENC@".length());        // 截取ENC@之后的字符串
    }
}
