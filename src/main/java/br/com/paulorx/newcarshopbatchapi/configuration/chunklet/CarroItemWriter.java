package br.com.paulorx.newcarshopbatchapi.configuration.chunklet;

import br.com.paulorx.newcarshopbatchapi.model.Carro;
import br.com.paulorx.newcarshopbatchapi.repository.CarroRepository;
import br.com.paulorx.newcarshopbatchapi.utils.CsvFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class CarroItemWriter implements ItemWriter<Carro>, StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarroItemProcessor.class);
    private CsvFileUtils csvSavedCars;

    @Autowired
    private CarroRepository carroRepository;
    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info("Inicializando o WRITER...");
        this.csvSavedCars = new CsvFileUtils("savedCars", false);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("Finalizando o WRITER...");
        try {
            this.csvSavedCars.closeWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(List<? extends Carro> carroOutList) {
        List<? extends Carro> savedCarsList = this.carroRepository.saveAll(carroOutList);
        savedCarsList.stream().forEach(carro -> {
            try {
                this.csvSavedCars.writer(carro);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
