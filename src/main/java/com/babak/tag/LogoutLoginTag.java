package com.babak.tag;

import com.babak.utils.constants.EntityConstants;
import com.babak.utils.constants.JspConstants;
import com.babak.utils.constants.PathConstants;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutLoginTag extends SimpleTagSupport {

    private String href;
    private boolean login = false;
    private String user;
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutLoginTag.class);

    public void setHref(String href) {
        this.href = href;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    /**
     * Create own taq.
     */
    public void doTag() {

        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();

        try {
            StringBuilder buffer = new StringBuilder();
            String title;
            if (login) {
                href = PathConstants.CABINET_JSP_PATH;
                title = user;
            } else {
                href = PathConstants.REGISTRATION_URL_SERVLET;
                title = EntityConstants.LOGIN;
            }
            buffer.append(JspConstants.START_TAG);
            buffer.append(JspConstants.HREF);
            buffer.append(href);
            buffer.append(JspConstants.FINISH_TAG_HREF);
            buffer.append(title);
            buffer.append(JspConstants.FINISH_TAG);
            out.println(buffer.toString());
        } catch (Exception e) {
            LOGGER.error("Exception occurred : {}", e.getMessage());
        }
    }
}
