package com.moguying.plant.core.controller.payment;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.payment.request.ImageUploadRequest;
import com.moguying.plant.core.entity.payment.response.ImageUploadResponse;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.service.payment.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/payment")
@Api(tags = "支付相关")
public class PaymentController {

    private Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    PaymentService paymentService;

    @RequestMapping("/upload/image/{id}")
    @ResponseBody
    @ApiOperation("实名上传照片")
    public ResponseData<String> uploadImage(HttpServletRequest request, @PathVariable String id) {
        String path = request.getServletContext().getRealPath("/upload/default1.jpg");
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageUploadRequest imageUploadRequest = new ImageUploadRequest();

            BufferedImage bi = ImageIO.read(new File(path));
            ImageIO.write(bi, "jpg", baos);
            String image = Base64.encodeBase64String(baos.toByteArray(), true);
            imageUploadRequest.setImage(image);
            imageUploadRequest.setMerchantNo(id);
            PaymentResponse<ImageUploadResponse> paymentResponse = paymentService.imageUpload(imageUploadRequest);
            ImageUploadResponse imageUploadResponse = paymentResponse.getData();
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), imageUploadResponse.getFileNum());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


}
