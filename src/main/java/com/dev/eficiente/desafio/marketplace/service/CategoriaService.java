package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.exception.BusinessException;
import com.dev.eficiente.desafio.marketplace.exception.CategoriaNotFoundException;
import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.model.vo.CategoriaRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.CategoriaRepository;
import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public String createCategoria(final CategoriaRequestVo vo) throws BusinessException {
        validateIdCategoriaMae(vo.idCategoriaMae());

        Categoria novaCategoria = new Categoria(vo);
        novaCategoria = categoriaRepository.save(novaCategoria);
        return novaCategoria.getNome();

    }

    private void validateIdCategoriaMae(final Long idCategoriaMae) {
        if (idCategoriaMae == null) {
            return;
        }

        boolean existsCategoria = categoriaRepository.existsById(idCategoriaMae);
        if (!existsCategoria) {
            throw new BusinessException(MessageConstants.CATEGORIA_MAE_INVALIDA, HttpStatus.BAD_REQUEST);
        }
    }

    public Categoria findCategoriaById(final Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(CategoriaNotFoundException::new);
    }
}
