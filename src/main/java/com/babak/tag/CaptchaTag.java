package com.babak.tag;

import com.babak.exception.InternalServerErrorException;
import com.babak.utils.constants.ErrorConstants;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.log4j.Logger;

public class CaptchaTag extends SimpleTagSupport {

    private String url = "";
    private boolean hiddenField = false;
    private static final Logger LOGGER = Logger.getLogger(CaptchaTag.class);

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHiddenField(boolean hiddenField) {
        this.hiddenField = hiddenField;
    }

    /**
     * Create own taq.
     */
    public void doTag() {

        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();

        try {
            StringBuilder buffer = new StringBuilder();
            if (hiddenField) {
                buffer.append("<input type=\"hidden\" name=\"captchaId\""
                        + "id=\"captchaId\" value\"${captchaId}\" > \n");
                LOGGER.debug("Hidden field exist");
            }
            buffer.append("<img alt=\"\" src=\"");
            buffer.append(url);
            buffer.append("\"/>");
            LOGGER.debug("Captcha was send");
            out.println(buffer.toString());
        } catch (Exception e) {
            LOGGER.error("Captcha wasn't send");
            throw new InternalServerErrorException(ErrorConstants.FAIL_SEND_CAPTCHA + e.getMessage(), e);
        }
    }
}
