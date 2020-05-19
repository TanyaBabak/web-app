package com.babak.servlet.impl;

import com.babak.entity.Captcha;
import com.babak.exception.InternalServerErrorException;
import com.babak.service.CaptchaService;
import com.babak.servlet.AbstractController;
import com.babak.utils.constants.WebConstants;
import com.babak.utils.strategy.storage.StorageData;
import com.babak.utils.strategy.storage.impl.BuilderStorageData;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/captchaServlet")
public class CaptchaServlet extends AbstractController {

    private StorageData storageData;
    private static final String FORMAT_PNG = "png";

    @Override
    public void initServlet(ServletConfig servletConfig) {
        BuilderStorageData builderStorageData = new BuilderStorageData();
        String valueParam = servletConfig.getServletContext().getInitParameter(WebConstants.STORAGE_DATA);
        storageData = builderStorageData.buildStorageData(valueParam);
        logger.debug("Captcha servlet  start");
    }


    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        CaptchaService captchaService = storageData.getAttributeCaptchaService(httpServletRequest);
        disableCaching(httpServletResponse);
        long captchaId = storageData.getAttributeId(httpServletRequest);
        Captcha captcha = captchaService.receiveImageById(captchaId);
        try (OutputStream outputStream = httpServletResponse.getOutputStream()) {
            ImageIO.write(captcha.getImage(), FORMAT_PNG, outputStream);
            logger.debug("Captcha was successfully written to output");
        } catch (IOException ex) {
            logger.error("Failed to write image to output", ex);
            throw new InternalServerErrorException("Failed to write image to output" + ex.getMessage(), ex);

        }
    }

    private void disableCaching(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
    }
}
