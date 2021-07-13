package com.cts.mfpe.service;

import java.util.List;

import com.cts.mfpe.exception.ExpertsiseNotFoundException;
import com.cts.mfpe.exception.IPTreatmentPackageNotFoundException;
import com.cts.mfpe.exception.IdNotFoundException;
import com.cts.mfpe.exception.SpecialistIdExistException;
import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;
import com.cts.mfpe.model.SpecialistDetail;

public interface IPTreatmentOfferingService {
	
	List<IPTreatmentPackage> findAllIPTreatmentPackages();
	IPTreatmentPackage findIPTreatmentPackageByName(AilmentCategory ailment, String packageName) throws IPTreatmentPackageNotFoundException;
	List<SpecialistDetail> findAllSpecialists();
	List<SpecialistDetail> getSpecialistByExpertsise(String expertsise) throws ExpertsiseNotFoundException;
	void addSpecialist(SpecialistDetail specialist) throws SpecialistIdExistException;
	void deleteSpecialist(Integer id) throws IdNotFoundException;
	void updatePackageById(Integer id ,IPTreatmentPackage packages) throws IdNotFoundException;
}
