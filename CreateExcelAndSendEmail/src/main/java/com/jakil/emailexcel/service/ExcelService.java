package com.jakil.emailexcel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.jakil.emailexcel.excelutil.ExcelHelper;
import com.jakil.emailexcel.model.Tutorial;
import com.jakil.emailexcel.repository.TutorialRepository;

@Service
public class ExcelService {

	@Autowired
	TutorialRepository repo;

	@Autowired
	ExcelHelper excelHelper;

	//Fetches the data from database. and sends the data to create excel
	public void processExcelAndEmail() {

		List<Tutorial> tutorials = repo.findAll();
		excelHelper.generateExcelFile(tutorials);
	}

}
