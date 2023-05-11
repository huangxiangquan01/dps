package cn.xqhuang.dps.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @author huangxq
 * @description: 认证服务器
 * @date 2022/11/714:37
 */
@Configuration
@EnableAuthorizationServer  //开启​认证服务器
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 加密方式 BCrypt
     */
    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * druid数据源
     * @return:
     */
    @Autowired
    private DataSource druidDataSource;

    /**
     * jdbc管理令牌
     *步骤：
     * 1.创建相关表
     * 2.添加jdbc相关依赖
     * 3.配置数据源信息
     * @return:
     */
    @Bean
    public TokenStore jdbcTokenStore(){
        return new JdbcTokenStore(druidDataSource);
    }

    /*@Autowired
    private RedisConnectionFactory redisConnectionFactory;
    *//**
     * Redis令牌管理
     * 步骤：
     * 1.启动redis
     * 2.添加redis依赖
     * 3.添加redis 依赖后, 容器就会有 RedisConnectionFactory 实例
     * @return
     *//*
    @Bean
    public TokenStore redisTokenStore(){
        return  new RedisTokenStore(redisConnectionFactory);
    }*/

    /**
     * 授权码管理策略
     * @return
     */
    @Bean
    public AuthorizationCodeServices jdbcAuthorizationCodeServices(){
        //使用JDBC方式保存授权码到 oauth_code中
        return new JdbcAuthorizationCodeServices(druidDataSource);
    }

    /**
     * 使用 JDBC 方式管理客户端信息
     * @return:
     */
    @Bean
    public ClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(druidDataSource);
    }


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //密码模式需要配置认证管理器
       endpoints.authenticationManager(authenticationManager);
        //刷新令牌获取新令牌时需要         
        endpoints.userDetailsService(userDetailsService);
        //令牌管理策略       
        endpoints.tokenStore(jdbcTokenStore());
        //授权码管理策略，针对授权码模式有效，会将授权码放到 auth_code 表，授权后就会删除它    
        endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices());
    }

    /**
     *
        配置说明：
        可以配置："authorization_code", "password", "implicit","client_credentials","refresh_token"
                ​​scopes​​:授权范围标识，比如指定微服务名称，则只可以访问指定的微服务
                ​​autoApprove​​: false跳转到授权页面手动点击授权，true不需要手动授权，直接响应授权码
                ​​redirectUris​​​：当获取授权码后，认证服务器会重定向到指定的这个​​URL​​​，并且带着一个授权码​​code​​响应。
                            ​​withClient​​:允许访问此认证服务器的客户端ID
                ​​secret​​:客户端密码，加密存储
                ​​authorizedGrantTypes​​:授权类型，支持同时多种授权类型

     */
    /**
     * 配置被允许访问此认证服务器的客户端详细信息
     * 1.内存管理
     * 2.数据库管理方式
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory()
                //客户端名称
                .withClient("test-pc")
                //客户端密码
                .secret(passwordEncoder.encode("123456"))
                //资源id,商品资源
                // .resourceIds("oauth2-server")
                //授权类型, 可同时支持多种授权类型
                .authorizedGrantTypes("refresh_token", "authorization_code", "implicit", "password", "client_credentials")
                //授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
                .scopes("all")
                // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码
                .autoApprove(false)
                .redirectUris("http://www.baidu.com/")//客户端回调地址
                ;*/
        /**
         * 配置被允许访问此认证服务器的客户端详细信息
         * 1.内存管理
         * 2.数据库管理方式
         */
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * 指定​​isAuthenticated()​​​认证后可以访问 ​​/oauth/check_token​​​ 端点,
     * 指定​​ permitAll()​​​ 所有人可访问​​/oauth/token_key​​​端点，后面要获取公钥。
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        //所有人可访问 /oauth/token_key 后面要获取公钥, 默认拒绝访问
        security.tokenKeyAccess("permitAll()");
        // 认证后可访问 /oauth/check_token , 默认拒绝访问
        security.checkTokenAccess("isAuthenticated()");
    }
}
