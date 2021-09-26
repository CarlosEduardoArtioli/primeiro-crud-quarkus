package br.com.caduartioli.controller;

import br.com.caduartioli.dto.CadastrarProdutoDTO;
import br.com.caduartioli.model.Produto;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @GET
    public List<Produto> buscarTodosProdutos() {
        return Produto.listAll();
    }

    @POST
    @Transactional
    public void salvarProduto(CadastrarProdutoDTO dto) {
        Produto p = new Produto();
        p.nome = dto.nome;
        p.valor = dto.valor;
        p.persist();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizarProduto(@PathParam("id") Long id, CadastrarProdutoDTO dto) {
        Optional<Produto> produtoOptional = Produto.findByIdOptional(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            produto.nome = dto.nome;
            produto.valor = dto.valor;
            produto.persist();
        } else {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deletarProduto(@PathParam("id") Long id) {
        Optional<Produto> produtoOptional = Produto.findByIdOptional(id);

        produtoOptional.ifPresentOrElse(Produto::delete, () -> {
            throw new NotFoundException();
        });
    }
}
