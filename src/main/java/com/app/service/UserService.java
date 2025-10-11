package com.app.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.UserDAO;
import com.app.dto.UserResposeDTO;
import com.app.entity.User;
import com.app.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class UserService implements UserDetailsService {
	
	private @NonNull UserRepository userRepository;
	
	private @NonNull UserDAO userDAO;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUserName(username);
		if(!userOptional.isPresent()){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		User user = userOptional.get();
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}


	public Optional<User> getByPhoneNo(String phoneNo) {
		return userRepository.findByPhoneNo(phoneNo);
	}

	public Optional<User> findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public Optional<User> findByUserEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}

	public void saveOrUpdate(User object) {
		object.setForcePasswordChange(false);
		userRepository.save(object);
	}

	public  List<UserResposeDTO> getAll() {
		return userDAO.findAll();
	}

	public Optional<User> findByAddress(String address) {
		return userRepository.findByAddress(address);
	}

	public Object getById(UUID id) {
		return userDAO.findById(id);
	}

	public void update(User user) {
		userRepository.save(user);
	}

	public Optional<User> findByPassword(String encryptedPassword, String userName) {
		return userRepository.findByPasswordAndUserName(encryptedPassword,userName);
	}

	public void delete(UUID id) {
		userRepository.deleteById(id);
		
	}

	public User findByName(String userName) {
		return userDAO.findByUserName(userName);
	}

	public User findByUser(String userName, UUID id) {
		return userDAO.findByUser(userName,id);
	}

	public User findByUserPhone(String phoneNo, UUID id) {
		return userDAO.findByUserPhone(phoneNo,id);
	}

	public User findByPhone(String phoneNo) {
		return userDAO.findByPhone(phoneNo);
	}

	public User getUserByUserNameOrEmail(String userNameOrEmail) {
		return userDAO.getUserByUserNameOrEmail(userNameOrEmail);
	}

	public User findByUserEmail(String email, UUID id) {
		return userDAO.findByUserEmail(email,id);
	}

	 
}
