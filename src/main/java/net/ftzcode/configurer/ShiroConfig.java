package net.ftzcode.configurer;

import net.ftzcode.component.shiro.CuteAuthorizationFilter;
import net.ftzcode.component.shiro.CuteCacheSessionDao;
import net.ftzcode.component.shiro.CuteRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by WangZiHe on ${DATE}
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 * Github:https://github.com/yancheng199287
 */
@Configuration
public class ShiroConfig {


    @Bean
    public CuteRealm cuteRealmBean() {
        return new CuteRealm();
    }

    @Bean
    public CuteCacheSessionDao cuteCacheSessionDaoBean() {
        return new CuteCacheSessionDao();
    }

    @Bean
    public CuteAuthorizationFilter cuteAuthorizationFilterBean() {
        return new CuteAuthorizationFilter();
    }


    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcherBean() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    //打开应用返回的
    @Bean
    public SimpleCookie sessionIdCookieBean() {
        SimpleCookie cookie = new SimpleCookie("sid");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        return cookie;
    }


    //登录成功返回的
    @Bean
    public SimpleCookie rememberMeSessionBean() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(86400 * 5);//5天
        return cookie;
    }

    //rememberme cookie加密的密钥
    @Bean
    public CookieRememberMeManager rememberManagerBean() {
        CookieRememberMeManager meManager = new CookieRememberMeManager();
        meManager.setCipherKey(Base64.decode("aHR0dHA6Ly93d3cuNTIwY29kZS5uZXQgUVE6NjQ4ODMwNjA1"));
        meManager.setCookie(rememberMeSessionBean());
        return meManager;
    }


    @Bean
    public DefaultWebSessionManager sessionManageBean() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //开启session调度，每隔多久执行检查
        sessionManager.setSessionValidationSchedulerEnabled(false);
        //无效session是否删除
        sessionManager.setDeleteInvalidSessions(true);
        //开启sessionid cookies
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookieBean());
        return sessionManager;
    }


    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManagerBean() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        securityManager.setSessionManager(sessionManageBean());
        securityManager.setRememberMeManager(rememberManagerBean());
        securityManager.setRealm(cuteRealmBean());
        return securityManager;
    }


    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{defaultWebSecurityManagerBean()});
        return factoryBean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessorBean() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessorBean")
    public DefaultAdvisorAutoProxyCreator getAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManagerBean());
        return advisor;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        Map<String, Filter> filtersMap = new LinkedHashMap<>();


        factoryBean.setSecurityManager(defaultWebSecurityManagerBean());
        // 这里的key authRole  可以在filterChainDefinitionMap中使用此过滤器，如：   filterChainDefinitionMap.put("/login**", "authRole")
        // 将对 login开头的页面用此过滤器进行拦截认证
        //  filtersMap.put("authRole", getMyAuthorizationFilterBean())
        factoryBean.setFilters(filtersMap);

        //静态配置自定义页面拦截
        //  factoryBean.loginUrl = "/toLogin"
        //  filterChainDefinitionMap.put("/resources/**", "anon")
        // filterChainDefinitionMap.put("/login**", "anon")
        // filterChainDefinitionMap.put("/**", "user")
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }


}
