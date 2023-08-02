package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.domain.filme.DadosCadastroFilme;
import br.com.alura.screenmatch.domain.filme.Filme;
import br.com.alura.screenmatch.domain.filme.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository repository;

    @GetMapping("/formulario")
    public String carregaPaginaFormulario(Long id, Model model) {
        if(id != null) {
            var filme = repository.getReferenceById(id);
            model.addAttribute("filme", filme);
        }

        return "filmes/formulario";
    }

    @GetMapping()
    public String carregaPaginaListagem(Model model) {
        model.addAttribute("lista", repository.findAll());
        return "filmes/listagem";
    }

    @PostMapping("/formulario")
    public String cadastraFilme(DadosCadastroFilme dados) {
        var filme = new Filme(dados);
        repository.save(filme);

        System.out.println(repository.findAll());

        return "redirect:/filmes";
    }

    @PutMapping("/formulario")
    @Transactional
    public String alteraFilme(DadosAlteracaoFilme dados) {
        var filme = repository.getReferenceById(dados.id());
        filme.atualizaDados(dados);

        System.out.println(repository.findAll());

        return "redirect:/filmes";
    }

    @DeleteMapping
    public String removeFilme(Long id) {
        repository.deleteById(id);

        return "redirect:/filmes";
    }

}
