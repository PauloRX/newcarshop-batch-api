package br.com.paulorx.newcarshopbatchapi.controller;

import br.com.paulorx.newcarshopbatchapi.model.Carro;
import br.com.paulorx.newcarshopbatchapi.repository.CarroRepository;
import br.com.paulorx.newcarshopbatchapi.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping("/home")
    public String mensagem() {
        return "<h2>Bem-vindo a API Batch - New Car Shop</h2>";
    }

    @GetMapping("/batch")
    public String execute() {
        return carroService.batchExecute().toString();
    }

    @GetMapping("/carros")
    public List<Carro> todosCarros() {
        return carroService.buscarTodos();
    }

}
