package ch.plannr.common.http;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * User: Raffael Schmid
 * <p/>
 * TODO
 */
public class WrappedResponse implements HttpServletResponse {
    private HttpServletResponse delegate;

    public WrappedResponse(HttpServletResponse delegate) {
        this.delegate = delegate;
    }

    public void addCookie(Cookie cookie) {
        delegate.addCookie(cookie);
    }

    public void addDateHeader(String name, long date) {
        delegate.addDateHeader(name, date);
    }

    public void addHeader(String name, String value) {
        delegate.addHeader(name, value);
    }

    public void addIntHeader(String name, int value) {
        delegate.addIntHeader(name, value);
    }

    public boolean containsHeader(String name) {
        return delegate.containsHeader(name);
    }

    public String encodeRedirectURL(String url) {
        return delegate.encodeRedirectURL(url);
    }

    public String encodeRedirectUrl(String url) {
        return delegate.encodeRedirectUrl(url);
    }

    public String encodeURL(String url) {
        return delegate.encodeURL(url);
    }

    public String encodeUrl(String url) {
        return delegate.encodeUrl(url);
    }

    public void sendError(int sc) throws IOException {
        delegate.sendError(sc);
    }

    public void sendError(int sc, String msg) throws IOException {
        delegate.sendError(sc, msg);
    }

    public void sendRedirect(String location) throws IOException {
        delegate.sendRedirect(location);
    }

    public void setDateHeader(String name, long date) {
        delegate.setDateHeader(name, date);
    }

    public void setHeader(String name, String value) {
        delegate.setHeader(name, value);
    }

    public void setIntHeader(String name, int value) {
        delegate.setIntHeader(name, value);
    }

    public void setStatus(int sc) {
        delegate.setStatus(sc);
    }

    public void setStatus(int sc, String sm) {
        delegate.setStatus(sc, sm);
    }

    public void flushBuffer() throws IOException {
        delegate.flushBuffer();
    }

    public int getBufferSize() {
        return delegate.getBufferSize();
    }

    public String getCharacterEncoding() {
        return delegate.getCharacterEncoding();
    }

    public String getContentType() {
        return delegate.getContentType();
    }

    public Locale getLocale() {
        return delegate.getLocale();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return delegate.getOutputStream();
    }

    public PrintWriter getWriter() throws IOException {
        return delegate.getWriter();
    }

    public boolean isCommitted() {
        return delegate.isCommitted();
    }

    public void reset() {
        delegate.reset();
    }

    public void resetBuffer() {
        delegate.resetBuffer();
    }

    public void setBufferSize(int size) {
        delegate.setBufferSize(size);
    }

    public void setCharacterEncoding(String charset) {
        delegate.setCharacterEncoding(charset);
    }

    public void setContentLength(int len) {
        delegate.setContentLength(len);
    }

    public void setContentType(String type) {
        delegate.setContentType(type);
    }

    public void setLocale(Locale loc) {
        delegate.setLocale(loc);
    }
}
