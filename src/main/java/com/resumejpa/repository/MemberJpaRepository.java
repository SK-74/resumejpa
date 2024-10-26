package com.resumejpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

	/**
	 * メアドが一致する件数を取得する(挙動はcountByEmailと同じ)
	 * QueryアノテーションによってSQLを直接書くことが出来る
	 * @param email メアド
	 * @return
	 */
	@Query(value = "SELECT COUNT(*) FROM members WHERE email = :email", nativeQuery = true)
	long queryCountByEmail(@Param(value = "email") String email);
}
