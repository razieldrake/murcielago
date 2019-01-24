package fr.offsec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.offsec.domain.Host;
import fr.offsec.domain.Log;
import fr.offsec.model.LogRepository;

@Service
public class LogService {
	
	
	@Autowired
	private LogRepository repo;
	
	public Iterable<Log> getAll() {
		return repo.findAll();
	}
	
	public Iterable<Log> findAllByID(int idLog){
		if (idLog==0) {
			return null;
		}
		Iterable<Log> log = repo.findAllByIdLog(idLog);
		if (log==null) {
			return null;
		}
		return log;
	}
	
	public Log save(Log logNew) {
		if (logNew == null) {
			return null;
		}
		
		return repo.save(logNew);
		
		
	}
	public void deleteByID(int idLog) {
		if (idLog != 0) {
			repo.delete(idLog);
		}
		
	}

}
