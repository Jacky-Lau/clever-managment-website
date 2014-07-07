package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.Role;

@Repository(value = "roleDao")
public class RoleDaoImpl extends GenericDaoImpl<Role, Integer> implements RoleDao{

}
