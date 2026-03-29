package com.dev.eficiente.desafio.marketplace.model.vo;

import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class NovasImagensRequest {

    @Size(min = 1, message = MessageConstants.QTD_IMAGENS_INVALIDA)
    private List<MultipartFile> imagens;

}
