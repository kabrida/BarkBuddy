package syksy2024.backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import syksy2024.backend.model.Owner;
import syksy2024.backend.repository.OwnerRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    OwnerRepository oRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner curUser = oRepo.findByUsername(username);
        UserDetails user = new org.springframework.security.core.userdetails.User(username, curUser.getPasswordHash(), 
        		AuthorityUtils.createAuthorityList(curUser.getRole()));
        return user;
    }


}
