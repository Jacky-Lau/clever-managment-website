package edu.zju.bme.clever.website.service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.bme.clever.website.dao.UserDao;
import edu.zju.bme.clever.website.entity.Role;
import edu.zju.bme.clever.website.entity.User;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "userDao")
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		
		User user = this.userDao.findUniqueByProperty("userName", userName);
		
		if (user == null) {
			throw new UsernameNotFoundException("User " + userName
					+ " does not exist.");
		}
		if(!user.isEnabled()){
			throw new UsernameNotFoundException("User " + userName
					+ " is not enabled.");
		}
		
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(); 
		
		for(Role role:user.getRoles()){
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}
}
