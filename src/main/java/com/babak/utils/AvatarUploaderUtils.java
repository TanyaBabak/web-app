package com.babak.utils;

import com.babak.entity.User;
import com.babak.repository.impl.UserRepositoryImpl;
import com.babak.utils.constants.JspConstants;
import com.babak.utils.constants.UtilConstants;
import com.babak.utils.constants.WebConstants;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AvatarUploaderUtils {

    private static final Set<String> EXTENSIONS = new HashSet<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    static {
        EXTENSIONS.add("jpeg");
        EXTENSIONS.add("jpg");
        EXTENSIONS.add("png");
        EXTENSIONS.add("gif");
        EXTENSIONS.add("bmp");
    }

    /**
     * Receive user's avatar.
     */
    public void setUserAvatar(HttpServletRequest request) {
        String fullSavePath = getDiskFile(request);
        saveFile(request, fullSavePath);
    }

    public String checkExtension(HttpServletRequest request) {
        String fullSavePath = getDiskFile(request);
        if (fullSavePath != null) {
            String extension = FilenameUtils.getExtension(fullSavePath);
            if (EXTENSIONS.contains(extension)) {
                return extension;
            }
        }
        return null;
    }


    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader(JspConstants.CONTENT_DISPOSITION);
        if (contentDisposition.equals("")) {
            return null;
        }
        String[] items = contentDisposition.split(";");
        for (String s : items) {
            if (s.trim().startsWith(UtilConstants.FILE_NAME)) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }

    private void saveDirectory(String fullSavePath) {
        File fileSaveDir = new File(fullSavePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
    }

    /**
     * Save image in exactly path.
     */
    private void saveFile(HttpServletRequest request, String fullSavePath) {
        HttpSession session = request.getSession(false);
        try {
            Part part = request.getPart(WebConstants.AVATAR);
            String fileUser = extractFileName(part);
            String extension = checkExtension(request);
            if (Objects.nonNull(extension)) {
                session.setAttribute(WebConstants.EXTENSION, "jpg");
            }
            User user = SessionUtils.getCurrentAccount(request);
            String fileName = user.getLogin() + user.getId();
            if (fileName.length() > 0) {
                String filePath = fullSavePath + File.separator + fileName + "." + "jpg";
                part.write(filePath);
            }
        } catch (Exception e) {
            LOGGER.error("File is wrong. Exception occurred : {}", e.getMessage());
        }
    }


    /**
     * Get full path.
     *
     * @return the full path
     */
    private String getDiskFile(HttpServletRequest request) {
        String fullSavePath = null;
        try {
            String appPath = request.getServletContext().getRealPath("");
            if (appPath.endsWith("\\")) {
                fullSavePath = appPath + UtilConstants.SAVE_DIRECTORY;
            } else {
                fullSavePath = appPath + "\\" + UtilConstants.SAVE_DIRECTORY;
            }
            saveDirectory(fullSavePath);
        } catch (Exception e) {
            LOGGER.error("Failed to open file. Exception occurred : {}", e.getMessage());
        }
        return fullSavePath;
    }

}

