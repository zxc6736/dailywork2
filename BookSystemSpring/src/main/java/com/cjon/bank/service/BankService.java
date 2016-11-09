package com.cjon.bank.service;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.util.DBTemplate;

public class BankService {


	 private DBTemplate template;
	 private BankDAO dao;
	 
 public DBTemplate getTemplate() {
		return template;
	}

	public void setTemplate(DBTemplate template) {
		this.template = template;
	}

	public BankDAO getDao() {
		return dao;
	}

	public void setDao(BankDAO dao) {
		this.dao = dao;
	}

public BankDTO deposit(BankDTO dto){
  //입금에 대한 로직 처리
  //database처리를 위해서 DAO를 생성
	 dao.setTemplate(template);
  dto = dao.update(dto);
  
  if(dto.isResult()){
   template.commit();
  }else{
   template.rollback();
  }
  try {
   template.getCon().close();
  } catch (SQLException e) {
   e.printStackTrace();
  }
  return dto;
 }
 
 public BankDTO withdraw(BankDTO dto){
  //입금에 대한 로직 처리
  //database처리를 위해서 DAO를 생성
	 dao.setTemplate(template);
  dto = dao.withdraw(dto);
  
  if(dto.isResult()){
   template.commit();
  }else{
   template.rollback();
  }
  
  try {
   template.getCon().close();
  } catch (SQLException e) {
   e.printStackTrace();
  }
  
  return dto;
 }

 public ArrayList<BankDTO> transfer(BankDTO dto1, BankDTO dto2) {
	 
	 dao.setTemplate(template);
  dto1 = dao.withdraw(dto1);   //입금처리
  dto2 = dao.update(dto2); //출금처리
  
  if(dto1.isResult() && dto2.isResult()){
	  
	  template.commit();
  }else{
	  template.rollback();
  }
  
  
  ArrayList<BankDTO> list = new ArrayList<BankDTO>();
  list.add(dto1);
  list.add(dto2);
  
  return list;
  
 }
 
}