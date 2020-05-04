package com.gavinkim.model.user;

import com.gavinkim.type.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleService {
	private final RoleRepository repository;

	@Autowired
	public RoleService(RoleRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public Optional<Role> getRoleByType(RoleName name){
		return repository.findByName(name);
	}
}
