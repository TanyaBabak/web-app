package com.babak.entity;

import java.awt.image.BufferedImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Captcha {

    private long id;
    private String captchaString;
    private BufferedImage image;
    private long finishTime;
}
