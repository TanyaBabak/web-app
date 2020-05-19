package com.babak.servlet.impl;

import com.babak.entity.Captcha;
import com.babak.entity.User;
import com.babak.exception.AccessDeniedException;
import com.babak.exception.ValidationException;
import com.babak.service.CaptchaService;
import com.babak.service.UserService;
import com.babak.servlet.AbstractController;
import com.babak.utils.AvatarUploaderUtils;
import com.babak.utils.Hashing;
import com.babak.utils.RegistrationFormBean;
import com.babak.utils.RoutingUtils;
import com.babak.utils.SessionUtils;
import com.babak.utils.constants.EntityConstants;
import com.babak.utils.constants.ErrorConstants;
import com.babak.utils.constants.PathConstants;
import com.babak.utils.constants.UtilConstants;
import com.babak.utils.constants.WebConstants;
import com.babak.utils.strategy.storage.StorageData;
import com.babak.utils.strategy.storage.impl.BuilderStorageData;
import com.babak.utils.strategy.validation.Validation;
import com.babak.utils.strategy.validation.impl.ValidationEmail;
import com.babak.utils.strategy.validation.impl.ValidationLogin;
import com.babak.utils.strategy.validation.impl.ValidationName;
import com.babak.utils.strategy.validation.impl.ValidationPassword;
import com.babak.utils.strategy.validation.impl.ValidationPasswordConfirmation;
import com.babak.utils.strategy.validation.impl.ValidationSurname;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet("/registration")
public class CheckUserRegistrationServlet extends AbstractController {

    private StorageData storageData;
    private AvatarUploaderUtils avatarUploaderUtils;

    /**
     * Check user registration.
     *
     * @param userService    the userService
     * @param captchaService the captchaService
     * @param storageData    the storageData
     */
    public CheckUserRegistrationServlet(UserService userService,
                                        CaptchaService captchaService,
                                        StorageData storageData, AvatarUploaderUtils avatarUploaderUtils) {
        setUserService(userService);
        setCaptchaService(captchaService);
        this.storageData = storageData;
        this.avatarUploaderUtils = avatarUploaderUtils;
    }

    public CheckUserRegistrationServlet() {
    }

    @Override
    public void initServlet(ServletConfig config) {
        BuilderStorageData builderStorageData = new BuilderStorageData();
        String valueParam = config.getServletContext().getInitParameter(WebConstants.STORAGE_DATA);
        storageData = builderStorageData.buildStorageData(valueParam);
        avatarUploaderUtils = new AvatarUploaderUtils();
        logger.debug("Registration servlet was initialized");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        generateCaptcha(request, response);
        logger.debug("Captcha was create and ready for send");
        RoutingUtils.forwardToPage(PathConstants.REGISTRATION_PATH, request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        long currentTime = System.currentTimeMillis();
        logger.debug("Button press time is fixed");
        RegistrationFormBean formBean = getRegistrationForm(request);
        Map<String, String> errors = new HashMap<>();
        long idCapture = storageData.getAttributeId(request);
        if (getCaptchaService().checkTimeOutForRegistration(idCapture, currentTime)) {
            errors.put(WebConstants.TIME_OUT, ErrorConstants.TIME_OVER);
            logger.debug("Registration time out");
        }
        try {
            if (errors.isEmpty()) {
                errors = putMapErrors(formBean, request);
                checkErrors(errors, formBean, request, response);
            } else {
                requestDispatcher(request, response, formBean, errors);
            }
        } catch (ServletException | IOException e) {
            logger.error("Exception occurred : {}", e.getMessage());
            throw new AccessDeniedException("Error opening page");
        }
        getCaptchaService().removeCaptcha(idCapture);
    }

    private Map<String, String> putMapErrors(RegistrationFormBean formBean, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        String password = formBean.getPassword();
        String login = formBean.getLogin();
        Map<String, Validation> validationMap = initMapValidation(password);
        for (Map.Entry<String, Validation> field : validationMap.entrySet()) {
            errors.putAll(validateFieldOfForm(field.getKey(), field.getValue(), formBean));
        }
        logger.debug("All fields went check on validation");
        if (getUserService().isUserExistByLogin(login)) {
            errors.put(WebConstants.LOGIN_EXIST, ErrorConstants.LOGIN_EXISTS);
        }
        try {
            String captcha = request.getParameter(EntityConstants.CAPTCHA);
            if (!getCaptchaService().checkCaptchaByString(captcha)) {
                errors.put(EntityConstants.CAPTCHA, ErrorConstants.CAPTCHA_NOT_CORRECT);
            }
        } catch (NumberFormatException ex) {
            logger.error("Field doesn't be empty");
            throw new ValidationException(ErrorConstants.FIELD_IS_EMPTY);
        }
        return errors;
    }

    private Map<String, Validation> initMapValidation(String password) {
        Map<String, Validation> validationMap = new HashMap<>();
        validationMap.put(EntityConstants.NAME, new ValidationName());
        validationMap.put(EntityConstants.SURNAME, new ValidationSurname());
        validationMap.put(EntityConstants.PASSWORD, new ValidationPassword());
        validationMap.put(EntityConstants.PASSWORD_CONFIRMATION, new ValidationPasswordConfirmation(password));
        validationMap.put(EntityConstants.EMAIL, new ValidationEmail());
        validationMap.put(EntityConstants.LOGIN, new ValidationLogin());
        logger.debug("Map validation's was fill");
        return validationMap;
    }


    /**
     * Get all field from form.
     */
    public RegistrationFormBean getRegistrationForm(HttpServletRequest request) {
        logger.debug("All field form's was read");
        return RegistrationFormBean.builder()
                .name(request.getParameter(EntityConstants.FIRST_NAME))
                .surname(request.getParameter(EntityConstants.SURNAME))
                .email(request.getParameter(EntityConstants.EMAIL))
                .login(request.getParameter(EntityConstants.LOGIN))
                .password(request.getParameter(EntityConstants.PASSWORD))
                .passwordConfirmation(request.getParameter(EntityConstants.PASSWORD_CONFIRMATION))
                .captcha(request.getParameter(EntityConstants.CAPTCHA))
                .build();

    }

    private User buildUser(RegistrationFormBean formBean) {
        return User.builder()
                .name(formBean.getName())
                .surname(formBean.getSurname())
                .email(formBean.getEmail())
                .login(formBean.getLogin())
                .password(Hashing.md5Apache(formBean.getPassword()))
                .build();
    }

    private Map<String, String> validateFieldOfForm(String key, Validation value, RegistrationFormBean formBean) {
        Field[] fieldsRegistrationFormBean = RegistrationFormBean.class.getDeclaredFields();
        return Stream.of(fieldsRegistrationFormBean)
                .filter(f -> f.getName().equals(key))
                .peek(f -> f.setAccessible(true))
                .map(f -> getValueFromField(f, formBean))
                .map(value::executeValidation)
                .filter(str -> !str.equals(UtilConstants.RIGHT))
                .collect(Collectors.toMap(v -> key, Function.identity()));
    }

    private String getValueFromField(Field field, RegistrationFormBean formBean) {
        String value;
        try {
            value = (String) field.get(formBean);
        } catch (IllegalAccessException e) {
            logger.error("It is impossible to take the value from the field");
            throw new ValidationException(ErrorConstants.VALUE_FIELD);
        }
        return value;
    }

    private void requestDispatcher(HttpServletRequest request, HttpServletResponse response,
                                   RegistrationFormBean formBean, Map<String, String> errors)
            throws IOException, ServletException {
        request.setAttribute(WebConstants.FORM_BEAN, formBean);
        request.setAttribute(WebConstants.ERROR, errors);
        generateCaptcha(request, response);
        logger.debug("Fields was fill with wrong");
        RoutingUtils.forwardToPage(PathConstants.REGISTRATION_PATH, request, response);
    }

    private void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Captcha captcha = getCaptchaService().createCaptcha();
        long captchaId = captcha.getId();
        getCaptchaService().putCaptcha(captcha);
        storageData.setAttributes(request, response, captchaId, getCaptchaService());
    }

    private void checkErrors(Map<String, String> errors,
                             RegistrationFormBean formBean,
                             HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException, ServletException {
        if (errors.isEmpty()) {
            User user = buildUser(formBean);
            if (Objects.nonNull(avatarUploaderUtils.checkExtension(request))) {
                errors.put(WebConstants.AVATAR, "Format image is wrong");
                checkErrors(errors, formBean, request, response);
            } else {
                User newUser = getUserService().insertUser(user);
                SessionUtils.setCurrentAccount(request, newUser);
                avatarUploaderUtils.setUserAvatar(request);
                response.sendRedirect(PathConstants.CABINET_JSP_PATH);
            }
        } else {
            requestDispatcher(request, response, formBean, errors);
        }
    }
}
