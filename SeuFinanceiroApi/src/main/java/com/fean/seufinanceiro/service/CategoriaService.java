package com.fean.seufinanceiro.service;


import com.fean.seufinanceiro.model.Categoria;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private static List<Categoria> categorias;

    public CategoriaService(){
        criarListCategoria();
    }

    private List<Categoria> criarListCategoria(){
        if (categorias == null) {
            categorias = new LinkedList<>();
            categorias.add(new Categoria(1L, "Alimentação"));
            categorias.add(new Categoria(2L, "Escola"));
            categorias.add(new Categoria(3L, "Pets"));
            categorias.add(new Categoria(4L, "Transporte"));
        }
        return categorias;
    }

    public void add(Categoria categoria){
        int posicao = categorias.size();
        categoria.setId(posicao + 1L);
        categorias.add(categoria);
    }

    public void remove(Long id){
        categorias.removeIf(categoria -> categoria.getId().equals(id));
    }

    public void update(Categoria categoria){
        categorias.stream().filter( (cat) -> categoria.getId().equals(categoria.getId()))
                .forEach( (cat) -> {
                    cat.setDescricao(categoria.getDescricao());
                });
    }

    public List<Categoria> categorias(){
        return categorias;
    }

    public Categoria finById(Long id) {
        return categorias().stream()
                .filter((categoria) -> categoria.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

}
