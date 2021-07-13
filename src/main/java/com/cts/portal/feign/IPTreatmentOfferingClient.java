package com.cts.portal.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cts.portal.exception.AuthorizationException;
import com.cts.portal.exception.ExpertsiseNotFoundException;
import com.cts.portal.exception.IPTreatmentPackageNotFoundException;
import com.cts.portal.exception.IdNotFoundException;
import com.cts.portal.exception.SpecialistIdExistException;
import com.cts.portal.model.AilmentCategory;
import com.cts.portal.model.IPTreatmentPackage;
import com.cts.portal.model.SpecialistDetail;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@FeignClient(name = "IPTreatmentOffering-service", url = "${ipoffering.URL}")
public interface IPTreatmentOfferingClient {
    
	@GetMapping("/ipTreatmentPackages")
	public List<IPTreatmentPackage> getAllIPTreatmentPackage(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException;
	
	@GetMapping("/ipTreatmentPackageByName/{ailment}/{packageName}")
	public IPTreatmentPackage getIPTreatmentPackageByName(
			@ApiParam(name = "ailment", value = "ailment of the package") @PathVariable AilmentCategory ailment,
			@ApiParam(name = "packageName", value = "name of the package") @PathVariable String packageName,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, IPTreatmentPackageNotFoundException;
	
	@GetMapping("/specialists")
	public List<SpecialistDetail> getAllSpecialist(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException;
	
	
	@GetMapping("/specialistsByExpertsise/{expertsise}")
	public List<SpecialistDetail> getSpecialistByExpertsise(
			@ApiParam(name = "expertsise", value = "specialists expertsise") @PathVariable String expertsise,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
					throws AuthorizationException,ExpertsiseNotFoundException;
	
	
	@PostMapping("/addSpecialist")
	public ResponseEntity<String> addNewSpecialist( 
			@ApiParam(name = "New Specialist", value = "Add new Specialist") @RequestBody SpecialistDetail specialsit,
			@RequestHeader(value="Authorization", required=true) String requestTokenHeader
			)throws AuthorizationException, SpecialistIdExistException;

	
	@DeleteMapping("/deleteSpecialist/{specialistId}")
	public ResponseEntity<String> deleteSpecialist( 
			@ApiParam(name = "Delete Specialist", value = "Delete Specialist by id") @PathVariable Integer specialistId,
			@RequestHeader(value="Authorization", required=true) String requestTokenHeader
			)throws AuthorizationException, IdNotFoundException;
	
	
	@PutMapping("/updatePackage/{id}")
	public ResponseEntity<String> updatePackage( 
			@ApiParam(name = "update package ", value = "Update package  by id") @PathVariable Integer id,
			@ApiParam(name = "package enity", value = "package entity body") @RequestBody IPTreatmentPackage packages,
			@RequestHeader(value="Authorization", required=true) String requestTokenHeader
			)throws AuthorizationException, IdNotFoundException, MethodArgumentTypeMismatchException;
}