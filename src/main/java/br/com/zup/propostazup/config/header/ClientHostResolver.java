package br.com.zup.propostazup.config.header;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class ClientHostResolver {

    private final HttpServletRequest request;

    public ClientHostResolver(HttpServletRequest request) {
        this.request = request;
    }

    public String getClientIP() {
        String xRealIp = request.getHeader("X-Real-IP");
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        String remoteAddr = request.getRemoteAddr();

        if (xRealIp != null)
            return xRealIp;

        if (xForwardedFor != null){
            return xForwardedFor;
        }
        return remoteAddr;
    }
}
