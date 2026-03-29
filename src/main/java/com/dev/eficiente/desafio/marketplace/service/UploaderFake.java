package com.dev.eficiente.desafio.marketplace.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class UploaderFake {

    public List<String> upload(final List<MultipartFile> imagens) {
        return imagens.stream().map(this::createLinkProduto).toList();
    }

    private String createLinkProduto(MultipartFile imagem) {
        String baseLink = "http://linkfake/storage/";
        return baseLink + imagem.getOriginalFilename();
    }
}
