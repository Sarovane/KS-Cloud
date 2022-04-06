package com.ihealthink.sk.gateway.fillter;


import com.ihealthink.ks.common.constant.CacheConstants;
import com.ihealthink.ks.common.constant.HttpStatus;
import com.ihealthink.ks.common.constant.SecurityConstants;
import com.ihealthink.ks.common.constant.TokenConstants;
import com.ihealthink.ks.common.utils.JwtUtils;
import com.ihealthink.ks.common.utils.ServletUtils;
import com.ihealthink.ks.common.utils.StringUtils;
import com.ihealthink.sk.gateway.common.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局拦截器-登录校验拦截
 *
 * @author xiaoyang
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthFilter {


    @Autowired
    private final RedisService redisService;


//    @Bean
//    @Order(0)
//    public GlobalFilter AuthFilter() {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpRequest.Builder mutate = request.mutate();
//
//            String url = request.getURI().getPath();
//            // 跳过不需要验证的路径
//            List<String> list =new ArrayList<>();
//
//            if (!StringUtils.matches(url, list)) {
//                return chain.filter(exchange);
//            }
//            String token = getToken(request);
//            if (StringUtils.isEmpty(token)) {
//                return unauthorizedResponse(exchange, "令牌不能为空");
//            }
//            Claims claims = JwtUtils.parseToken(token);
//            if (claims == null) {
//                return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
//            }
//            String userkey = JwtUtils.getUserKey(claims);
//            boolean islogin = redisService.hasKey(getTokenKey(userkey));
//            if (!islogin) {
//                return unauthorizedResponse(exchange, "登录状态已过期");
//            }
//            String userid = JwtUtils.getUserId(claims);
//            String username = JwtUtils.getUserName(claims);
//            if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username)) {
//                return unauthorizedResponse(exchange, "令牌验证失败");
//            }
//
//            // 设置用户信息到请求
//            addHeader(mutate, SecurityConstants.USER_KEY, userkey);
//            addHeader(mutate, SecurityConstants.DETAILS_USER_ID, userid);
//            addHeader(mutate, SecurityConstants.DETAILS_USERNAME, username);
//            // 内部请求来源参数清除
//            removeHeader(mutate, SecurityConstants.FROM_SOURCE);
//            return chain.filter(exchange.mutate().request(mutate.build()).build());
//        };
//    }


    /**
     * 鉴权异常处置
     */
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 获取请求token
     */
    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(TokenConstants.AUTHENTICATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

    /**
     * 获取缓存key
     */
    private String getTokenKey(String token) {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = ServletUtils.urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    /**
     * 内部请求来源参数清除
     */
    private void removeHeader(ServerHttpRequest.Builder mutate, String name) {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }
}
