package com.resumejpa.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.resumejpa.entity.Member;
import com.resumejpa.entity.Resume;

import jakarta.transaction.Transactional;

public interface ResumeJpaRepository extends JpaRepository<Resume, Long> 
											, JpaSpecificationExecutor<Resume> {

	/**
	 * 会員IDをキーに検索する
	 * @param menber
	 * @param sort
	 * @return
	 */
	List<Resume> findByMember(Member menber, Sort sort);
	
	/**
	 * 会員IDをキーに削除する
	 * @param menber
	 * @return
	 */
	@Transactional
	long deleteByMember(Member menber);
}
