package ch.plannr.common.http;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * User: Raffael Schmid
 * <p/>
 * TODO
 */
public class WrappedRequest implements HttpServletRequest {

    private HttpServletRequest delegate;

    public WrappedRequest(HttpServletRequest delegate) {
        this.delegate = delegate;
    }


    public String getAuthType() {
        return delegate.getAuthType();
    }

    public String getContextPath() {
        return delegate.getContextPath();
    }

    public Cookie[] getCookies() {
        return delegate.getCookies();
    }

    public long getDateHeader(String name) {
        return delegate.getDateHeader(name);
    }

    public String getHeader(String name) {
        return delegate.getHeader(name);
    }

    public Enumeration getHeaderNames() {
        return delegate.getHeaderNames();
    }

    public Enumeration getHeaders(String name) {
        return delegate.getHeaders(name);
    }

    public int getIntHeader(String name) {
        return delegate.getIntHeader(name);
    }

    /**
     * Because Browsers do not send PUT and Delete requests, in flex client i
     * use an additional http-header which overrides the real http method.
     *
     * @return virtual http method based on the X-HTTP-Method-Override http header attribute.
     */
    public String getMethod() {
        String retVal = delegate.getMethod();
        if ("POST".equalsIgnoreCase(retVal)) {
            String methodOverride = getHeader("X-HTTP-Method-Override");
            if ("DELETE".equalsIgnoreCase(methodOverride))
                retVal = "DELETE";
            else if ("PUT".equalsIgnoreCase(methodOverride))
                retVal = "PUT";
        }

        return retVal;
    }

    public String getPathInfo() {
        return delegate.getPathInfo();
    }

    public String getPathTranslated() {
        return delegate.getPathTranslated();
    }

    public String getQueryString() {
        return delegate.getQueryString();
    }

    public String getRemoteUser() {
        return delegate.getRemoteUser();
    }

    public String getRequestedSessionId() {
        return delegate.getRequestedSessionId();
    }

    public String getRequestURI() {
        return delegate.getRequestURI();
    }

    public StringBuffer getRequestURL() {
        return delegate.getRequestURL();
    }

    public String getServletPath() {
        return delegate.getServletPath();
    }

    public HttpSession getSession() {
        return delegate.getSession();
    }

    public HttpSession getSession(boolean create) {
        return delegate.getSession(create);
    }

    public Principal getUserPrincipal() {
        return delegate.getUserPrincipal();
    }

    public boolean isRequestedSessionIdFromCookie() {
        return delegate.isRequestedSessionIdFromCookie();
    }

    public boolean isRequestedSessionIdFromURL() {
        return delegate.isRequestedSessionIdFromURL();
    }

    public boolean isRequestedSessionIdFromUrl() {
        return delegate.isRequestedSessionIdFromUrl();
    }

    public boolean isRequestedSessionIdValid() {
        return delegate.isRequestedSessionIdValid();
    }

    public boolean isUserInRole(String role) {
        return delegate.isUserInRole(role);
    }

    public Object getAttribute(String name) {
        return delegate.getAttribute(name);
    }

    public Enumeration getAttributeNames() {
        return delegate.getAttributeNames();
    }

    public String getCharacterEncoding() {
        return delegate.getCharacterEncoding();
    }

    public int getContentLength() {
        return delegate.getContentLength();
    }

    public String getContentType() {
        String contentType = delegate.getContentType();
        final String retVal;
        if ("application/xml".equals(delegate.getContentType())) retVal = "text/xml";
        else retVal = delegate.getContentType();

        return retVal;
    }

    public ServletInputStream getInputStream() throws IOException {
        return delegate.getInputStream();
    }

    public String getLocalAddr() {
        return delegate.getLocalAddr();
    }

    public Locale getLocale() {
        return delegate.getLocale();
    }

    public Enumeration getLocales() {
        return delegate.getLocales();
    }

    public String getLocalName() {
        return delegate.getLocalName();
    }

    public int getLocalPort() {
        return delegate.getLocalPort();
    }

    public String getParameter(String name) {
        return delegate.getParameter(name);
    }

    public Map getParameterMap() {
        return delegate.getParameterMap();
    }

    public Enumeration getParameterNames() {
        return delegate.getParameterNames();
    }

    public String[] getParameterValues(String name) {
        return delegate.getParameterValues(name);
    }

    public String getProtocol() {
        return delegate.getProtocol();
    }

    public BufferedReader getReader() throws IOException {
        return delegate.getReader();
    }

    public String getRealPath(String path) {
        return delegate.getRealPath(path);
    }

    public String getRemoteAddr() {
        return delegate.getRemoteAddr();
    }

    public String getRemoteHost() {
        return delegate.getRemoteHost();
    }

    public int getRemotePort() {
        return delegate.getRemotePort();
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return delegate.getRequestDispatcher(path);
    }

    public String getScheme() {
        return delegate.getScheme();
    }

    public String getServerName() {
        return delegate.getServerName();
    }

    public int getServerPort() {
        return delegate.getServerPort();
    }

    public boolean isSecure() {
        return delegate.isSecure();
    }

    public void removeAttribute(String name) {
        delegate.removeAttribute(name);
    }

    public void setAttribute(String name, Object o) {
        delegate.setAttribute(name, o);
    }

    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        delegate.setCharacterEncoding(env);
    }


}
