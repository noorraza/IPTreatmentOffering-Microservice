package com.cts.portal.controller;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cts.portal.exception.ExpertsiseNotFoundException;
import com.cts.portal.exception.IPTreatmentPackageNotFoundException;
import com.cts.portal.exception.IdNotFoundException;
import com.cts.portal.feign.IPTreatmentOfferingClient;
import com.cts.portal.model.FormInputsGetByExpertsise;
import com.cts.portal.model.FormInputsGetByPackageName;
import com.cts.portal.model.IPTreatmentPackage;
import com.cts.portal.model.PackageDetail;
import com.cts.portal.model.SpecialistDetail;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/portal")
@Log4j2
public class IpTreatmentOfferingController {

	@Autowired
	private IPTreatmentOfferingClient client;

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/specialists")
	public ModelAndView showSpecialistPage(HttpServletRequest request) throws Exception {
		
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * get the list of specialists using feign client of IPOfferingMicroservice
		 */
		log.info("Inside /specialists");
		List<SpecialistDetail> specialists = client
				.getAllSpecialist((String) request.getSession().getAttribute("Authorization"));
		ModelAndView model = new ModelAndView("user-view-list-of-specialist-page");
		model.addObject("specialists", specialists);
		return model;
	}

	/**
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/ipTreatmentPackages")
	public ModelAndView showIPTreatmentPackages(Model model, HttpServletRequest request) throws Exception {
		log.info("Inside IP Treatment Packages");
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		List<IPTreatmentPackage> packageDetails = client
				.getAllIPTreatmentPackage((String) request.getSession().getAttribute("Authorization"));
		ModelAndView modelAndView = new ModelAndView("user-view-package-detail-page");
		modelAndView.addObject("ipTreatmentPackagekageName", packageDetails);
		return modelAndView;
	}
	
	@GetMapping("/updatePackage")
	public ModelAndView updateIpTreamentPackages(HttpServletRequest request)throws Exception{
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		List<IPTreatmentPackage> packageDetails = client
				.getAllIPTreatmentPackage((String) request.getSession().getAttribute("Authorization"));
		ModelAndView modelAndView = new ModelAndView("user-update-package-page");
		modelAndView.addObject("ipTreatmentPackagekageName", packageDetails);
		return modelAndView;
		
	}
	@GetMapping("/updateForm/{pid}")
	public ModelAndView packageUpdateForm(
			HttpServletRequest request, 
			@ModelAttribute("IpTreatmentPackage") IPTreatmentPackage ipTreatmentPackage,
			@PathVariable("pid") Integer id) throws Exception{
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		ModelAndView modelAndView = new ModelAndView("user-update-package-form");
		List<IPTreatmentPackage> packageDetails = client
				.getAllIPTreatmentPackage((String) request.getSession().getAttribute("Authorization"));
		IPTreatmentPackage pkg=null;
		for(IPTreatmentPackage p:packageDetails) {
			if(p.getTreatmentPackageId()==id)
				pkg=p;
		}
		ipTreatmentPackage.setAilmentCategory(pkg.getAilmentCategory());
		ipTreatmentPackage.setTreatmentPackageId(pkg.getTreatmentPackageId());
		ipTreatmentPackage.setPackageDetail(pkg.getPackageDetail());
		return modelAndView;
		
	}
	
	@PostMapping("/updatePackage")
	public ModelAndView updateIpTreamentPackages2(HttpServletRequest request,@ModelAttribute("IPTreatmentPackage") IPTreatmentPackage ipTreatmentPackage)throws Exception{
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		log.info("packageId : "+ ipTreatmentPackage.getAilmentCategory());
		client.updatePackage(ipTreatmentPackage.getTreatmentPackageId(), ipTreatmentPackage,(String) request.getSession().getAttribute("Authorization"));
		return new ModelAndView("redirect:/portal/updatePackage");
	}

	/**
	 * @param formInputsGetByPackageName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/ipTreatmentPackageByName")
	public ModelAndView showIPTreatmentPackageByName2(
			@ModelAttribute("formInputsGetByPackageName") FormInputsGetByPackageName formInputsGetByPackageName,
			HttpServletRequest request) throws Exception {
		
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, 
		 * then allow access to view
		 */
		ModelAndView model = new ModelAndView("user-package-detail-by-name-page");
		if (formInputsGetByPackageName != null && formInputsGetByPackageName.getAilment() != null
				&& formInputsGetByPackageName.getPackageName() != null) {
			try {
				/*
				 * get the package details by Name 
				 * using feign client of IPOfferingMicroservice
				 */
				IPTreatmentPackage ipTreatmentPackagekageName = client.getIPTreatmentPackageByName(
						formInputsGetByPackageName.getAilment(),
						formInputsGetByPackageName.getPackageName(),
						(String) request.getSession().getAttribute("Authorization"));
				model.addObject("ipTreatmentPackagekageName", ipTreatmentPackagekageName);
			} catch (IPTreatmentPackageNotFoundException e) {
				model.addObject("error", e.getMessage());
			}
		}
		return model;
	}
	
	@GetMapping(value="/specialistsByExpertsise")
	public ModelAndView showSpecilistByExperties(
			@ModelAttribute("FormInputsGetByExpertsise") FormInputsGetByExpertsise formInputsGetByExpertsise,
			HttpServletRequest request) throws Exception {
		
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, 
		 * then allow access to view
		 */
		ModelAndView model = new ModelAndView("user-package-detail-by-expertsise-page");
		if (formInputsGetByExpertsise !=null && formInputsGetByExpertsise.getExpertsise() != null) {
			try {
				/*
				 * get the package details by Name 
				 * using feign client of IPOfferingMicroservice
				 */
				List<SpecialistDetail> specialists = client.
						getSpecialistByExpertsise(formInputsGetByExpertsise.getExpertsise(),(String) request.getSession().getAttribute("Authorization"));
				model.addObject("specialists", specialists);
			} catch (ExpertsiseNotFoundException e) {
				model.addObject("error", e.getMessage());
			}
		}
		return model;
	}
	
	@GetMapping("/deleteSpecialist")
	public ModelAndView deleteSpecialaist(HttpServletRequest request) throws Exception {
		
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		ModelAndView model=new ModelAndView("user-delete-specialist-page");
		List<SpecialistDetail> specialists = client
				.getAllSpecialist((String) request.getSession().getAttribute("Authorization"));
		model.addObject("specialists", specialists);
		return model;
	}
	
	@GetMapping("/deleteSpecialist/{id}")
	public ModelAndView deleteSpecialist2(HttpServletRequest request, @PathVariable("id") Integer id )
	throws Exception, IdNotFoundException{
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		client.deleteSpecialist(id, (String) request.getSession().getAttribute("Authorization"));
		return new ModelAndView("redirect:/portal/deleteSpecialist");
	}
	
	
	@GetMapping("/addSpecialist")
	public ModelAndView addSpecialist(HttpServletRequest request, @ModelAttribute("SpecialistDetail") SpecialistDetail specialistDetail) 
	throws Exception{
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		List<SpecialistDetail> specialists = client.getAllSpecialist((String) request.getSession().getAttribute("Authorization"));
		log.info(specialists.get(specialists.size()-1).getSpecialistId());
		specialistDetail.setSpecialistId(specialists.get(specialists.size()-1).getSpecialistId()+1);
		return new ModelAndView("user-add-specialist-page");
	}
	
	@PostMapping("/addSpecialist")
	public ModelAndView addSpecialist2(HttpServletRequest request, @ModelAttribute("SpecialistDetail") SpecialistDetail specialistDetail)
	throws Exception{
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		client.addNewSpecialist(specialistDetail, (String) request.getSession().getAttribute("Authorization"));
		return new ModelAndView("redirect:/portal/specialists");
	}
	
	
	@ModelAttribute("ailmentList")
	public Set<String> populateAilmentEnumList() {
		return EnumSet.allOf(com.cts.portal.model.AilmentCategory.class).stream().map(a -> a.name())
				.collect(Collectors.toSet());

	}

	@ModelAttribute("packageList")
	public List<String> populatePackageList() {
		return Arrays.asList("Package 1", "Package 2");

	}
	
	@ModelAttribute("expertsise")
	public List<String> populateExpertsise() {
		return Arrays.asList("UROLOGY","ORTHOPAIDICS");
	}
	
	
}
