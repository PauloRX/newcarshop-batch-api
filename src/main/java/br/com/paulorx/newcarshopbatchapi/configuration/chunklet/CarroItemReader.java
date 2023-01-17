package br.com.paulorx.newcarshopbatchapi.configuration.chunklet;

import br.com.paulorx.newcarshopbatchapi.dto.CarroDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.*;

import java.util.Iterator;
import java.util.List;

public class CarroItemReader implements ItemReader<CarroDto>, StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarroItemReader.class);

    private Iterator<CarroDto> carroInIterator;
    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info("Inicializando o READER...");
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        List<CarroDto> carroDtoList = (List<CarroDto>) executionContext.get("carroInList");
        this.carroInIterator = carroDtoList.iterator();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("Finalizando o READER...");
        return ExitStatus.COMPLETED;
    }

    @Override
    public CarroDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (carroInIterator != null && carroInIterator.hasNext()) {
            return this.carroInIterator.next();
        }
        return null;
    }
}
