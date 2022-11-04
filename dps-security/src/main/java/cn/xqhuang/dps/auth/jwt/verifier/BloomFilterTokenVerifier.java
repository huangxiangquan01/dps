package cn.xqhuang.dps.auth.jwt.verifier;


public class BloomFilterTokenVerifier implements TokenVerifier {
    @Override
    public boolean verify(String jti) {
        return true;
    }
}
