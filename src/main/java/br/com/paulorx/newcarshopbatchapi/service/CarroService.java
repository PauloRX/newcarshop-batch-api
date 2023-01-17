package br.com.paulorx.newcarshopbatchapi.service;

import br.com.paulorx.newcarshopbatchapi.model.Carro;
import br.com.paulorx.newcarshopbatchapi.repository.CarroRepository;
import br.com.paulorx.newcarshopbatchapi.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarroService {

    private static final String A_CADA_10_SEG = "*/10 * * * * *";
    private static final String A_CADA_30_SEG = "*/30 * * * * *";
    private static final String A_CADA_60_SEG = "*/60 * * * * *";
    private static final String AS_8_E_9_TDS_DIAS = "0 0 8-9 * * *";
    private static final String DE_HORA_EM_HORA = "0 0 * * * *";
    private static final String AS_6_E_18_TDS_DIAS = "0 0 6,18 * * *";
    private static final String NO_NATAL = "0 0 0 25 12 ?";
    private static final String CADA_30_MIN_ENTRE_7_AS_11_TDS_DIAS = "0 0/30 8-10 * * *";
    private static final String CADA_60_MIN_ENTRE_8_AS_17_SEG_A_SEX = "0 0 8-17 * * MON-FRI";

    private static final Logger LOGGER = LoggerFactory.getLogger(CarroService.class);
    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> buscarTodos() {
        return carroRepository.findAll();
    }

    @Scheduled(cron = AS_6_E_18_TDS_DIAS)
    public BatchStatus batchExecute() {
        LOGGER.info("Iniciou o job" + DateUtils.getNow());

        Map<String, JobParameter> params = new HashMap<>();
        params.put("time", new JobParameter(System.currentTimeMillis()));

        try {
            JobExecution jobExecution = jobLauncher.run(job, new JobParameters(params));
            while (jobExecution.isRunning()) {
                LOGGER.info("Job em execucao...");
            }
            return jobExecution.getStatus();
        } catch (Exception e) {
            return BatchStatus.FAILED;
        }
    }
}
