package com.etz.merchanttransactionsync;

import com.etz.merchanttransactionsync.model.ecarddb.WebConnectTransactionLog;
import com.etz.merchanttransactionsync.model.payoutletnet.PayOutletTransactionLog;
import com.etz.merchanttransactionsync.repository.payoutletnet.PayOutletTransactionRepository;
import com.etz.merchanttransactionsync.service.WebConnectService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.etz.merchanttransactionsync.repository.ecarddb.WebConnectTransactionRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
@ComponentScan({"com.etz.merchanttransactionsync.model","com.etz.merchanttransactionsync.service"})
public class MerchantTransactionSyncApplication  implements CommandLineRunner {

	@Autowired
	WebConnectTransactionRepository webConnectTransactionRepository;

	@Autowired
	PayOutletTransactionRepository payOutletTransactionRepository;

	@Autowired
	WebConnectService webConnectService;
        
        @Value("${app.data.sync-interval.in.milliseconds}")
        private String interval;
        
	public static void main(String[] args) {
		SpringApplication.run(MerchantTransactionSyncApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Page<WebConnectTransactionLog> some = webConnectTransactionRepository.findAll(PageRequest.of(1, 5));
		log.info("The retrieved WebConnect Data::{}", some.getContent());

		List<PayOutletTransactionLog > payOutletTransactionLogList = payOutletTransactionRepository.findAllById(Arrays.asList(1L,2L,3L,4L));
		log.info("The retrieved  payOutletTransactionLog  Data ::{}", payOutletTransactionLogList);
	}
	
	@Scheduled(fixedDelay = 120000)
	public String scheduledExecution() throws Exception {
		
                log.info("Synced Interval In MiliSeconds:::{}", interval);
		List<PayOutletTransactionLog>  payOutletTransactionLogLists = payOutletTransactionRepository.findByUniqueMerchantCode("3561704009");
		//log.info("The retrieved By MerchantCode payOutletTransactionLog  Data ::{}", payOutletTransactionLogLists);
		log.info("The retrieved Unique payOutletTransactionLog Single Data ::{}", payOutletTransactionRepository.findById(1L));
		//payOutletTransactionRepository.findById(1L)
		return null;         
	}
        
        @Scheduled(fixedDelayString = "${app.data.sync-interval.in.milliseconds}")
	public void scheduledTransactionSyncing() throws Exception {
			Date lastsyncedTime = webConnectTransactionRepository.findById("01ESA20200507012609MDJ5JM").get().getTransDate();
			List<WebConnectTransactionLog> webConnectTransactionLogList = webConnectService.getUnsyncedTransactions("0000000001", lastsyncedTime);
		    log.info("The Length of  Retrieved Transaction SyncedData ::{}", webConnectTransactionLogList.size());
			log.info("The Retrieved Transaction SyncedData ::{}", webConnectTransactionLogList);
	}
        
}
