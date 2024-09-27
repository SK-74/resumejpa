package com.resumejpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.resumejpa.entity.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long>
											, JpaSpecificationExecutor<Member> {

	//findById,save,deleteByIdはJpaRepositoryの機能で自動生成される

	/**
	 * メアドが一致する件数を取得する
	 * @param email メアド
	 * @return
	 */
	long countByEmail(String email);
	
	/**
	 * メアドが一致する件数を取得する
	 * @param email メアド
	 * @param excludeId 検索時に除外する会員ID
	 * @return
	 */
	long countByEmailAndIdNot(String email, Long excludeId);
}
