package com.etz.merchanttransactionsync;

//import com.etz.merchanttransactionsync.model.ecarddb.WebConnectTransactionLog;
import com.etz.merchanttransactionsync.domain.request.PaginationRequest;
import com.etz.merchanttransactionsync.model.ecarddb.WebConnectTransactionLog;
import com.etz.merchanttransactionsync.model.payoutletnet.PayOutletTransactionLog;
//import com.etz.merchanttransactionsync.repository.payoutletnet.PayOutletTransactionRepository;
import com.etz.merchanttransactionsync.repository.payoutletnet.PayOutletTransactionRepository;
import com.etz.merchanttransactionsync.service.ClientMerchantDetailsService;
import com.etz.merchanttransactionsync.service.CustomUserDetailsService;
//import com.etz.merchanttransactionsync.service.WebConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
@RestController
@ComponentScan({"com.etz.merchanttransactionsync.model","com.etz.merchanttransactionsync.repository", "com.etz.merchanttransactionsync.service", "com.etz.merchanttransactionsync.config.security","com.etz.merchanttransactionsync.config.db"})
public class MerchantTransactionSyncApplication  implements CommandLineRunner {

	/*
	

	@Autowired
	PayOutletTransactionRepository payOutletTransactionRepository;

	@Autowired
	WebConnectService webConnectService;

	 */
    
         @Autowired
	 PayOutletTransactionRepository payOutletTransactionRepository;
         
         @Autowired
	 WebConnectTransactionRepository webConnectTransactionRepository;

	 @Autowired
	 CustomUserDetailsService customUserDetailsService;

	@Autowired
	ClientMerchantDetailsService clientMerchantDetailsService;



        @Value("${app.data.sync-interval.in.milliseconds}")
        private String interval;

	public static void main(String[] args) {
		SpringApplication.run(MerchantTransactionSyncApplication.class, args);
	}

	@GetMapping("/getSome")
	public List<WebConnectTransactionLog> getSomeWebConnectTransactions(){
		//return webConnectTransactionRepository.findAll(PageRequest.of(1, 5)).getContent();
		return null;
	}
	// */

	@Override
	public void run(String... args) throws Exception {
		log.info("The retrieved User Data::{}", customUserDetailsService.getUserDetails("afam.okonkwo@etranzactng.com"));
		PaginationRequest paginationRequest = new PaginationRequest();
		paginationRequest.setPage(1);
		paginationRequest.setSize(5);
		//Page<WebConnectTransactionLog> some = webConnectTransactionRepository.findAllBy(WebConnectTransactionLog.class, new HashMap<>(), paginationRequest);
		//log.info("The retrieved WebConnect List Data::{}", webConnectTransactionRepository);
		log.info("The retrieved WebConnect Optional Data::{}", webConnectTransactionRepository.findOneOptional(WebConnectTransactionLog.class,"01ESA20200507012609MDJ5JM"));

		clientMerchantDetailsService.createClientMerchantDetails();
		clientMerchantDetailsService.findClientMerchantDetails("KGIRS_SERVICE");
		log.info("The retrieved  Single payOutletTransactionLog  Data ::{}", payOutletTransactionRepository.findOne(PayOutletTransactionLog.class,1L));
		Set<Long> idSets = new HashSet<>();
		idSets.add(1L);idSets.add(2L);idSets.add(3L);idSets.add(4L);
		List<PayOutletTransactionLog > payOutletTransactionLogList = payOutletTransactionRepository.findAllById(PayOutletTransactionLog.class, idSets);
		//log.info("The retrieved  payOutletTransactionLog  Data  List::{}", payOutletTransactionLogList);



	}
	
	@Scheduled(fixedDelay = 120000)
	public String scheduledExecution() throws Exception {
		/*
                log.info("Synced Interval In MiliSeconds:::{}", interval);
		List<PayOutletTransactionLog>  payOutletTransactionLogLists = payOutletTransactionRepository.findByUniqueMerchantCode("3561704009");
		//log.info("The retrieved By MerchantCode payOutletTransactionLog  Data ::{}", payOutletTransactionLogLists);
		log.info("The retrieved Unique payOutletTransactionLog Single Data ::{}", payOutletTransactionRepository.findById(1L));

		 */

		return null;         
	}
        
        @Scheduled(fixedDelayString = "${app.data.sync-interval.in.milliseconds}")
	public void scheduledTransactionSyncing() throws Exception {
		/*
			Date lastsyncedTime = webConnectTransactionRepository.findById("01ESA20200507012609MDJ5JM").get().getTransDate();
			List<WebConnectTransactionLog> webConnectTransactionLogList = webConnectService.getUnsyncedTransactions("0000000001", lastsyncedTime);
		    log.info("The Length of  Retrieved Transaction SyncedData ::{}", webConnectTransactionLogList.size());
			log.info("The Retrieved Transaction SyncedData ::{}", webConnectTransactionLogList);

		 */
	}
        
}
