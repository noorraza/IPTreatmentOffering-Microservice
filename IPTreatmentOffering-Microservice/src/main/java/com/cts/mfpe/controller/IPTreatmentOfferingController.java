package com.cts.mfpe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cts.mfpe.exception.AuthorizationException;
import com.cts.mfpe.exception.ExpertsiseNotFoundException;
import com.cts.mfpe.exception.IPTreatmentPackageNotFoundException;
import com.cts.mfpe.exception.IdNotFoundException;
import com.cts.mfpe.exception.SpecialistIdExistException;
import com.cts.mfpe.feign.AuthorisingClient;
import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;
import com.cts.mfpe.model.SpecialistDetail;
import com.cts.mfpe.service.IPTreatmentOfferingService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class IPTreatmentOfferingController {

	@Autowired
	private IPTreatmentOfferingService ipOfferingService;

	@Autowired
	private AuthorisingClient authorisingClient;

	/**
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 * @throws Exception
	 */
	@GetMapping("/ipTreatmentPackages")
	@ApiOperation(notes = "Returns the list of IP Treatment Packages", value = "Find IP Treatment Package")
	public List<IPTreatmentPackage> getAllIPTreatmentPackage(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException {
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.findAllIPTreatmentPackages();
		} else {
			throw new AuthorizationException("Not allowed");
		}

	}

	/**
	 * @param ailment
	 * @param packageName
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 * @throws IPTreatmentPackageNotFoundException
	 * @throws Exception
	 */
	@GetMapping("/ipTreatmentPackageByName/{ailment}/{packageName}")
	@ApiOperation(notes = "Returns the IP Treatment Package based on package name", value = "Find IP Treatment Package by name")
	public IPTreatmentPackage getIPTreatmentPackageByName(
			@ApiParam(name = "ailment", value = "ailment of the package") @PathVariable AilmentCategory ailment,
			@ApiParam(name = "packageName", value = "name of the package") @PathVariable String packageName,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, IPTreatmentPackageNotFoundException {

		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.findIPTreatmentPackageByName(ailment, packageName);
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}

	/**
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 * @throws Exception
	 */
	@GetMapping("/specialists")
	@ApiOperation(notes = "Returns the list of specialists along with their experience and contact details", value = "Find specialists")
	public List<SpecialistDetail> getAllSpecialist(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException {
		log.info("Inside ================"+requestTokenHeader);
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.findAllSpecialists();
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}

	@GetMapping("/health-check")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Ok", HttpStatus.OK);
	}
	
	
	@GetMapping("/specialistsByExpertsise/{expertsise}")
	@ApiOperation(notes = "Returns the list of specialists as per there expertsise along with their experience and contact details", value = "Find specialists by their expertsise")
	public List<SpecialistDetail> getSpecialistByExpertsise(
			@ApiParam(name = "expertsise", value = "specialists expertsise") @PathVariable String expertsise,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
					throws AuthorizationException,ExpertsiseNotFoundException{
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.getSpecialistByExpertsise(expertsise);
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@PostMapping("/addSpecialist")
	@ApiOperation(notes="Add a new specialist", value="Add specialist")
	public ResponseEntity<String> addNewSpecialist( 
			@ApiParam(name = "New Specialist", value = "Add new Specialist") @RequestBody SpecialistDetail specialsit,
			@RequestHeader(value="Authorization", required=true) String requestTokenHeader
			)throws AuthorizationException, SpecialistIdExistException {
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			ipOfferingService.addSpecialist(specialsit);
			return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED.toString());
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@DeleteMapping("/deleteSpecialist/{specialistId}")
	@ApiOperation(notes="Delete specialist", value="Delete specialist")
	public ResponseEntity<String> deleteSpecialist( 
			@ApiParam(name = "Delete Specialist", value = "Delete Specialist by id") @PathVariable Integer specialistId,
			@RequestHeader(value="Authorization", required=true) String requestTokenHeader
			)throws AuthorizationException, IdNotFoundException {
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			ipOfferingService.deleteSpecialist(specialistId);
			return ResponseEntity.status(HttpStatus.OK).body("200 Deleted");
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@PutMapping("/updatePackage/{id}")
	@ApiOperation(notes="Update Package", value="Update a package by there id")
	public ResponseEntity<String> updatePackage( 
			@ApiParam(name = "update package ", value = "Update package  by id") @PathVariable Integer id,
			@ApiParam(name = "package enity", value = "package entity body") @RequestBody IPTreatmentPackage packages,
			@RequestHeader(value="Authorization", required=true) String requestTokenHeader
			)throws AuthorizationException, IdNotFoundException, MethodArgumentTypeMismatchException{
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			ipOfferingService.updatePackageById(id, packages);
			return ResponseEntity.status(HttpStatus.OK).body("200  Packages Updated");
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}
}
