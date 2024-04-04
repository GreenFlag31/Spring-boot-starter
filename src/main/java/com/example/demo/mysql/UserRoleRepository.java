package com.example.demo.mysql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
  public UserRole findByUserId(Integer userId);

  public List<UserRole> findAllByRoleId(Integer roleId);
}