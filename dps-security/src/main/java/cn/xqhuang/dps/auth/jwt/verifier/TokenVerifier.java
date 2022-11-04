package cn.xqhuang.dps.auth.jwt.verifier;


public interface TokenVerifier {
    boolean verify(String jti);
}
