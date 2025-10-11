package com.bslogi.test.booking;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bslogi.test.IntegrationTest;
import com.bslogi.test.TestAuth;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@IntegrationTest
public class InvestorKycReviewIntegrationTest {

    private static final int USER_ID = 2;

    private static final Logger LOG = LoggerFactory.getLogger(InvestorKycReviewIntegrationTest.class);

    @Autowired
    private TestRestTemplate restTemplate;


    private TestAuth testAuth = null;

    @PostConstruct
    public void init() {
        // Needs to be an Admin user for this test to pass
        testAuth = new TestAuth(restTemplate, "test@rangde.in.test");
    }

    @Before
    public void doLogin() {
        testAuth.loginIfNotLoggedIn();
    }

    @Test
    public void testPendingKycsForRIs() throws Exception {
        long userId = USER_ID;
        HttpHeaders headers = testAuth.initHeaders();
        HttpEntity<Long> requestEntity = new HttpEntity<>(userId, headers);
        LOG.debug("getting pending KYCs");
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/kyc/list/pending/RI?page=0&size=2", HttpMethod.GET, requestEntity, String.class);
        
        LOG.debug("pending KYCs: {}", responseEntity);
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
    @Test
    public void testPendingKycsForNRIs() throws Exception {
        long userId = USER_ID;
        HttpHeaders headers = testAuth.initHeaders();
        HttpEntity<Long> requestEntity = new HttpEntity<>(userId, headers);
        LOG.debug("getting pending KYCs");
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/kyc/list/pending/NRI?page=0&size=2", HttpMethod.GET, requestEntity, String.class);
        
        LOG.debug("pending KYCs: {}", responseEntity);
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
    @Test
    public void verifyKycForUser_whenDocsNotAccepted() throws Exception {
        long userId = USER_ID;
        HttpHeaders headers = testAuth.initHeaders();
        HttpEntity<Long> requestEntity = new HttpEntity<>(userId, headers);
        LOG.debug("verifyInvestorKycForUserId: {}", userId);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/kyc/verify", HttpMethod.POST, requestEntity,
                String.class);
        LOG.debug("responseEntity: {}", responseEntity);
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    /*
    @Test
    public void verifyKycForUser_whenDocsAccepted() throws Exception {
        long userId = USER_ID;
        // First mark pan and address proof as accepted
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@rangde.in.test", "MetalRat316$");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        InvestorKyc investorKyc = kycRepository.findByUserId(userId).get();
        investorKyc.setPancardFilename("PanFileName");
        investorKyc.setPancardStatus(DocumentStatus.ACCEPTED);
        investorKyc.setAddressProofFilename1("PanFileName");
        investorKyc.setAddressProofStatus1(DocumentStatus.ACCEPTED);
        investorKyc.setProofType(ProofType.PAN_AND_DRIVING_LICENSE);
        investorKyc.setKycVerified(false);
        kycRepository.save(investorKyc);

        HttpHeaders headers = testAuth.initHeaders();
        HttpEntity<Long> requestEntity = new HttpEntity<>(userId, headers);
        LOG.debug("verifyInvestorKycForUserId: {}", userId);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/kyc/verify", HttpMethod.POST, requestEntity,
                String.class);
        LOG.debug("responseEntity: {}", responseEntity);
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        // clean up above changes
        authentication = new UsernamePasswordAuthenticationToken("test@rangde.in.test", "MetalRat316$");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        InvestorKyc kyc = kycRepository.findByUserId(userId).get();
        kyc.setPancardStatus(DocumentStatus.PENDING);
        kyc.setAddressProofStatus1(DocumentStatus.PENDING);
        kyc.setKycVerified(false);
        kycRepository.save(kyc);

    }

    @Test
    public void verifyPanForUser() {

        KycActionParams kycActionParams = new KycActionParams();
        kycActionParams.setUserId(USER_ID);
        kycActionParams.setKycProofType(KycProofType.PANCARD);
        kycActionParams.setStatus(DocumentStatus.ACCEPTED);
        long userId = USER_ID;
        HttpHeaders headers = testAuth.initHeaders();
        HttpEntity<KycActionParams> requestEntity = new HttpEntity<>(kycActionParams, headers);
        LOG.debug("verifyInvestorKycForUserId: {}", userId);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/kyc/verifyDocument", HttpMethod.POST,
                requestEntity, String.class);
        LOG.debug("responseEntity: {}", responseEntity);
        LOG.debug("responseEntity.getBody: {}", responseEntity.getBody());
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        // clean up above changes
        // authenticate as admin first, to be able to invoke the required repo
        // methods
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@rangde.in.test", "MetalRat316$");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        InvestorKyc investorKyc = kycRepository.findByUserId(kycActionParams.getUserId()).get();
        investorKyc.setNameOnId("");
        investorKyc.setPancardStatus(DocumentStatus.PENDING);
        kycRepository.save(investorKyc);
    }

    @Test
    public void verifyAddressProofForUser() throws Exception {
        KycActionParams kycActionParams = new KycActionParams();
        kycActionParams.setUserId(USER_ID);
        kycActionParams.setAddress(currentAddress());
        kycActionParams.setKycProofType(KycProofType.ADDRESS_PROOF_1);
        kycActionParams.setStatus(DocumentStatus.ACCEPTED);
        long userId = USER_ID;
        HttpHeaders headers = testAuth.initHeaders();
        HttpEntity<KycActionParams> requestEntity = new HttpEntity<>(kycActionParams, headers);
        LOG.debug("verifyInvestorKycForUserId: {}", userId);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/kyc/verifyDocument", HttpMethod.POST,
                requestEntity, String.class);
        LOG.debug("responseEntity: {}", responseEntity);
        LOG.debug("responseEntity.getBody: {}", responseEntity.getBody());
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        // clean up above changes
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@rangde.in.test", "MetalRat316$");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        InvestorKyc investorKyc = kycRepository.findByUserId(kycActionParams.getUserId()).get();
        investorKyc.setAddressProofStatus1(DocumentStatus.PENDING);
        kycRepository.save(investorKyc);
    }
*/
    @Test
    public void notifyRejection() throws Exception {
        HttpHeaders headers = testAuth.initHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/kyc/" + USER_ID + "/notifyRejection", HttpMethod.POST,
            requestEntity, String.class);
        LOG.debug("responseEntity: {}", responseEntity);
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }
 
}
