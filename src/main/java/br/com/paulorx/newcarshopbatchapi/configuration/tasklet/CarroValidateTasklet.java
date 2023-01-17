package br.com.paulorx.newcarshopbatchapi.configuration.tasklet;

import br.com.paulorx.newcarshopbatchapi.dto.CarroDto;
import br.com.paulorx.newcarshopbatchapi.utils.CsvFileUtils;
import br.com.paulorx.newcarshopbatchapi.validate.CarroValidate;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.List;

public class CarroValidateTasklet implements Tasklet, StepExecutionListener {
    private String filename;
    private List<CarroDto> carroDtoList;

    public CarroValidateTasklet(String filename) {
        this.filename = filename;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.carroDtoList = new ArrayList<CarroDto>();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution
                .getJobExecution()
                .getExecutionContext()
                .put("carroInList", this.carroDtoList);
        return ExitStatus.COMPLETED;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        CsvFileUtils csvIn = new CsvFileUtils(this.filename, true);
        CarroDto carroDto = csvIn.read();

        while (carroDto != null) {
            this.carroDtoList.add(carroDto);
            carroDto = csvIn.read();
        }
        csvIn.closeReader();

        this.carroDtoList = CarroValidate.validate(this.carroDtoList);

        if (carroDtoList.isEmpty()) {
            throw new RuntimeException("A lista de carros esta vazia!");
        }

        return RepeatStatus.FINISHED;
    }
}
